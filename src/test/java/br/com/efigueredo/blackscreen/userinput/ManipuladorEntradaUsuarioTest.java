package br.com.efigueredo.blackscreen.userinput;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ManipuladorEntradaUsuarioTest {
	
	private ManipuladorEntradaUsuario manipulador;
	private String expressaoCorreta;

	@BeforeEach
	public void setUp() throws Exception {
		this.manipulador = new ManipuladorEntradaUsuario();
		this.expressaoCorreta = "comando --parametro valor";
	}

	@Test
	public void deveriaRetornarOPrimeiroComponenteDaExpressaoInserida() {
		String resultado = this.manipulador.extrairComando(this.expressaoCorreta);
		assertEquals("comando", resultado);
	}
	
	
	
	


}
