package br.com.efigueredo.blackscreen.sistema.configuracoes.respostas;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import br.com.efigueredo.blackscreen.prototipo_configuracao_resposta.correta.unica.ConfiguracaoResposta1;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ClasseDeConfiguracaoSemImplementacaoException;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ClassesDeConfiguracoesInexistentesException;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ClassesDeConfiguracoesRespostaInexistentesException;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.MaisDeUmaClasseDeConfiguracaoResposta;

class CarregadorConfiguracaoRespostaTest {
	
	private CarregadorConfiguracaoResposta carregador;

	private void setUp(String pacotePrototipo) throws Exception {
		Reflections reflections = new Reflections(pacotePrototipo, new SubTypesScanner(false), new TypeAnnotationsScanner());
		this.carregador = new CarregadorConfiguracaoResposta(reflections);
	}

	@Test
	void deveriaRetornarA_UnicaClasseDeConfiguracaoDeRespostas() throws Exception {
		this.setUp("br.com.efigueredo.blackscreen.prototipo_configuracao_resposta.correta.unica");
		assertDoesNotThrow(() -> this.carregador.getClasseConfiguracaoResposta());
		assertEquals(this.carregador.getClasseConfiguracaoResposta(), ConfiguracaoResposta1.class);
	}
	
	@Test
	public void deveriaLancarExcecao_QuandoNaoHouverClasseDeConfiguracao() throws Exception {
		this.setUp("br.com.efigueredo.blackscreen.prototipo_configuracao_resposta.vazio");
		assertThrows(ClassesDeConfiguracoesInexistentesException.class, () -> this.carregador.getClasseConfiguracaoResposta());
	}
	
	@Test
	public void deveriaLancarExcecao_QuandoNaoHouverClassesDeConfiguracaoRespostas() throws Exception {
		this.setUp("br.com.efigueredo.blackscreen.prototipo_configuracao_teste");
		assertThrows(ClassesDeConfiguracoesRespostaInexistentesException.class, () -> this.carregador.getClasseConfiguracaoResposta());
	}

	@Test
	public void deveriaLancarExcecao_QuandoHouverMaisDeUmaClasseDeConfiguracaoRespostas() throws Exception {
		this.setUp("br.com.efigueredo.blackscreen.prototipo_configuracao_resposta.correta.duplo");
		assertThrows(MaisDeUmaClasseDeConfiguracaoResposta.class, () -> this.carregador.getClasseConfiguracaoResposta());
	}
	
	@Test
	public void deveriaLancarExcecao_CasoAClasseDeConfiguracaoNaoSejaImplementacaoDeConfiguracaoResposta() throws Exception {
		this.setUp("br.com.efigueredo.blackscreen.prototipo_configuracao_resposta.incorreto");
		assertThrows(ClasseDeConfiguracaoSemImplementacaoException.class, () -> this.carregador.getClasseConfiguracaoResposta());
	}

}
