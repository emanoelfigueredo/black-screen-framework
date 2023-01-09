package br.com.efigueredo.blackscreen.sistema.configuracoes.respostas;

import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.fontes.IntefaceConfiguracaoResposta;

/**
 * <h4>Interface para às classes de configuração de respostas.</h4>
 * 
 * A implementação de uma configuração de resposta deve ter o método
 * {@code configurarRespostas}.
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public interface ConfiguracaoResposta {

	/**
	 * Método responsável por disponibilizar a interface de configuração de resposta
	 * para o usuário.
	 *
	 * O objeto {@linkplain IntefaceConfiguracaoResposta} será injetado para o uso
	 * do usuário.
	 *
	 * @param interfaceConfiguracao Objeto {@linkplain IntefaceConfiguracaoResposta}
	 *                              que disponibiliza recursos de configuração das
	 *                              respostas do sistema.
	 */
	void configurarRespostas(IntefaceConfiguracaoResposta interfaceConfiguracao);

}
