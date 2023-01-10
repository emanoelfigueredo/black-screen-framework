package br.com.efigueredo.blackscreen.comandos.metodos;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import br.com.efigueredo.blackscreen.anotacoes.Comando;
import br.com.efigueredo.blackscreen.comandos.metodos.exception.NomeComandoInexistenteException;
import br.com.efigueredo.blackscreen.comandos.metodos.exception.ParametroDeComandoInexistenteException;
import br.com.efigueredo.blackscreen.comandos.metodos.exception.SolicitacaoDeMetodoComandoInexistenteException;
import br.com.efigueredo.blackscreen.comandos.metodos.exception.ValoresIncoerentesComOsComandosExistentesException;
import br.com.efigueredo.blackscreen.sistema.AplicacaoBackScreen;
import br.com.efigueredo.blackscreen.userinput.EntradaUsuario;

/**
 * <h4>A classe {@code GerenciadorComandoControlador} é responsável por
 * gerenciar as classes manipuladoras de métodos.</h4>
 * 
 * Com o objetivo final de apenas obter o método que representa o comando na
 * classe controladora atual do sistema.<br>
 * <br>
 * 
 * Seu método mais importante recebe uma entrada de usuário e retorna um objeto
 * do tipo {@linkplain Method} que representa o comando correspondente.
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class GerenciadorComandoControlador {

	/**
	 * O objeto do tipo {@linkplain Class} que representa a classe do controlador
	 */
	private Class<?> controlador;

	/**
	 * O objeto responsável por manipular apenas os métodos de classes
	 * controladoras.
	 */
	private ManipuladorMetodosComandos manipuladorMetodosComando;

	/**
	 * Construtor da classe.
	 *
	 * @param classeControlador O objeto do tipo {@linkplain Class} que representa a
	 *                          classe do controlador.
	 */
	public GerenciadorComandoControlador() {
		this.controlador = AplicacaoBackScreen.getControladorAtual();
		this.manipuladorMetodosComando = new ManipuladorMetodosComandos();
	}

	/**
	 * Obtenha o método que corresponda ao comando solicitado pelo usuário.
	 * 
	 * Seu funcionamento consiste em delegar funções a outros métodos da classe para
	 * filtrar cada vez mais todos os métodos da classe controladora. Até encontrar
	 * o método correspondente.
	 * 
	 * Se por ventura, o método não for encontrado, uma exceção será lançada de
	 * acordo com a situação.
	 *
	 * @param entradaUsuario Objeto do tipo {@linkplain EntradaUsuario} que contém
	 *                       todas as informações que o usuário inseriu.
	 * @return O objeto do tipo {@linkplain Method} que representa o método
	 *         correpondente ao comando solicitado.
	 * @throws NomeComandoInexistenteException                    O nome de comando
	 *                                                            inserido pelo
	 *                                                            usuário não existe
	 *                                                            na classe
	 *                                                            controladora
	 *                                                            atual.
	 * 
	 * @throws ParametroDeComandoInexistenteException             O parâmetro de
	 *                                                            comando inserido
	 *                                                            pelo usuário não
	 *                                                            existe nos métodos
	 *                                                            de comando da
	 *                                                            classe
	 *                                                            controladora
	 *                                                            atual.
	 * 
	 * @throws ValoresIncoerentesComOsComandosExistentesException Não existe método
	 *                                                            de comando que
	 *                                                            possa receber a
	 *                                                            quantidade de
	 *                                                            parâmetros que foi
	 *                                                            passada pelo
	 *                                                            usuário como
	 *                                                            valores do
	 *                                                            comando.
	 * @throws SolicitacaoDeMetodoComandoInexistenteException     Ocorrerá caso não
	 *                                                            exista métodos que
	 *                                                            possam atender a
	 *                                                            solicitação.
	 */
	public Method getMetodoComando(EntradaUsuario entradaUsuario)
			throws NomeComandoInexistenteException, ParametroDeComandoInexistenteException,
			ValoresIncoerentesComOsComandosExistentesException, SolicitacaoDeMetodoComandoInexistenteException {
		List<Method> metodos = this.getMetodosAnotadosComParametroNomeCorrespondente(entradaUsuario.getComando());
		metodos = this.getMetodosAnotadosComValorParametroCorrespondente(metodos, entradaUsuario.getParametros());
		metodos = this.getMetodosPorTiposParametros(metodos, entradaUsuario.getClassesDosValores());
		return metodos.get(0);
	}

	/**
	 * Obtenha todos os métodos anotados com @Comando com o atributo nome igual ao
	 * inserido por parâmetro.
	 * 
	 * Seu funcionamento consiste em obter o resultado do método do objeto
	 * manipulador responsável. Caso o resultado seja uma lista vazia, uma exceção
	 * será lançada.
	 *
	 * @param nomeComando O nome do comando.
	 * @return Uma lista de Objetos do tipo {@linkplain Method} que representam
	 *         todos os métodos. Tal que atributo nome de sua anotação vala
	 *         nomeComando inserido.
	 * @throws NomeComandoInexistenteException O nome de comando inserido pelo
	 *                                         usuário não existe na classe
	 *                                         controladora atual.
	 */
	List<Method> getMetodosAnotadosComParametroNomeCorrespondente(String nomeComando)
			throws NomeComandoInexistenteException {
		ManipuladorMetodos manipuladorMetodos = new ManipuladorMetodos(AplicacaoBackScreen.getControladorAtual());
		List<Method> metodosControlador = Arrays.asList(this.controlador.getDeclaredMethods());
		metodosControlador = manipuladorMetodos.getMetodosAnotados(Comando.class);
		metodosControlador = this.manipuladorMetodosComando.getMetodosAnotadosPorNome(metodosControlador, nomeComando);
		if (metodosControlador.isEmpty()) {
			throw new NomeComandoInexistenteException("O comando " + nomeComando
					+ " não está atribuído a anotações do tipo @Comando nos métodos da classe controladora atual ["
					+ this.controlador.getName() + "].");
		}
		return metodosControlador;
	}

	/**
	 * Método responsável por retornar, dado uma lista de métodos, os que possuem
	 * algum valor do atributo parametros da anotação {@linkplain @Comando} igual ao
	 * inserido no método.
	 * 
	 * Seu funcionamento consiste retonar o resultado do método responsável por
	 * manipular a lista de métodos. Se não houver parâmetros de comando, a lista
	 * inserida será retornada.
	 *
	 * @param metodos    A lista de objetos do tipo {@linkplain Method} que será
	 *                   manipulada.
	 * @param parametros O parâmetro da expressão inserida pelo usuário contido numa
	 *                   lista.
	 * @return Uma lista de objetos {@linkplain Method} que representam os métodos
	 *         que em suas anotações, esteja o parâmetro de comando inserido.
	 * @throws ParametroDeComandoInexistenteException O parâmetro de comando
	 *                                                inserido pelo usuário não
	 *                                                existe nos métodos de comando
	 *                                                da classe controladora atual.
	 */
	List<Method> getMetodosAnotadosComValorParametroCorrespondente(List<Method> metodos, List<String> parametros)
			throws ParametroDeComandoInexistenteException {
		if (parametros.size() > 0) {
			String parametro = parametros.get(0);
			metodos = this.manipuladorMetodosComando.getMetodosAnotadosPorParametro(metodos, parametro);
			if (metodos.isEmpty()) {
				throw new ParametroDeComandoInexistenteException("O parâmetro de comando: " + parametro
						+ " não está atribuído a anotações do tipo @Comando nos métodos da classe controladora atual ["
						+ this.controlador.getName() + "].");
			}
			return metodos;
		}
		return this.manipuladorMetodosComando.getMetodosAnotadosSemParametroDeComando(metodos);
	}

	/**
	 * Método responsável por retornar, dado uma lista de métodos, apenas os que
	 * possuam a quantidade de parâmetros e os tipos correspondentes aos inseridos
	 * pelo usuário.
	 * 
	 * Seu funcionamento consiste em retornar o resultado do objeto manipulador
	 * reponsável os métodos que correspondam aos requisitos.
	 * 
	 * Se o resultado for uma lista vazia, será lançado uma exceção correspondente.
	 *
	 * @param metodos           Lista de métodos {@linkplain Method}.
	 * @param classesDosValores Lista contendo todas as classes dos valores que
	 *                          devem ser passados por parâmetros.
	 * @return Todos os métodos correspondentes.
	 * @throws ValoresIncoerentesComOsComandosExistentesException Não existe método
	 *                                                            de comando que
	 *                                                            possa receber a
	 *                                                            quantidade de
	 *                                                            parâmetros que foi
	 *                                                            passada pelo
	 *                                                            usuário como
	 *                                                            valores do
	 *                                                            comando.
	 * @throws SolicitacaoDeMetodoComandoInexistenteException     Ocorrerá caso não
	 *                                                            exista métodos que
	 *                                                            possam atender a
	 *                                                            solicitação.
	 */
	List<Method> getMetodosPorTiposParametros(List<Method> metodos, List<Class<?>> classesDosValores)
			throws ValoresIncoerentesComOsComandosExistentesException, SolicitacaoDeMetodoComandoInexistenteException {
		ManipuladorMetodos manipuladorMetodos = new ManipuladorMetodos(AplicacaoBackScreen.getControladorAtual());
		List<Method> metodosComParametros = manipuladorMetodos.getMetodosComParametros(classesDosValores);
		if (metodosComParametros == null) {
			throw new ValoresIncoerentesComOsComandosExistentesException(
					"Não existe métodos anotados com @Comando que possam receber os parâmetros enviador como valores do objeto do tipo EntradaUsuario.");
		}
		metodos = metodos.stream().filter(m -> metodosComParametros.contains(m)).toList();
		if (metodos.isEmpty()) {
			throw new SolicitacaoDeMetodoComandoInexistenteException("Não existe método que atenda a solicitação.");
		}
		return metodos;
	}

}
