package br.com.efigueredo.blackscreen.userinput;

import java.util.Arrays;
import java.util.List;

/**
 * <h4>Classe responsável por manipular uma expressão inserida no sistema pelo
 * usuário.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class ManipuladorEntradaUsuario {

	/**
	 * Método responsável por extrair o comando da expressão passada pelo usuário ao
	 * sistema.<br>
	 * <br>
	 * 
	 * O campo de comando da expressão será a primeira palavra da expressão passada.
	 * A mesma será retornada como String.
	 * 
	 * @param expressao Expressão inserida no sistema pelo usuário.
	 * @return A primeira palavra da expressão.
	 */
	public String extrairComando(String expressao) {
		return expressao.split(" ")[0];
	}

	/**
	 * Método responsável por extrair todos os parâmetros da expressão passada pelo
	 * usuário ao sistema.<br>
	 * <br>
	 * 
	 * O método extrai todas as palavras que possuam o prefixo "--" e retorna-os
	 * numa coleção de valores. Retira o primeiro elemento da expressão.
	 * 
	 * @param expressao Expressão inserida no sistema pelo usuário.
	 * @return Uma lista contendo todas os parâmetros extraidos.
	 */
	public List<String> extrairParametros(String expressao) {
		List<String> componentes = Arrays.asList(expressao.split(" "));
		componentes = componentes.subList(1, componentes.size());
		return componentes.stream().filter(c -> c.startsWith("--")).toList();
	}

	/**
	 * Método responsável por extrair todos os valores da expressão passada pelo
	 * usuário ao sistema.<br>
	 * <br>
	 * 
	 * O método elimina a primeira palavra da expressão e filtra todas que não
	 * possuam o prefixo "--".
	 * 
	 * @param expressao Expressão inserida no sistema pelo usuário.
	 * @return Uma lista contendo todas os valores extraidos.
	 */
	public List<String> extrairValores(String expressao) {
		List<String> todosComponentes = Arrays.asList(expressao.split(" "));
		List<String> todosParametrosEValores = todosComponentes.subList(1, todosComponentes.size());
		return todosParametrosEValores.stream().filter(c -> !c.startsWith("--")).toList();
	}

}
