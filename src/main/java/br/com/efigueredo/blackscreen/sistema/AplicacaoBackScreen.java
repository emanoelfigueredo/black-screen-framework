package br.com.efigueredo.blackscreen.sistema;

import java.lang.reflect.Method;

import br.com.efigueredo.blackscreen.anotacoes.Comando;
import br.com.efigueredo.blackscreen.comandos.invocacao.InvocadorComando;
import br.com.efigueredo.blackscreen.comandos.invocacao.exception.InvocacaoComandoInterrompidaException;
import br.com.efigueredo.blackscreen.comandos.metodos.GerenciadorComandoControlador;
import br.com.efigueredo.blackscreen.comandos.metodos.exception.NomeComandoInexistenteException;
import br.com.efigueredo.blackscreen.comandos.metodos.exception.ParametroDeComandoInexistenteException;
import br.com.efigueredo.blackscreen.comandos.metodos.exception.SolicitacaoDeMetodoComandoInexistenteException;
import br.com.efigueredo.blackscreen.comandos.metodos.exception.ValoresIncoerentesComOsComandosExistentesException;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.RespostasSistema;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.RespostasSistemaFactory;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ClasseDeConfiguracaoSemImplementacaoException;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ConfiguracaoInterrompidaException;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.MaisDeUmaClasseDeConfiguracaoResposta;
import br.com.efigueredo.blackscreen.sistema.exception.ControladorAtualInexistenteException;
import br.com.efigueredo.blackscreen.userinput.EntradaUsuario;
import br.com.efigueredo.blackscreen.userinput.GerenciadorEntradaUsuario;
import br.com.efigueredo.blackscreen.userinput.exception.EntradaUsuarioInvalidaException;
import br.com.efigueredo.container.ContainerIoc;
import br.com.efigueredo.container.exception.ClasseIlegalParaIntanciaException;
import br.com.efigueredo.container.exception.ContainerIocException;
import br.com.efigueredo.container.exception.InversaoDeControleInvalidaException;
import br.com.efigueredo.project_loader.projeto.exception.PacoteInexistenteException;

