package br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.fontes.detalhes;

/**
 * <h4>Interface para representar uma configuração de fonte para as
 * configurações de respostas do sistema.</h4><br>
 * <br>
 * 
 * Todas devem implementar o método que especifica seu código ANSI.
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public interface ConfiguracaoFonte {

	/**
	 * Obtenha o código ANSI da configuração.
	 *
	 * @return código ANSI da configuração.
	 */
	String getCodigoANSI();

}
