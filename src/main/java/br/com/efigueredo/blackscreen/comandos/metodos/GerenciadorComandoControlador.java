package br.com.efigueredo.blackscreen.comandos.metodos;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import br.com.efigueredo.blackscreen.anotacoes.Comando;
import br.com.efigueredo.blackscreen.anotacoes.Parametro;
import br.com.efigueredo.blackscreen.anotacoes.Teste;
import br.com.efigueredo.blackscreen.comandos.metodos.exception.ComandoInvalidoException;
import br.com.efigueredo.blackscreen.comandos.metodos.exception.ControladorException;
import br.com.efigueredo.blackscreen.comandos.metodos.exception.NomeComandoInexistenteException;
import br.com.efigueredo.blackscreen.comandos.metodos.exception.ParametroDeComandoInexistenteException;
import br.com.efigueredo.blackscreen.comandos.metodos.exception.SemComandoCorrespondenteException;
import br.com.efigueredo.blackscreen.comandos.metodos.exception.SolicitacaoDeMetodoComandoInexistenteException;
import br.com.efigueredo.blackscreen.comandos.metodos.exception.ValoresIncoerentesComOsComandosExistentesException;
import br.com.efigueredo.blackscreen.userinput.expressao.ExpressaoUsuario;
import br.com.efigueredo.blackscreen.userinput.expressao.ExpressaoUsuarioParametrosValores;
import br.com.efigueredo.blackscreen.userinput.expressao.ExpressaoUsuarioValores;

public class GerenciadorComandoControlador {

	private ManipuladorMetodosComandos manipuladorMetodosComando;

	public GerenciadorComandoControlador() {
		this.manipuladorMetodosComando = new ManipuladorMetodosComandos();
	}

	public Method getMetodoComando(ExpressaoUsuario expressaoUsuario, Class<?> classeControladoraAtual)
			throws NomeComandoInexistenteException, ParametroDeComandoInexistenteException,
			ValoresIncoerentesComOsComandosExistentesException, SolicitacaoDeMetodoComandoInexistenteException,
			ComandoInvalidoException, SemComandoCorrespondenteException, ControladorException {
		List<Method> metodos = this.getMetodosAnotadosComParametroNomeCorrespondente(expressaoUsuario.getComando(),
				classeControladoraAtual);
		if (expressaoUsuario instanceof ExpressaoUsuarioValores) {
			return obterMetodoComandoSemParametrosDeComandoAdequado(classeControladoraAtual, metodos,
					(ExpressaoUsuarioValores) expressaoUsuario);
		} else {
			return obterComandoComParametrosCorrespondentes(metodos,
					(ExpressaoUsuarioParametrosValores) expressaoUsuario);
		}
	}

	private static Method obterMetodoComandoSemParametrosDeComandoAdequado(Class<?> classeControladora,
			List<Method> metodos, ExpressaoUsuarioValores expressao)
			throws ComandoInvalidoException, SemComandoCorrespondenteException {

		List<Method> metodosQueCorrespondemAQuantidadeDeValoresENaoSaoRecebemList = obterComandosComQuantidadeDeValoresCorrespondenteENaoRecebemList(
				metodos, expressao.getValores().size());
		if (metodosQueCorrespondemAQuantidadeDeValoresENaoSaoRecebemList.size() == 0) {

			// procurar metodo que receba lista de valores
			List<Method> comandosComUnicoAtributoList = obterComandoComUnicoAtributoListString(metodos);
			if (comandosComUnicoAtributoList.size() == 0) {
				throw new ComandoInvalidoException("Não existe comando que possa receber "
						+ expressao.getValores().size()
						+ " valor/es e nem comando que possa receber uma lista de valores na classe controladora ["
						+ classeControladora + "].");
			}
			if (comandosComUnicoAtributoList.size() > 1) {
				throw new ComandoInvalidoException("Existe mais de um comando com o nome " + expressao.getComando()
						+ " que receba um List<String> "
						+ "como único parâmetro. Só é possível que exista um comando com essa característica.");
			}
			return comandosComUnicoAtributoList.get(0);

		}

		if (metodosQueCorrespondemAQuantidadeDeValoresENaoSaoRecebemList.size() > 1) {
			throw new ComandoInvalidoException("Para o comando " + expressao.getComando()
					+ " existe mais de um método que receba a quantidade de parâmetros inserida. Mantenha somente um.\nMétodos: "
					+ metodosQueCorrespondemAQuantidadeDeValoresENaoSaoRecebemList);
		}
		return metodosQueCorrespondemAQuantidadeDeValoresENaoSaoRecebemList.get(0);
	}

