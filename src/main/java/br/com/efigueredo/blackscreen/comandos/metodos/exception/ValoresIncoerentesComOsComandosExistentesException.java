package br.com.efigueredo.blackscreen.comandos.metodos.exception;

/**
 * <h4>Exceção que representa a situação onde não há métodos anotados com
 * {@linkplain @Comando} que possam receber nos parâmetros a quantidade de
 * valores e os tipos solicitados.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class ValoresIncoerentesComOsComandosExistentesException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor
	 *
	 * @param mensagem Texto que descreva com mais detalher a exceção.
	 */
	public ValoresIncoerentesComOsComandosExistentesException(String mensagem) {
		super(mensagem);
	}

}
