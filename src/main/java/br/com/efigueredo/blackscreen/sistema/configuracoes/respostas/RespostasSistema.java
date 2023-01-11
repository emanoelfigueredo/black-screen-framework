package br.com.efigueredo.blackscreen.sistema.configuracoes.respostas;

import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.fontes.detalhes.ConfiguracaoFonte;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.fontes.detalhes.CoresBackgroundFontes;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.fontes.detalhes.CoresFontes;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.fontes.detalhes.DetalhesDeFontes;

/**
 * <h4>Classe que representa uma configuração de respostas do sistema.</h4><br>
 * <br>
 * 
 * Disponibiliza a possibilidade de imprimir no console para o usuário mensagens
 * personalizadas da forma que foi configurado.
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class RespostasSistema {

	/** Código ANSI que limpa todas as configurações presentes. */
	private final String INICIAL = "\033[0;0m";

	/** O padão de códigos ANSI para o indicador do sistema. */
	private String padraoIndicador;

	/** O padão de códigos ANSI para os textos do sistema. */
	private String padraoTexto;

	/** O padão de códigos ANSI para os textos de erro do sistema. */
	private String padraoTextoErro;

	/** O padão de códigos ANSI para o banner do sistema. */
	private String padraoBanner;

	/** O indicador do sistema. */
	private String indicador;

	/** O banner do sistema. */
	private String banner;

	/**
	 * Construtor.
	 *
	 * @param padraoIndicador O padão de códigos ANSI para o indicador do sistema.
	 * @param padraoTexto     O padão de códigos ANSI para os textos do sistema.
	 * @param padraoTextoErro O padão de códigos ANSI para os textos de erro do
	 *                        sistema.
	 * @param padraoBanner    O padão de códigos ANSI para o banner do sistema.
	 * @param indicador       O indicador do sistema.
	 * @param banner          O banner do sistema.
	 */
	private RespostasSistema(String padraoIndicador, String padraoTexto, String padraoTextoErro, String padraoBanner,
			String indicador, String banner) {
		this.padraoIndicador = padraoIndicador;
		this.padraoTexto = padraoTexto;
		this.padraoTextoErro = padraoTextoErro;
		this.padraoBanner = padraoBanner;
		this.indicador = indicador;
		this.banner = banner;
	}

	/**
	 * Imprima uma mensagem no padrão de respostas do sistema.
	 *
	 * @param mensagem A mensagem;
	 */
	public void imprimirMensagem(String mensagem) {
		System.out.println(this.INICIAL + this.padraoIndicador + this.indicador + this.padraoTexto + mensagem);
	}

	/**
	 * Imprima uma mensagem de errono padrão de respostas do sistema.
	 *
	 * @param mensagem A mensagem;
	 */
	public void imprimirMensagemErro(String mensagem) {
		System.out.println(this.INICIAL + this.padraoIndicador + this.indicador + this.padraoTextoErro + mensagem
				+ this.padraoTexto);
	}

	/**
	 * Imprima o indicador do sistema.
	 */
	public void imprimirIndicador() {
		System.out.print(this.INICIAL + this.padraoIndicador + this.indicador + this.padraoTexto);
	}

	/**
	 * Imprima o banner do sistema.
	 */
	public void imprimirBanner() {
		System.out.print(this.INICIAL + this.padraoBanner + this.banner + this.padraoTexto);
	}

	/**
	 * <h4>Classe responsável por obter as configurações de respostas do sistema
	 * para construir um objeto {@linkplain RespostasSistema}.</h4>
	 * 
	 * @author Emanoel
	 * @since 1.0.0
	 */
	public static class RespostasSistemaBuilder {

		/** A cor do texto. */
		private ConfiguracaoFonte corTexto;

		/** O detalhe da fonte do texto. */
		private ConfiguracaoFonte detalheTexto;

		/** A cor de background do texto. */
		private ConfiguracaoFonte corBackgroundTexto;

		/** A cor do texto de erro. */
		private ConfiguracaoFonte corTextoErro;

		/** O detalhe da fonte do texto de erro. */
		private ConfiguracaoFonte detalheTextoErro;

		/** A cor de background do texto de erro. */
		private ConfiguracaoFonte corBackgroundTextoErro;

		/** A cor do indicador. */
		private ConfiguracaoFonte corIndicador;

		/** O detalhe da fonte do indicador. */
		private ConfiguracaoFonte detalheIndicador;

		/** A cor de background do indicador. */
		private ConfiguracaoFonte corBackgroundIndicador;

		/** A cor do banner. */
		private ConfiguracaoFonte corBanner;

		/** O detalhe da fonte do banner. */
		private ConfiguracaoFonte detalheBanner;

		/** A cor do background banner. */
		private ConfiguracaoFonte corBackgroundBanner;

		/** O indicador do sistema. */
		private String indicador;

		/** O banner do sistema. */
		private String banner;

		/**
		 * Construtor.
		 * 
		 * As configurações padrões do sistema são definidas no construtor.
		 */
		public RespostasSistemaBuilder() {
			this.corTexto = CoresFontes.ANSI_BRANCO;
			this.detalheTexto = DetalhesDeFontes.NORMAL;
			this.corBackgroundTexto = CoresBackgroundFontes.NORMAL;
			this.corTextoErro = CoresFontes.ANSI_VERMELHO;
			this.detalheTextoErro = DetalhesDeFontes.NORMAL;
			this.corBackgroundTextoErro = CoresBackgroundFontes.NORMAL;
			this.corIndicador = CoresFontes.ANSI_BRANCO;
			this.detalheIndicador = DetalhesDeFontes.NORMAL;
			this.corBackgroundIndicador = CoresBackgroundFontes.NORMAL;
			this.corBanner = CoresFontes.ANSI_BRANCO;
			this.detalheBanner = DetalhesDeFontes.NORMAL;
			this.corBackgroundBanner = CoresBackgroundFontes.NORMAL;
			this.indicador = "[BLACKSCREEN] > ";
			this.banner = 
					"\n"
					+ "  ██████╗ ██╗      █████╗  ██████╗██╗  ██╗    ███████╗ ██████╗██████╗ ███████╗███████╗███╗   ██╗\r\n"
					+ "  ██╔══██╗██║     ██╔══██╗██╔════╝██║ ██╔╝    ██╔════╝██╔════╝██╔══██╗██╔════╝██╔════╝████╗  ██║\r\n"
					+ "  ██████╔╝██║     ███████║██║     █████╔╝     ███████╗██║     ██████╔╝█████╗  █████╗  ██╔██╗ ██║\r\n"
					+ "  ██╔══██╗██║     ██╔══██║██║     ██╔═██╗     ╚════██║██║     ██╔══██╗██╔══╝  ██╔══╝  ██║╚██╗██║\r\n"
					+ "  ██████╔╝███████╗██║  ██║╚██████╗██║  ██╗    ███████║╚██████╗██║  ██║███████╗███████╗██║ ╚████║\r\n"
					+ "  ╚═════╝ ╚══════╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝    ╚══════╝ ╚═════╝╚═╝  ╚═╝╚══════╝╚══════╝╚═╝  ╚═══╝\r\n"
					+ "                                                                                                 \n";
		}

		/**
		 * Setar a cor de texto.
		 *
		 * @param corTexto Constante que represente a cor do texto.
		 * @return O próprio objeto builder.
		 */
		public RespostasSistemaBuilder setCorTexto(ConfiguracaoFonte corTexto) {
			this.corTexto = corTexto;
			return this;
		}

		/**
		 * Setar o detalhe da fonte do texto.
		 *
		 * @param detalheTexto Constante que represente o detalhe de fonte do texto.
		 * @return O próprio objeto builder.
		 */
		public RespostasSistemaBuilder setDetalheTexto(ConfiguracaoFonte detalheTexto) {
			this.detalheTexto = detalheTexto;
			return this;
		}

		/**
		 * Setar a cor de background do texto.
		 *
		 * @param backgroundTexto Constante que represente a cor de background do texto.
		 * @return O próprio objeto builder.
		 */
		public RespostasSistemaBuilder setBackgroundTexto(ConfiguracaoFonte backgroundTexto) {
			this.corBackgroundTexto = backgroundTexto;
			return this;
		}

		/**
		 * Setar cor texto de erro.
		 *
		 * @param corTextoErro Constante que represente a cor do texto de erro.
		 * @return O próprio objeto builder.
		 */
		public RespostasSistemaBuilder setCorTextoErro(ConfiguracaoFonte corTextoErro) {
			this.corTextoErro = corTextoErro;
			return this;
		}

		/**
		 * Setar detalhe de fonte do texto de erro.
		 *
		 * @param detalheTextoErro Constante que represente o detalhe de fonte do texto
		 *                         de erro.
		 * @return O próprio objeto builder.
		 */
		public RespostasSistemaBuilder setDetalheTextoErro(ConfiguracaoFonte detalheTextoErro) {
			this.detalheTextoErro = detalheTextoErro;
			return this;
		}

		/**
		 * Setar a cor do background do texto de erro.
		 *
		 * @param backgroundTextoErro Constante que represente a cor de background do
		 *                            texto de erro.
		 * @return O próprio objeto builder.
		 */
		public RespostasSistemaBuilder setBackgroundTextoErro(ConfiguracaoFonte backgroundTextoErro) {
			this.corBackgroundTextoErro = backgroundTextoErro;
			return this;
		}

		/**
		 * Setar a cor do indicador.
		 *
		 * @param corIndicador Constante que represente a cor do indicador.
		 * @return O próprio objeto builder.
		 */
		public RespostasSistemaBuilder setCorIndicador(ConfiguracaoFonte corIndicador) {
			this.corIndicador = corIndicador;
			return this;
		}

		/**
		 * Setar detalhe da fonte do indicador.
		 *
		 * @param detalheIndicador Constante que represente o detalhe da fonte do
		 *                         indicador.
		 * @return O próprio objeto builder.
		 */
		public RespostasSistemaBuilder setDetalheIndicador(ConfiguracaoFonte detalheIndicador) {
			this.detalheIndicador = detalheIndicador;
			return this;
		}

		/**
		 * Setar a cor de background do indicador.
		 *
		 * @param corBackgroundIndicador Constante que represente a cor de background do
		 *                               indicador.
		 * @return O próprio objeto builder.
		 */
		public RespostasSistemaBuilder setCorBackgroundIndicador(ConfiguracaoFonte corBackgroundIndicador) {
			this.corBackgroundIndicador = corBackgroundIndicador;
			return this;
		}

		/**
		 * Setar a cor da fonte do banner.
		 *
		 * @param corBanner Constante que represente a cor da fonte do banner.
		 * @return O próprio objeto builder.
		 */
		public RespostasSistemaBuilder setCorBanner(ConfiguracaoFonte corBanner) {
			this.corBanner = corBanner;
			return this;
		}

		/**
		 * Setar o detalhe da fonte do banner.
		 *
		 * @param detalheBanner Constante que represente o detalhe da fonte do banner.
		 * @return O próprio objeto builder.
		 */
		public RespostasSistemaBuilder setDetalheBanner(ConfiguracaoFonte detalheBanner) {
			this.detalheBanner = detalheBanner;
			return this;
		}

		/**
		 * Setar cor background do banner.
		 *
		 * @param corBackgroundBanner Constante que represente a cor de background do
		 *                            banner.
		 * @return O próprio objeto builder.
		 */
		public RespostasSistemaBuilder setCorBackgroundBanner(ConfiguracaoFonte corBackgroundBanner) {
			this.corBackgroundBanner = corBackgroundBanner;
			return this;
		}

		/**
		 * Setar o indicador do sistema.
		 *
		 * @param indicador Indicador.
		 * @return O próprio objeto builder.
		 */
		public RespostasSistemaBuilder setIndicador(String indicador) {
			this.indicador = indicador;
			return this;
		}

		/**
		 * Setar o banner do sistema.
		 *
		 * @param banner Banner.
		 * @return O próprio objeto builder.
		 */
		public RespostasSistemaBuilder setBanner(String banner) {
			this.banner = banner;
			return this;
		}

		/**
		 * Construir o objeto {@linkplain RespostasSistema}.
		 *
		 * @return Objeto {@linkplain RespostasSistema};
		 */
		public RespostasSistema build() {
			String padraoIndicador = this.corIndicador.getCodigoANSI() + this.detalheIndicador.getCodigoANSI()
					+ this.corBackgroundIndicador.getCodigoANSI();
			String padraoTexto = this.corTexto.getCodigoANSI() + this.detalheTexto.getCodigoANSI()
					+ this.corBackgroundTexto.getCodigoANSI();
			String padraoTextoErro = this.corTextoErro.getCodigoANSI() + this.detalheTextoErro.getCodigoANSI()
					+ this.corBackgroundTextoErro.getCodigoANSI();
			String padraoBanner = this.corBanner.getCodigoANSI() + this.detalheBanner.getCodigoANSI()
					+ this.corBackgroundBanner.getCodigoANSI();
			return new RespostasSistema(padraoIndicador, padraoTexto, padraoTextoErro, padraoBanner, this.indicador,
					this.banner);
		}

	}

}
