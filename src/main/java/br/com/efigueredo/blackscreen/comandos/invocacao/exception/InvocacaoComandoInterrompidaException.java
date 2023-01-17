package br.com.efigueredo.blackscreen.comandos.invocacao.exception;

/**
 * <h4>Exceção para a situação na qual a invocação do método não foi terminada.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class InvocacaoComandoInterrompidaException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor
	 *
	 * @param mensagem mensagem que descreva com mais detalher a exceção.
	 * @param causa    A causa da exceção.
	 */
	public InvocacaoComandoInterrompidaException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
	
	public InvocacaoComandoInterrompidaException(String mensagem) {
		super(mensagem);
	}

}
