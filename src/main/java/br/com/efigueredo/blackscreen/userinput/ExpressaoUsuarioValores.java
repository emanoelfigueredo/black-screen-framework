package br.com.efigueredo.blackscreen.userinput;

import java.util.List;

/**
 * <h4>Classe que representa uma expressão inserida pelo usuário com somente
 * comando e valores.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class ExpressaoUsuarioValores extends ExpressaoUsuario {

	private List<String> valores;

	public ExpressaoUsuarioValores(String comando, List<String> valores) {
		super(comando);
		this.valores = valores;
	}

	public List<String> getValores() {
		return this.valores;
	}

}
