package br.com.efigueredo.blackscreen.userinput.verificacao;

import java.util.List;

import br.com.efigueredo.blackscreen.userinput.EntradaUsuario;
import br.com.efigueredo.blackscreen.userinput.exception.CampoComandoInvalidoExpception;
import br.com.efigueredo.blackscreen.userinput.exception.EntradaUsuarioInvalidaException;
import br.com.efigueredo.blackscreen.userinput.exception.MaisDeUmParametroNaExpressaoException;

/**
 * <h4>A classe {@code VerificacaoExpressaoUsuarioParametro} representa a
 * verificação para a característica esperada no campo de parâmetro da expressão
 * inserida pelo usuário.</h4>
 * 
 * Seus métodos são implemetações do modelo especificado pela super classe
 * abstrata {@linkplain VerificacaoExpressaoUsuario} que usa de suas
 * implementações no design pattern Template Method.
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class VerificacaoExpressaoUsuarioParametro extends VerificacaoExpressaoUsuario {

	/**
	 * Construtor que recebe como parâmetro a próxima verificação caso a expressão
	 * inserida pelo usuário atenda aos requisitos da verificação atual.
	 * 
	 * Parâmetro delegado a super classe.
	 *
	 * @param proximo Objeto do tipo {@linkplain VerificacaoExpressaoUsuario}, a
	 *                implementação da próxima verificação.
	 */
	public VerificacaoExpressaoUsuarioParametro(VerificacaoExpressaoUsuario proximo) {
		super(proximo);
	}

	/**
	 * O método {@code verificacao} é uma implementação da especificação da super
	 * classe. Responsável por conter a expressão booleana que representa que a
	 * expressão inserida pelo usuário é inválida.
	 *
	 * @param entrada Objeto {@linkplain EntradaUsuario}
	 * @return <b>true</b>, se a lista que contem os parâmetros no objeto
	 *         {@linkplain EntradaUsuario} <em><u>tive mais de um
	 *         elemento.</u></em>.<br>
	 *         <b>false</b>, se a lista que contem os parâmetros no objeto
	 *         {@linkplain EntradaUsuario} <em><u>não tive mais de um
	 *         elemento.</u></em>.
	 */
	@Override
	protected boolean verificacao(EntradaUsuario entrada) {
		List<String> parametros = entrada.getParametros();
		return parametros.size() > 1;
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
	protected void jogarExcecao() throws EntradaUsuarioInvalidaException {
		throw new MaisDeUmParametroNaExpressaoException("Uma expressão não pode ter mais que um parâmtro.");
	}

}
