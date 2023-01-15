package br.com.efigueredo.blackscreen.comandos.invocacao.prototipo;

import java.util.List;

import br.com.efigueredo.container.anotacao.Injecao;

public class PrototipoControladorDuploConstrutorAnotado {

	@Injecao
	public PrototipoControladorDuploConstrutorAnotado(String arg1) {
		
	}
	
	@Injecao
	public PrototipoControladorDuploConstrutorAnotado(List<?> arg1) {
		
	}
	
}
