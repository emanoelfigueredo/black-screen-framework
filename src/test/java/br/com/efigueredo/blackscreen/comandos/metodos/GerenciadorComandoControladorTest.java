package br.com.efigueredo.blackscreen.comandos.metodos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import br.com.efigueredo.blackscreen.comandos.metodos.exception.NomeComandoInexistenteException;
import br.com.efigueredo.blackscreen.comandos.metodos.exception.ParametroDeComandoInexistenteException;
import br.com.efigueredo.blackscreen.comandos.metodos.exception.SolicitacaoDeMetodoComandoInexistenteException;
import br.com.efigueredo.blackscreen.comandos.metodos.exception.ValoresIncoerentesComOsComandosExistentesException;
import br.com.efigueredo.blackscreen.comandos.metodos.prototipos.PrototipoControlador4;
import br.com.efigueredo.blackscreen.sistema.Sistema;
import br.com.efigueredo.blackscreen.userinput.EntradaUsuario;

@Tag("integracao")
class GerenciadorComandoControladorTest {

	private Sistema sistema;
	private GerenciadorComandoControlador gerenciador;

	@BeforeEach
	void setUp() throws Exception {
		this.sistema = new Sistema(PrototipoControlador4.class);
		this.gerenciador = new GerenciadorComandoControlador();
	}

	@Test
	public void deveriaRetornarTodosOsMetodosAnotadosComArrobaComandoDoPrototipoControlador4_TalQueNomeDaAnotacaoSejaAtualizar()
			throws NoSuchMethodException, SecurityException, NomeComandoInexistenteException {
		List<Method> metodos = this.gerenciador.getMetodosAnotadosComParametroNomeCorrespondente("atualizar");
		assertTrue(metodos.contains(PrototipoControlador4.class.getDeclaredMethod("metodo3", String.class)));
		assertTrue(metodos.contains(PrototipoControlador4.class.getDeclaredMethod("metodo4", String.class)));
		assertTrue(metodos.contains(PrototipoControlador4.class.getDeclaredMethod("metodo6")));
		assertFalse(metodos.contains(PrototipoControlador4.class.getDeclaredMethod("metodo1")));
		assertFalse(metodos.contains(PrototipoControlador4.class.getDeclaredMethod("metodo2")));
		assertFalse(metodos.contains(PrototipoControlador4.class.getDeclaredMethod("metodo5")));
	}

	@Test
	public void deveriaJogarExcecao_QuandoMetodosAnotadosNaoTeremNomeComandoCorrespondenteNaAnotacao() {
		try {
			assertThrows(NomeComandoInexistenteException.class, () -> this.gerenciador
					.getMetodosAnotadosComParametroNomeCorrespondente("nome_comando_inexistente"));
		} catch (Exception e) {
		}
	}

	@Test
	public void deveriaRetornarMetodosAnotadosComOValorParametroDaAnotacaoCorrespondente()
			throws NomeComandoInexistenteException, NoSuchMethodException, SecurityException,
			ParametroDeComandoInexistenteException {
		EntradaUsuario entradaUsuario = new EntradaUsuario("atualizar", Arrays.asList("--nome"), Arrays.asList("any"));
		List<Method> metodos = this.gerenciador
				.getMetodosAnotadosComParametroNomeCorrespondente(entradaUsuario.getComando());
		List<Method> resultado = this.gerenciador.getMetodosAnotadosComValorParametroCorrespondente(metodos,
				entradaUsuario.getParametros());
		assertEquals(1, resultado.size());
		assertTrue(resultado.contains(PrototipoControlador4.class.getDeclaredMethod("metodo3", String.class)));
	}

