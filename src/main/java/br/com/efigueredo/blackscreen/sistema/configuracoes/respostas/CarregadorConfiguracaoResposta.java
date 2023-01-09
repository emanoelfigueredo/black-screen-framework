package br.com.efigueredo.blackscreen.sistema.configuracoes.respostas;

import java.util.List;

import br.com.efigueredo.blackscreen.anotacoes.ConfiguracaoSistema;
import br.com.efigueredo.blackscreen.sistema.configuracoes.ConfiguracaoSistemaTipo;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ClasseDeConfiguracaoSemImplementacaoException;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ClassesDeConfiguracoesInexistentesException;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ClassesDeConfiguracoesRespostaInexistentesException;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.MaisDeUmaClasseDeConfiguracaoResposta;
import br.com.efigueredo.project_loader.projeto.ProjetoFactory;
import br.com.efigueredo.project_loader.projeto.exception.PacoteInexistenteException;
import br.com.efigueredo.project_loader.projeto.recursos.java.GerenteDeClasses;

/**
 * <h4>Classe responsável pela obtenção da classe de configuração de
 * respostas.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class CarregadorConfiguracaoResposta {

	/** Objeto responsável pelo gerenciamento de todas as classes do projeto. */
	private GerenteDeClasses gerenteClasses;

	/** Objeto responsável pelas verificações das clases de configuração. */
	private VerificadorClassesConfiguracaoRespostas verificador;

	/**
	 * Construto.
	 *
	 * @throws PacoteInexistenteException Ocorrerá caso o pacote raiz não exista no
	 *                                    sistema de arquivos do sistema
	 *                                    operacional.
	 */
	public CarregadorConfiguracaoResposta() throws PacoteInexistenteException {
		this.gerenteClasses = new ProjetoFactory().criarProjeto().getSRC_MAIN_JAVA().getGerenteDeClasses();
		this.verificador = new VerificadorClassesConfiguracaoRespostas();
	}

	/**
	 * Obtenha a classe de configuração de resposta.
	 * 
	 * Consiste em gerenciar todos os recuros para obter todas a classes de
	 * configuração resposta. Obter todas as classes de configuração do sistema do
	 * projeto. Filtrar as do tipo de resosta. Realizar as verificações. E retornar
	 * a classe encontrada.
	 *
	 * @return A classe de configuração de respostas.
	 * @throws ClassesDeConfiguracoesInexistentesException         Ocorrerá caso não
	 *                                                             exista classe de
	 *                                                             configuração para
	 *                                                             o sistema no
	 *                                                             projeto.
	 * @throws ClassesDeConfiguracoesRespostaInexistentesException Ocorrerá caso não
	 *                                                             exista classe de
	 *                                                             configuração de
	 *                                                             respostas no
	 *                                                             projeto.
	 * @throws MaisDeUmaClasseDeConfiguracaoResposta               Ocorrerá caso
	 *                                                             exista mais de
	 *                                                             uma classe de
	 *                                                             configuração de
	 *                                                             respostas no
	 *                                                             projeto.
	 * @throws ClasseDeConfiguracaoSemImplementacaoException       Ocorrerá caso a
	 *                                                             classe de
	 *                                                             configuração de
	 *                                                             respostas
	 *                                                             encontrada não
	 *                                                             seja uma
	 *                                                             implementação da
	 *                                                             interface
	 *                                                             {@linkplain ConfiguracaoResposta}.
	 */
	public Class<?> getClasseConfiguracaoResposta()
			throws ClassesDeConfiguracoesInexistentesException, ClassesDeConfiguracoesRespostaInexistentesException,
			MaisDeUmaClasseDeConfiguracaoResposta, ClasseDeConfiguracaoSemImplementacaoException {
		List<Class<?>> classesAnotadas = this.gerenteClasses.getClassesPelaAnotacao(ConfiguracaoSistema.class);
		this.verificador.verificarSeExisteAlgumaClasseDeConfiguracao(classesAnotadas);
		List<Class<?>> classesDeConfiguracaoMensagem = this.obterClassesDeConfiguracaoRespostas(classesAnotadas);
		this.verificador.verificarSeExisteAlgumaClasseDeConfiguracaoRespostas(classesDeConfiguracaoMensagem);
		this.verificador.verificarSeExisteMaisDeUmaClasseDeConfiguracaoRespostas(classesDeConfiguracaoMensagem);
		Class<?> classeConfiguracao = classesDeConfiguracaoMensagem.get(0);
		this.verificador.verificarSeAClasseImplementaAInterfaceConfiguracaoResposta(classeConfiguracao);
		return classeConfiguracao;
	}

	/**
	 * Método privado auxiliar responsável por obter todas às classes de
	 * configuração do tipo respostas.
	 *
	 * @param classesAnotadas Lista de classes de configuração.
	 * @return Lista de classes de configuração do tipo Respostas.
	 */
	private List<Class<?>> obterClassesDeConfiguracaoRespostas(List<Class<?>> classesAnotadas) {
		return classesAnotadas.stream().filter(
				c -> c.getAnnotation(ConfiguracaoSistema.class).tipo().equals(ConfiguracaoSistemaTipo.RESPOSTAS))
				.toList();
	}

}
