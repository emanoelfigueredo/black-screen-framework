package br.com.efigueredo.blackscreen.userinput.exception;

/**
 * <h4>A classe {@code MaisDeUmParametroNaExpressaoException} é uma exceção para
 * situações onde a expressão inserida aprensenta mais de um parâmetro de
 * comando.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class MaisDeUmParametroNaExpressaoException extends ExpressaoInvalidaException {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor.
	 * 
	 * @param mensagem Texto que descreva a exceção com mais detalhes.
	 */
	public MaisDeUmParametroNaExpressaoException(String mensagem) {
		super(mensagem);
	}

}
