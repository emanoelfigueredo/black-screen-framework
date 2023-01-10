package br.com.efigueredo.blackscreen.sistema.configuracoes.respostas;

import java.lang.reflect.Method;

import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.fontes.IntefaceConfiguracaoResposta;
import br.com.efigueredo.container.ContainerIoc;
import br.com.efigueredo.container.exception.ContainerIocException;

/**
 * <h4>Classe responsável por manipular os recursos de uma classe de
 * configuração de respostas do sistema.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class ManipuladorClasseConfiguracaoResposta {

	/** Objeto responsável pela inversão de controle e injeção de dependências. */
	private ContainerIoc container;

	/**
	 * Construtor.
	 *
	 * @param pacoteRaiz O pacote raiz do projeto.
	 * @throws ContainerIocException Erro no container ioc, analise a stack trace.
	 */
	public ManipuladorClasseConfiguracaoResposta(String pacoteRaiz) throws ContainerIocException {
		this.container = new ContainerIoc(pacoteRaiz);
	}

	/**
	 * Obtenha uma intância da classe de configuração inserida.
	 *
	 * @param classeConfiguracao Classe de configuração.
	 * @return Intância da classe de configuração inserida.
	 * @throws ContainerIocException Erro no container ioc, analise a stack trace.
	 */
	public Object getIntanciaClasseConfiguracao(Class<?> classeConfiguracao) throws ContainerIocException {
		return this.container.getInstancia(classeConfiguracao);
	}

	/**
	 * Obtenha o método de configuração da classe de configuração de respostas.
	 *
	 * @param classeConfiguracao Classe de configuração de respostas.
	 * @return O método de configuração.
	 * @throws NoSuchMethodException Ocorrerá caso o método
	 *                               {@code configurarRespostas} não exista na
	 *                               classe de configuração.
	 * @throws SecurityException     Lançado pelo gerente de segurança para indicar
	 *                               uma violação de segurança.
	 */
	public Method getMetodoConfiguracao(Class<?> classeConfiguracao) throws NoSuchMethodException, SecurityException {
		return classeConfiguracao.getMethod("configurarRespostas", IntefaceConfiguracaoResposta.class);
	}

}