	private static List<Method> obterComandosComQuantidadeDeValoresCorrespondenteENaoRecebemList(List<Method> metodos,
			int quantidadeDeValores) throws ComandoInvalidoException {
		List<Method> metodosSelecionados = new ArrayList<Method>();
		for (Method metodo : metodos) {
			boolean recebeSomenteUmaParametro = metodo.getParameterCount() == 1;
			boolean mesmaQuantidadeDeValores = metodo.getParameterCount() == quantidadeDeValores;
			if (recebeSomenteUmaParametro && mesmaQuantidadeDeValores) {
				Parameter unicoParametro = metodo.getParameters()[0];
				boolean unicoParametroEList = unicoParametro.getType().equals(List.class);
				if (unicoParametroEList) {
					continue;
				}
			} else if (!recebeSomenteUmaParametro && mesmaQuantidadeDeValores) {
				// verificar se contem algum parametro lista
				boolean algumParametroELista = Stream.of(metodo.getParameters())
						.anyMatch(parametro -> parametro.getType().equals(List.class));
				if (algumParametroELista) {
					throw new ComandoInvalidoException("O comando [" + metodo
							+ "] não pode receber duas listas de valores sem estar anotado a um parâmetro de comando.");
				}
			}
			if (mesmaQuantidadeDeValores) {
				metodosSelecionados.add(metodo);
			}
		}
		return metodosSelecionados;
	}

	private static List<Method> obterComandoComUnicoAtributoListString(List<Method> metodos) {
		return metodos.stream().filter(m -> m.getParameterCount() == 1 && verificarSeUnicoParametroEList(m)).toList();
	}

	private static boolean verificarSeUnicoParametroEList(Method metodo) {
		Parameter[] parametros = metodo.getParameters();
		if (parametros.length == 1) {
			Parameter parametro = parametros[0];
			return parametro.getType().equals(List.class);
		}
		return false;
	}

	public static void main(String[] args)
			throws SecurityException, ComandoInvalidoException, SemComandoCorrespondenteException {
		Map<String, List<String>> parametrosValores = new HashMap<>();
		parametrosValores.put("--param1", Arrays.asList("valor1"));
		parametrosValores.put("--param2", Arrays.asList("valor1", "valor2"));
		parametrosValores.put("--param3", Arrays.asList("valor1", "valor3"));

		ExpressaoUsuarioParametrosValores expressao = new ExpressaoUsuarioParametrosValores("adicionar",
				parametrosValores);

		Method metodo = obterComandoComParametrosCorrespondentes(Arrays.asList(Teste.class.getDeclaredMethods()),
				expressao);

		System.out.println(metodo);

	}

	// parametros
	
	private static Method obterComandoComParametrosCorrespondentes(List<Method> metodos,
			ExpressaoUsuarioParametrosValores expressaoUsuario) throws ComandoInvalidoException {
		List<Method> metodosComParametrosDeComando = obterComandosComTodosOsParametrosAnotados(metodos);
		if (metodosComParametrosDeComando.size() == 0) {
			throw new ComandoInvalidoException("Não existe comando com parâmetros na classe controladora.");
		}
		// mesma quantidade de parametros de metodos que quantidade de parametros de
		// expressao
		List<Method> metodosComQuantidadeDeParametrosCorrepondentes = filtrarPelaQuantidadeDeParametrosInseridos(
				metodosComParametrosDeComando, expressaoUsuario.getParametrosValores().size());

		// Parametros de metodos do mesmo nome que parametros de expressao
		List<Method> metodosComParametrosCorrespondentes = obterMetodosComNomesDeParametrosIguaisAExpressao(
				metodosComQuantidadeDeParametrosCorrepondentes, expressaoUsuario);
		
		if (metodosComParametrosCorrespondentes.size() == 0) {
			throw new ComandoInvalidoException(
					"Os comandos correspondentes não possuem os parâmetros inseridos.\nParâmetros: "
							+ expressaoUsuario.getParametrosValores().keySet());
		}

		// Valores que nao sao unicos devem ir para parametros que recebam listas
		List<Method> metodosFinais = obterMetodosFiltradosPelaCapacidadeDeValoresParaSeremInjetados(
				metodosComParametrosCorrespondentes, expressaoUsuario);
		if (metodosFinais.size() == 0) {
			throw new ComandoInvalidoException(
					"Os únicos comandos que possuem os parâmetros correpondetes não podem receber a quantidade de valores inserida.\nMétodos: "
							+ metodosComParametrosCorrespondentes);
		}

		if (metodosFinais.size() > 1) {
			throw new ComandoInvalidoException("Comando iguais:\n" + metodosComParametrosCorrespondentes);
		}
		return metodosFinais.get(0);
	}

