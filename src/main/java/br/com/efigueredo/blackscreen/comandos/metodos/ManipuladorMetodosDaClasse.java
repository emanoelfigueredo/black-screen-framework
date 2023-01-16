package br.com.efigueredo.blackscreen.comandos.metodos;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * <h4>A classe {@code ManipuladorMetodos} é reponsável por fornecer maneiras de
 * manipular os métodos de uma classe.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class ManipuladorMetodosDaClasse {

	/**
	 * Lista com os métodos públicos, privados, e herdados.
	 */
	private List<Method> metodos;

	/**
	 * Construtor.
	 * 
	 * Recebe a classe que terá seus métodos manipulados. Seus métodos públicos,
	 * privados, e herdados serão extraídos e juntados no atributo metodos da
	 * classe.
	 * 
	 * @param classe Objeto {@linkplain Class} que terá seus métodos manipulados.
	 */
	public ManipuladorMetodosDaClasse(Class<?> classe) {
		this.metodos = new ArrayList<Method>();
		this.setupMetodos(Arrays.asList(classe.getMethods()));
		this.setupMetodos(Arrays.asList(classe.getDeclaredMethods()));
	}

	/**
	 * Método privado auxiliar responsável por adicionar a lista de métodos apenas
	 * os métodos que não existam nela.
	 * 
	 * @param metodos Lista de objetos {@linkplain Method}.
	 */
	private void setupMetodos(List<Method> listaMetodos) {
		listaMetodos.forEach(m -> {
			if (!this.metodos.contains(m)) {
				this.metodos.add(m);
			}
		});
	}

	/**
	 * O método {@code getMetodosAnotados} é responsável por retornar todos os
	 * métodos anotados com uma anotação específica.
	 * 
	 * Seu funcionamento consiste basicamente em filtrar a lista de métodos pela
	 * anotação inserida por parâmetro.
	 *
	 * @param classeAnotacao Objeto {@linkplain Class} que represente a anotação a
	 *                       ser usada como filtro.
	 * @return Caso haja metódos corresponsdente, será retornado uma Lista de
	 *         objetos do tipo {@linkplain Method}.<br>
	 *         Caso não hava, será retornado null.
	 */
	public List<Method> getMetodosAnotados(Class<? extends Annotation> classeAnotacao) {
		List<Method> metodos = this.metodos.stream().filter(m -> m.isAnnotationPresent(classeAnotacao)).toList();
		return this.verificaResultadoLista(metodos);
	}

	/**
	 * O método {@code getMetodosComParametros} é responsável por filtrar objetos do
	 * tipo {@linkplain Method} pelos seus parâmetros de assinatura.
	 * 
	 * Seu funcionamento se baseia em selecionar todos os métodos tal que a classe
	 * de seus parâmetros sejam correspondentes e na mesma ordem que a lista de
	 * classes passada como parâmetro.
	 *
	 * @param listaParametros Lista de Objetos do tipo {@linkplain Class} que
	 *                        deverão servir como parâmetros de filtragem.
	 * @return Caso haja métodos correspondentes, uma lista de objetos do tipo
	 *         {@linkplain Method}.<br>
	 *         Caso não hava, null.
	 */
	public List<Method> getMetodosComParametros(List<Class<? extends Object>> listaParametros) {
		List<Method> metodosSelecionados = new ArrayList<Method>();
		List<Method> metodosComAMesmaQuantidadeDeParametros = this.metodos.stream()
				.filter(m -> m.getParameterCount() == listaParametros.size()).toList();
		if (metodosComAMesmaQuantidadeDeParametros.isEmpty()) {
			return null;
		}
		List<Class<?>> tiposDosParametros = new ArrayList<Class<?>>();
		metodosComAMesmaQuantidadeDeParametros.forEach(m -> {
			Stream.of(m.getParameters()).forEach(param -> {
				tiposDosParametros.add(param.getType());
			});
			if (tiposDosParametros.equals(listaParametros)) {
				metodosSelecionados.add(m);
			}
			tiposDosParametros.clear();
		});
		return metodosSelecionados;
	}

	/**
	 * Getter do atributo {@code metodos}.
	 * 
	 * @return Lista de objetos {@linkplain Method} contendo todos os métodos
	 *         setados.
	 */
	public List<Method> getMetodos() {
		return this.metodos;
	}

	/**
	 * Método auxiliar responsável por verificar a existência do resultado do
	 * recurso manipulado.
	 *
	 * @param <T>               A classe do recurso.
	 * @param resultadoOptional O resultado encapsulado no objeto
	 *                          {@linkplain Optional}.
	 * @return true, se o resultado optional não for vazio.<br>
	 *         false, se o resultado optional for vazio.
	 */
	protected <T> boolean verificaExistencial(Optional<T> resultadoOptional) {
		if (resultadoOptional.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * Método auxiliar responsável por verificar a lista de resultados de um recurso
	 * manipulado.
	 *
	 * @param <T>       A classe do recurso.
	 * @param resultado Uma lista de resultados da manipulação.
	 * @return Se a lista de resultados não for vazia, retornará a mesma.<br>
	 *         Caso seja vazia, retornará null.
	 */
	protected <T> List<T> verificaResultadoLista(List<T> resultado) {
		if (resultado.isEmpty()) {
			return null;
		}
		return resultado;
	}

	/**
	 * Método auxiliar responsável por verificar se o resursos manipulado está
	 * presente no objeto {@linkplain Optional}.
	 *
	 * @param <T>     A classe do recurso.
	 * @param recurso - Objeto {@linkplain Optional} do recurso.
	 * @return O recurso T caso exista.<br>
	 *         Se não existir, null.
	 */
	protected <T> T verificarRecursoPresente(Optional<T> recurso) {
		if (!recurso.isPresent()) {
			return null;
		}
		return recurso.get();
	}
	
	

}