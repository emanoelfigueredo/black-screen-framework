package br.com.efigueredo.blackscreen.sistema.configuracoes.respostas;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.reflections.Reflections;

import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.RespostasSistema.RespostasSistemaBuilder;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ClassesDeConfiguracoesInexistentesException;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ClassesDeConfiguracoesRespostaInexistentesException;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ConfiguracaoInterrompidaException;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ConfiguracaoRespostaSistemaException;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.fontes.IntefaceConfiguracaoResposta;
import br.com.efigueredo.container.exception.ContainerIocException;

/**
 * <h4>Classe responsável por manipular os recursos da classe de configuração de
 * respostas do sistema.</h4>
 * 
 * Utiliza de classes responsáveis por tarefas ligadas as configurações de
 * respostas para implementá-las no sistema.
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class ManipuladorConfiguracoesRespostas {

	/** Objeto responsável por obter a classe de configuração de respostas. */
	private CarregadorConfiguracaoResposta carregador;

	/**
	 * Objeto responsável por manipular os recursos da classe de configuração de
	 * respostas.
	 */
	private ManipuladorClasseConfiguracaoResposta manipuladorClasse;

	/**
	 * Construtor.
	 *
	 * @param reflection the reflection
	 * @param pacoteRaiz the pacote raiz
	 * @throws ContainerIocException the container ioc exception
	 */
	public ManipuladorConfiguracoesRespostas(Reflections reflection, String pacoteRaiz) throws ContainerIocException {
		this.carregador = new CarregadorConfiguracaoResposta(reflection);
		this.manipuladorClasse = new ManipuladorClasseConfiguracaoResposta(pacoteRaiz);
	}

	/**
	 * Obtenha o builder {@linkplain RespostasSistemaBuilder}, configurado com as
	 * configurações obtidas.
	 * 
	 * Seu funcionamento consiste em obter a classe de configuracao, seu método e
	 * sua intância e invocá-lo injetando uma inteface de configuração. O builder
	 * que a mesma contém receberá as configurações e será retornado.
	 * 
	 * Caso não existam classes de configurações ou classes de configurações de
	 * respostas, uma exceção será capturada e o retorno será null.
	 *
	 * @return Builder configurado.
	 * @throws ConfiguracaoRespostaSistemaException Falha no carregamento das
	 *                                              configurações de respostas do
	 *                                              sistema. Analise a cuasa.
	 * @throws ContainerIocException                Erro no Container Ioc.
	 */
	public RespostasSistemaBuilder getBuilder() throws ConfiguracaoRespostaSistemaException, ContainerIocException {
		try {
			Class<?> classeConfiguracao = this.carregador.getClasseConfiguracaoResposta();
			Method metodoConfiguracao = this.manipuladorClasse.getMetodoConfiguracao(classeConfiguracao);
			Object instanciaClasseConfiguracao = this.manipuladorClasse
					.getIntanciaClasseConfiguracao(classeConfiguracao);
			return invocarMetodoComInterfaceDeConfiguracao(metodoConfiguracao, instanciaClasseConfiguracao);
		} catch (ClassesDeConfiguracoesInexistentesException | ClassesDeConfiguracoesRespostaInexistentesException e) {
			return null;
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new ConfiguracaoInterrompidaException(
					"O processo de configuracao da classe de configuracao de respostas foi interrompido. Analise a causa e corriga o problema",
					e);
		}

	}

	/**
	 * Método privado auxiliar responsável por relizar o procedimento de invocação
	 * do método de configuração.<br>
	 * <br>
	 * 
	 * Para invocá-lo é necessário invocar um objeto
	 * {@linkplain IntefaceConfiguracaoResposta}, que contém o builder que irá
	 * receber as configurações na invocação do método.<br>
	 * <br>
	 * 
	 * O builder será retornado.
	 *
	 * @param metodoConfiguracao          Método de configuração que será invocado.
	 * @param instanciaClasseConfiguracao Instância da classe de configuração de
	 *                                    respostas.
	 * @return Builder configurado.
	 * @throws IllegalAccessException    Uma IllegalAccessException é lançada quando
	 *                                   um aplicativo tenta para criar
	 *                                   reflexivamente uma instância (diferente de
	 *                                   um array), definir ou obter um campo ou
	 *                                   invocar um método, mas o atual o método de
	 *                                   execução não tem acesso à definição de a
	 *                                   classe, campo, método ou construtor
	 *                                   especificado.
	 * @throws IllegalArgumentException  Lançado para indicar que um método passou
	 *                                   por um argumento ilegal ou inapropriado.
	 * @throws InvocationTargetException InvocationTargetException é uma exceção
	 *                                   verificada que envolve uma exceção lançada
	 *                                   por um método ou construtor invocado.
	 */
	private RespostasSistemaBuilder invocarMetodoComInterfaceDeConfiguracao(Method metodoConfiguracao,
			Object instanciaClasseConfiguracao)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		RespostasSistemaBuilder builder = new RespostasSistemaBuilder();
		IntefaceConfiguracaoResposta interfaceConfiguracao = new IntefaceConfiguracaoResposta(builder);
		metodoConfiguracao.invoke(instanciaClasseConfiguracao, interfaceConfiguracao);
		return builder;
	}

}
