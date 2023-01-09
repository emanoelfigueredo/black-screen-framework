package br.com.efigueredo.blackscreen.comandos.invocacao.prototipo;

import br.com.efigueredo.blackscreen.anotacoes.Comando;
import br.com.efigueredo.container.anotacao.Injecao;

public class PrototipoControladorComDependencia {

	private PrototipoControlador controller;

	@Injecao
	public PrototipoControladorComDependencia(PrototipoControlador controller) {
		this.controller = controller;
	}
	
	public PrototipoControlador getController() {
		return controller;
	}

	@Comando(nome = "comando1")
	public void comando1() {

	}

	@Comando(nome = "comando2")
	public void comando2(String arg1) {

	}

}
