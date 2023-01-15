package br.com.efigueredo.blackscreen.userinput.expressao;

import java.util.List;
import java.util.Map;

/**
 * <h4>Classe que representa a expressão do usuário com comando, parâmetros de
 * comando e valores.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class ExpressaoUsuarioParametrosValores extends ExpressaoUsuario {

	private Map<String, List<String>> parametrosValores;

	public ExpressaoUsuarioParametrosValores(String comando, Map<String, List<String>> parametrosValores) {
		super(comando);
		this.parametrosValores = parametrosValores;
	}

	public Map<String, List<String>> getParametrosValores() {
		return this.parametrosValores;
	}

}
