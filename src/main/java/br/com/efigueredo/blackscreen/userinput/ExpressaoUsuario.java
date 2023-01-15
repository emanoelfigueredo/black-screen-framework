package br.com.efigueredo.blackscreen.userinput;

/**
 * <h4>A classe representa a expressão inserida pelo usuário.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public abstract class ExpressaoUsuario {

	private String comando;

	public ExpressaoUsuario(String comando) {
		this.comando = comando;
	}

	public String getComando() {
		return this.comando;
	}

}
