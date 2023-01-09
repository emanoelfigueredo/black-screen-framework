package br.com.efigueredo.blackscreen.comandos.metodos.exception;

/**
 * <h4>Exceção para a situação onde não exista nenhum método que possa
 * corresponder a solicitação do usuário</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class SolicitacaoDeMetodoComandoInexistenteException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor.
	 *
	 * @param mensagem Texto que descreva a situação com mais detalhes.
	 */
	public SolicitacaoDeMetodoComandoInexistenteException(String mensagem) {
		super(mensagem);
	}

}
