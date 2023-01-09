package br.com.efigueredo.blackscreen.comandos.invocacao;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.efigueredo.blackscreen.comandos.invocacao.prototipo.PrototipoControlador;
import br.com.efigueredo.blackscreen.comandos.invocacao.prototipo.PrototipoControladorComDependencia;
import br.com.efigueredo.blackscreen.comandos.invocacao.prototipo.PrototipoControladorDependenciaInvalida;
import br.com.efigueredo.blackscreen.comandos.invocacao.prototipo.PrototipoControladorDuploConstrutorAnotado;
import br.com.efigueredo.blackscreen.comandos.invocacao.prototipo.PrototipoControladorSemConstrutorAdequando;
import br.com.efigueredo.container.ContainerIoc;
import br.com.efigueredo.container.exception.ClasseIlegalParaIntanciaException;
import br.com.efigueredo.container.exception.InversaoDeControleInvalidaException;

class InstanciadorControladorUnitarioTest {

	@Mock
	private ContainerIoc containerIoc;
	
	@InjectMocks
	private InstanciadorControlador instanciador;

	@BeforeEach
	void setUp() throws Exception {
		this.instanciador = new InstanciadorControlador();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void deveriaRetornarUmaInstanciaDaClasseControladoraSemDependencias() throws InversaoDeControleInvalidaException, ClasseIlegalParaIntanciaException {
		Class<?> controlador = PrototipoControlador.class;
		when(this.containerIoc.getIntancia(controlador)).thenReturn(new PrototipoControlador());
		Object instanciaControlador = this.instanciador.intanciarControlador(controlador);
		assertTrue(instanciaControlador instanceof PrototipoControlador);
	}
	
	@Test
	void deveriaRetornarUmaInstanciaDaClasseControladoraComDependencias() throws InversaoDeControleInvalidaException, ClasseIlegalParaIntanciaException {
		Class<?> controlador = PrototipoControladorComDependencia.class;
		when(this.containerIoc.getIntancia(controlador)).thenReturn(new PrototipoControladorComDependencia(new PrototipoControlador()));
		PrototipoControladorComDependencia instanciaControlador = (PrototipoControladorComDependencia) this.instanciador.intanciarControlador(controlador);
		assertTrue(instanciaControlador instanceof PrototipoControladorComDependencia);
		PrototipoControlador controller = instanciaControlador.getController();
		assertTrue(controller instanceof PrototipoControlador);
	}
	
	@Test
	public void deveriaJogarExcecao_SeHouverMaisDeUmContrutorAnotado_ComArrobaInjecaoNoControlador() throws InversaoDeControleInvalidaException, ClasseIlegalParaIntanciaException {
		Class<?> controlador = PrototipoControladorDuploConstrutorAnotado.class;
		when(this.containerIoc.getIntancia(controlador)).thenThrow(InversaoDeControleInvalidaException.class);
		assertThrows(InversaoDeControleInvalidaException.class, () -> this.instanciador.intanciarControlador(controlador));
	}
	
	@Test
	public void deveriaJogarExcecao_SeNaoHouverConstrutorPadraoENaoHouverConstrutorAnotado_ComArrobaInjecaoNoControlador() throws InversaoDeControleInvalidaException, ClasseIlegalParaIntanciaException {
		Class<?> controlador = PrototipoControladorSemConstrutorAdequando.class;
		when(this.containerIoc.getIntancia(controlador)).thenThrow(InversaoDeControleInvalidaException.class);
		assertThrows(InversaoDeControleInvalidaException.class, () -> this.instanciador.intanciarControlador(controlador));
	}
	
	@Test
	public void deveriaJogarExcecao_SeADependenciaInterfaceNaoEstiverConfigurada() throws InversaoDeControleInvalidaException, ClasseIlegalParaIntanciaException {
		Class<?> controlador = PrototipoControladorDependenciaInvalida.class;
		when(this.containerIoc.getIntancia(controlador)).thenThrow(ClasseIlegalParaIntanciaException.class);
		assertThrows(ClasseIlegalParaIntanciaException.class, () -> this.instanciador.intanciarControlador(controlador));
	}

}
