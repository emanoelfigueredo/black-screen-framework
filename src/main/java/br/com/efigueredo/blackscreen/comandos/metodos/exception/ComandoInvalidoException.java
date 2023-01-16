package br.com.efigueredo.blackscreen.comandos.metodos.exception;

public class ComandoInvalidoException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ComandoInvalidoException(String mensagem) {
		super(mensagem);
	}

}
