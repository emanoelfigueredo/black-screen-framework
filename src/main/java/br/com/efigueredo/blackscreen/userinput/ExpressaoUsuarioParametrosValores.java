package br.com.efigueredo.blackscreen.userinput;

import java.util.Map;

/**
 * <h4>Classe que representa a expressão do usuário com comando, parâmetros de
 * comando e valores.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class ExpressaoUsuarioParametrosValores extends ExpressaoUsuario {

	private Map<String, String> parametrosValores;

	public ExpressaoUsuarioParametrosValores(String comando, Map<String, String> parametrosValores) {
		super(comando);
		this.parametrosValores = parametrosValores;
	}

	public Map<String, String> getParametrosValores() {
		return this.parametrosValores;
	}

}
