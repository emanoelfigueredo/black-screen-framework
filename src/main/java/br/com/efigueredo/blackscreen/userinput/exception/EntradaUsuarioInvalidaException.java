package br.com.efigueredo.blackscreen.userinput.exception;

/**
 * <h4>A classe {@code EntradaUsuarioInvalidaExpcetion} é uma exceção para
 * situações onde a expressão inserida pelo usuário é <b>inválida</b> aos
 * parâmetros do sistema.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class EntradaUsuarioInvalidaException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor.
	 * 
	 * @param mensagem Texto que descreva a exceção com mais detalhes.
	 */
	public EntradaUsuarioInvalidaException(String mensagem) {
		super(mensagem);
	}

}
