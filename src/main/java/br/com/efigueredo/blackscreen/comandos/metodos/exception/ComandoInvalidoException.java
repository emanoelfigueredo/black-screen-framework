package br.com.efigueredo.blackscreen.comandos.metodos.exception;

import br.com.efigueredo.blackscreen.sistema.exception.BlackStreenException;

public class ComandoInvalidoException extends BlackStreenException {

	private static final long serialVersionUID = 1L;
	
	public ComandoInvalidoException(String mensagem) {
		super(mensagem);
	}

}
