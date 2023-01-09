package br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception;

/**
 * <h4>Exceção para a situação onde a leitura da configuração foi interrompida
 * por alguma exceção de reflexão.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class ConfiguracaoInterrompidaException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor.
	 *
	 * @param mensagem Texto que descreva a exceção com mais detalhes.
	 * @param causa    Causa da interrupção.
	 */
	public ConfiguracaoInterrompidaException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}
