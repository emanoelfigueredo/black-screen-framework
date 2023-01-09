package br.com.efigueredo.blackscreen.userinput.verificacao;

import br.com.efigueredo.blackscreen.userinput.EntradaUsuario;
import br.com.efigueredo.blackscreen.userinput.exception.EntradaUsuarioInvalidaException;

/**
 * <h4>A classe {@linkplain VerificadorExpressoes} é responsável por executar às
 * verificações dada a expressão inserida pelo usuário.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class VerificadorExpressoes {

	/**
	 * Objeto do tipo {@linkplain ConfiguracaoVerificacoes} que contém a cadeia de
	 * objetos de verificações.
	 */
	private ConfiguracaoVerificacoes configuracao;

	/**
	 * Construtor que recebe como parâmetro o objeto do tipo
	 * {@linkplain ConfiguracaoVerificacoes}.
	 *
	 * @param configuracao Objeto do tipo {@linkplain ConfiguracaoVerificacoes}
	 */
	public VerificadorExpressoes(ConfiguracaoVerificacoes configuracao) {
		this.configuracao = configuracao;
	}

	/**
	 * Método responsável por executar todas às verificações encadeadas pelo design
	 * pattern Chain of Resposibility.
	 *
	 * @param entradaUsuario Objeto {@linkplain EntradaUsuario} que contém os
	 *                       recursos que serão verificados.
	 * @throws EntradaUsuarioInvalidaException Se algum dos parâmetros para a
	 *                                         expressão do comando não for atendida
	 *                                         nas verificações será lançado uma
	 *                                         sub-exceção representando o erro.
	 */
	public void executarVerificacao(EntradaUsuario entradaUsuario) throws EntradaUsuarioInvalidaException {
		VerificacaoExpressaoUsuario chain = this.configuracao.getChainVerificacaoExpressaoUsuario();
		chain.verificar(entradaUsuario);
	}

}
