package br.com.efigueredo.blackscreen.comandos.metodos;

import br.com.efigueredo.blackscreen.anotacoes.Comando;

public class PrototipoControladorComandosSoValoresIncorretos2MetodosMesmosParametros {

	@Comando(nome = "adicionar")
	public void comando1(String args) {
		
	}
	
	@Comando(nome = "adicionar")
	public void comando2(String args) {
		
	}
	
}
