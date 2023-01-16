package br.com.efigueredo.blackscreen.comandos.metodos.obtentores;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import br.com.efigueredo.blackscreen.anotacoes.Comando;
import br.com.efigueredo.blackscreen.comandos.metodos.manipuladores.ManipuladorMetodosComandos;
import br.com.efigueredo.blackscreen.comandos.metodos.manipuladores.ManipuladorMetodosDaClasse;
import br.com.efigueredo.blackscreen.comandos.metodos.verificadores.VerificadorListaMetodos;
import br.com.efigueredo.blackscreen.sistema.exception.BlackStreenException;

public class ObtentorDeComandoPeloNome {

	private ManipuladorMetodosComandos manipuladorMetodosComando;
	private VerificadorListaMetodos verificadorListasMetodos;

	public ObtentorDeComandoPeloNome() {
		this.manipuladorMetodosComando = new ManipuladorMetodosComandos();
		this.verificadorListasMetodos = new VerificadorListaMetodos();
	}

	public List<Method> getMetodosAnotadosComParametroNomeCorrespondente(String nomeComando,
			Class<?> classeControladoraAtual) throws BlackStreenException {
		ManipuladorMetodosDaClasse manipuladorMetodos = new ManipuladorMetodosDaClasse(classeControladoraAtual);
		List<Method> metodosControlador = Arrays.asList(classeControladoraAtual.getDeclaredMethods());
		this.verificarSeExisteMetodosNoControlador(metodosControlador, classeControladoraAtual);
		metodosControlador = manipuladorMetodos.getMetodosAnotados(Comando.class);
		this.verificarSeListaDeMetodosAnotadosValeNull(metodosControlador, classeControladoraAtual);
		metodosControlador = this.manipuladorMetodosComando.getMetodosAnotadosPorNome(metodosControlador, nomeComando);
		this.verificarSeListaDeMetodosSelecionadosEstaVazia(metodosControlador, classeControladoraAtual, nomeComando);
		return metodosControlador;
	}

	private void verificarSeExisteMetodosNoControlador(List<Method> metodos, Class<?> controlador)
			throws BlackStreenException {
		this.verificadorListasMetodos.lancarErroControladorSeEstiverVazia(metodos,
				"O controlador atual + [" + controlador + "] não possui nenhum método.");
	}

	private void verificarSeListaDeMetodosAnotadosValeNull(List<Method> metodos, Class<?> controlador)
			throws BlackStreenException {
		this.verificadorListasMetodos.lancarErroControladorSeValerNull(metodos,
				"O controlador atual + [" + controlador + "] não possui nenhum método anotado com @Comando");
	}

	private void verificarSeListaDeMetodosSelecionadosEstaVazia(List<Method> metodos, Class<?> controlador,
			String nomeComando) throws BlackStreenException {
		this.verificadorListasMetodos.lancarErroControladorSeEstiverVazia(metodos,
				"A classe controladora atual " + controlador + "] não possui nenhum comando de nome " + nomeComando);
	}

}
