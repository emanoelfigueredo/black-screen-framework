package br.com.efigueredo.blackscreen.userinput.verificacao;

/**
 * <h4>A classe responsável por representar uma configuração de
 * verificações.</h4>
 * 
 * Nessa classe de método único {@code getChainVerificacaoExpressaoUsuario}
 * reponsável por armazenar todas as verificações que devem ser executadas uma
 * após a outra para garantir que a expressão inserida possa passar adiante.
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class ConfiguracaoVerificacoes {

	/**
	 * Obtenha a cadeia de verificações configurada, pronta para execução.
	 * 
	 * O método consiste na utilização do design pattern Chain of Resposability. O
	 * retorno será um objeto do tipo {@linkplain VerificacaoExpressaoUsuario} que
	 * decora em seu atributo a próxima verificação.
	 *
	 * @return O objeto {@linkplain VerificacaoExpressaoUsuario}.
	 */
	public VerificacaoExpressaoUsuario getChainVerificacaoExpressaoUsuario() {
		VerificacaoExpressaoUsuario cadeia = new VerificacaoExpressaoUsuarioComando(
				new VerificacaoExpressaoUsuarioParametro(new VerificacaoNulaUsuarioParametro()));
		return cadeia;
	}

}
