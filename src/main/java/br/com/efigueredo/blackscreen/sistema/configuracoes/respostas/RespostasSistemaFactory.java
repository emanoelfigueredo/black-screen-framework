package br.com.efigueredo.blackscreen.sistema.configuracoes.respostas;

import org.reflections.Reflections;

import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.RespostasSistema.RespostasSistemaBuilder;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ConfiguracaoRespostaSistemaException;
import br.com.efigueredo.container.exception.ContainerIocException;

/**
 * <h4>Classe responsável por fabricar os objetos
 * {@linkplain RespostasSistema}.</h4>
 * 
 * A necessidade de uma fábrica para esse objeto se deve ao fato de ele ser
 * configurável pelo usuário.
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class RespostasSistemaFactory {

	/**
	 * Obter objeto {@linkplain RespostasSistema} configurado.
	 * 
	 * O objeto {@linkplain ManipuladorConfiguracoesRespostas} deve obter às
	 * configurações de sistema através do {@linkplain RespostasSistemaBuilder}.
	 * Caso o builder retornado seja null, será instânciado um novo com as
	 * configurações padronizadas. Por meio dele, é obtido o objeto
	 * {@linkplain RespostasSistema}.
	 *
	 * @param reflections Objeto responsável pela reflexão do projeto.
	 * @param pacoteRaiz Pacote raiz do projeto.
	 * @return Objeto {@linkplain RespostasSistema} configurada.
	 * @throws ContainerIocException                Erro no Container Ioc.
	 * @throws ConfiguracaoRespostaSistemaException Falha no carregamento das
	 *                                              configurações de respostas do
	 *                                              sistema. Analise a cuasa.
	 */
	public RespostasSistema getRespostasSistema(Reflections reflections, String pacoteRaiz)
			throws ContainerIocException, ConfiguracaoRespostaSistemaException {
		RespostasSistemaBuilder builder = new ManipuladorConfiguracoesRespostas(reflections, pacoteRaiz).getBuilder();
		if (builder == null) {
			return new RespostasSistemaBuilder().build();
		}
		return builder.build();
	}

}
