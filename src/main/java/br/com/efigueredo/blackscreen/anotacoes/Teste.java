package br.com.efigueredo.blackscreen.anotacoes;

import java.util.List;

public class Teste {

	// SEM PARÂMETROS DE COMANDO
	
	@Comando(nome = "adicionar")
	public void metodo1() {

	}
	
	@Comando(nome = "adicionar")
	public void metodo2(String arg) {
		
	}
	
	@Comando(nome = "adicionar")
	public void metodo3(List<String> args) {
		
	}
	
	
	
	@Comando(nome = "adicionar")
	public void metodo3(@Parametro("--param2") List<String> nomes, @Parametro("--param1") String idade) {

	}

	@Comando(nome = "adicionar")
	public void metodo5(@Parametro("--param1") String nome, @Parametro("--param2") String idade) {

	}

	@Comando(nome = "adicionar")
	public void metodo10(@Parametro("--param1") String nome, 
			@Parametro("--param2") String idade,
			@Parametro("--param3") List<String> valores) {

	}

}
