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
	private ManipuladorDeMetodos manipuladorMetodos;
	private VerificadorListaMetodos verificadorListas;

	public GerenciadorComandoControlador() {
		this.manipuladorMetodos = new ManipuladorDeMetodos();
		this.manipuladorMetodosComando = new ManipuladorMetodosComandos();
		this.verificadorListas = new VerificadorListaMetodos();
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

	private Method obterMetodoComandoSemParametrosDeComandoAdequado(Class<?> classeControladora, List<Method> metodos,
			ExpressaoUsuarioValores expressao) throws ComandoInvalidoException {
		List<Method> poolMetodos;
		int quantidadeValores = expressao.getValores().size();
		poolMetodos = this.manipuladorMetodos.extrairMetodosPelaQuantidadeDeParametros(metodos, quantidadeValores);
		poolMetodos = this.manipuladorMetodos.extrairMetodosQueNaoRecebemAClasseNosParametros(poolMetodos, List.class);
		if (poolMetodos.size() == 0) {
			poolMetodos = this.manipuladorMetodos.extrairMetodosQueSoRecebamUmParametro(metodos);
			poolMetodos = manipuladorMetodos.extrairMetodosTodosParametrosRecebemMesmaClasse(poolMetodos, List.class);
			this.executarVerificacoesPoolMetodosSoValores(poolMetodos, expressao, classeControladora);
			return poolMetodos.get(0);
		}
		this.verificadorListas.lancarErroSeHouverMaisDeUmValor(poolMetodos, "Para o comando " + expressao.getComando()
				+ " existe mais de um método que receba a quantidade de parâmetros inserida. Mantenha somente um.\nMétodos: "
				+ poolMetodos);
		return poolMetodos.get(0);
	}

	private void executarVerificacoesPoolMetodosSoValores(List<Method> poolMetodos, ExpressaoUsuarioValores expressao,
			Class<?> classeControladora) throws ComandoInvalidoException {
		this.verificadorListas.lancarErroSeEstiverVazia(poolMetodos,
				"Não existe comando que possa receber " + expressao.getValores().size()
						+ " valor/es e nem comando que possa receber uma lista de valores na classe controladora ["
						+ classeControladora + "].");
		this.verificadorListas.lancarErroSeHouverMaisDeUmValor(poolMetodos,
				"Existe mais de um comando com o nome " + expressao.getComando() + " que receba um List<String> "
						+ "como único parâmetro. Só é possível que exista um comando com essa característica.");
	}

	// parametros

	private Method obterComandoComParametrosCorrespondentes(List<Method> metodos,
			ExpressaoUsuarioParametrosValores expressaoUsuario) throws ComandoInvalidoException {
		List<Method> poolMetodos;
		poolMetodos = this.manipuladorMetodos.extrairMetodosOndeTodosOsParametrosEstaoAnotadosCom(Parametro.class,
				metodos);
		this.verificadorListas.lancarErroSeEstiverVazia(poolMetodos,
				"Não existe comando com parâmetros na classe controladora.");
		poolMetodos = this.manipuladorMetodos.extrairMetodosPelaQuantidadeDeParametros(poolMetodos,
				expressaoUsuario.getParametrosValores().size());
		Set<String> nomesParametros = expressaoUsuario.getParametrosValores().keySet();
		poolMetodos = this.manipuladorMetodosComando
				.extrairMetodosOndeSeusNomesDeParametrosCorrespondemAListaDeNomes(nomesParametros, poolMetodos);
		this.verificadorListas.lancarErroSeEstiverVazia(poolMetodos,
				"Os comandos correspondentes não possuem os parâmetros inseridos.\nParâmetros: " + nomesParametros);


		// Valores que nao sao unicos devem ir para parametros que recebam listas
		List<Method> metodosFinais = obterMetodosFiltradosPelaCapacidadeDeValoresParaSeremInjetados(poolMetodos,
				expressaoUsuario);
		if (metodosFinais.size() == 0) {
			throw new ComandoInvalidoException(
					"Os únicos comandos que possuem os parâmetros correpondetes não podem receber a quantidade de valores inserida.\nMétodos: "
							+ poolMetodos);
		}

		if (metodosFinais.size() > 1) {
			throw new ComandoInvalidoException("Comando iguais:\n" + poolMetodos);
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

	List<Method> getMetodosAnotadosComParametroNomeCorrespondente(String nomeComando, Class<?> classeControladoraAtual)
			throws ControladorException, ComandoInvalidoException {
		ManipuladorMetodosDaClasse manipuladorMetodos = new ManipuladorMetodosDaClasse(classeControladoraAtual);
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
