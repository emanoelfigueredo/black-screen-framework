package br.com.efigueredo.blackscreen.userinput;

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

	

}