/**
 * <h4>Classe que representa o sistema. Sua função é disponibilizar o método que
 * inicia o sistema.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class AplicacaoBackScreen {

	/**
	 * Objeto {@linkplain Class} estático que representa a classe controladora
	 * atual. Nela deve ter ao menos um método anotado com {@linkplain Comando}.
	 */
	private static Class<?> controladorAtual;

	/**
	 * Objeto {@linkplain ContainerIoc} estático responsável pela inversão de
	 * controle e injeção de dependências. Deve ser acessado pelas outras classes.
	 */
	private static ContainerIoc containerIoc;

	/**
	 * Objeto responsável por gerenciar todos os procedimentos necessários para
	 * obtermos a entrada do usuário.
	 */
	private GerenciadorEntradaUsuario gerenteEntrada;

	/**
	 * Objeto responsável pro gerenciar os comandos da classe constroladora atual.
	 */
	private GerenciadorComandoControlador gerenteMetodos;

	/** Objeto responsável por invocar os métodos de comandos. */
	private InvocadorComando invocadorComandos;

	/** Objeto responsável pelas respostas do sistema. */
	private RespostasSistema respostasSistema;

	/**
	 * Construtor.
	 * 
	 * Inicializa os objetos funcionais para a classe.
	 *
	 * @param controladorInicial Objeto {@linkplain Class} que represente a classe
	 *                           constroladora inicial.
	 * @throws PacoteInexistenteException                    Ocorrerá se o pacote
	 *                                                       raiz do projeto não
	 *                                                       existir no sistema de
	 *                                                       arquivos do sistema
	 *                                                       operacional.
	 * @throws ControladorAtualInexistenteException          Ocorrerá se o
	 *                                                       paramâmetro
	 *                                                       controladorInicial for
	 *                                                       preenchido com valor
	 *                                                       null.
	 * @throws ConfiguracaoInterrompidaException             the configuracao
	 *                                                       interrompida exception
	 * @throws MaisDeUmaClasseDeConfiguracaoResposta         the mais de uma classe
	 *                                                       de configuracao
	 *                                                       resposta
	 * @throws ClasseDeConfiguracaoSemImplementacaoException the classe de
	 *                                                       configuracao sem
	 *                                                       implementacao
	 * @throws ContainerIocException 
	 */
	public AplicacaoBackScreen(Class<?> controladorInicial)
			throws ControladorAtualInexistenteException, ConfiguracaoInterrompidaException,
			MaisDeUmaClasseDeConfiguracaoResposta, ClasseDeConfiguracaoSemImplementacaoException, ContainerIocException {
		if (controladorInicial == null) {
			throw new ControladorAtualInexistenteException("Não existe classe controladora setada no sistema.");
		}
		AplicacaoBackScreen.controladorAtual = controladorInicial;
		this.gerenteEntrada = new GerenciadorEntradaUsuario();
		this.gerenteMetodos = new GerenciadorComandoControlador();
		this.invocadorComandos = new InvocadorComando();
		this.respostasSistema = new RespostasSistemaFactory().getRespostasSistema();
	}

	/**
	 * Executar o sistema.
	 * 
	 * Primeiramente, recebendo a entrada do usuário. Caso seja nula, o laço será
	 * recomeçado. Usando a resposta do usuário, podemos obter o método
	 * correspondente ao comando desejado, com seus parâmetros de comando e seus
	 * valores. Se não valer nulo, então o método encontrado será invocado. Assim
	 * executando o comando desejado.
	 * @throws ContainerIocException 
	 */
	public void executar() throws ContainerIocException {
		this.respostasSistema.imprimirBanner();
		while (true) {
			EntradaUsuario entradaUsuario = this.receberEntrada();
			if (entradaUsuario == null) {
				continue;
			}
			Method metodoComando = this.obterMetodoComando(entradaUsuario);
			if (metodoComando == null) {
				continue;
			}
			this.invocarMetodoComando(entradaUsuario, metodoComando);
		}
	}

	/**
	 * Método auxiliar privado responsável por tratar o procedimento de recebimento
	 * da entrada.
	 * 
	 * Caso ocorra alguma exceção, a mesma será tratada e impressa no console o
	 * erro.
	 *
	 * @return Objeto {@linkplain EntradaUsuario} que encapsule todos as partes da
	 *         entrada expressão inserida.<br>
	 *         null, caso uma exceção seja lançada.
	 */
	private EntradaUsuario receberEntrada() {
		EntradaUsuario entradaUsuario = null;
		try {
			entradaUsuario = this.gerenteEntrada.buildEntradaUsuario();
		} catch (EntradaUsuarioInvalidaException e) {
			this.respostasSistema.imprimirMensagemErro("Insira uma expressão válida");
		}
		return entradaUsuario;
	}

	/**
	 * Método privado auxiliar responsável por tratar do procedimento de obter o
	 * método de comando adequado.
	 *
	 * @param entradaUsuario Objeto {@linkplain EntradaUsuario} que encapsule todos
	 *                       as partes da entrada expressão inserida.
	 * @return Objeto {@linkplain Method} caso exista um método correspondente.<br>
	 *         null, caso não exista.
	 */
	private Method obterMetodoComando(EntradaUsuario entradaUsuario) {
		Method metodoComando = null;
		try {
			metodoComando = this.gerenteMetodos.getMetodoComando(entradaUsuario);
		} catch (NomeComandoInexistenteException e) {
			this.respostasSistema.imprimirMensagemErro("O comando " + entradaUsuario.getComando() + " não existe");
		} catch (ParametroDeComandoInexistenteException e) {
			this.respostasSistema.imprimirMensagemErro("O parâmetro de comando " + entradaUsuario.getParametros().get(0)
					+ " não existe para o comando " + entradaUsuario.getComando());
		} catch (ValoresIncoerentesComOsComandosExistentesException e) {
			this.respostasSistema.imprimirMensagemErro("Os valores inseridos não podem ser aceitos pelo comando");
		} catch (SolicitacaoDeMetodoComandoInexistenteException e) {
			this.respostasSistema.imprimirMensagemErro("Não existe comando que corresponda ao formato inserido.");
		}
		return metodoComando;
	}

	/**
	 * Método privado auxiliar responsável por tratar do procedimento de invocar o
	 * método de comando correspondente.
	 *
	 * @param entradaUsuario Objeto {@linkplain EntradaUsuario} que encapsule todos
	 *                       as partes da entrada expressão inserida.
	 * @param metodoComando  Objeto {@linkplain Method} que represente o método de
	 *                       comando.
	 * @throws ContainerIocException 
	 */
	private void invocarMetodoComando(EntradaUsuario entradaUsuario, Method metodoComando) throws ContainerIocException {
		try {
			this.invocadorComandos.invocarComando(controladorAtual, metodoComando, entradaUsuario.getValores());
		} catch (InversaoDeControleInvalidaException e) {
			this.respostasSistema.imprimirMensagemErro(
					"Não foi possível realizar a inversão de controle e injeção de dependências da classe controladora "
							+ AplicacaoBackScreen.controladorAtual.getName());
		} catch (ClasseIlegalParaIntanciaException e) {
			this.respostasSistema.imprimirMensagemErro(
					"A classe controladora atual possui dependências que não podem ser intânciadas");
		} catch (InvocacaoComandoInterrompidaException e) {
			this.respostasSistema.imprimirMensagemErro("A invocação do comando foi interrompida");
		}
	}

	/**
	 * Obter o objeto {@linkplain Class} da classe controladora atual do sistema.
	 *
	 * @return Objeto {@linkplain Class} da classe controladora atual.
	 */
	public static Class<?> getControladorAtual() {
		return AplicacaoBackScreen.controladorAtual;
	}

	/**
	 * Obter o objeto {@linkplain ContainerIoc} responsável pela inversão de
	 * controle e injeção de dependências.
	 *
	 * @return {@linkplain ContainerIoc}
	 */
	public static ContainerIoc getContainerIoc() {
		return AplicacaoBackScreen.containerIoc;
	}

	/**
	 * Alterar a classe controladora atual do sistema.
	 *
	 * @param classe Objeto {@linkplain Class} que represete a nova classe
	 *               controladora atual do sistema.
	 */
	public static void setControladorAtual(Class<?> classe) {
		AplicacaoBackScreen.controladorAtual = classe;
	}

}
