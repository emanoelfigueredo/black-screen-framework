package br.com.efigueredo.blackscreen.userinput;

import java.util.ArrayList;
import java.util.List;

/**
 * <h4>A classe {@code EntradaUsuario} representa a entrada do usuário.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class EntradaUsuario {

	/**
	 * O atributo {@code comando} comando representa o comando passado pelo usuário
	 * O atributo não pode valer {@code null}.
	 */
	private String comando;

	/**
	 * O atributo {@code parametro} comando representa os parâmetros passado pelo
	 * usuário. O parâmetro pode valer {@code null}.
	 */
	private List<String> parametros;

	/**
	 * O atributo {@code valores} comando representa todos os valores passado pelo
	 * usuário O parâmetro pode valer {@code null}.
	 */
	private List<String> valores;

	/**
	 * Construtor completo.<br>
	 * 
	 * Construtor privado. Será acessado somente pelo builder da classe.
	 * 
	 * @param comando   Comando inserido pelo usuário.
	 * @param parametro Lista de parametros inseridos pelo usuário.
	 * @param valores   Lista de valores inseridos pelo usuário.
	 */
	public EntradaUsuario(String comando, List<String> parametros, List<String> valores) {
		this.comando = comando;
		this.parametros = parametros;
		this.valores = valores;
	}

	/**
	 * Obtenha o comando inserido pelo usuário.
	 *
	 * @return Atributo comando.
	 */
	public String getComando() {
		return this.comando;
	}

	/**
	 * Obtenha os parametros inseridos pelo usuário.
	 *
	 * @return Atributo parametros.
	 */
	public List<String> getParametros() {
		return this.parametros;
	}

	/**
	 * Obtenha os valores inseridos pelo usuário.
	 *
	 * @return Atributo valores.
	 */
	public List<String> getValores() {
		return this.valores;
	}

	/**
	 * Obtenha uma lista com as classes dos valores inseridos pelo usuário.
	 * 
	 * @return Lista de objetos do tipo {@linkplain Class} representando as
	 *         classes dos valores.
	 */
	public List<Class<?>> getClassesDosValores() {
		List<Class<?>> classesDosValores = new ArrayList<Class<?>>();
		this.valores.forEach(v -> classesDosValores.add(v.getClass()));
		return classesDosValores;
	}

}
