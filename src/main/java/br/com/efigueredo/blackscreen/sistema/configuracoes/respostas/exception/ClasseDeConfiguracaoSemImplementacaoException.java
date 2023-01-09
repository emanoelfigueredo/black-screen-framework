package br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception;

/**
 * <h4>Exceção para a situação onde a classe de configuração não implementa a
 * interface necessária.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class ClasseDeConfiguracaoSemImplementacaoException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor.
	 *
	 * @param mensagem Texto que descreva a exceção com mais detalhes.
	 */
	public ClasseDeConfiguracaoSemImplementacaoException(String mensagem) {
		super(mensagem);
	}

}
