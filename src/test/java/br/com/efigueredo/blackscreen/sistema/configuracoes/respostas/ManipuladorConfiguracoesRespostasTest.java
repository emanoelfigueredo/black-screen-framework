package br.com.efigueredo.blackscreen.sistema.configuracoes.respostas;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.RespostasSistema.RespostasSistemaBuilder;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ClasseDeConfiguracaoSemImplementacaoException;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.MaisDeUmaClasseDeConfiguracaoResposta;

class ManipuladorConfiguracoesRespostasTest {

	private ManipuladorConfiguracoesRespostas manipulador;

	private void setUp(String pacotePrototipo) throws Exception {
		Reflections reflections = new Reflections(pacotePrototipo, new SubTypesScanner(false),
				new TypeAnnotationsScanner());
		this.manipulador = new ManipuladorConfiguracoesRespostas(reflections, pacotePrototipo);
	}

	@Test
	void deveriaRetornarOBuilder_DadoUmaClasseDeConfiguracaoDeRespostas_NoProjeto() throws Exception {
		this.setUp("br.com.efigueredo.blackscreen.prototipo_configuracao_resposta.correta.unica");
		assertDoesNotThrow(() -> this.manipulador.getBuilder());
	}

	@Test
	public void deveriaRetornarNull_QuandoNaoHouverClasseDeConfiguracao() throws Exception {
		this.setUp("br.com.efigueredo.blackscreen.prototipo_configuracao_resposta.vazio");
		RespostasSistemaBuilder resultado = this.manipulador.getBuilder();
		assertNull(resultado);	
	}
	
	@Test
	public void deveriaRetornarLancarExcecao_QuandoHouverMaisDeUmaClasse_DeConfiguracaoDeRespostas() throws Exception {
		this.setUp("br.com.efigueredo.blackscreen.prototipo_configuracao_resposta.correta.duplo");
		assertThrows(MaisDeUmaClasseDeConfiguracaoResposta.class, () -> this.manipulador.getBuilder());
	}
	
	@Test
	public void deveriaRetornarLancarExcecao_QuandoAClasseDeConfiguracaoRespotas_NaoImplementarAInterfaceObrigatoria() throws Exception {
		this.setUp("br.com.efigueredo.blackscreen.prototipo_configuracao_resposta.incorreto");
		assertThrows(ClasseDeConfiguracaoSemImplementacaoException.class, () -> this.manipulador.getBuilder());
	}

	@Test
	public void deveriaRetornarNull_QuandoNaoHouverClasseDeConfiguracaoRespostas() throws Exception {
		this.setUp("br.com.efigueredo.blackscreen.prototipo_configuracao_teste");
		RespostasSistemaBuilder resultado = this.manipulador.getBuilder();
		assertNull(resultado);	
	}

}
