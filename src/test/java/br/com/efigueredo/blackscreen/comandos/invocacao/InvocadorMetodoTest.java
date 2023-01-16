package br.com.efigueredo.blackscreen.comandos.invocacao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.efigueredo.blackscreen.comandos.invocacao.exception.InvocacaoComandoInterrompidaException;
import br.com.efigueredo.blackscreen.comandos.invocacao.prototipo.PrototipoControlador;

class InvocadorMetodoTest {

	private InvocadorMetodo invocador;

	@BeforeEach
	void setUp() throws Exception {
		this.invocador = new InvocadorMetodo();
	}

	@Test
	void deveriaInvocarOMetodoControladorSemParametro() throws NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InvocacaoComandoInterrompidaException {
		PrototipoControlador controlador = new PrototipoControlador();
		Method metodo = controlador.getClass().getMethod("comando1");
		this.invocador.invocar(controlador, metodo, Arrays.asList());
	}

	@Test
	void deveriaInvocarOMetodoControladorComParametro() throws NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InvocacaoComandoInterrompidaException {
		PrototipoControlador controlador = new PrototipoControlador();
		Method metodo = controlador.getClass().getMethod("comando2", String.class);
		this.invocador.invocar(controlador, metodo, Arrays.asList(""));
	}

}
