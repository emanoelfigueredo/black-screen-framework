package br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception;

/**
 * <h4>Exceção para situações de erros no carregamento das configurações de
 * respostas do sistema.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class ConfiguracaoRespostaSistemaException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor.
	 */
	public ConfiguracaoRespostaSistemaException() {
		super();
	}

	/**
	 * Construtor.
	 *
	 * @param mensagem Texto que descreva o problema com mais detalhes.
	 */
	public ConfiguracaoRespostaSistemaException(String mensagem) {
		super(mensagem);
	}

	/**
	 * Construtor.
	 *
	 * @param mensagem Texto que descreva o problema com mais detalhes.
	 * @param excecao  Exceção raiz.
	 */
	public ConfiguracaoRespostaSistemaException(String mensagem, Exception excecao) {
		super(mensagem, excecao);
	}

}
