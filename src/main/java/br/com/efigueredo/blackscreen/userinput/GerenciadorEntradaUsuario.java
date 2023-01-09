package br.com.efigueredo.blackscreen.userinput;

import java.util.List;

import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.ConfiguracaoResposta;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ClasseDeConfiguracaoSemImplementacaoException;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ConfiguracaoInterrompidaException;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.MaisDeUmaClasseDeConfiguracaoResposta;
import br.com.efigueredo.blackscreen.userinput.exception.EntradaUsuarioInvalidaException;
import br.com.efigueredo.container.exception.ClasseIlegalParaIntanciaException;
import br.com.efigueredo.container.exception.InversaoDeControleInvalidaException;
import br.com.efigueredo.project_loader.projeto.exception.PacoteInexistenteException;

/**
 * <h4>A classe {@code GerenciadorEntradaUsuario} é responsável por gerenciar os
 * procedimentos necessários para a obtenção de um objeto
 * {@linkplain EntradaUsuario}.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class GerenciadorEntradaUsuario {

	/** Objeto reponsável por recursos de recebimento de expressões do usuário. */
	private RecebedorEntradaUsuario recebedor;

	/**
	 * Objeto reponsável por recursos de manipulação de expressões inseridas pelo
	 * usuário.
	 */
	private ManipuladorEntradaUsuario manipulador;

	/** Objeto reponsável por verificar as expressões inseridas pelo usuário. */
	private GerenciadorVerificacaoesExpressaoUsuario gerenciadorVerificacoes;

	/**
	 * Construtor da classe.
	 * 
	 * @throws PacoteInexistenteException                    Ocorrerá caso o pacote
	 *                                                       raiz do projeto não
	 *                                                       seja encontrada no
	 *                                                       sistema de arquivos do
	 *                                                       sistema operacional.
	 * @throws ClasseIlegalParaIntanciaException             Ocorrerá caso alguma
	 *                                                       das depêndencias seja
	 *                                                       interface e não esteja
	 *                                                       configurada.
	 * @throws InversaoDeControleInvalidaException           Ocorrerá alguma classe
	 *                                                       que utilize a injeção
	 *                                                       de depêndencia não siga
	 *                                                       os meios determinados
	 *                                                       para o procedimento.
	 * @throws ClasseDeConfiguracaoSemImplementacaoException Ocorrerá caso a classe
	 *                                                       de configuração de
	 *                                                       resposta do sistema não
	 *                                                       implemente a classe
	 *                                                       {@linkplain ConfiguracaoResposta}.
	 * @throws MaisDeUmaClasseDeConfiguracaoResposta         Ocorrerá caso exista
	 *                                                       mais de uma classe de
	 *                                                       configuração de
	 *                                                       respostas no projeto.
	 * @throws ConfiguracaoInterrompidaException             Ocorrerá se a leitura
	 *                                                       da configuração for
	 *                                                       interrompida.
	 */
	public GerenciadorEntradaUsuario() throws ConfiguracaoInterrompidaException, MaisDeUmaClasseDeConfiguracaoResposta,
			InversaoDeControleInvalidaException, ClasseIlegalParaIntanciaException, PacoteInexistenteException,
			ClasseDeConfiguracaoSemImplementacaoException {
		this.recebedor = new RecebedorEntradaUsuario();
		this.manipulador = new ManipuladorEntradaUsuario();
		this.gerenciadorVerificacoes = new GerenciadorVerificacaoesExpressaoUsuario();
	}

	/**
	 * Obtenha a expressão inserida pelo usuário.
	 *
	 * @return A expressão inserida pelo usuário.
	 */
	public String receberExpressao() {
		return this.recebedor.receberEntrada();
	}

	/**
	 * Método responsável por manipular a expressão do usuário.
	 *
	 * @param expressao A expressão inserida pelo usuário.
	 * @return Objeto {@linkplain EntradaUsuario} encapsulando todas as informações
	 *         extraídas.
	 */
	public EntradaUsuario manipularExpressao(String expressao) {
		String comando = this.manipulador.extrairComando(expressao);
		List<String> parametros = this.manipulador.extrairParametros(expressao);
		List<String> valores = this.manipulador.extrairValores(expressao);
		return new EntradaUsuario(comando, parametros, valores);
	}

	/**
	 * Método responsável por atribuir a tarefa de verificação ao objeto
	 * {@linkplain GerenciadorVerificacaoesExpressaoUsuario}.
	 * 
	 * @param entradaUsuario Objeto do tipo {@linkplain EntradaUsuario}.
	 * @throws EntradaUsuarioInvalidaException Se algum dos parâmetros para a
	 *                                         expressão do comando não for atendida
	 *                                         será lançado uma sub-exceção
	 *                                         representando o erro.
	 */
	public void executarVerificacoesExpressao(EntradaUsuario entradaUsuario) throws EntradaUsuarioInvalidaException {
		this.gerenciadorVerificacoes.executar(entradaUsuario);
	}

	/**
	 * Método responsável por construir o objeto {@linkplain EntradaUsuario}.
	 * 
	 * Ele executa todas as partes necessárias para que o objeto seja passado a
	 * frente no sistema.
	 *
	 * @return EntradaUsuario Objeto {@linkplain EntradaUsuario}.
	 * @throws EntradaUsuarioInvalidaException Se algum dos parâmetros para a
	 *                                         expressão do comando não for atendida
	 *                                         será lançado uma sub-exceção
	 *                                         representando o erro.
	 */
	public EntradaUsuario buildEntradaUsuario() throws EntradaUsuarioInvalidaException {
		String expressao = this.receberExpressao();
		EntradaUsuario entrada = this.manipularExpressao(expressao);
		this.executarVerificacoesExpressao(entrada);
		return entrada;
	}

}
