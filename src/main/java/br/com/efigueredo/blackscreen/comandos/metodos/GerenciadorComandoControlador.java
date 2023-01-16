package br.com.efigueredo.blackscreen.comandos.metodos;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.efigueredo.blackscreen.anotacoes.Comando;
import br.com.efigueredo.blackscreen.anotacoes.Parametro;
import br.com.efigueredo.blackscreen.anotacoes.Teste;
import br.com.efigueredo.blackscreen.comandos.metodos.exception.ComandoInvalidoException;
import br.com.efigueredo.blackscreen.comandos.metodos.exception.NomeComandoInexistenteException;
import br.com.efigueredo.blackscreen.comandos.metodos.exception.ParametroDeComandoInexistenteException;
import br.com.efigueredo.blackscreen.comandos.metodos.exception.SemComandoCorrespondenteException;
import br.com.efigueredo.blackscreen.comandos.metodos.exception.SolicitacaoDeMetodoComandoInexistenteException;
import br.com.efigueredo.blackscreen.comandos.metodos.exception.ValoresIncoerentesComOsComandosExistentesException;
import br.com.efigueredo.blackscreen.sistema.AplicacaoBlackScreen;
import br.com.efigueredo.blackscreen.userinput.expressao.ExpressaoUsuario;
import br.com.efigueredo.blackscreen.userinput.expressao.ExpressaoUsuarioParametrosValores;
import br.com.efigueredo.blackscreen.userinput.expressao.ExpressaoUsuarioValores;

public class GerenciadorComandoControlador {

	private Class<?> controlador;

	private ManipuladorMetodosComandos manipuladorMetodosComando;

	public GerenciadorComandoControlador() {
		this.controlador = AplicacaoBlackScreen.getControladorAtual();
		this.manipuladorMetodosComando = new ManipuladorMetodosComandos();
	}

	public Method getMetodoComando(ExpressaoUsuario expressaoUsuario)
			throws NomeComandoInexistenteException, ParametroDeComandoInexistenteException,
			ValoresIncoerentesComOsComandosExistentesException, SolicitacaoDeMetodoComandoInexistenteException,
			ComandoInvalidoException, SemComandoCorrespondenteException {
		List<Method> metodos = this.getMetodosAnotadosComParametroNomeCorrespondente(expressaoUsuario.getComando());
		if (expressaoUsuario instanceof ExpressaoUsuarioValores) {
			return obterMetodoComandoSemParametrosDeComandoAdequado(metodos,
					(ExpressaoUsuarioValores) expressaoUsuario);
		} else {
			return obterComandoComParametrosCorrespondentes(metodos,
					(ExpressaoUsuarioParametrosValores) expressaoUsuario);
		}
	}

	private static Method obterMetodoComandoSemParametrosDeComandoAdequado(List<Method> metodos,
			ExpressaoUsuarioValores expressao) throws ComandoInvalidoException, SemComandoCorrespondenteException {
		List<Method> comandosComUnicoAtributoList = obterComandoComUnicoAtributoListString(metodos);
		if (comandosComUnicoAtributoList.size() == 0) {
			List<Method> metodosComQuantidadeDeParametrosCorrespondente = obterMetodosPelaQuantidadeDeParametros(
					metodos, expressao.getValores().size());
			if (metodosComQuantidadeDeParametrosCorrespondente.size() > 1) {
				throw new ComandoInvalidoException("Para o comando " + expressao.getComando()
						+ " existe mais de um método que receba a quantidade de parâmetros inserida. Mantenha somente um.");
			}

			if (metodosComQuantidadeDeParametrosCorrespondente.size() == 0) {
				throw new SemComandoCorrespondenteException("Para o comando " + expressao.getComando()
						+ " não existe método que possa receber os parâmetros inseridos.");
			}
			return metodosComQuantidadeDeParametrosCorrespondente.get(0);
		}
		if (comandosComUnicoAtributoList.size() > 1) {
			throw new ComandoInvalidoException("Existe mais de um comando com o mesmo nome que receba um List<String> "
					+ "como único parâmetro. Só é possível que exista um comando com essa característica.");
		}
		return comandosComUnicoAtributoList.get(0);
	}

