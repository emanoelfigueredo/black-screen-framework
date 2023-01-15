package br.com.efigueredo.blackscreen.userinput;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import br.com.efigueredo.blackscreen.userinput.exception.ExpressaoInvalidaException;

class ManipuladorExpressaoParametrosEValoresTest {
	
	private ManipuladorExpressaoParametrosEValores manipulador = new ManipuladorExpressaoParametrosEValores();

	@Test
	public void deveriaRetornarUmMapa_DeParametroValores_DadoUmaExpressao_SemComando() throws ExpressaoInvalidaException {
		String expressaoSemAspas = "--param1 valor1 --param2 valor2";
		Map<String, List<String>> resultado1 = this.manipulador.extrairParametrosEValores(expressaoSemAspas);
		assertEquals(resultado1.get("param1").get(0), "valor1");
		assertEquals(resultado1.get("param2").get(0), "valor2");
		
		String expressaoSemAspasVariosValores = "--param1 valor1 valor2 valor3 --param2 valor2";
		Map<String, List<String>> resultado15 = this.manipulador.extrairParametrosEValores(expressaoSemAspasVariosValores);
		assertEquals(resultado15.get("param1").get(0), "valor1");
		assertEquals(resultado15.get("param1").get(1), "valor2");
		assertEquals(resultado15.get("param1").get(2), "valor3");
		assertEquals(resultado15.get("param2").get(0), "valor2");
		
		String expressaoSemAspasVariosValores2 = "--param1 valor1 valor2 valor3 --param2 valor1 valor2 valor3";
		Map<String, List<String>> resultado16 = this.manipulador.extrairParametrosEValores(expressaoSemAspasVariosValores2);
		assertEquals(resultado16.get("param1").get(0), "valor1");
		assertEquals(resultado16.get("param1").get(1), "valor2");
		assertEquals(resultado16.get("param1").get(2), "valor3");
		assertEquals(resultado16.get("param2").get(0), "valor1");
		assertEquals(resultado16.get("param2").get(1), "valor2");
		assertEquals(resultado16.get("param2").get(2), "valor3");
		
		String expressaoComAspas = "--param1 valor1 --param2 \"valor2 com detalhes\"";
		Map<String, List<String>> resultado2 = this.manipulador.extrairParametrosEValores(expressaoComAspas);
		assertEquals(resultado2.get("param1").get(0), "valor1");
		assertEquals(resultado2.get("param2").get(0), "valor2 com detalhes");
		
		String expressaoComAspas15 = "--param1 valor1 valor2 --param2 \"valor2 com detalhes\" \"valor3 com detalhes\"";
		Map<String, List<String>> resultado50 = this.manipulador.extrairParametrosEValores(expressaoComAspas15);
		assertEquals(resultado50.get("param1").get(0), "valor1");
		assertEquals(resultado50.get("param1").get(1), "valor2");
		assertEquals(resultado50.get("param2").get(0), "valor2 com detalhes");
		assertEquals(resultado50.get("param2").get(1), "valor3 com detalhes");
		
		String expressaoComAspas16 = "--param1 \"valor1 com detalhes\" \"valor2 com detalhes\" --param2 \"valor2 com detalhes\" \"valor3 com detalhes\"";
		Map<String, List<String>> resultado51 = this.manipulador.extrairParametrosEValores(expressaoComAspas16);
		assertEquals(resultado51.get("param1").get(0), "valor1 com detalhes");
		assertEquals(resultado51.get("param1").get(1), "valor2 com detalhes");
		assertEquals(resultado51.get("param2").get(0), "valor2 com detalhes");
		assertEquals(resultado51.get("param2").get(1), "valor3 com detalhes");

		String expressaoComAspas2 = "param1 \"valor1 com detalhes\"";
		Map<String, List<String>> resultado3 = this.manipulador.extrairParametrosEValores(expressaoComAspas2);
		assertEquals(resultado3.get("param1").get(0), "valor1 com detalhes");
		
		String expressaoComAspas3 = "--param1 \"valor1 com detalhes\" --param2 valor2";
		Map<String, List<String>> resultado4 = this.manipulador.extrairParametrosEValores(expressaoComAspas3);
		assertEquals(resultado4.get("param1").get(0), "valor1 com detalhes");
		assertEquals(resultado4.get("param2").get(0), "valor2");
		
		String expressaoComAspas4 = "--param1 \"valor1 com detalhes\" --param2 \"valor2 com detalhes\"";
		Map<String, List<String>> resultado5 = this.manipulador.extrairParametrosEValores(expressaoComAspas4);
		assertEquals(resultado5.get("param1").get(0), "valor1 com detalhes");
		assertEquals(resultado5.get("param2").get(0), "valor2 com detalhes");
	}

}
