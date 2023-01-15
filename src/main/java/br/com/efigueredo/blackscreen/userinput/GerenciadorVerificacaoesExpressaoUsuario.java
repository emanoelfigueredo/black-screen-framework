package br.com.efigueredo.blackscreen.userinput;

import br.com.efigueredo.blackscreen.userinput.exception.ExpressaoInvalidaException;
import br.com.efigueredo.blackscreen.userinput.expressao.ExpressaoUsuario;
import br.com.efigueredo.blackscreen.userinput.verificacao.ConfiguracaoVerificacoes;
import br.com.efigueredo.blackscreen.userinput.verificacao.VerificadorExpressoes;

/**
 * <h4>A classe {@code GerenciadorVerificacaoesExpressaoUsuario} é reponsável
 * por gerenciar as verificações para a expressão do usuário.</h4>
 * 
 * Seu funcionamento se resume em obter as configurações de verifições e inserir
 * no verificador, resoponsável por executar todas as verificações configuradas.
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class GerenciadorVerificacaoesExpressaoUsuario {

	/**
	 * Método responsável por retornar o objeto
	 * {@linkplain ConfiguracaoVerificacoes}.
	 *
	 * @return Objeto {@linkplain ConfiguracaoVerificacoes}.
	 */
	private ConfiguracaoVerificacoes getObjetoConfiguracaoDeVerificacoes() {
		return new ConfiguracaoVerificacoes();
	}

	/**
	 * Método responsável por retornar o objeto {@linkplain VerificadorExpressoes}.
	 *
	 * @param configuracao Objeto {@linkplain ConfiguracaoVerificacoes}.
	 * @return Objeto {@linkplain VerificadorExpressoes}
	 */
	private VerificadorExpressoes getVerificadorExpressoes(ConfiguracaoVerificacoes configuracao) {
		return new VerificadorExpressoes(configuracao);
	}

	/**
	 * Método responsável por executar todos os processos necessários para que a
	 * verificação da expressão do usuário seja possível.
	 *
	 * @param entrada Objeto {@linkplain EntradaUsuario}
	 * @throws EntradaUsuarioInvalidaException Se algum dos parâmetros para a
	 *                                         expressão do comando não for atendida
	 *                                         será lançado uma sub-exceção
	 *                                         representando o erro.
	 */
	public void executar(ExpressaoUsuario expressao) throws ExpressaoInvalidaException {
		ConfiguracaoVerificacoes configuracao = this.getObjetoConfiguracaoDeVerificacoes();
		VerificadorExpressoes verificador = this.getVerificadorExpressoes(configuracao);
		verificador.executarVerificacao(expressao);
	}

}
