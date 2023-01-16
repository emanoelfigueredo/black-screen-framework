package br.com.efigueredo.blackscreen.comandos.invocacao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import br.com.efigueredo.blackscreen.comandos.metodos.prototipos.PrototipoControladorComandosParametrosValores;
import br.com.efigueredo.blackscreen.comandos.metodos.prototipos.PrototipoControladorComandosSoValores;

class InvocadorMetodoTest {

	private InvocadorMetodo invocador = new InvocadorMetodo();

	@Test
	public void deveriaInvocarComando_SomenteValores_SemLancarExcecoes() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		PrototipoControladorComandosSoValores controlador = new PrototipoControladorComandosSoValores();
		
		Method metodo1SemParametros = PrototipoControladorComandosSoValores.class.getMethod("metodo1");
		List<String> valores1 = Arrays.asList();
		assertDoesNotThrow(() -> this.invocador.invocarComandoSemParametrosDeComando(controlador, metodo1SemParametros, valores1));
		
		Method metodo2Com1Parametro = PrototipoControladorComandosSoValores.class.getMethod("metodo2", String.class);
		List<String> valores2 = Arrays.asList("valor1");
		assertDoesNotThrow(() -> this.invocador.invocarComandoSemParametrosDeComando(controlador, metodo2Com1Parametro, valores2));
		
		Method metodo3Com2Parametros = PrototipoControladorComandosSoValores.class.getMethod("metodo3", String.class, String.class);
		List<String> valores3 = Arrays.asList("valor1", "valor2");
		assertDoesNotThrow(() -> this.invocador.invocarComandoSemParametrosDeComando(controlador, metodo3Com2Parametros, valores3));
		
		Method metodo4ComListaValores = PrototipoControladorComandosSoValores.class.getMethod("metodo4", List.class);
		List<String> valores4 = Arrays.asList("valor1", "valor2", "valor3", "valor4", "valor5");
		
		assertDoesNotThrow(() -> this.invocador.invocarComandoSemParametrosDeComando(controlador, metodo4ComListaValores, valores4));
	}
	
	@Test
	public void deveriaInvocarComando_ParametrosEValores_SemLancarExcecoes() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		PrototipoControladorComandosParametrosValores controlador = new PrototipoControladorComandosParametrosValores();
		
		Method metodoSomenteValoresUnicos = PrototipoControladorComandosParametrosValores.class.getMethod("metodo1", String.class, String.class);
		Map<String, List<String>> parametrosValores = new HashMap<String, List<String>>();
		parametrosValores.put("--param1", Arrays.asList("valor1"));
		parametrosValores.put("--param2", Arrays.asList("valor2"));
		assertDoesNotThrow(() -> this.invocador.invocarComandoComParametrosDeComando(controlador, metodoSomenteValoresUnicos, parametrosValores));
		
		Method metodoMuitosValores = PrototipoControladorComandosParametrosValores.class.getMethod("metodo3", List.class, List.class);
		Map<String, List<String>> parametrosValores2 = new HashMap<String, List<String>>();
		parametrosValores2.put("--param1", Arrays.asList("valor1", "valor2 com detalhes"));
		parametrosValores2.put("--param2", Arrays.asList("valor2", "valor2", "valor3"));
		assertDoesNotThrow(() -> this.invocador.invocarComandoComParametrosDeComando(controlador, metodoMuitosValores, parametrosValores2));
		
		Method metodoMuitosValoresValoresUnicos = PrototipoControladorComandosParametrosValores.class.getMethod("metodo2", String.class, List.class);
		Map<String, List<String>> parametrosValores3 = new HashMap<String, List<String>>();
		parametrosValores3.put("--param1", Arrays.asList("valor1"));
		parametrosValores3.put("--param2", Arrays.asList("valor2", "valor2", "valor3"));
		assertDoesNotThrow(() -> this.invocador.invocarComandoComParametrosDeComando(controlador, metodoMuitosValoresValoresUnicos, parametrosValores3));
		
	}

}