	private static List<Method> obterMetodosFiltradosPelaCapacidadeDeValoresParaSeremInjetados(List<Method> metodos,
			ExpressaoUsuarioParametrosValores expressaoUsuario) {

		// metodos com nomes de parametros correpondentes, mesma quantidade, so falta
		// definir se o método pode aceitar varios valores

		Map<String, Class<?>> mapaParametroClasseNecessaria = new HashMap<String, Class<?>>();

		Map<String, List<String>> parametrosValores = expressaoUsuario.getParametrosValores();
		List<String> parametros = parametrosValores.keySet().stream().toList();
		parametros.forEach(parametro -> {
			List<String> valoresDoParametro = parametrosValores.get(parametro);
			if (valoresDoParametro.size() == 1) {
				mapaParametroClasseNecessaria.put(parametro, String.class);
			} else {
				mapaParametroClasseNecessaria.put(parametro, List.class);
			}
		});
		
		List<Method> metodosSelecionados = new ArrayList<Method>();
		for (Method metodo : metodos) {
			Parameter[] parametrosDoMetodo = metodo.getParameters();
			for (int i = 0; i < parametrosDoMetodo.length; i++) {
				Parameter parametro = parametrosDoMetodo[i];
				String nomeParametroMetodo = parametro.getAnnotation(Parametro.class).value();
				Class<?> classeQueOParametroDoMetodoDeveReceber = mapaParametroClasseNecessaria
						.get(nomeParametroMetodo);
				boolean parametroRecebeTipoCorreto = parametro.getType().equals(classeQueOParametroDoMetodoDeveReceber);
				if (parametroRecebeTipoCorreto) {
					if (i == parametrosDoMetodo.length - 1) {
						metodosSelecionados.add(metodo);
					}
					continue;
				}
				break;
			}
		}
		return metodosSelecionados;
	}

	// here
	private static List<Method> obterMetodosComNomesDeParametrosIguaisAExpressao(List<Method> metodos,
			ExpressaoUsuarioParametrosValores expressaoUsuario) {
		List<Method> metodosSelecionados = new ArrayList<Method>();
		Set<String> parametros = expressaoUsuario.getParametrosValores().keySet();
		for (Method metodo : metodos) {
			Parameter[] parametrosMetodo = metodo.getParameters();
			for (int i = 0; i < parametrosMetodo.length; i++) {
				Parameter parametro = parametrosMetodo[i];
				String nomeParametro = parametro.getAnnotation(Parametro.class).value();
				if (parametros.contains(nomeParametro)) {
					if (i == parametrosMetodo.length - 1) {
						metodosSelecionados.add(metodo);
					}
					continue;
				}
				break;
			}
		}
		return metodosSelecionados;
	}

	private static List<Method> filtrarPelaQuantidadeDeParametrosInseridos(List<Method> metodosComParametrosDeComando,
			int quantidadeDeParametros) {
		// metodos com todos os parametros anotados
		return metodosComParametrosDeComando.stream().filter(m -> m.getParameterCount() == quantidadeDeParametros)
				.toList();
	}

	private static List<Method> obterComandosComTodosOsParametrosAnotados(List<Method> metodos)
			throws ComandoInvalidoException {
		List<Method> metodosCorrespondentesAnotados = new ArrayList<Method>();
		for (Method metodo : metodos) {
			List<Parameter> parametrosDoMetodo = Arrays.asList(metodo.getParameters());
			int quantiadeParametrosAnotados = 0;
			for (Parameter parametro : parametrosDoMetodo) {
				if (parametro.isAnnotationPresent(Parametro.class)) {
					quantiadeParametrosAnotados++;
				}
			}
			if (quantiadeParametrosAnotados != 0 && quantiadeParametrosAnotados != parametrosDoMetodo.size()) {
				throw new ComandoInvalidoException(
						"O comando [" + metodo + "] não possui todos os parâmetros anotados com @Parametro.");
			}
			if (quantiadeParametrosAnotados == parametrosDoMetodo.size()) {
				metodosCorrespondentesAnotados.add(metodo);
			}
		}
		return metodosCorrespondentesAnotados;
	}

	List<Method> getMetodosAnotadosComParametroNomeCorrespondente(String nomeComando, Class<?> classeControladoraAtual)
			throws ControladorException, ComandoInvalidoException {
		ManipuladorMetodos manipuladorMetodos = new ManipuladorMetodos(classeControladoraAtual);
		List<Method> metodosControlador = Arrays.asList(classeControladoraAtual.getDeclaredMethods());
		if (metodosControlador.size() == 0) {
			throw new ControladorException(
					"O controlador atual + [" + classeControladoraAtual + "] não possui nenhum método.");
		}
		List<Method> metodosControladorAnotados = manipuladorMetodos.getMetodosAnotados(Comando.class);
		if (metodosControladorAnotados == null) {
			throw new ControladorException("O controlador atual + [" + classeControladoraAtual
					+ "] não possui nenhum método anotado com @Comando");
		}
		metodosControlador = this.manipuladorMetodosComando.getMetodosAnotadosPorNome(metodosControladorAnotados,
				nomeComando);
		if (metodosControlador.isEmpty()) {
			throw new ComandoInvalidoException("A classe controladora atual " + classeControladoraAtual
					+ "] não possui nenhum comando de nome " + nomeComando);
		}
		return metodosControlador;
	}

}
