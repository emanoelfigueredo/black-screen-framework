package br.com.efigueredo.blackscreen.comandos.metodos;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import br.com.efigueredo.blackscreen.anotacoes.Comando;

/**
 * <h4>A classe {@code ManipuladorMetodosComandos} é responsável por manipular
 * métodos anotados com {@linkplain @Comando}.</h4>
 */
public class ManipuladorMetodosComandos {

	private ManipuladorDeParametrosComandos manipuladorParametros;

	public ManipuladorMetodosComandos() {
		this.manipuladorParametros = new ManipuladorDeParametrosComandos();
	}

	public List<Method> getMetodosAnotadosPorNome(List<Method> metodos, String nomeComando) {
		return metodos.stream().filter(m -> m.getAnnotation(Comando.class).nome().equals(nomeComando)).toList();
	}

	public List<Method> extrairMetodosOndeSeusNomesDeParametrosCorrespondemAListaDeNomes(Set<String> set,
			List<Method> metodos) {
		return metodos.stream().filter(
				metodo -> this.manipuladorParametros.nomesDeParametrosCorrespondemAListaDeNomesInserida(set,
						Arrays.asList(metodo.getParameters())))
				.toList();
	}

}
