package br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.prototipo;

import br.com.efigueredo.blackscreen.anotacoes.ConfiguracaoSistema;
import br.com.efigueredo.blackscreen.sistema.configuracoes.ConfiguracaoSistemaTipo;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.ConfiguracaoResposta;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.fontes.IntefaceConfiguracaoResposta;

@ConfiguracaoSistema(tipo = ConfiguracaoSistemaTipo.RESPOSTAS)
public class ConfiguracaoRespostaPrototipoSemConstrutorValido implements ConfiguracaoResposta {
	
	public ConfiguracaoRespostaPrototipoSemConstrutorValido(String arg1) {
		
	}

	@Override
	public void configurarRespostas(IntefaceConfiguracaoResposta interfaceConfiguracao) {
		// TODO Auto-generated method stub
		
	}

}
