package br.com.efigueredo.blackscreen.prototipo_configuracao_resposta;

import java.util.List;

import br.com.efigueredo.blackscreen.anotacoes.ConfiguracaoSistema;
import br.com.efigueredo.blackscreen.sistema.configuracoes.ConfiguracaoSistemaTipo;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.ConfiguracaoResposta;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.fontes.IntefaceConfiguracaoResposta;
import br.com.efigueredo.container.anotacao.Injecao;

@ConfiguracaoSistema(tipo = ConfiguracaoSistemaTipo.RESPOSTAS)
public class ConfiguracaoRespostaPrototipoDependenciaNaoConfigurada implements ConfiguracaoResposta {
	
	@Injecao
	public ConfiguracaoRespostaPrototipoDependenciaNaoConfigurada(List<?> arg1) {
		
	}

	@Override
	public void configurarRespostas(IntefaceConfiguracaoResposta interfaceConfiguracao) {
		// TODO Auto-generated method stub
		
	}

}
