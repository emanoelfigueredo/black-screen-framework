package br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.fontes;

import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.RespostasSistema.RespostasSistemaBuilder;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.fontes.detalhes.CoresBackgroundFontes;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.fontes.detalhes.CoresFontes;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.fontes.detalhes.DetalhesDeFontes;

/**
 * <h4>Objeto que disponibiliza apenas os métodos que o usuário deve ter acesso
 * RespostasSistemaBuilder para a configuração de resposta do sistema.</h4>
 *
 * @author Emanoel
 * @since 1.0.0
 */
public class IntefaceConfiguracaoResposta {

	/** Builder que irá receber as configurações. */
	private RespostasSistemaBuilder builder;

	/**
	 * Construtor.
	 *
	 * @param builder Builder que irá receber as configurações.
	 */
	public IntefaceConfiguracaoResposta(RespostasSistemaBuilder builder) {
		this.builder = builder;
	}

	/**
	 * Setar cor texto.
	 *
	 * @param cor Constante que represente o código ANSI da cor desejada.
	 * @return O próprio objeto {@linkplain IntefaceConfiguracaoResposta};
	 */
	public IntefaceConfiguracaoResposta setarCorTexto(CoresFontes cor) {
		this.builder.setCorTexto(cor);
		return this;
	}

	/**
	 * Setar cor background texto.
	 *
	 * @param cor Constante que represente o código ANSI da cor desejada.
	 * @return O próprio objeto {@linkplain IntefaceConfiguracaoResposta};
	 */
	public IntefaceConfiguracaoResposta setarCorBackgroundTexto(CoresBackgroundFontes cor) {
		this.builder.setBackgroundTexto(cor);
		return this;
	}

	/**
	 * Setar detalhes de fonte.
	 *
	 * @param detalhe Constante que represente o código ANSI do detalhe desejado.
	 * @return O próprio objeto {@linkplain IntefaceConfiguracaoResposta};
	 */
	public IntefaceConfiguracaoResposta setarDetalhesDeFonte(DetalhesDeFontes detalhe) {
		this.builder.setDetalheTexto(detalhe);
		return this;
	}

	/**
	 * Setar cor texto erro.
	 *
	 * @param cor Constante que represente o código ANSI da cor desejada.
	 * @return O próprio objeto {@linkplain IntefaceConfiguracaoResposta};
	 */
	public IntefaceConfiguracaoResposta setarCorTextoErro(CoresFontes cor) {
		this.builder.setCorTextoErro(cor);
		return this;
	}

	/**
	 * Setar cor background texto erro.
	 *
	 * @param cor Constante que represente o código ANSI da cor desejada.
	 * @return O próprio objeto {@linkplain IntefaceConfiguracaoResposta};
	 */
	public IntefaceConfiguracaoResposta setarCorBackgroundTextoErro(CoresBackgroundFontes cor) {
		this.builder.setBackgroundTextoErro(cor);
		return this;
	}

	/**
	 * Setar detalhes de fonte erro.
	 *
	 * @param detalhe Constante que represente o código ANSI do detalhe desejado.
	 * @return O próprio objeto {@linkplain IntefaceConfiguracaoResposta};
	 */
	public IntefaceConfiguracaoResposta setarDetalhesDeFonteErro(DetalhesDeFontes detalhe) {
		this.builder.setDetalheTextoErro(detalhe);
		return this;
	}

	/**
	 * Setar cor indicador.
	 *
	 * @param cor Constante que represente o código ANSI da cor desejada.
	 * @return O próprio objeto {@linkplain IntefaceConfiguracaoResposta};
	 */
	public IntefaceConfiguracaoResposta setarCorIndicador(CoresFontes cor) {
		this.builder.setCorIndicador(cor);
		return this;
	}

	/**
	 * Setar cor background indicador.
	 *
	 * @param cor Constante que represente o código ANSI da cor desejada.
	 * @return O próprio objeto {@linkplain IntefaceConfiguracaoResposta};
	 */
	public IntefaceConfiguracaoResposta setarCorBackgroundIndicador(CoresBackgroundFontes cor) {
		this.builder.setCorBackgroundIndicador(cor);
		return this;
	}

	/**
	 * Setar detalhes indicador.
	 *
	 * @param detalhe Constante que represente o código ANSI do detalhe desejado.
	 * @return O próprio objeto {@linkplain IntefaceConfiguracaoResposta};
	 */
	public IntefaceConfiguracaoResposta setarDetalhesIndicador(DetalhesDeFontes detalhe) {
		this.builder.setDetalheIndicador(detalhe);
		return this;
	}

	/**
	 * Setar cor banner.
	 *
	 * @param cor Constante que represente o código ANSI da cor desejada.
	 * @return O próprio objeto {@linkplain IntefaceConfiguracaoResposta};
	 */
	public IntefaceConfiguracaoResposta setarCorBanner(CoresFontes cor) {
		this.builder.setCorBanner(cor);
		return this;
	}

	/**
	 * Setar cor background banner.
	 *
	 * @param cor Constante que represente o código ANSI da cor desejada.
	 * @return O próprio objeto {@linkplain IntefaceConfiguracaoResposta};
	 */
	public IntefaceConfiguracaoResposta setarCorBackgroundBanner(CoresBackgroundFontes cor) {
		this.builder.setCorBackgroundBanner(cor);
		return this;
	}

	/**
	 * Setar detalhes banner.
	 *
	 * @param detalhe Constante que represente o código ANSI do detalhe desejado.
	 * @return O próprio objeto {@linkplain IntefaceConfiguracaoResposta};
	 */
	public IntefaceConfiguracaoResposta setarDetalhesBanner(DetalhesDeFontes detalhe) {
		this.builder.setDetalheBanner(detalhe);
		return this;
	}

	/**
	 * Setar indicador.
	 *
	 * @param String que represente o indicador de entrada de texto.
	 * @return O próprio objeto {@linkplain IntefaceConfiguracaoResposta};
	 */
	public IntefaceConfiguracaoResposta setarIndicador(String indicador) {
		this.builder.setIndicador(indicador);
		return this;
	}

	/**
	 * Setar banner.
	 *
	 * @param String que represente o banner do projeto.
	 * @return O próprio objeto {@linkplain IntefaceConfiguracaoResposta};
	 */
	public IntefaceConfiguracaoResposta setarBanner(String banner) {
		this.builder.setBanner(banner);
		return this;
	}

}
