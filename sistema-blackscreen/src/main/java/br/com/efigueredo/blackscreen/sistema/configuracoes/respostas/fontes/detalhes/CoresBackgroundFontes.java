package br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.fontes.detalhes;

/**
 * <h4>Constantes para configurações de background da fonte.</h4>
 *
 * @author Emanoel
 * @since 1.0.0
 */
public enum CoresBackgroundFontes implements ConfiguracaoFonte {

	/** Constante de configuração para background preto. */
	ANSI_BACKGROUND_PRETO {

		@Override
		public String getCodigoANSI() {
			return "\u001B[40m";
		}

	},

	/** Constante de configuração para background vermelho. */
	ANSI_BACKGROUND_VERMELHO {

		@Override
		public String getCodigoANSI() {
			return "\u001B[41m";
		}

	},

	/** Constante de configuração para background verde. */
	ANSI_BACKGROUND_VERDE {

		@Override
		public String getCodigoANSI() {
			return "\u001B[42m";
		}

	},

	/** Constante de configuração para background amarelo. */
	ANSI_BACKGROUND_AMARELO {

		@Override
		public String getCodigoANSI() {
			return "\u001B[43m";
		}

	},

	/** Constante de configuração para background azul. */
	ANSI_BACKGROUND_AZUL {

		@Override
		public String getCodigoANSI() {
			return "\u001B[44m";
		}

	},

	/** Constante de configuração para background roxo. */
	ANSI_BACKGROUND_ROXO {

		@Override
		public String getCodigoANSI() {
			return "\u001B[45m";
		}

	},

	/** Constante de configuração para background ciano. */
	ANSI_BACKGROUND_CIANO {

		@Override
		public String getCodigoANSI() {
			return "\u001B[46m";
		}

	},

	/** Constante de configuração para background branco. */
	ANSI_BACKGROUND_BRANCO {

		@Override
		public String getCodigoANSI() {
			return "\u001B[47m";
		}

	},

	/** Constante de configuração para background padrão. */
	NORMAL {

		@Override
		public String getCodigoANSI() {
			return "";
		}

	}

}
