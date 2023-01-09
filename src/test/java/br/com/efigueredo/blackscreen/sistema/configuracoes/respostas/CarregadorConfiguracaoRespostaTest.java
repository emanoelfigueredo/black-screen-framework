package br.com.efigueredo.blackscreen.sistema.configuracoes.respostas;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.efigueredo.blackscreen.anotacoes.ConfiguracaoSistema;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ClasseDeConfiguracaoSemImplementacaoException;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ClassesDeConfiguracoesInexistentesException;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ClassesDeConfiguracoesRespostaInexistentesException;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.MaisDeUmaClasseDeConfiguracaoResposta;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.prototipo.ConfiguracaoRespostaPrototipo1;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.prototipo.ConfiguracaoRespostaPrototipo2;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.prototipo.ConfiguracaoRespostaPrototipo3;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.prototipo.ConfiguracaoRespostaPrototipo4;
import br.com.efigueredo.project_loader.projeto.recursos.java.GerenteDeClasses;

class CarregadorConfiguracaoRespostaTest {
	
	@InjectMocks
	private CarregadorConfiguracaoResposta carregador;
	
	@Mock
	private GerenteDeClasses gerenteClasses;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void deveriaRetornarAClasseDeConfiguracaoDeRespostas() throws ClassesDeConfiguracoesInexistentesException, ClassesDeConfiguracoesRespostaInexistentesException, MaisDeUmaClasseDeConfiguracaoResposta, ClasseDeConfiguracaoSemImplementacaoException {
		when(this.gerenteClasses.getClassesPelaAnotacao(ConfiguracaoSistema.class)).thenReturn(Arrays.asList(ConfiguracaoRespostaPrototipo1.class));
		assertDoesNotThrow(() -> this.carregador.getClasseConfiguracaoResposta());
		assertEquals(this.carregador.getClasseConfiguracaoResposta(), ConfiguracaoRespostaPrototipo1.class);
	}
	
	@Test
	public void deveriaLancarExcecao_QuandoNaoHouverClasseDeConfiguracao() {
		when(this.gerenteClasses.getClassesPelaAnotacao(ConfiguracaoSistema.class)).thenReturn(null);
		assertThrows(ClassesDeConfiguracoesInexistentesException.class, () -> this.carregador.getClasseConfiguracaoResposta());
	}
	
	@Test
	public void deveriaLancarExcecao_QuandoNaoHouverClassesDeConfiguracaoRespostas() {
		when(this.gerenteClasses.getClassesPelaAnotacao(ConfiguracaoSistema.class)).thenReturn(Arrays.asList(ConfiguracaoRespostaPrototipo2.class));
		assertThrows(ClassesDeConfiguracoesRespostaInexistentesException.class, () -> this.carregador.getClasseConfiguracaoResposta());
	}
	
	@Test
	public void deveriaLancarExcecao_QuandoHouverMaisDeUmaClasseDeConfiguracaoRespostas() {
		when(this.gerenteClasses.getClassesPelaAnotacao(ConfiguracaoSistema.class)).thenReturn(Arrays.asList(ConfiguracaoRespostaPrototipo1.class, ConfiguracaoRespostaPrototipo3.class));
		assertThrows(MaisDeUmaClasseDeConfiguracaoResposta.class, () -> this.carregador.getClasseConfiguracaoResposta());
	}
	
	@Test
	public void deveriaLancarExcecao_CasoAClasseDeConfiguracaoNaoSejaImplementacaoDeConfiguracaoResposta() {
		when(this.gerenteClasses.getClassesPelaAnotacao(ConfiguracaoSistema.class)).thenReturn(Arrays.asList(ConfiguracaoRespostaPrototipo4.class));
		assertThrows(ClasseDeConfiguracaoSemImplementacaoException.class, () -> this.carregador.getClasseConfiguracaoResposta());
	}

}
