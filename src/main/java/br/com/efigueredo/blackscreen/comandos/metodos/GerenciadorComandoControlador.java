package br.com.efigueredo.blackscreen.comandos.metodos;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
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

	private Method obterComandoComParametrosCorrespondentes(List<Method> metodos,
			ExpressaoUsuarioParametrosValores expressaoUsuario) throws ComandoInvalidoException {
		List<Method> poolMetodos;
		poolMetodos = this.extrairMetodosComParametrosAnotadosECorretos(metodos);
		poolMetodos = this.extrairMetodosComQuantidadeDeParametrosCorrespondentes(poolMetodos, expressaoUsuario);
		poolMetodos = this.extrairMetodosComNomesDeParametosCorrespondentes(poolMetodos, expressaoUsuario);
		poolMetodos = this.extrairMetodosPelasClassesDeSeusParametros(poolMetodos, expressaoUsuario);
		return poolMetodos.get(0);
	}

	private List<Method> extrairMetodosPelasClassesDeSeusParametros(List<Method> metodos,
			ExpressaoUsuarioParametrosValores expressaoUsuario) throws ComandoInvalidoException {
		metodos = this.manipuladorMetodosComando
				.extrairMetodosFiltradosPelaCapacidadeDeValoresParaSeremInjetados(metodos, expressaoUsuario);
		this.verificadorListas.lancarErroSeEstiverVazia(metodos,
				"Os únicos comandos que possuem os parâmetros correpondetes não podem receber a quantidade de valores inserida.\nMétodos: "
						+ metodos);
		this.verificadorListas.lancarErroSeHouverMaisDeUmValor(metodos, "Comando iguais:\n" + metodos);
		return metodos;
	}

	private List<Method> extrairMetodosComNomesDeParametosCorrespondentes(List<Method> metodos,
			ExpressaoUsuarioParametrosValores expressaoUsuario) throws ComandoInvalidoException {
		Set<String> nomesParametros = expressaoUsuario.getParametrosValores().keySet();
		metodos = this.manipuladorMetodosComando
				.extrairMetodosOndeSeusNomesDeParametrosCorrespondemAListaDeNomes(nomesParametros, metodos);
		this.verificadorListas.lancarErroSeEstiverVazia(metodos,
				"Os comandos correspondentes não possuem os parâmetros inseridos.\nParâmetros: " + nomesParametros);
		return metodos;
	}

	private List<Method> extrairMetodosComQuantidadeDeParametrosCorrespondentes(List<Method> metodos,
			ExpressaoUsuarioParametrosValores expressaoUsuario) {
		return this.manipuladorMetodos.extrairMetodosPelaQuantidadeDeParametros(metodos,
				expressaoUsuario.getParametrosValores().size());
	}

	private List<Method> extrairMetodosComParametrosAnotadosECorretos(List<Method> metodos)
			throws ComandoInvalidoException {
		metodos = this.manipuladorMetodos.extrairMetodosOndeTodosOsParametrosEstaoAnotadosCom(Parametro.class, metodos);
		this.verificadorListas.lancarErroSeEstiverVazia(metodos,
				"Não existe comando com parâmetros na classe controladora.");
		return metodos;
	}

}
