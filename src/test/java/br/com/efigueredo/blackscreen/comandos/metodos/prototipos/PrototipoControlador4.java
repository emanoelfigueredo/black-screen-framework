package br.com.efigueredo.blackscreen.comandos.metodos.prototipos;

import br.com.efigueredo.blackscreen.anotacoes.Comando;

public class PrototipoControlador4 {

	@Comando(nome="adicionar", parametros = {"--nome", "-n"})
	public void metodo1() {

	}

	@Comando(nome="remove",parametros = {"--nome", "-n"})
	public void metodo2() {

	}
	
	@Comando(nome="remove")
	public void metodo8(String nome) {

	}
	
	@Comando(nome="atualizar", parametros = {"--nome", "-n"})
	public void metodo3(String nome) {

	}
	
	@Comando(nome="atualizar", parametros = {"--id", "-i"})
	public void metodo4(String id) {

	}
	
	@Comando(nome="command5")
	public void metodo5() {

	}
	
	@Comando(nome="atualizar")
	public void metodo6() {

	}
	
	@Comando(nome="listar", parametros = {"--all"})
	public void metodo7(String categoria) {

	}
	
	@Comando(nome="listar", parametros = {"--all"})
	public void metodo8() {

	}
	
	@Comando(nome="qualquer", parametros = {"--add", "-a"})
	public void metodo9() {
		
	}
	
	@Comando(nome="metodo9")
	public void metodo9(String arg1) {
		
	}

}
