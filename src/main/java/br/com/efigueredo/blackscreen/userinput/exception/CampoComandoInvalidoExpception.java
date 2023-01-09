package br.com.efigueredo.blackscreen.userinput.exception;

/**
 * <h4>A classe {@code CampoComandoInvalidoExpception} é uma exceção que deve
 * ser usada quando o usuário inserir no campo de comando uma palavra inválida.
 * Normalmente será uma palavra com o prefixo "--", que simboliza um parâmetro
 * de comando.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class CampoComandoInvalidoExpception extends EntradaUsuarioInvalidaException {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor.
	 * 
	 * @param mensagem Texto que descreva a exceção com mais detalhes.
	 */
	public CampoComandoInvalidoExpception(String mensagem) {
		super(mensagem);
	}

}
