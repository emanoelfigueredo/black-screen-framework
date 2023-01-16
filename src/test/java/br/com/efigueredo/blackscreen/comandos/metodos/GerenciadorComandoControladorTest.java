package br.com.efigueredo.blackscreen.comandos.metodos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import br.com.efigueredo.blackscreen.comandos.metodos.exception.ComandoInvalidoException;
import br.com.efigueredo.blackscreen.comandos.metodos.prototipos.PrototipoControladorComandosParametrosValoresIncorretosParametrosNaoAnotados;
import br.com.efigueredo.blackscreen.comandos.metodos.prototipos.PrototipoControladorComandosSoValores;
import br.com.efigueredo.blackscreen.comandos.metodos.prototipos.PrototipoControladorComandosSoValoresIncorretos2ListasValores;
import br.com.efigueredo.blackscreen.comandos.metodos.prototipos.PrototipoControladorComandosSoValoresIncorretos2MetodosMesmosParametros;
import br.com.efigueredo.blackscreen.comandos.metodos.prototipos.PrototipoControladorComandosSoValoresIncorretosDoisParametrosListas;
import br.com.efigueredo.blackscreen.comandos.metodos.prototipos.PrototipoControladorComandosSoValoresIncorretosSemMetodoQueAtendam;
import br.com.efigueredo.blackscreen.sistema.exception.BlackStreenException;
import br.com.efigueredo.blackscreen.userinput.expressao.ExpressaoUsuarioParametrosValores;
import br.com.efigueredo.blackscreen.userinput.expressao.ExpressaoUsuarioValores;

@Tag("integracao")
class GerenciadorComandoControladorTest {

	private GerenciadorComandoControlador gerenciador = new GerenciadorComandoControlador();

	@Test
	public void deveriaRetornarOComandoAdequado_ParaExpressoesSomenteComValores()
			throws BlackStreenException, NoSuchMethodException, SecurityException {
		// Comando com lista de valores vazia
		List<String> valores = Arrays.asList();
		ExpressaoUsuarioValores expressao = new ExpressaoUsuarioValores("adicionar", valores);
		Method metodoEsperado = PrototipoControladorComandosSoValores.class.getMethod("metodo1");
		Method metodoEncontrado = this.gerenciador.getMetodoComando(expressao,
				PrototipoControladorComandosSoValores.class);
		assertEquals(metodoEsperado, metodoEncontrado);

		// Comando com lista contendo somente um valor
		List<String> valores2 = Arrays.asList("valor1");
		ExpressaoUsuarioValores expressao2 = new ExpressaoUsuarioValores("adicionar", valores2);
		Method metodoEsperado2 = PrototipoControladorComandosSoValores.class.getMethod("metodo2", String.class);
		Method metodoEncontrado2 = this.gerenciador.getMetodoComando(expressao2,
				PrototipoControladorComandosSoValores.class);
		assertEquals(metodoEsperado2, metodoEncontrado2);

		// Comando com lista contendo dois valores
		List<String> valores3 = Arrays.asList("valor1", "valor2");
		ExpressaoUsuarioValores expressao3 = new ExpressaoUsuarioValores("adicionar", valores3);
		Method metodoEsperado3 = PrototipoControladorComandosSoValores.class.getMethod("metodo3", String.class,
				String.class);
		Method metodoEncontrado3 = this.gerenciador.getMetodoComando(expressao3,
				PrototipoControladorComandosSoValores.class);
		assertEquals(metodoEsperado3, metodoEncontrado3);

		// Comando com lista contendo tres valores, não existe metodo que suporte 3
		// valores, portanto, deve retornar o metodo
		// que receba uma lista de valores se existir.
		List<String> valores4 = Arrays.asList("valor1", "valor2", "valor3");
		ExpressaoUsuarioValores expressao4 = new ExpressaoUsuarioValores("adicionar", valores4);
		Method metodoEsperado4 = PrototipoControladorComandosSoValores.class.getMethod("metodo4", List.class);
		Method metodoEncontrado4 = this.gerenciador.getMetodoComando(expressao4,
				PrototipoControladorComandosSoValores.class);
		assertEquals(metodoEsperado4, metodoEncontrado4);
	}

