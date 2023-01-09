package br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.prototipo;

import br.com.efigueredo.blackscreen.anotacoes.ConfiguracaoSistema;
import br.com.efigueredo.blackscreen.sistema.configuracoes.ConfiguracaoSistemaTipo;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.ConfiguracaoResposta;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.fontes.IntefaceConfiguracaoResposta;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.fontes.detalhes.CoresBackgroundFontes;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.fontes.detalhes.CoresFontes;

@ConfiguracaoSistema(tipo = ConfiguracaoSistemaTipo.TESTE)
public class ConfiguracaoRespostaPrototipo2  implements ConfiguracaoResposta {

	@Override
	public void configurarRespostas(IntefaceConfiguracaoResposta ic) {
		ic.setarBanner("[BLACKSCREEN]")
			.setarCorBackgroundBanner(CoresBackgroundFontes.ANSI_BACKGROUND_PRETO)
			.setarCorTexto(CoresFontes.ANSI_PRETO)
			.setarCorBackgroundTexto(CoresBackgroundFontes.ANSI_BACKGROUND_VERMELHO);
	}

}
