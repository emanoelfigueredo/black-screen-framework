package br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.fontes.detalhes;

/**
 * <h4>Constantes para configurações de cor da fonte.</h4>
 *
 * @author Emanoel
 * @since 1.0.0
 */
public enum CoresFontes implements ConfiguracaoFonte {

	/** Constante de configuração para cor preta. */
	ANSI_PRETO {

		@Override
		public String getCodigoANSI() {
			return "\u001B[30m";
		}
		
	},

	/** Constante de configuração para cor vermelho. */
	ANSI_VERMELHO {
		
		@Override
		public String getCodigoANSI() {
			return "\u001B[31m";
		}
		
	},

	/** Constante de configuração para cor verde. */
	ANSI_VERDE {
		
		@Override
		public String getCodigoANSI() {
			return "\u001B[32m";
		}
		
	},

	/** Constante de configuração para cor amarelo. */
	ANSI_AMARELO {
		
		@Override
		public String getCodigoANSI() {
			return "\u001B[33m";
		}
		
	},

	/** Constante de configuração para cor azul. */
	ANSI_AZUL {
		
		@Override
		public String getCodigoANSI() {
			return "\u001B[34m";
		}
		
	},

	/** Constante de configuração para cor roxo. */
	ANSI_ROXO {

		@Override
		public String getCodigoANSI() {
			return "\u001B[35m";
		}
		
	},

	/** Constante de configuração para cor ciano. */
	ANSI_CIANO {
		
		@Override
		public String getCodigoANSI() {
			return "\u001B[36m";
		}
		
	},

	/** Constante de configuração para cor branco. */
	ANSI_BRANCO {
		
		@Override
		public String getCodigoANSI() {
			return "\u001B[37m";
		}
		
	},
	
	/** Constante de configuração para cor padrão. */
	NORMAL {

		@Override
		public String getCodigoANSI() {
			return "";
		}
		
	}

}
