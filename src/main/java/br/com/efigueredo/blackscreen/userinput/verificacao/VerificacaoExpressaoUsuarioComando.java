package br.com.efigueredo.blackscreen.userinput.verificacao;

import br.com.efigueredo.blackscreen.userinput.exception.CampoComandoInvalidoExpception;
import br.com.efigueredo.blackscreen.userinput.expressao.ExpressaoUsuario;

/**
 * <h4>A classe {@code VerificacaoExpressaoUsuarioComando} representa a
 * verificação para a característica esperada no campo do comando da expressão
 * inserida pelo usuário.</h4>
 * 
 * Seus métodos são implemetações do modelo especificado pela super classe
 * abstrata {@linkplain VerificacaoExpressaoUsuario} que usa de suas
 * implementações no design pattern Template Method.
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class VerificacaoExpressaoUsuarioComando extends VerificacaoExpressaoUsuario {

	/**
	 * Construtor que recebe como parâmetro a próxima verificação caso a expressão
	 * inserida pelo usuário atenda aos requisitos da verificação atual.
	 * 
	 * Parâmetro delegado a super classe.
	 *
	 * @param proximo Objeto do tipo {@linkplain VerificacaoExpressaoUsuario}, a
	 *                implementação da próxima verificação.
	 */
	public VerificacaoExpressaoUsuarioComando(VerificacaoExpressaoUsuario proximo) {
		super(proximo);
	}

	/**
	 * O método {@code verificacao} é uma implementação da especificação da super
	 * classe. Responsável por conter a expressão booleana que representa que a
	 * expressão inserida pelo usuário é inválida.
	 *
	 * @param entrada Objeto {@linkplain EntradaUsuario}
	 * @return <b>true</b>, se no elemento comando da <em><u>expressão iniciar com o
	 *         prefixo "--"</u></em>.<br>
	 *         <b>false</b>, se no elemento comando da <em><u>expressão não iniciar
	 *         com o prefixo "--"</u></em>.
	 */
	@Override
	protected boolean verificacao(ExpressaoUsuario expressao) {
		return expressao.getComando().startsWith("--");
	}

	/**
	 * O método {@code jogarExcecao} é uma implementação da especificação da super
	 * classe. Responsável por jogar a exceção do tipo
	 * {@linkplain CampoComandoInvalidoExpception} na pilha.
	 * 
	 * Será usado caso o método {@code verificacao} retorne o valor true.
	 *
	 * @throws CampoComandoInvalidoExpception Exceção do tipo
	 *                                        {@linkplain EntradaUsuarioInvalidaException}
	 *                                        que será lançada caso o método
	 *                                        {@code verificacao} retorne o valor
	 *                                        true.
	 */
	@Override
	protected void jogarExcecao() throws CampoComandoInvalidoExpception {
		throw new CampoComandoInvalidoExpception("A primeira palavra da expressão não pode começar com \"--\". "
				+ "Pois essa característica está reservada somente para os parâmetros de comando");
	}

}
