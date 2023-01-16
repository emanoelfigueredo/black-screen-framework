package br.com.efigueredo.blackscreen.comandos.metodos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.efigueredo.blackscreen.comandos.metodos.prototipos.PrototipoControlador4;

class ManipuladorMetodosComandosTest {
	
	private ManipuladorMetodosComandos manipulador;

	@BeforeEach
	void setUp() throws Exception {
		this.manipulador = new ManipuladorMetodosComandos();
	}

	@Test
	void dadoUmaListaDeMetodos_DeveRetornarMetodosAnotadosComParametrosCorrespondentes() throws NoSuchMethodException, SecurityException {
		List<Method> todosMetodos = Arrays.asList(PrototipoControlador4.class.getDeclaredMethods());
		String parametro1 = "--add";
		List<Method> resultado1 = this.manipulador.getMetodosAnotadosPorParametro(todosMetodos, parametro1);
		assertEquals(1, resultado1.size());
		assertEquals(PrototipoControlador4.class.getDeclaredMethod("metodo9"), resultado1.get(0));
		String parametro2 = "-a";
		List<Method> resultado2 = this.manipulador.getMetodosAnotadosPorParametro(todosMetodos, parametro2);
		assertEquals(1, resultado2.size());
		assertEquals(PrototipoControlador4.class.getDeclaredMethod("metodo9"), resultado2.get(0));
	}
	
	@Test
	void dadoUmaListaDeMetodosIncongruenteComOsParametros_DeveRetornarListaVaziaDeMetodos() throws NoSuchMethodException, SecurityException {
		List<Method> todosMetodos = Arrays.asList(PrototipoControlador4.class.getDeclaredMethods());
		String parametro1 = "--qualquer_parametro";
		List<Method> resultado1 = this.manipulador.getMetodosAnotadosPorParametro(todosMetodos, parametro1);
		assertTrue(resultado1.isEmpty());
		String parametro2 = "-qualquer_parametro";
		List<Method> resultado2 = this.manipulador.getMetodosAnotadosPorParametro(todosMetodos, parametro2);
		assertTrue(resultado2.isEmpty());
	}
	
	@Test
	public void dadoUmListaDeMetodosAnotadosComArrobaControle_deveriaRetornarTodosQueTenhamOParametroNomeIgualAoInserido() throws NoSuchMethodException, SecurityException {
		String nomeComando = "atualizar";
		List<Method> todosMetodos = Arrays.asList(PrototipoControlador4.class.getDeclaredMethods());
		List<Method> resultado = this.manipulador.getMetodosAnotadosPorNome(todosMetodos, nomeComando);
		assertEquals(3, resultado.size());
		assertTrue(resultado.contains((PrototipoControlador4.class.getDeclaredMethod("metodo3", String.class))));
		assertTrue(resultado.contains((PrototipoControlador4.class.getDeclaredMethod("metodo4", String.class))));
		assertTrue(resultado.contains((PrototipoControlador4.class.getDeclaredMethod("metodo6"))));
	}
	
	@Test
	public void dadoUmListaDeMetodosAnotadosComArrobaControle_deveriaRetornarUmListaVazia_QuandoNaoHouverMetodosComParametroNomeNaAnotacaoCorrespondentes() throws NoSuchMethodException, SecurityException {
		String nomeComando = "qualquer_nome";
		List<Method> todosMetodos = Arrays.asList(PrototipoControlador4.class.getDeclaredMethods());
		List<Method> resultado = this.manipulador.getMetodosAnotadosPorNome(todosMetodos, nomeComando);
		assertEquals(0, resultado.size());
	}


}
