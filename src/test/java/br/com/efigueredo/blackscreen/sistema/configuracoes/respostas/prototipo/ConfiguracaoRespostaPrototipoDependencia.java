package br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.prototipo;

import br.com.efigueredo.blackscreen.anotacoes.ConfiguracaoSistema;
import br.com.efigueredo.blackscreen.sistema.configuracoes.ConfiguracaoSistemaTipo;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.ConfiguracaoResposta;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.fontes.IntefaceConfiguracaoResposta;
import br.com.efigueredo.container.anotacao.Injecao;

@ConfiguracaoSistema(tipo = ConfiguracaoSistemaTipo.RESPOSTAS)
public class ConfiguracaoRespostaPrototipoDependencia implements ConfiguracaoResposta {
	
	private String string;

	@Injecao
	public ConfiguracaoRespostaPrototipoDependencia(String string) {
		this.string = string;
	}

	@Override
	public void configurarRespostas(IntefaceConfiguracaoResposta interfaceConfiguracao) {
		
	}
	
	public String getString() {
		return string;
	}
	
}
