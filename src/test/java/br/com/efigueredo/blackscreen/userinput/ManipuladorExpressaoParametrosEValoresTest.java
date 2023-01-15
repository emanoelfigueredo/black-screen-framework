package br.com.efigueredo.blackscreen.userinput;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

class ManipuladorExpressaoParametrosEValoresTest {
	
	private ManipuladorExpressaoParametrosEValores manipulador = new ManipuladorExpressaoParametrosEValores();

	@Test
	public void deveriaRetornarUmMapa_DeParametroValores_DadoUmaExpressao_SemComando() {
		String expressaoSemAspas = "--param1 valor1 --param2 valor2";
		Map<String, String> resultado1 = this.manipulador.extrairParametrosEValores(expressaoSemAspas);
		assertEquals(resultado1.get("param1"), "valor1");
		assertEquals(resultado1.get("param2"), "valor2");
		
		String expressaoComAspas = "--param1 valor1 --param2 \"valor2 com detalhes\"";
		Map<String, String> resultado2 = this.manipulador.extrairParametrosEValores(expressaoComAspas);
		assertEquals(resultado2.get("param1"), "valor1");
		assertEquals(resultado2.get("param2"), "valor2 com detalhes");

		String expressaoComAspas2 = "param1 \"valor1 com detalhes\"";
		Map<String, String> resultado3 = this.manipulador.extrairParametrosEValores(expressaoComAspas2);
		assertEquals(resultado3.get("param1"), "valor1 com detalhes");
		
		String expressaoComAspas3 = "--param1 \"valor1 com detalhes\" --param2 valor2";
		Map<String, String> resultado4 = this.manipulador.extrairParametrosEValores(expressaoComAspas3);
		assertEquals(resultado4.get("param1"), "valor1 com detalhes");
		assertEquals(resultado4.get("param2"), "valor2");
		
		String expressaoComAspas4 = "--param1 \"valor1 com detalhes\" --param2 \"valor2 com detalhes\"";
		Map<String, String> resultado5 = this.manipulador.extrairParametrosEValores(expressaoComAspas4);
		assertEquals(resultado5.get("param1"), "valor1 com detalhes");
		assertEquals(resultado5.get("param2"), "valor2 com detalhes");
	}

}
