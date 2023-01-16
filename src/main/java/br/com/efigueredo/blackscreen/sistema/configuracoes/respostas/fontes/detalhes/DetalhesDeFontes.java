package br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.fontes.detalhes;

/**
 * <h4>Constantes para os detalhes de fonte.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public enum DetalhesDeFontes implements ConfiguracaoFonte {

	/** Constante de configuração para negrito. */
	ANSI_NEGRITO {

		@Override
		public String getCodigoANSI() {
			return "\033[;1m";
		}

	},

	/** Constante de configuração para forma normal. */
	NORMAL {

		@Override
		public String getCodigoANSI() {
			return "";
		}

	}

}
