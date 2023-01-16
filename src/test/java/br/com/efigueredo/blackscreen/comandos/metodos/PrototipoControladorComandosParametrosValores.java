package br.com.efigueredo.blackscreen.comandos.metodos;

import java.util.List;

import br.com.efigueredo.blackscreen.anotacoes.Comando;
import br.com.efigueredo.blackscreen.anotacoes.Parametro;

public class PrototipoControladorComandosParametrosValores {

	@Comando(nome = "adicionar")
	public void metodo1(@Parametro("--param1") String arg1, @Parametro("--param2") String arg2) {

	}

	@Comando(nome = "adicionar")
	public void metodo2(@Parametro("--param1") String arg1, @Parametro("--param2") List<String> args2) {

	}

	@Comando(nome = "adicionar")
	public void metodo3(@Parametro("--param1") List<String> args1, @Parametro("--param2") List<String> args2) {

	}

	@Comando(nome = "adicionar")
	public void metodo4(@Parametro("--param1") List<String> args1, @Parametro("--param2") List<String> args2,
			@Parametro("--param3") List<String> args3) {

	}

	@Comando(nome = "adicionar")
	public void metodo5(@Parametro("--param1") String arg1, @Parametro("--param2") String arg2,
			@Parametro("--param3") String arg3, @Parametro("--param4") String arg4) {

	}

	// iguais

	@Comando(nome = "adicionar")
	public void metodo6(@Parametro("--param3") List<String> args3, @Parametro("--param1") String arg1,
			@Parametro("--param2") List<String> args2) {

	}

	@Comando(nome = "adicionar")
	public void metodo7(@Parametro("--param2") List<String> args2, @Parametro("--param1") String arg1,
			@Parametro("--param3") List<String> args3) {

	}

}
