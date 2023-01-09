package br.com.efigueredo.blackscreen.sistema.configuracoes.respostas;

import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.RespostasSistema.RespostasSistemaBuilder;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ClasseDeConfiguracaoSemImplementacaoException;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ConfiguracaoInterrompidaException;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.MaisDeUmaClasseDeConfiguracaoResposta;
import br.com.efigueredo.container.exception.ClasseIlegalParaIntanciaException;
import br.com.efigueredo.container.exception.InversaoDeControleInvalidaException;
import br.com.efigueredo.project_loader.projeto.exception.PacoteInexistenteException;

/**
 * <h4>Classe responsável por fabricar os objetos
 * {@linkplain RespostasSistema}.</h4>
 * 
 * A necessidade de uma fabrica para esse objeto se deve ao fato de ele ser
 * configurável pelo usuário.
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class RespostasSistemaFactory {

	/**
	 * Objeto responsável por manipular as configurações de respostas do sistema.
	 */
	private ManipuladorConfiguracoesRespostas manipuladorConfiguracoes;

	/**
	 * Construtor.
	 *
	 * @throws PacoteInexistenteException Ocorrerá caso o pacote raiz do projeto não
	 *                                    possa ser encontrado no sistema de
	 *                                    arquivos do sistema operacional.
	 */
	public RespostasSistemaFactory() throws PacoteInexistenteException {
		this.manipuladorConfiguracoes = new ManipuladorConfiguracoesRespostas();
	}

	/**
	 * Obter objeto {@linkplain RespostasSistema} configurado.
	 * 
	 * O objeto {@linkplain ManipuladorConfiguracoesRespostas} deve obter às
	 * configurações de sistema através do {@linkplain RespostasSistemaBuilder}.
	 * Caso o builder retornado seja null, será instânciado um novo com as
	 * configurações padronizadas. Por meio dele, é obtido o objeto
	 * {@linkplain RespostasSistema}.
	 *
	 * @return Objeto {@linkplain RespostasSistema} configurada.
	 * @throws ConfiguracaoInterrompidaException             Ocorrerá caso a leitura
	 *                                                       das configurações para
	 *                                                       o builder seja
	 *                                                       interrompida.
	 * @throws MaisDeUmaClasseDeConfiguracaoResposta         Ocorrerá caso exista
	 *                                                       mais de uma classe de
	 *                                                       configuração de
	 *                                                       respostas no projeto.
	 * @throws ClasseDeConfiguracaoSemImplementacaoException Ocorrerá caso a classe
	 *                                                       de configuração de
	 *                                                       respostas encontrada
	 *                                                       não seja uma
	 *                                                       implementação da
	 *                                                       interface
	 *                                                       {@linkplain ConfiguracaoResposta}.
	 * @throws InversaoDeControleInvalidaException           Lançado quando há
	 *                                                       alguma situação em que
	 *                                                       não seja possível
	 *                                                       realizar a intanciação
	 *                                                       do objeto.
	 * @throws ClasseIlegalParaIntanciaException             Lançado quando é
	 *                                                       requerido uma intância
	 *                                                       de interface.
	 */
	public RespostasSistema getRespostasSistema() throws ConfiguracaoInterrompidaException,
			MaisDeUmaClasseDeConfiguracaoResposta, ClasseDeConfiguracaoSemImplementacaoException,
			InversaoDeControleInvalidaException, ClasseIlegalParaIntanciaException {
		RespostasSistemaBuilder builder = this.manipuladorConfiguracoes.getBuilder();
		if (builder == null) {
			return new RespostasSistemaBuilder().build();
		}
		return builder.build();
	}

}
