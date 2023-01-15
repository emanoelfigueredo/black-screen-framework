package br.com.efigueredo.blackscreen.userinput.verificacao;

import br.com.efigueredo.blackscreen.userinput.exception.ExpressaoInvalidaException;
import br.com.efigueredo.blackscreen.userinput.expressao.ExpressaoUsuario;

/**
 * <h4>A classe {@code VerificacaoNulaUsuarioParametro} não representa
 * verificações. Devido ao design pattern Chain of Responsability, é necessário
 * que uma classe seja a última na cadeia de instâncias.</h4>
 * 
 * Seus métodos são implemetações do modelo especificado pela super classe
 * abstrata {@linkplain VerificacaoExpressaoUsuario} que usa de suas
 * implementações no design pattern Template Method.
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class VerificacaoNulaUsuarioParametro extends VerificacaoExpressaoUsuario {

	/**
	 * Construtor.
	 */
	public VerificacaoNulaUsuarioParametro() {
		super(null);
	}

	/**
	 * Método fictício, pois se trata do encerramento da cadeia de instâncias.
	 *
	 * @param entrada Objeto {@linkplain EntradaUsuario}.
	 * @return true, pois é o objeto finalizador da cadeia de instâncias.
	 */
	@Override
	protected boolean verificacao(ExpressaoUsuario expressao) {
		return true;
	}

	/**
	 * Método fictício, pois se trata do encerramento da cadeia de instâncias.
	 *
	 * @throws EntradaUsuarioInvalidaException
	 */
	@Override
	protected void jogarExcecao() throws ExpressaoInvalidaException {
		return;
	}

}
