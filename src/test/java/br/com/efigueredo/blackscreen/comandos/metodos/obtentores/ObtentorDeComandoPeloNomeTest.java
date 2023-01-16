package br.com.efigueredo.blackscreen.comandos.metodos.obtentores;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.com.efigueredo.blackscreen.comandos.metodos.exception.ComandoInvalidoException;
import br.com.efigueredo.blackscreen.comandos.metodos.exception.ControladorException;
import br.com.efigueredo.blackscreen.comandos.metodos.prototipos.PrototipoControlador1;
import br.com.efigueredo.blackscreen.comandos.metodos.prototipos.PrototipoControladorSemMetodos;
import br.com.efigueredo.blackscreen.comandos.metodos.prototipos.PrototipoControladorSemMetodosComando;
import br.com.efigueredo.blackscreen.sistema.exception.BlackStreenException;

class ObtentorDeComandoPeloNomeTest {
	
	private ObtentorDeComandoPeloNome obtentor = new ObtentorDeComandoPeloNome();

	@Test
	public void deveriaRetornarTodosMetodos_ComONomeDeComandoCorrespondente()
			throws NoSuchMethodException, SecurityException, BlackStreenException {
		List<Method> metodosComando = this.obtentor.getMetodosAnotadosComParametroNomeCorrespondente("cmd1",
				PrototipoControlador1.class);
		Method metodo1 = PrototipoControlador1.class.getMethod("metodo1");
		Method metodo2 = PrototipoControlador1.class.getMethod("metodo5");
		assertTrue(metodosComando.size() == 2);
		assertTrue(metodosComando.contains(metodo1));
		assertTrue(metodosComando.contains(metodo2));
	}

	@Test
	public void deveriaLancarExececao_QuandoClasseControladoraNaoTiverMetodos()
			throws NoSuchMethodException, SecurityException, ControladorException, ComandoInvalidoException {
		assertThrows(ControladorException.class,
				() -> this.obtentor.getMetodosAnotadosComParametroNomeCorrespondente("cmd1",
						PrototipoControladorSemMetodos.class),
				"O controlador atual + [" + PrototipoControladorSemMetodos.class + "] não possui nenhum método.");
	}

	@Test
	public void deveriaLancarExececao_QuandoClasseControladoraNaoTiverMetodosDeComando()
			throws NoSuchMethodException, SecurityException, ControladorException, ComandoInvalidoException {
		assertThrows(ControladorException.class,
				() -> this.obtentor.getMetodosAnotadosComParametroNomeCorrespondente("cmd1",
						PrototipoControladorSemMetodosComando.class),
				"O controlador atual + [" + PrototipoControladorSemMetodosComando.class
						+ "] não possui nenhum método anotado com @Comando");
	}

}
