package br.com.efigueredo.blackscreen.userinput;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

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
	
	@Test
	public void deveriaRetornarUmParametro_DadoAExpressaoInseridaComUmParametro() {
		String expressaoUmParametro = "comando --param";
		List<String> resultados = this.manipulador.extrairParametros(expressaoUmParametro);
		assertEquals(1, resultados.size());
		assertEquals("--param", resultados.get(0));
	}
	
	@Test
	public void deveriaRetornarDoisParametro_DadoAExpressaoInseridaComDoisParametro() {
		String expressaoDoisParametro = "comando --param1 valor1 valor 2 --param2 valor3";
		List<String> resultados = this.manipulador.extrairParametros(expressaoDoisParametro);
		assertEquals(2, resultados.size());
		assertEquals("--param1", resultados.get(0));
		assertEquals("--param2", resultados.get(1));
	}
	
	@Test 
	public void deveriaRetornarListaValores_DadoExpressao() {
		String expressao = "comando --param1 valor1 --param2 valor2 valor3";
		List<String> resultados = this.manipulador.extrairValores(expressao);
		assertEquals(3, resultados.size());
		assertEquals("valor1", resultados.get(0));
		assertEquals("valor2", resultados.get(1));
		assertEquals("valor3", resultados.get(2));
	}


}