	@Test
	public void deveriaRetornarOsMetodosSemParametrosDeComando_QuandoNaoHouverParametrosDeComandoNaEntradaUsuario()
			throws NomeComandoInexistenteException, NoSuchMethodException, SecurityException,
			ParametroDeComandoInexistenteException {
		EntradaUsuario entradaUsuario = new EntradaUsuario("atualizar", Arrays.asList(), Arrays.asList("any"));
		List<Method> metodos = this.gerenciador
				.getMetodosAnotadosComParametroNomeCorrespondente(entradaUsuario.getComando());
		List<Method> resultado = this.gerenciador.getMetodosAnotadosComValorParametroCorrespondente(metodos,
				entradaUsuario.getParametros());
		assertTrue(resultado.size() == 1);
		assertEquals(PrototipoControlador4.class.getMethod("metodo6").getName(), resultado.get(0).getName());
	}

	@Test
	public void deveriaJogarExecao_QuandoHouverParametroDeComandoNaEntradaUsuario_EOParametroNaoForEncontradoNosMetodosAnotados()
			throws NomeComandoInexistenteException, NoSuchMethodException, SecurityException,
			ParametroDeComandoInexistenteException {
		try {
			EntradaUsuario entradaUsuario = new EntradaUsuario("atualizar", Arrays.asList("--param_inexistente"),
					Arrays.asList("any"));
			List<Method> metodos = this.gerenciador
					.getMetodosAnotadosComParametroNomeCorrespondente(entradaUsuario.getComando());
			assertThrows(ParametroDeComandoInexistenteException.class, () -> this.gerenciador
					.getMetodosAnotadosComValorParametroCorrespondente(metodos, entradaUsuario.getParametros()));

		} catch (Exception e) {
		}
	}

	@Test
	public void deveriaRetornarMetodosComParametrosCorrespondentesAosValoresDaEntradaUsuario()
			throws ParametroDeComandoInexistenteException, NomeComandoInexistenteException, NoSuchMethodException,
			SecurityException, ValoresIncoerentesComOsComandosExistentesException,
			SolicitacaoDeMetodoComandoInexistenteException {
		EntradaUsuario entradaUsuario = new EntradaUsuario("listar", Arrays.asList("--all"), Arrays.asList("valor1"));
		List<Method> metodos = this.gerenciador
				.getMetodosAnotadosComParametroNomeCorrespondente(entradaUsuario.getComando());
		metodos = this.gerenciador.getMetodosPorTiposParametros(metodos, entradaUsuario.getClassesDosValores());
		List<Method> resultado = this.gerenciador.getMetodosAnotadosComValorParametroCorrespondente(metodos,
				entradaUsuario.getParametros());
		assertEquals(1, resultado.size());
		assertTrue(resultado.contains(PrototipoControlador4.class.getDeclaredMethod("metodo7", String.class)));
	}

	@Test
	public void deveriaRetornarMetodosSemParametros_QuandoNaoHouverValoresNaEntradaUsuario()
			throws NomeComandoInexistenteException, NoSuchMethodException, SecurityException,
			ParametroDeComandoInexistenteException, ValoresIncoerentesComOsComandosExistentesException,
			SolicitacaoDeMetodoComandoInexistenteException {
		EntradaUsuario entradaUsuario = new EntradaUsuario("listar", Arrays.asList("--all"), Arrays.asList());
		List<Method> metodos = this.gerenciador
				.getMetodosAnotadosComParametroNomeCorrespondente(entradaUsuario.getComando());
		metodos = this.gerenciador.getMetodosPorTiposParametros(metodos, entradaUsuario.getClassesDosValores());
		List<Method> resultado = this.gerenciador.getMetodosAnotadosComValorParametroCorrespondente(metodos,
				entradaUsuario.getParametros());
		assertEquals(1, resultado.size());
		assertTrue(resultado.contains(PrototipoControlador4.class.getDeclaredMethod("metodo8")));
	}

