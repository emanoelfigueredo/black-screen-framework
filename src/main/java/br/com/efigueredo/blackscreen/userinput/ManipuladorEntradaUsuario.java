package br.com.efigueredo.blackscreen.userinput;

import java.util.List;
import java.util.Map;

import br.com.efigueredo.blackscreen.userinput.exception.ExpressaoInvalidaException;

/**
 * <h4>Classe responsável por manipular uma expressão inserida no sistema pelo
 * usuário.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class ManipuladorEntradaUsuario {
	
	private ManipuladorExpressaoParametrosEValores manipuladorParametrosValores;
	private ManipuladorExpressaoSomenteValores manipuladorSomenteValores;
	
	public ManipuladorEntradaUsuario() {
		this.manipuladorSomenteValores = new ManipuladorExpressaoSomenteValores();
		this.manipuladorParametrosValores = new ManipuladorExpressaoParametrosEValores();
	}

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
	
	public Map<String, String> extrairParametrosEValores(String expressao) {
		return this.manipuladorParametrosValores.extrairParametrosEValores(expressao);
	}

	public List<String> extrairValoresExpressaoSemParametros(String expressao) throws ExpressaoInvalidaException {
		return this.manipuladorSomenteValores.extrairValores(expressao);
	}

}
