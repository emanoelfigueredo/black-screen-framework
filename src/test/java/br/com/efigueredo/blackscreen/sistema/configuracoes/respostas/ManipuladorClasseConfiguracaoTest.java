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

import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.prototipo.ConfiguracaoRespostaPrototipo1;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.prototipo.ConfiguracaoRespostaPrototipoDependencia;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.prototipo.ConfiguracaoRespostaPrototipoDependenciaNaoConfigurada;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.prototipo.ConfiguracaoRespostaPrototipoDuploConstrutorDependencia;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.prototipo.ConfiguracaoRespostaPrototipoSemConstrutorValido;
import br.com.efigueredo.container.ContainerIoc;
import br.com.efigueredo.container.exception.ClasseIlegalParaIntanciaException;
import br.com.efigueredo.container.exception.InversaoDeControleInvalidaException;

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
	void deveriaRetornarUmaInstanciaDaClasseDeConfiguracaoInserida() throws InversaoDeControleInvalidaException, ClasseIlegalParaIntanciaException {
		when(this.container.getIntancia(ConfiguracaoRespostaPrototipo1.class)).thenReturn(new ConfiguracaoRespostaPrototipo1());
		Object instancia = this.manipulador.getIntanciaClasseConfiguracao(ConfiguracaoRespostaPrototipo1.class);
		assertTrue(instancia instanceof ConfiguracaoRespostaPrototipo1);
	}
	
	@Test
	void deveriaRetornarUmaInstanciaDaClasseDeConfiguracaoInseridaComDependencia() throws InversaoDeControleInvalidaException, ClasseIlegalParaIntanciaException {
		ConfiguracaoRespostaPrototipoDependencia conf = new ConfiguracaoRespostaPrototipoDependencia("resultado");
		when(this.container.getIntancia(ConfiguracaoRespostaPrototipoDependencia.class)).thenReturn(conf);
		Object instancia = this.manipulador.getIntanciaClasseConfiguracao(ConfiguracaoRespostaPrototipoDependencia.class);
		assertTrue(instancia instanceof ConfiguracaoRespostaPrototipoDependencia);
		ConfiguracaoRespostaPrototipoDependencia resultado = (ConfiguracaoRespostaPrototipoDependencia) instancia;
		String stringInjetada = resultado.getString();
		assertTrue(stringInjetada.equals("resultado"));
	}
	
	@Test
	public void deveriaLancarExcecao_QuandoConfiguracaoComDoisConstrutoresAnotados_ComArrobaInjecao() throws InversaoDeControleInvalidaException, ClasseIlegalParaIntanciaException {
		when(this.container.getIntancia(ConfiguracaoRespostaPrototipoDuploConstrutorDependencia.class)).thenThrow(InversaoDeControleInvalidaException.class);
		assertThrows(InversaoDeControleInvalidaException.class, () -> this.manipulador.getIntanciaClasseConfiguracao(ConfiguracaoRespostaPrototipoDuploConstrutorDependencia.class));
	}
	
	@Test
	public void deveriaLancarExcecao_QuandoConfiguracaoSemConstrutoAnotadoESemConstrutorPadrao() throws InversaoDeControleInvalidaException, ClasseIlegalParaIntanciaException {
		when(this.container.getIntancia(ConfiguracaoRespostaPrototipoSemConstrutorValido.class)).thenThrow(InversaoDeControleInvalidaException.class);
		assertThrows(InversaoDeControleInvalidaException.class, () -> this.manipulador.getIntanciaClasseConfiguracao(ConfiguracaoRespostaPrototipoSemConstrutorValido.class));
	}
	
	@Test
	public void deveriaLancarExcecao_QuandoNaoHouverDependenciaConfigurada() throws InversaoDeControleInvalidaException, ClasseIlegalParaIntanciaException {
		when(this.container.getIntancia(ConfiguracaoRespostaPrototipoDependenciaNaoConfigurada.class)).thenThrow(ClasseIlegalParaIntanciaException.class);
		assertThrows(ClasseIlegalParaIntanciaException.class, () -> this.manipulador.getIntanciaClasseConfiguracao(ConfiguracaoRespostaPrototipoDependenciaNaoConfigurada.class));
	}
	
	@Test
	public void deveriaRetornarOMétodoDeConfiguracao_DadoAClasseDeConfiguracao() throws NoSuchMethodException, SecurityException {
		assertDoesNotThrow(() -> this.manipulador.getMetodoConfiguracao(ConfiguracaoRespostaPrototipo1.class));
	}

}
