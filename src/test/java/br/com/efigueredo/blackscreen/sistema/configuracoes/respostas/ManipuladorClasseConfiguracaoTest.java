package br.com.efigueredo.blackscreen.sistema.configuracoes.respostas;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.efigueredo.blackscreen.prototipo_configuracao_resposta.ConfiguracaoRespostaPrototipoDependencia;
import br.com.efigueredo.blackscreen.prototipo_configuracao_resposta.ConfiguracaoRespostaPrototipoDependenciaNaoConfigurada;
import br.com.efigueredo.blackscreen.prototipo_configuracao_resposta.ConfiguracaoRespostaPrototipoDuploConstrutorDependencia;
import br.com.efigueredo.blackscreen.prototipo_configuracao_resposta.ConfiguracaoRespostaPrototipoSemConstrutorValido;
import br.com.efigueredo.blackscreen.prototipo_configuracao_resposta.correta.duplo.ConfiguracaoResposta1;
import br.com.efigueredo.container.ContainerIoc;
import br.com.efigueredo.container.construtor.exception.InversaoDeControleInvalidaException;
import br.com.efigueredo.container.exception.ContainerIocException;
import br.com.efigueredo.container.objetos.exception.ClasseIlegalParaIntanciaException;

class ManipuladorClasseConfiguracaoTest {
	
	@InjectMocks
	private ManipuladorClasseConfiguracaoResposta manipulador;
	
	@Mock
	private ContainerIoc container;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void deveriaRetornarUmaInstanciaDaClasseDeConfiguracaoInserida() throws ContainerIocException {
		when(this.container.getInstancia(ConfiguracaoResposta1.class)).thenReturn(new ConfiguracaoResposta1());
		Object instancia = this.manipulador.getIntanciaClasseConfiguracao(ConfiguracaoResposta1.class);
		assertTrue(instancia instanceof ConfiguracaoResposta1);
	}
	
	@Test
	void deveriaRetornarUmaInstanciaDaClasseDeConfiguracaoInseridaComDependencia() throws ContainerIocException {
		ConfiguracaoRespostaPrototipoDependencia conf = new ConfiguracaoRespostaPrototipoDependencia("resultado");
		when(this.container.getInstancia(ConfiguracaoRespostaPrototipoDependencia.class)).thenReturn(conf);
		Object instancia = this.manipulador.getIntanciaClasseConfiguracao(ConfiguracaoRespostaPrototipoDependencia.class);
		assertTrue(instancia instanceof ConfiguracaoRespostaPrototipoDependencia);
	}
	
	@Test
	public void deveriaLancarExcecao_QuandoConfiguracaoComDoisConstrutoresAnotados_ComArrobaInjecao() throws ContainerIocException {
		when(this.container.getInstancia(ConfiguracaoRespostaPrototipoDuploConstrutorDependencia.class)).thenThrow(InversaoDeControleInvalidaException.class);
		assertThrows(InversaoDeControleInvalidaException.class, () -> this.manipulador.getIntanciaClasseConfiguracao(ConfiguracaoRespostaPrototipoDuploConstrutorDependencia.class));
	}
	
	@Test
	public void deveriaLancarExcecao_QuandoConfiguracaoSemConstrutoAnotadoESemConstrutorPadrao() throws ContainerIocException {
		when(this.container.getInstancia(ConfiguracaoRespostaPrototipoSemConstrutorValido.class)).thenThrow(InversaoDeControleInvalidaException.class);
		assertThrows(InversaoDeControleInvalidaException.class, () -> this.manipulador.getIntanciaClasseConfiguracao(ConfiguracaoRespostaPrototipoSemConstrutorValido.class));
	}
	
	@Test
	public void deveriaLancarExcecao_QuandoNaoHouverDependenciaConfigurada() throws ContainerIocException {
		when(this.container.getInstancia(ConfiguracaoRespostaPrototipoDependenciaNaoConfigurada.class)).thenThrow(ClasseIlegalParaIntanciaException.class);
		assertThrows(ClasseIlegalParaIntanciaException.class, () -> this.manipulador.getIntanciaClasseConfiguracao(ConfiguracaoRespostaPrototipoDependenciaNaoConfigurada.class));
	}
	
	@Test
	public void deveriaRetornarOMétodoDeConfiguracao_DadoAClasseDeConfiguracao() throws NoSuchMethodException, SecurityException {
		assertDoesNotThrow(() -> this.manipulador.getMetodoConfiguracao(ConfiguracaoResposta1.class));
	}

}
