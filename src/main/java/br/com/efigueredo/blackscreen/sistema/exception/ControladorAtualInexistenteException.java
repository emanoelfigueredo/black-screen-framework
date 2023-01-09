package br.com.efigueredo.blackscreen.sistema.exception;

/**
 * <h4>Exceção para a situação onde não há classe controladora para o sistema
 * consumir.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class ControladorAtualInexistenteException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor.
	 *
	 * @param mensagem Texto que descreva a exceção com mais detalhes.
	 */
	public ControladorAtualInexistenteException(String mensagem) {
		super(mensagem);
	}

}
