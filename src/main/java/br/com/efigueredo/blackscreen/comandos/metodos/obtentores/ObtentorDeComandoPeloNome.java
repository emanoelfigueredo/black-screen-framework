package br.com.efigueredo.blackscreen.comandos.metodos.obtentores;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import br.com.efigueredo.blackscreen.anotacoes.Comando;
import br.com.efigueredo.blackscreen.comandos.metodos.exception.ComandoInvalidoException;
import br.com.efigueredo.blackscreen.comandos.metodos.exception.ControladorException;
import br.com.efigueredo.blackscreen.comandos.metodos.manipuladores.ManipuladorMetodosComandos;
import br.com.efigueredo.blackscreen.comandos.metodos.manipuladores.ManipuladorMetodosDaClasse;

public class ObtentorDeComandoPeloNome {

	private ManipuladorMetodosComandos manipuladorMetodosComando;

	public ObtentorDeComandoPeloNome() {
		this.manipuladorMetodosComando = new ManipuladorMetodosComandos();
	}

	public List<Method> getMetodosAnotadosComParametroNomeCorrespondente(String nomeComando, Class<?> classeControladoraAtual)
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
