package br.com.efigueredo.blackscreen.comandos.metodos.exception;

/**
 * <h4>Exceção que representa a situação onde não há métodos anotados com
 * {@linkplain @Comando} com algum dos valores do atributo parametro
 * correspondente ao solicitado.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class ParametroDeComandoInexistenteException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor
	 *
	 * @param mensagem Texto que descreva com mais detalher a exceção.
	 */
	public ParametroDeComandoInexistenteException(String mensagem) {
		super(mensagem);
	}

}
