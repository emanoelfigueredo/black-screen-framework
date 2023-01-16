package br.com.efigueredo.blackscreen.comandos.metodos;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import br.com.efigueredo.blackscreen.anotacoes.Comando;

/**
 * <h4>A classe {@code ManipuladorMetodosComandos} é responsável por manipular
 * métodos anotados com {@linkplain @Comando}.</h4>
 */
public class ManipuladorMetodosComandos {

	/**
	 * Obtenha todos os métodos anotados com @Comando no qual seu atributo Array de
	 * parametros conhenha o parametro de comando inserido.<br>
	 * <br>
	 * 
	 * O funcinamento consiste basicamente em iterar por todos os métodos, obtendo o
	 * array de parametros e verificando se o parametro passado é igual a algum
	 * deles.<br>
	 * <br>
	 *
	 * @param metodos   A lista de objetos do tipo {@linkplain Method} que serão
	 *                  manipulados.
	 * @param parametro A String que será procurada nas anotações dos métodos.
	 * @return Retorna uma lista de objetos {@linkplain Method} caso hajam
	 *         correspondentes ao parametro.<br>
	 *         Caso não hava, será retornado uma lista vazia.
	 */
	public List<Method> getMetodosAnotadosPorParametro(List<Method> metodos, String parametro) {
		return metodos.stream()
				.filter(m -> Arrays.asList(m.getAnnotation(Comando.class).parametros()).contains(parametro)).toList();
	}

	/**
	 * Dado uma lista de métodos anotados com @Comando, obtenha todos que não
	 * possuam valores no atributo parametros da anotação.
	 * 
	 * Seu funcionamento consiste em iterar por todos os métodos, extraindo os que
	 * não possuam valores no atributo parametros da anotação.
	 * 
	 * @param metodos Lista de métodos para serem manipulados.
	 * @return Uma lista de métodos correspondentes.
	 */
	public List<Method> getMetodosAnotadosSemParametroDeComando(List<Method> metodos) {
		return metodos.stream().filter(m -> Arrays.asList(m.getAnnotation(Comando.class).parametros()).isEmpty())
				.toList();
	}

	/**
	 * Dado uma lista de métodos anotados com @Comando, obtenha todos que possuam o
	 * atributo da anotação nome igual ao inserido.
	 * 
	 * O funcionamento consiste em iterar por todos os métodos, e filtrar aqueles no
	 * qual o valor nome da sua anotação corresponde ao nomeComando inserido.
	 * 
	 * @param metodos     A lista de objetos do tipo {@linkplain Method} que serão
	 *                    manipulados.
	 * @param nomeComando O valor que será procurado no parâmetro nome da anotação
	 *                    dos métodos.
	 * @return Retorna uma lista de objetos {@linkplain Method} caso hajam
	 *         correspondentes ao parâmetro.<br>
	 *         Caso não hava, será retornado uma lista vazia.
	 */
	public List<Method> getMetodosAnotadosPorNome(List<Method> metodos, String nomeComando) {
		return metodos.stream().filter(m -> m.getAnnotation(Comando.class).nome().equals(nomeComando)).toList();
	}

}
