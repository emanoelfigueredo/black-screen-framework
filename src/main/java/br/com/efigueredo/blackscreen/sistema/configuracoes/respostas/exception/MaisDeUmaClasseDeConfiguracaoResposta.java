package br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception;

/**
 * <h4>Exceção para a situação onde exista mais de uma classe de configuração de
 * resposta no projeto.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class MaisDeUmaClasseDeConfiguracaoResposta extends ConfiguracaoRespostaSistemaException {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor.
	 *
	 * @param mensagem Texto que descreva a exceção com mais detalhes.
	 */
	public MaisDeUmaClasseDeConfiguracaoResposta(String mensagem) {
		super(mensagem);
	}

}
