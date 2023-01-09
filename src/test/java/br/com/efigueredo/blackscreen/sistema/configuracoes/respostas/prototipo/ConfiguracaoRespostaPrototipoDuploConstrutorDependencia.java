package br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.prototipo;

import java.util.ArrayList;

import br.com.efigueredo.blackscreen.anotacoes.ConfiguracaoSistema;
import br.com.efigueredo.blackscreen.sistema.configuracoes.ConfiguracaoSistemaTipo;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.ConfiguracaoResposta;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.fontes.IntefaceConfiguracaoResposta;
import br.com.efigueredo.container.anotacao.Injecao;

@ConfiguracaoSistema(tipo = ConfiguracaoSistemaTipo.RESPOSTAS)
public class ConfiguracaoRespostaPrototipoDuploConstrutorDependencia implements ConfiguracaoResposta {
	
	@Injecao
	public ConfiguracaoRespostaPrototipoDuploConstrutorDependencia(String arg1) {
		
	}
	
	@Injecao
	public ConfiguracaoRespostaPrototipoDuploConstrutorDependencia(ArrayList<?> arg2) {
		
	}

	@Override
	public void configurarRespostas(IntefaceConfiguracaoResposta interfaceConfiguracao) {
		// TODO Auto-generated method stub
		
	}

	
	
}