	@Test
	public void deveriaLancarExcecao_QuandoMetodosComandoSomenteValores_EstiveremIncorretos()
			throws NoSuchMethodException, SecurityException {
		// Dois métodos com o mesmo nome de comando sem parâmetro que recebe somente uma
		// lista de valores.
		List<String> valores = Arrays.asList();
		ExpressaoUsuarioValores expressao = new ExpressaoUsuarioValores("adicionar", valores);
		assertThrows(ComandoInvalidoException.class,
				() -> this.gerenciador.getMetodoComando(expressao,
						PrototipoControladorComandosSoValoresIncorretos2ListasValores.class),
				"Existe mais de um comando com o nome " + expressao.getComando() + " que receba um List<String> "
						+ "como único parâmetro. Só é possível que exista um comando com essa característica.");

		// Dois métodos com o mesmo nome de comando sem parâmetro que recebe a mesma
		// quantidade de parametros sem ser listas
		List<String> valores2 = Arrays.asList("valor1");
		ExpressaoUsuarioValores expressao2 = new ExpressaoUsuarioValores("adicionar", valores2);
		List<Method> metodos = Arrays.asList(
				PrototipoControladorComandosSoValoresIncorretos2MetodosMesmosParametros.class.getDeclaredMethods());
		assertThrows(ComandoInvalidoException.class,
				() -> this.gerenciador.getMetodoComando(expressao2,
						PrototipoControladorComandosSoValoresIncorretos2MetodosMesmosParametros.class),
				"Para o comando " + expressao.getComando()
						+ " existe mais de um método que receba a quantidade de parâmetros inserida. Mantenha somente um.\nMétodos: "
						+ metodos);

		// Não existe métodos que possa atender aos valores inseridos
		List<String> valores3 = Arrays.asList("valor1", "valor2");
		ExpressaoUsuarioValores expressao3 = new ExpressaoUsuarioValores("adicionar", valores3);
		assertThrows(ComandoInvalidoException.class,
				() -> this.gerenciador.getMetodoComando(expressao3,
						PrototipoControladorComandosSoValoresIncorretosSemMetodoQueAtendam.class),
				"Não existe comando que possa receber " + expressao.getValores().size()
						+ " valor/es e nem comando que possa receber uma lista de valores na classe controladora ["
						+ PrototipoControladorComandosSoValoresIncorretosSemMetodoQueAtendam.class + "].");

		// Metodo que recebe dois parametros, onde os dois são uma lista de valores sem
		// estarem anotadas como parâmetro de comando.
		List<String> valores4 = Arrays.asList("valor1", "valor2");
		ExpressaoUsuarioValores expressao4 = new ExpressaoUsuarioValores("adicionar", valores4);
		Method metodo = PrototipoControladorComandosSoValoresIncorretosDoisParametrosListas.class.getMethod("metodo1",
				List.class, List.class);
		assertThrows(ComandoInvalidoException.class,
				() -> this.gerenciador.getMetodoComando(expressao4,
						PrototipoControladorComandosSoValoresIncorretosDoisParametrosListas.class),
				"O comando [" + metodo
						+ "] não pode receber duas listas de valores sem estar anotado a um parâmetro de comando.");
	}

	@Test
	public void deveriaRetornarMetodoAdequado_ParaExpressoesComParametrosEValores()
			throws BlackStreenException, NoSuchMethodException, SecurityException {
		// Metodo que recebe valores unicos
		Map<String, List<String>> parametrosValores = new HashMap<>();
		parametrosValores.put("--param1", Arrays.asList("valor1"));
		parametrosValores.put("--param2", Arrays.asList("valor1"));
		ExpressaoUsuarioParametrosValores expressao = new ExpressaoUsuarioParametrosValores("adicionar",
				parametrosValores);
		Method metodoEsperado = PrototipoControladorComandosParametrosValores.class.getMethod("metodo1", String.class,
				String.class);
		Method metodoEncontrado = this.gerenciador.getMetodoComando(expressao,
				PrototipoControladorComandosParametrosValores.class);
		assertEquals(metodoEsperado, metodoEncontrado);

		// Metodo que recebe mais de um valor por parametro.
		Map<String, List<String>> parametrosValores2 = new HashMap<>();
		parametrosValores2.put("--param1", Arrays.asList("valor1"));
		parametrosValores2.put("--param2", Arrays.asList("valor1", "valor2"));
		ExpressaoUsuarioParametrosValores expressao2 = new ExpressaoUsuarioParametrosValores("adicionar",
				parametrosValores2);
		Method metodoEsperado2 = PrototipoControladorComandosParametrosValores.class.getMethod("metodo2", String.class,
				List.class);
		Method metodoEncontrado2 = this.gerenciador.getMetodoComando(expressao2,
				PrototipoControladorComandosParametrosValores.class);
		assertEquals(metodoEsperado2, metodoEncontrado2);

		// Metodo que recebe mais de um valor por parametro.
		Map<String, List<String>> parametrosValores3 = new HashMap<>();
		parametrosValores3.put("--param1", Arrays.asList("valor1", "valor2", "valor3"));
		parametrosValores3.put("--param2", Arrays.asList("valor1", "valor2"));
		ExpressaoUsuarioParametrosValores expressao3 = new ExpressaoUsuarioParametrosValores("adicionar",
				parametrosValores3);
		Method metodoEsperado3 = PrototipoControladorComandosParametrosValores.class.getMethod("metodo3", List.class,
				List.class);
		Method metodoEncontrado3 = this.gerenciador.getMetodoComando(expressao3,
				PrototipoControladorComandosParametrosValores.class);
		assertEquals(metodoEsperado3, metodoEncontrado3);

		// Metodo que recebe mais de um valor por parametro.
		Map<String, List<String>> parametrosValores4 = new HashMap<>();
		parametrosValores4.put("--param1", Arrays.asList("valor1", "valor2", "valor3"));
		parametrosValores4.put("--param2", Arrays.asList("valor1", "valor2"));
		parametrosValores4.put("--param3", Arrays.asList("valor1", "valor2", "valor3", "valor4"));
		ExpressaoUsuarioParametrosValores expressao4 = new ExpressaoUsuarioParametrosValores("adicionar",
				parametrosValores4);
		Method metodoEsperado4 = PrototipoControladorComandosParametrosValores.class.getMethod("metodo4", List.class,
				List.class, List.class);
		Method metodoEncontrado4 = this.gerenciador.getMetodoComando(expressao4,
				PrototipoControladorComandosParametrosValores.class);
		assertEquals(metodoEsperado4, metodoEncontrado4);
	}

