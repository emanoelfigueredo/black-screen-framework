package br.com.efigueredo.blackscreen.sistema;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import br.com.efigueredo.blackscreen.comandos.invocacao.prototipo.PrototipoControlador;
import br.com.efigueredo.blackscreen.sistema.exception.ControladorAtualInexistenteException;

class SistemaTest {

	@Test
	void deveriaLancarExcecao_QuandoAClasseControladoraInicialValeNull() {
		assertThrows(ControladorAtualInexistenteException.class, () -> new Sistema(null));
	}
	
	@Test
	void naoDeveriaLancarExcecao_QuandoAClasseControladoraInicialNaoValerNull() {
		assertDoesNotThrow(() -> new Sistema(PrototipoControlador.class));
	}

}
