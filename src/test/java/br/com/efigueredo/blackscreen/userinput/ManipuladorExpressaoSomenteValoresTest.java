package br.com.efigueredo.blackscreen.userinput;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.com.efigueredo.blackscreen.userinput.exception.ExpressaoInvalidaException;

class ManipuladorExpressaoSomenteValoresTest {
	
	private ManipuladorExpressaoSomenteValores manipulador = new ManipuladorExpressaoSomenteValores();

	@Test
	public void deveriaRetornarUmaLista_ComValoresSeparados_QuandoExpressaoSemParametros_SemComando() throws ExpressaoInvalidaException {
		String expessao1 = "valor1 valor2 valor3";
		List<String> resultado1 = this.manipulador.extrairValores(expessao1);
		assertEquals(resultado1.get(0), "valor1");
		assertEquals(resultado1.get(1), "valor2");
		assertEquals(resultado1.get(2), "valor3");
		
		String expessao4 = "\"valor1 com detalhes\" valor2 valor3";
		List<String> resultado4 = this.manipulador.extrairValores(expessao4);
		assertEquals(resultado4.get(0), "valor1 com detalhes");
		assertEquals(resultado4.get(1), "valor2");
		assertEquals(resultado4.get(2), "valor3");
		
		String expessao2 = "valor1 \"valor2 com detalhes\" valor3";
		List<String> resultado2 = this.manipulador.extrairValores(expessao2);
		assertEquals(resultado2.get(0), "valor1");
		assertEquals(resultado2.get(1), "valor2 com detalhes");
		assertEquals(resultado2.get(2), "valor3");
		
		String expessao6 = "valor1 valor2 \"valor3 com detalhes\"";
		List<String> resultado6 = this.manipulador.extrairValores(expessao6);
		assertEquals(resultado6.get(0), "valor1");
		assertEquals(resultado6.get(1), "valor2");
		assertEquals(resultado6.get(2), "valor3 com detalhes");
		
		String expessao5 = "\"valor1 com detalhes\" \"valor2 com detalhes\" valor3";
		List<String> resultado5 = this.manipulador.extrairValores(expessao5);
		assertEquals(resultado5.get(0), "valor1 com detalhes");
		assertEquals(resultado5.get(1), "valor2 com detalhes");
		assertEquals(resultado5.get(2), "valor3");
		
		String expessao7 = "valor1 \"valor2 com detalhes\" \"valor3 com detalhes\"";
		List<String> resultado7 = this.manipulador.extrairValores(expessao7);
		assertEquals(resultado7.get(0), "valor1");
		assertEquals(resultado7.get(1), "valor2 com detalhes");
		assertEquals(resultado7.get(2), "valor3 com detalhes");
		
		String expessao3 = "\"valor1 com detalhes\" \"valor2 com detalhes\" \"valor3 com detalhes\"";
		List<String> resultado3 = this.manipulador.extrairValores(expessao3);
		assertEquals(resultado3.get(0), "valor1 com detalhes");
		assertEquals(resultado3.get(1), "valor2 com detalhes");
		assertEquals(resultado3.get(2), "valor3 com detalhes");
		
		String expessao8 = "\"valor1 @\"com@\" detalhes\" valor2 valor3";
		List<String> resultado8 = this.manipulador.extrairValores(expessao8);
		assertEquals("valor1 @\"com@\" detalhes", resultado8.get(0));
		assertEquals("valor2", resultado8.get(1));
		assertEquals("valor3", resultado8.get(2));
		
		String expessao9 = "valor1 \"valor2 @\"com@\" detalhes\" valor3";
		List<String> resultado9 = this.manipulador.extrairValores(expessao9);
		assertEquals("valor1", resultado9.get(0));
		assertEquals("valor2 @\"com@\" detalhes", resultado9.get(1));
		assertEquals("valor3", resultado9.get(2));
		
		String expessao10 = "valor1 valor2 \"valor3 @\"com@\" detalhes\"";
		List<String> resultado10 = this.manipulador.extrairValores(expessao10);
		assertEquals(resultado10.get(0), "valor1");
		assertEquals(resultado10.get(1), "valor2");
		assertEquals(resultado10.get(2), "valor3 @\"com@\" detalhes");
	
		String expessao11 = "\"valor1 @\"com@\" detalhes\" \"valor2 @\"com@\" detalhes\" valor3";
		List<String> resultado11 = this.manipulador.extrairValores(expessao11);
		assertEquals(resultado11.get(0), "valor1 @\"com@\" detalhes");
		assertEquals(resultado11.get(1), "valor2 @\"com@\" detalhes");
		assertEquals(resultado11.get(2), "valor3");
		
		String expessao12 = "valor1 \"valor2 @\"com@\" detalhes\" \"valor3 @\"com@\" detalhes\"";
		List<String> resultado12 = this.manipulador.extrairValores(expessao12);
		assertEquals(resultado12.get(0), "valor1");
		assertEquals(resultado12.get(1), "valor2 @\"com@\" detalhes");
		assertEquals(resultado12.get(2), "valor3 @\"com@\" detalhes");
		
		String expessao13 = "\"valor1 @\"com@\" detalhes\" \"valor2 @\"com@\" detalhes\" \"valor3 @\"com@\" detalhes\"";
		List<String> resultado13 = this.manipulador.extrairValores(expessao13);
		assertEquals(resultado13.get(0), "valor1 @\"com@\" detalhes");
		assertEquals(resultado13.get(1), "valor2 @\"com@\" detalhes");
		assertEquals(resultado13.get(2), "valor3 @\"com@\" detalhes");
		
		String expessao14 = "\"valor1 @\"com@\" detalhes\" \"valor2 com detalhes\" \"valor3 @\"com@\" detalhes\"";
		List<String> resultado14 = this.manipulador.extrairValores(expessao14);
		assertEquals(resultado14.get(0), "valor1 @\"com@\" detalhes");
		assertEquals(resultado14.get(1), "valor2 com detalhes");
		assertEquals(resultado14.get(2), "valor3 @\"com@\" detalhes");
		
		
	}
	
	@Test
	public void deveriaLancarExcecao_QuandoExpressaoSoValoresIncorreta() throws ExpressaoInvalidaException {
		String expessao1 = "valor1 @\"valor2@\" valor3";
		assertThrows(ExpressaoInvalidaException.class, () -> this.manipulador.extrairValores(expessao1));
	}

}
