package br.com.efigueredo.blackscreen.anotacoes;

import java.util.List;

public class Teste {

	@Comando(nome = "adicionar")
	public void metodo1(@Parametro(nome = "nome") List<String> nomes) {

	}

//	
//	@Comando(nome = "adicionar")
//	public void metodo2(List<String> nomes) {
//		
//	}
//	
	@Comando(nome = "adicionar")
	public void metodo3(@Parametro(nome = "--param2") List<String> nomes, @Parametro(nome = "--param1") String idade) {

	}

	@Comando(nome = "adicionar")
	public void metodo5(@Parametro(nome = "--param1") String nome, @Parametro(nome = "--param2") String idade) {
		
	}

	@Comando(nome = "adicionar")
	public void metodo10(@Parametro(nome = "--param1") String nome, @Parametro(nome = "--param2") String idade, @Parametro(nome = "--param3") List<String> valores) {
		
	}

}
