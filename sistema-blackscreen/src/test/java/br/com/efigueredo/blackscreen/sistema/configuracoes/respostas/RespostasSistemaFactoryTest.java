package br.com.efigueredo.blackscreen.sistema.configuracoes.respostas;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ConfiguracaoRespostaSistemaException;
import br.com.efigueredo.container.exception.ContainerIocException;

class RespostasSistemaFactoryTest {
	
	private RespostasSistemaFactory factory;
	
	@BeforeEach
	public void setup() {
		this.factory = new RespostasSistemaFactory();
	}

	@Test
	void deveriaRetornarObjetoRespostasSistemaPadrao() throws ContainerIocException, ConfiguracaoRespostaSistemaException {
		String pacoteRaiz = "br.com.efigueredo.blackscreen.prototipo_configuracao_resposta.correta.unica";
		Reflections reflections = new Reflections(pacoteRaiz, new SubTypesScanner(false), new TypeAnnotationsScanner());
		RespostasSistema resultado = this.factory.getRespostasSistema(reflections, pacoteRaiz);
		assertTrue(resultado instanceof RespostasSistema);
	}

}