	@Test
	public void deveriaLancarExcecao_QuandoNaoHouverMetodoCorrespondenteAosValoresInseridosNaEntradaUsuario()
			throws NomeComandoInexistenteException, NoSuchMethodException, SecurityException,
			ParametroDeComandoInexistenteException {
		try {
			EntradaUsuario entradaUsuario = new EntradaUsuario("listar", Arrays.asList("--all"),
					Arrays.asList("valor1", "valor2", "valor3"));
			List<Method> metodos = this.gerenciador
					.getMetodosAnotadosComParametroNomeCorrespondente(entradaUsuario.getComando());
			List<Method> resultado = this.gerenciador.getMetodosAnotadosComValorParametroCorrespondente(metodos,
					entradaUsuario.getParametros());
			assertThrows(ValoresIncoerentesComOsComandosExistentesException.class, () -> this.gerenciador
					.getMetodosPorTiposParametros(resultado, entradaUsuario.getClassesDosValores()));
		} catch (Exception e) {
		}
	}

	@Test
	public void dadoAEntradaDoUsuario_DeveriaRetornarOMetodoCorrespondenteAoSeuComando() throws NoSuchMethodException,
			SecurityException, NomeComandoInexistenteException, ParametroDeComandoInexistenteException,
			ValoresIncoerentesComOsComandosExistentesException, SolicitacaoDeMetodoComandoInexistenteException {
		EntradaUsuario entradaUsuario1 = new EntradaUsuario("atualizar", Arrays.asList("--nome"),
				Arrays.asList("nome"));
		Method comando1 = this.gerenciador.getMetodoComando(entradaUsuario1);
		assertTrue(comando1.equals(PrototipoControlador4.class.getDeclaredMethod("metodo3", String.class)));

		EntradaUsuario entradaUsuario2 = new EntradaUsuario("atualizar", Arrays.asList("-n"), Arrays.asList("id"));
		Method comando2 = this.gerenciador.getMetodoComando(entradaUsuario2);
		assertTrue(comando2.equals(PrototipoControlador4.class.getDeclaredMethod("metodo3", String.class)));

		EntradaUsuario entradaUsuario3 = new EntradaUsuario("atualizar", Arrays.asList("--id"), Arrays.asList("id"));
		Method comando3 = this.gerenciador.getMetodoComando(entradaUsuario3);
		assertTrue(comando3.equals(PrototipoControlador4.class.getDeclaredMethod("metodo4", String.class)));

		EntradaUsuario entradaUsuario4 = new EntradaUsuario("atualizar", Arrays.asList("-i"), Arrays.asList("id"));
		Method comando4 = this.gerenciador.getMetodoComando(entradaUsuario4);
		assertTrue(comando4.equals(PrototipoControlador4.class.getDeclaredMethod("metodo4", String.class)));

		EntradaUsuario entradaUsuario5 = new EntradaUsuario("command5", Arrays.asList(), Arrays.asList());
		Method comando5 = this.gerenciador.getMetodoComando(entradaUsuario5);
		assertTrue(comando5.equals(PrototipoControlador4.class.getDeclaredMethod("metodo5")));

		EntradaUsuario entradaUsuario6 = new EntradaUsuario("remove", Arrays.asList("-n"), Arrays.asList());
		Method comando6 = this.gerenciador.getMetodoComando(entradaUsuario6);
		assertTrue(comando6.equals(PrototipoControlador4.class.getDeclaredMethod("metodo2")));

		EntradaUsuario entradaUsuario7 = new EntradaUsuario("remove", Arrays.asList(), Arrays.asList("valor1"));
		Method comando7 = this.gerenciador.getMetodoComando(entradaUsuario7);
		assertTrue(comando7.equals(PrototipoControlador4.class.getDeclaredMethod("metodo8", String.class)));

	}

	@Test
	public void deveriaLancarExcecao_QuandoComandoInseridoNaoTemOsValoresNecessariosParaAInvocacao()
			throws NomeComandoInexistenteException, ParametroDeComandoInexistenteException,
			ValoresIncoerentesComOsComandosExistentesException, SolicitacaoDeMetodoComandoInexistenteException {
		EntradaUsuario entradaUsuario7 = new EntradaUsuario("metodo9", Arrays.asList(), Arrays.asList());
		assertThrows(SolicitacaoDeMetodoComandoInexistenteException.class,
				() -> this.gerenciador.getMetodoComando(entradaUsuario7));
	}

}