	@Test
	public void deveriaLancarExcecao_DadasAsSituacoes_QuandoObterComandoDeExpressaoComParametrosEValores()
			throws BlackStreenException, NoSuchMethodException, SecurityException {
		// Quando os métodos não possuem todos os parametros anotados
		ExpressaoUsuarioParametrosValores expressao4 = new ExpressaoUsuarioParametrosValores("adicionar", null);
		Method metodo = PrototipoControladorComandosParametrosValoresIncorretosParametrosNaoAnotados.class
				.getMethod("metodo4", List.class, List.class, List.class);
		assertThrows(ComandoInvalidoException.class,
				() -> this.gerenciador.getMetodoComando(expressao4,
						PrototipoControladorComandosParametrosValoresIncorretosParametrosNaoAnotados.class),
				"O comando [" + metodo + "] não possui todos os parâmetros anotados com @Parametro.");

		// Não existe metodos de parametros de comando na classe controladora
		Map<String, List<String>> parametrosValores5 = new HashMap<>();
		parametrosValores5.put("--param1", Arrays.asList("valor1", "valor2", "valor3"));
		ExpressaoUsuarioParametrosValores expressao5 = new ExpressaoUsuarioParametrosValores("adicionar",
				parametrosValores5);
		assertThrows(ComandoInvalidoException.class,
				() -> this.gerenciador.getMetodoComando(expressao5, PrototipoControladorComandosSoValores.class),
				"Não existe comando com parâmetros na classe controladora.");

		// Não existe métodos com todos os nomes de parâmetros correspondentes
		Map<String, List<String>> parametrosValores6 = new HashMap<>();
		parametrosValores6.put("--desconhecido1", Arrays.asList("valor1", "valor2", "valor3"));
		parametrosValores6.put("--desconhecido2", Arrays.asList("valor1", "valor2", "valor3"));
		ExpressaoUsuarioParametrosValores expressao6 = new ExpressaoUsuarioParametrosValores("adicionar",
				parametrosValores6);
		assertThrows(ComandoInvalidoException.class,
				() -> this.gerenciador.getMetodoComando(expressao6,
						PrototipoControladorComandosParametrosValores.class),
				"Os comandos correspondentes não possuem os parâmetros inseridos.\nParâmetros: "
						+ expressao6.getParametrosValores().keySet());

		// Não existe métodos que suportem receber a quantidae de valores inserida
		Map<String, List<String>> parametrosValores7 = new HashMap<>();
		parametrosValores7.put("--param1", Arrays.asList("valor1"));
		parametrosValores7.put("--param2", Arrays.asList("valor1"));
		parametrosValores7.put("--param3", Arrays.asList("valor1"));
		parametrosValores7.put("--param4", Arrays.asList("valor1", "valor2", "valor3"));
		ExpressaoUsuarioParametrosValores expressao7 = new ExpressaoUsuarioParametrosValores("adicionar",
				parametrosValores7);
		assertThrows(ComandoInvalidoException.class, () -> this.gerenciador.getMetodoComando(expressao7,
				PrototipoControladorComandosParametrosValores.class));

		// Existem métodos de parametros de comando iguais no controlador
		Map<String, List<String>> parametrosValores8 = new HashMap<>();
		parametrosValores8.put("--param1", Arrays.asList("valor1"));
		parametrosValores8.put("--param2", Arrays.asList("valor1", "valor2", "valor3"));
		parametrosValores8.put("--param3", Arrays.asList("valor1", "valor2", "valor3"));
		ExpressaoUsuarioParametrosValores expressao8 = new ExpressaoUsuarioParametrosValores("adicionar",
				parametrosValores8);
		assertThrows(ComandoInvalidoException.class, () -> this.gerenciador.getMetodoComando(expressao8,
				PrototipoControladorComandosParametrosValores.class));

	}

}
