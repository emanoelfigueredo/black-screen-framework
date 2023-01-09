package br.com.efigueredo.blackscreen.userinput;

import java.util.Scanner;

import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.ConfiguracaoResposta;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.RespostasSistema;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.RespostasSistemaFactory;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ClasseDeConfiguracaoSemImplementacaoException;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ConfiguracaoInterrompidaException;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.MaisDeUmaClasseDeConfiguracaoResposta;
import br.com.efigueredo.container.exception.ClasseIlegalParaIntanciaException;
import br.com.efigueredo.container.exception.InversaoDeControleInvalidaException;
import br.com.efigueredo.project_loader.projeto.exception.PacoteInexistenteException;

/**
 * <h4>Classe responsável por receber a entrada via console pelo usuário.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class RecebedorEntradaUsuario {

	/**
	 * Objeto responsável por obter inputs via console.
	 */
	private Scanner scan;

	/**
	 * Objeto responsável por imprimir no console conforme configurado.
	 * 
	 * Sua função nessa classe será imprimir o indicador configurado.
	 */
	private RespostasSistema respostas;

	/**
	 * Construtor.
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
	public RecebedorEntradaUsuario() throws ConfiguracaoInterrompidaException, MaisDeUmaClasseDeConfiguracaoResposta,
			ClasseDeConfiguracaoSemImplementacaoException, InversaoDeControleInvalidaException,
			ClasseIlegalParaIntanciaException, PacoteInexistenteException {
		this.scan = new Scanner(System.in);
		this.respostas = new RespostasSistemaFactory().getRespostasSistema();
	}

	/**
	 * Método reponsável por obter a entrada do usuário via console.
	 * 
	 * @return Expressão inserida pelo usuário.
	 */
	public String receberEntrada() {
		this.respostas.imprimirIndicador();
		return this.scan.nextLine().strip().toLowerCase();
	}

}