	private static List<Method> obterMetodosPelaQuantidadeDeParametros(List<Method> metodos, int quantidade) {
		return metodos.stream().filter(m -> m.getParameterCount() == quantidade).toList();
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

	private static Method obterComandoComParametrosCorrespondentes(List<Method> metodos,
			ExpressaoUsuarioParametrosValores expressaoUsuario) throws ComandoInvalidoException {
		List<Method> metodosComParametrosDeComando = obterComandosComTodosOsParametrosAnotados(metodos);
		if (metodosComParametrosDeComando.size() == 0) {
			throw new ComandoInvalidoException("Não existe comando com parâmetros na classe controladora.");
		}
		// mesma quantidade de parametros de metodos que quantidade de parametros de expressao
		List<Method> metodosComQuantidadeDeParametrosCorrepondentes = filtrarPelaQuantidadeDeParametrosInseridos(
				metodosComParametrosDeComando, expressaoUsuario.getParametrosValores().size());
		
		// Parametros de metodos do mesmo nome que parametros de expressao
		List<Method> metodosComParametrosCorrespondentes = obterMetodosComNomesDeParametrosIguaisAExpressao(metodosComQuantidadeDeParametrosCorrepondentes, expressaoUsuario);		
		if(metodosComParametrosCorrespondentes.size() == 0) {
			throw new ComandoInvalidoException("Os comandos correspondentes não possuem os parâmetros inseridos.\nParâmetros: " + expressaoUsuario.getParametrosValores().keySet());
		}
		
		// Valores que nao sao unicos devem ir para parametros que recebam listas
		List<Method> metodosFinais = obterMetodosFiltradosPelaCapacidadeDeValoresParaSeremInjetados(metodosComParametrosCorrespondentes, expressaoUsuario);
		if(metodosFinais.size() == 0) {
			throw new ComandoInvalidoException("Os únicos comandos que possuem os parâmetros correpondetes não podem receber a quantidade de valores inserida.\nMétodos: " + metodosComParametrosCorrespondentes);
		}
		
		if(metodosFinais.size() > 1) {
			throw new ComandoInvalidoException("Comando iguais:\n" + metodosComParametrosCorrespondentes);
		}
		return metodosFinais.get(0);
	}

	private static List<Method> obterMetodosFiltradosPelaCapacidadeDeValoresParaSeremInjetados(
			List<Method> metodos, ExpressaoUsuarioParametrosValores expressaoUsuario) {
		
		// metodos com nomes de parametros correpondentes, mesma quantidade, so falta definir se o método pode aceitar varios valores
		
		Map<String, Class<?>> mapaParametroClasseNecessaria = new HashMap<String, Class<?>>();
		
		Map<String, List<String>> parametrosValores = expressaoUsuario.getParametrosValores();
		List<String> parametros = parametrosValores.keySet().stream().toList();
		parametros.forEach(parametro -> {
			List<String> valoresDoParametro = parametrosValores.get(parametro);
			if(valoresDoParametro.size() == 1) {
				mapaParametroClasseNecessaria.put(parametro, String.class);
			} else {
				mapaParametroClasseNecessaria.put(parametro, List.class);
			}
		});
		
		List<Method> metodosSelecionados = new ArrayList<Method>();
		for(Method metodo : metodos) {
			List<Parameter> parametrosDoMetodo = Arrays.asList(metodo.getParameters());
			for (int i = 0; i < parametrosDoMetodo.size(); i++) {
				Parameter parametro = parametrosDoMetodo.get(i);
				String nomeParametroMetodo = parametro.getAnnotation(Parametro.class).nome();
				Class<?> classeQueOParametroDoMetodoDeveReceber = mapaParametroClasseNecessaria.get(nomeParametroMetodo);
				boolean parametroRecebeTipoCorreto = parametro.getType().equals(classeQueOParametroDoMetodoDeveReceber);
				if(parametroRecebeTipoCorreto) {
					if(i == metodos.size() - 1) {
						metodosSelecionados.add(metodo);
					}
					continue;
				}
				break;
			}
		}
		return metodosSelecionados;
	}

	private static List<Method> obterMetodosComNomesDeParametrosIguaisAExpressao(List<Method> metodos,
			ExpressaoUsuarioParametrosValores expressaoUsuario) {
		List<Method> metodosSelecionados = new ArrayList<Method>();
		Set<String> parametros = expressaoUsuario.getParametrosValores().keySet();
		for(Method metodo : metodos) {
			for (int i = 0; i < metodos.size(); i++) {
				Parameter[] parametrosMetodo = metodo.getParameters();
				Parameter parametro = parametrosMetodo[i];
				String nomeParametro = parametro.getAnnotation(Parametro.class).nome();
				if(parametros.contains(nomeParametro)) {
					if(i == metodos.size() - 1) {
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

	List<Method> getMetodosAnotadosComParametroNomeCorrespondente(String nomeComando)
			throws NomeComandoInexistenteException {
		ManipuladorMetodos manipuladorMetodos = new ManipuladorMetodos(AplicacaoBlackScreen.getControladorAtual());
		List<Method> metodosControlador = Arrays.asList(this.controlador.getDeclaredMethods());
		metodosControlador = manipuladorMetodos.getMetodosAnotados(Comando.class);
		metodosControlador = this.manipuladorMetodosComando.getMetodosAnotadosPorNome(metodosControlador, nomeComando);
		if (metodosControlador.isEmpty()) {
			throw new NomeComandoInexistenteException("O comando " + nomeComando
					+ " não está atribuído a anotações do tipo @Comando nos métodos da classe controladora atual ["
					+ this.controlador.getName() + "].");
		}
		return metodosControlador;
	}

}
