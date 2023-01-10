package br.com.efigueredo.blackscreen.comandos.invocacao;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.efigueredo.blackscreen.comandos.invocacao.prototipo.PrototipoControlador;
import br.com.efigueredo.blackscreen.comandos.invocacao.prototipo.PrototipoControladorComDependencia;
import br.com.efigueredo.blackscreen.comandos.invocacao.prototipo.PrototipoControladorDependenciaInvalida;
import br.com.efigueredo.blackscreen.comandos.invocacao.prototipo.PrototipoControladorDuploConstrutorAnotado;
import br.com.efigueredo.blackscreen.comandos.invocacao.prototipo.PrototipoControladorSemConstrutorAdequando;
import br.com.efigueredo.container.construtor.exception.InversaoDeControleInvalidaException;
import br.com.efigueredo.container.exception.ContainerIocException;
import br.com.efigueredo.container.objetos.exception.ClasseIlegalParaIntanciaException;

class InstanciadorControladorIntegradoTest {
	
	private InstanciadorControlador instanciador;

	@BeforeEach
	void setUp() throws Exception {
		this.instanciador = new InstanciadorControlador("br.com.efigueredo.blackscreen.comandos");
	}

	@Test
	void deveriaRetornarUmaInstanciaDaClasseControladoraSemDependencias() throws ContainerIocException {
		Class<?> controlador = PrototipoControlador.class;
		Object instanciaControlador = this.instanciador.intanciarControlador(controlador);
		assertTrue(instanciaControlador instanceof PrototipoControlador);
	}
	
	@Test
	void deveriaRetornarUmaInstanciaDaClasseControladoraComDependencias() throws ContainerIocException {
		Class<?> controlador = PrototipoControladorComDependencia.class;
		PrototipoControladorComDependencia instanciaControlador = (PrototipoControladorComDependencia) this.instanciador.intanciarControlador(controlador);
		assertTrue(instanciaControlador instanceof PrototipoControladorComDependencia);
		PrototipoControlador controller = instanciaControlador.getController();
		assertTrue(controller instanceof PrototipoControlador);
	}
	
	@Test
	public void deveriaJogarExcecao_SeHouverMaisDeUmContrutorAnotado_ComArrobaInjecaoNoControlador() throws InversaoDeControleInvalidaException, ClasseIlegalParaIntanciaException {
		try {
			Class<?> controlador = PrototipoControladorDuploConstrutorAnotado.class;
			assertThrows(InversaoDeControleInvalidaException.class, () -> this.instanciador.intanciarControlador(controlador));
		} catch (Exception e) {}
	}
	
	@Test
	public void deveriaJogarExcecao_SeNaoHouverConstrutorPadraoENaoHouverConstrutorAnotado_ComArrobaInjecaoNoControlador() throws InversaoDeControleInvalidaException, ClasseIlegalParaIntanciaException {
		try {
			Class<?> controlador = PrototipoControladorSemConstrutorAdequando.class;
			assertThrows(InversaoDeControleInvalidaException.class, () -> this.instanciador.intanciarControlador(controlador));
		} catch (Exception e) {}
	} 
	
	@Test
	public void deveriaJogarExcecao_SeADependenciaInterfaceNaoEstiverConfigurada() throws InversaoDeControleInvalidaException, ClasseIlegalParaIntanciaException {
		try {
			Class<?> controlador = PrototipoControladorDependenciaInvalida.class;
			assertThrows(ClasseIlegalParaIntanciaException.class, () -> this.instanciador.intanciarControlador(controlador));
		} catch (Exception e) {}
	}

}
