package br.com.efigueredo.blackscreen.comandos.invocacao;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.efigueredo.blackscreen.comandos.invocacao.exception.InvocacaoComandoInterrompidaException;
import br.com.efigueredo.blackscreen.comandos.invocacao.prototipo.PrototipoControlador;
import br.com.efigueredo.blackscreen.comandos.invocacao.prototipo.PrototipoControladorComDependencia;
import br.com.efigueredo.blackscreen.comandos.invocacao.prototipo.PrototipoControladorDependenciaInvalida;
import br.com.efigueredo.blackscreen.comandos.invocacao.prototipo.PrototipoControladorDuploConstrutorAnotado;
import br.com.efigueredo.blackscreen.comandos.invocacao.prototipo.PrototipoControladorSemConstrutorAdequando;
import br.com.efigueredo.container.exception.ClasseIlegalParaIntanciaException;
import br.com.efigueredo.container.exception.ContainerIocException;
import br.com.efigueredo.container.exception.InversaoDeControleInvalidaException;

class InvocadorComandoUnitarioTest {
	
	private InvocadorComando invocador;
	
	@Mock
	private InstanciadorControlador intanciador;
	
	@Mock
	private InvocadorMetodo invocadorMetodo;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		this.invocador = new InvocadorComando();
	}

	@Test
	void deveriaInvocarComandoSemParametrosDeControladorSemDependencias() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InvocacaoComandoInterrompidaException, ContainerIocException  {
		Class<?> controlador = PrototipoControlador.class;
		Object objetoControlador = controlador.getDeclaredConstructor().newInstance();
		Method comando = controlador.getMethod("comando1");
		List<String> parametros = Arrays.asList();
		
		when(this.intanciador.intanciarControlador(controlador)).thenReturn(objetoControlador);
		this.invocador.invocarComando(controlador, comando, parametros);
	}
	
	@Test
	void deveriaInvocarComandoSemParametrosDeControladorComDependencias() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InvocacaoComandoInterrompidaException, ContainerIocException  {
		Class<?> controlador = PrototipoControladorComDependencia.class;
		Object objetoControlador = controlador.getDeclaredConstructor(PrototipoControlador.class).newInstance(new PrototipoControlador());
		Method comando = controlador.getMethod("comando1");
		List<String> parametros = Arrays.asList();
		
		when(this.intanciador.intanciarControlador(controlador)).thenReturn(objetoControlador);
		this.invocador.invocarComando(controlador, comando, parametros);
	}
	
	@Test
	void deveriaInvocarComandoComParametrosDeControladorSemDependencias() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InvocacaoComandoInterrompidaException, ContainerIocException  {
		Class<?> controlador = PrototipoControlador.class;
		Object objetoControlador = controlador.getDeclaredConstructor().newInstance();
		Method comando = controlador.getMethod("comando2", String.class);
		List<String> parametros = Arrays.asList("");
		
		when(this.intanciador.intanciarControlador(controlador)).thenReturn(objetoControlador);
		this.invocador.invocarComando(controlador, comando, parametros);
	}
	
	@Test
	void deveriaInvocarComandoComParametrosDeControladorComDependencias() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InvocacaoComandoInterrompidaException, ContainerIocException  {
		Class<?> controlador = PrototipoControladorComDependencia.class;
		Object objetoControlador = controlador.getDeclaredConstructor(PrototipoControlador.class).newInstance(new PrototipoControlador());
		Method comando = controlador.getMethod("comando2", String.class);
		List<String> parametros = Arrays.asList("");
		
		when(this.intanciador.intanciarControlador(controlador)).thenReturn(objetoControlador);
		this.invocador.invocarComando(controlador, comando, parametros);
	}
	
	@Test
	public void deveriaJogarExcecao_SeHouverMaisDeUmContrutorAnotado_ComArrobaInjecaoNoControlador() throws ContainerIocException  {
		Class<?> controlador = PrototipoControladorDuploConstrutorAnotado.class;
		Method comando = null;
		List<String> parametros = Arrays.asList("");
		
		when(this.intanciador.intanciarControlador(controlador)).thenThrow(InversaoDeControleInvalidaException.class);
		assertThrows(InversaoDeControleInvalidaException.class, () -> this.invocador.invocarComando(controlador, comando, parametros));
	}
	
	@Test
	public void deveriaJogarExcecao_SeNaoHouverConstrutorPadraoENaoHouverConstrutorAnotado_ComArrobaInjecaoNoControlador() throws ContainerIocException  {
		Class<?> controlador = PrototipoControladorSemConstrutorAdequando.class;
		Method comando = null;
		List<String> parametros = Arrays.asList("");
		
		when(this.intanciador.intanciarControlador(controlador)).thenThrow(InversaoDeControleInvalidaException.class);
		assertThrows(InversaoDeControleInvalidaException.class, () -> this.invocador.invocarComando(controlador, comando, parametros));
	}
	
	@Test
	public void deveriaJogarExcecao_SeADependenciaInterfaceNaoEstiverConfigurada() throws InversaoDeControleInvalidaException, ClasseIlegalParaIntanciaException {
		try {
			Class<?> controlador = PrototipoControladorDependenciaInvalida.class;
			Method comando = null;
			List<String> parametros = Arrays.asList("");
			
			when(this.intanciador.intanciarControlador(controlador)).thenThrow(InversaoDeControleInvalidaException.class);
			assertThrows(ClasseIlegalParaIntanciaException.class, () -> this.invocador.invocarComando(controlador, comando, parametros));
		} catch (Exception e) {}
	}

}
