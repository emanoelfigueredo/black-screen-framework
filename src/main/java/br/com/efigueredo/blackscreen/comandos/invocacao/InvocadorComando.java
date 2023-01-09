package br.com.efigueredo.blackscreen.comandos.invocacao;

import java.lang.reflect.Method;
import java.util.List;

import br.com.efigueredo.blackscreen.comandos.invocacao.exception.InvocacaoComandoInterrompidaException;
import br.com.efigueredo.container.anotacao.Injecao;
import br.com.efigueredo.container.exception.ClasseIlegalParaIntanciaException;
import br.com.efigueredo.container.exception.InversaoDeControleInvalidaException;
import br.com.efigueredo.project_loader.projeto.exception.PacoteInexistenteException;

/**
 * <h4>Classe responsável por invocar os métodos de comando.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class InvocadorComando {

	/** Objeto responsável por obter uma instância do controlador do método. */
	private InstanciadorControlador intanciadorControlador;

	/** Objeto responsável por invocar o método de comando. */
	private InvocadorMetodo invocadorMetodo;

	/**
	 * Construtor.
	 *
	 * @throws PacoteInexistenteException Ocorrerá se o pacote raiz do projeto não
	 *                                    existir no sistema de arquivos do sistema
	 *                                    operacional.
	 */
	public InvocadorComando() throws PacoteInexistenteException {
		this.intanciadorControlador = new InstanciadorControlador();
		this.invocadorMetodo = new InvocadorMetodo();
	}

	/**
	 * Invoque o método de comando.
	 * 
	 * Seu funcionamento consiste em delegar as tarefas necessárias para a invocação
	 * do método passado por parâmetro. Os objetos responsáveis irão intânciar o
	 * controlador atual com todas as suas dependências e invocar o método com todos
	 * os parâmetros.
	 *
	 * @param controlador   Objeto {@linkplain Class} que represente a classe
	 *                      controladora ao qual o método pertence.
	 * @param metodoComando Objeto {@linkplain Method} que represente o método para
	 *                      ser invocado.
	 * @param valores       Lista de valores para ser passada como parâmetro na
	 *                      invocação do método.
	 * @throws InversaoDeControleInvalidaException   Ocorrerá se houver algum
	 *                                               problema na inversão de
	 *                                               controle e injeção de
	 *                                               dependências no momento da
	 *                                               instânciação da classe
	 *                                               controladora inserida. Podendo
	 *                                               ser:<br>
	 *                                               <ul>
	 *                                               <li>Dois construtores anotados
	 *                                               com {@linkplain Injecao}.</li>
	 *                                               <li>Inexistência de construtor
	 *                                               anotado com
	 *                                               {@linkplain Injecao} e
	 *                                               construtor padrão</li>
	 *                                               </ul>
	 * @throws ClasseIlegalParaIntanciaException     Ocorrerá se alguma depêndencia
	 *                                               for uma interface e não houve
	 *                                               classe concetra configurada
	 *                                               para ser instânciada.
	 * @throws InvocacaoComandoInterrompidaException Ocorrerá se houver alguma falha
	 *                                               na invocação do comando. A
	 *                                               cuasa estára indicada na stack
	 *                                               trace.
	 */
	public void invocarComando(Class<?> controlador, Method metodoComando, List<String> valores)
			throws InversaoDeControleInvalidaException, ClasseIlegalParaIntanciaException,
			InvocacaoComandoInterrompidaException {
		Object objetoControlador = this.intanciadorControlador.intanciarControlador(controlador);
		this.invocadorMetodo.invocar(objetoControlador, metodoComando, valores);
	}

}
