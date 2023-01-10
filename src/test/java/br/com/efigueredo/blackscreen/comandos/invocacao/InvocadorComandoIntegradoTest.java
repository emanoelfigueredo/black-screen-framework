package br.com.efigueredo.blackscreen.comandos.invocacao;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.efigueredo.blackscreen.comandos.invocacao.exception.InvocacaoComandoInterrompidaException;
import br.com.efigueredo.blackscreen.comandos.invocacao.prototipo.PrototipoControlador;
import br.com.efigueredo.blackscreen.comandos.invocacao.prototipo.PrototipoControladorComDependencia;
import br.com.efigueredo.blackscreen.comandos.invocacao.prototipo.PrototipoControladorDependenciaInvalida;
import br.com.efigueredo.blackscreen.comandos.invocacao.prototipo.PrototipoControladorDuploConstrutorAnotado;
import br.com.efigueredo.blackscreen.comandos.invocacao.prototipo.PrototipoControladorSemConstrutorAdequando;
import br.com.efigueredo.container.exception.ContainerIocException;
import br.com.efigueredo.container.exception.InversaoDeControleInvalidaException;

class InvocadorComandoIntegradoTest {
	
	private InvocadorComando invocador;

	@BeforeEach
	void setUp() throws Exception {
		this.invocador = new InvocadorComando();
	}

	@Test
	void deveriaInvocarComandoSemParametrosDeControladorSemDependencias() throws NoSuchMethodException, SecurityException, InvocacaoComandoInterrompidaException, ContainerIocException{
		Class<?> controlador = PrototipoControlador.class;
		Method comando = controlador.getMethod("comando1");
		List<String> parametros = Arrays.asList();
		this.invocador.invocarComando(controlador, comando, parametros);
	}
	
	@Test
	void deveriaInvocarComandoSemParametrosDeControladorComDependencias() throws NoSuchMethodException, SecurityException, InvocacaoComandoInterrompidaException, ContainerIocException  {
		Class<?> controlador = PrototipoControladorComDependencia.class;
		Method comando = controlador.getMethod("comando1");
		List<String> parametros = Arrays.asList();
		this.invocador.invocarComando(controlador, comando, parametros);
	}
	
	@Test
	void deveriaInvocarComandoComParametrosDeControladorSemDependencias() throws NoSuchMethodException, SecurityException, InvocacaoComandoInterrompidaException, ContainerIocException  {
		Class<?> controlador = PrototipoControlador.class;
		Method comando = controlador.getMethod("comando2", String.class);
		List<String> parametros = Arrays.asList("");
		this.invocador.invocarComando(controlador, comando, parametros);
	}
	
	@Test
	void deveriaInvocarComandoComParametrosDeControladorComDependencias() throws NoSuchMethodException, SecurityException, InvocacaoComandoInterrompidaException, ContainerIocException  {
		Class<?> controlador = PrototipoControladorComDependencia.class;
		Method comando = controlador.getMethod("comando2", String.class);
		List<String> parametros = Arrays.asList("");
		this.invocador.invocarComando(controlador, comando, parametros);
	}
	
	@Test
	public void deveriaJogarExcecao_SeHouverMaisDeUmContrutorAnotado_ComArrobaInjecaoNoControlador()  {
		Class<?> controlador = PrototipoControladorDuploConstrutorAnotado.class;
		Method comando = null;
		List<String> parametros = Arrays.asList("");
		assertThrows(InversaoDeControleInvalidaException.class, () -> this.invocador.invocarComando(controlador, comando, parametros));
	}
	
	@Test
	public void deveriaJogarExcecao_SeNaoHouverConstrutorPadraoENaoHouverConstrutorAnotado_ComArrobaInjecaoNoControlador()  {
		Class<?> controlador = PrototipoControladorSemConstrutorAdequando.class;
		Method comando = null;
		List<String> parametros = Arrays.asList("");
		assertThrows(InversaoDeControleInvalidaException.class, () -> this.invocador.invocarComando(controlador, comando, parametros));
	}
	
	@Test
	public void deveriaJogarExcecao_SeADependenciaInterfaceNaoEstiverConfigurada()  {
		try {
			Class<?> controlador = PrototipoControladorDependenciaInvalida.class;
			Method comando = controlador.getMethod("comando2", String.class);
			List<String> parametros = Arrays.asList("");
			assertThrows(InversaoDeControleInvalidaException.class, () -> this.invocador.invocarComando(controlador, comando, parametros));
		} catch (Exception e) {}
	}

}
