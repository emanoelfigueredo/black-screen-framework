![banner git](https://user-images.githubusercontent.com/121516171/211707781-cfce9e4d-39d5-4e70-8046-c830dfabfdc6.png)

<p align="center">
<img src="https://img.shields.io/badge/Testes-79%20sucessos%2C%200%20falhas-green?style=for-the-badge&logo=appveyor">
<img src="https://img.shields.io/badge/Licen%C3%A7a-MIT-yellowgreen?style=for-the-badge&logo=appveyor">
<img src="https://img.shields.io/badge/Windows-0078D6?style=for-the-badge&logo=windows&logoColor=white">
<img src="https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=linux&logoColor=black">
<img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white">
</p>

<a name="ancora"></a>
# Índice
- [Descrição](#descricao)
- [Obtenção](#obtencao)
	- [Maven](#maven)
	- [Donwload jar](#jar)
- [Como utilizar](#utilizar)
	- [Setup](#utilizar-setup)
	- [Criar comandos](#utilizar-criarComandos)
	- [Comandos com parâmetros e valores](#utilizar-parametrosValores)
- [Personalização do console](#personalizacao)
- [Respostas do sistema](#respostas)
	- [Obteção](#respostas-obtencao)
	- [Utilização prática](#respostas-utilizacao)
- [Inversão de controle e injeção de dependências](#inversao)
- [Tecnologias utilizadas](#tecnologias)
- [Licença](#licenca)
- [Autor](#autor)

<a name="descricao"></a>
## Descrição
Framework para aplicações comand-line.

- Crie comandos com parâmetros e atributos baseado em anotação;
- Personalize o console;
- Utilize a injeção de dependências.

<a name="obtencao"></a>
## Obtenção
<a name="maven"></a>
### Maven
~~~xml
<dependency>
      <groupId>com.github.emanoelfigueredo</groupId>
      <artifactId>black-screen-framework</artifactId>
      <version>1.0.1</version>
</dependency>
~~~
<a name="jar"></a>
### Donwload jar
<a href="https://github-registry-files.githubusercontent.com/587088759/6e92af80-92bb-11ed-9163-3b21ac442593?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAIWNJYAX4CSVEH53A%2F20230112%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20230112T235908Z&X-Amz-Expires=300&X-Amz-Signature=766dc7089906a2b4aa347b8ca8326623fa3b6575323dba12d9de95e094439c6e&X-Amz-SignedHeaders=host&actor_id=0&key_id=0&repo_id=587088759&response-content-disposition=filename%3Dblack-screen-framework-1.0.1.jar&response-content-type=application%2Foctet-stream">black-screen-framework-1.0.1.jar</a>

<a name="utilizar"></a>
## Como utilizar

<a name="utilizar-setup"></a>
### Setup
Instâncie o objeto __AplicacaoBlackScreen__, inserindo no seu construtor, a classe controladora inicial.
Utilize o método __executar__, passando como parâmetro um booleano. Onde true habilita o comando de sair padrão, e false desabilita.
 ~~~java
import br.com.efigueredo.blackscreen.sistema.AplicacaoBlackScreen;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ConfiguracaoRespostaSistemaException;
import br.com.efigueredo.blackscreen.sistema.exception.ControladorAtualInexistenteException;
import br.com.efigueredo.container.exception.ContainerIocException;

public class Main {

	public static void main(String[] args)
			throws ControladorAtualInexistenteException, ContainerIocException, ConfiguracaoRespostaSistemaException {

		AplicacaoBlackScreen app = new AplicacaoBlackScreen(Controlador.class);
		app.executar(true);
		
	}

}
 ~~~
 <a name="utilizar-criarComandos"></a>
 ### Criar comandos
 Numa classe controladora, crie um método de qualquer nome. Anote com __@Comando__. Passando como parâmetro da anotação o nome do comando.
 
 ~~~java
import br.com.efigueredo.blackscreen.anotacoes.Comando;

public class Controlador {

	@Comando(nome = "comando1")
	public void comando1() {
		System.out.println("Executando comando 1");
	}
	
}
 ~~~
 #### Resultado na linha de comando
 ![comando](https://user-images.githubusercontent.com/121516171/211817842-28165f99-39d1-4bb1-b1d9-27849911dec7.png)
 
<a name="utilizar-parametrosValores"></a>
### Parâmetros de comando e valores
É possível definir parâmetros para o comando e valores que ele recebe.
> __Parâmetros__
> Defina na anotação @Comando os parâmetros no atributo da anotação ___parametros___, que recebe um array de strings. 
> É possível adicionar quantos quiser.

> OBS: Utilize o prefixo __"--"__ para os parâmetros.
 ~~~java
@Comando(nome = "comando1", parametros = {"--parametro1", "--p"})
 ~~~
 > __Valores__
> Na assinatura do método, defina como __String__ os valores que ele deve receber.

~~~java
public void comando1(String valor1, String valor2)
~~~
 
#### Resultado na linha de comando
Para o comando finalizado:
~~~java
import br.com.efigueredo.blackscreen.anotacoes.Comando;

public class Controlador {

	@Comando(nome = "comando1", parametros = {"--parametro1", "--p"})
	public void comando1(String valor1, String valor2) {
		
		System.out.println("Executando comando 1");
		System.out.println("VALOR 1 => " + valor1);
		System.out.println("VALOR 2 => " + valor2);
		
	}
	
}
~~~
> Parametro --parametro1
> 
![comando](https://user-images.githubusercontent.com/121516171/211820597-4e65dca4-354c-40a2-b034-77d01d09459f.png)

> Parametro --p
> 
![comando](https://user-images.githubusercontent.com/121516171/211821149-e42e87eb-67d5-4331-ba9a-8cce4ba83c84.png)

<a name="personalizacao"></a>
## Personalização do console
Para personalizar o console, crie uma classe de configuração e anote com __@ConfiguracaoSistema__, passando como atributo da anotação o Enum __ConfiguracaoSistemaTipo.RESPOSTAS__.

~~~java
@ConfiguracaoSistema(tipo = ConfiguracaoSistemaTipo.RESPOSTAS)
~~~

Na assinatura da classe de configuração, implemente a interface __ConfiguracaoResposta__. Sobreescrevendo o método __configurarRespostas__.

> OBS: Se a classe estiver anotada para configuração de respotas do sistema, e não for implementação da interface __ConfiguracaoResposta__, uma exceção será lançada.

~~~java
import br.com.efigueredo.blackscreen.anotacoes.ConfiguracaoSistema;
import br.com.efigueredo.blackscreen.sistema.configuracoes.ConfiguracaoSistemaTipo;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.ConfiguracaoResposta;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.fontes.IntefaceConfiguracaoResposta;

@ConfiguracaoSistema(tipo = ConfiguracaoSistemaTipo.RESPOSTAS)
public class ConfiguracaoMensagem implements ConfiguracaoResposta {

	@Override
	public void configurarRespostas(IntefaceConfiguracaoResposta interfaceConfiguracao) {

	}
	
}
~~~

O método __configurarRespostas__ disponibiliza o objeto __IntefaceConfiguracaoResposta__, no qual é possível configurar a forma como o sistema imprime mensagens no console.

> Alterar cor e background de:
> - Texto comum
> - Texto de erro
> - Banner do sistema
> - Indicador do sistema

> Também é possível setar o banner do sistema e indicador do sistema

> Crie banners para o sistema em -> https://manytools.org/hacker-tools/ascii-banner/

### Exemplo

~~~java
public void configurarRespostas(IntefaceConfiguracaoResposta interfaceConfiguracao) {
		interfaceConfiguracao
			.setarCorTexto(CoresFontes.ANSI_AMARELO)
			.setarCorIndicador(CoresFontes.ANSI_VERMELHO)
			.setarIndicador("[EXEMPLO] => ")
			.setarCorBanner(CoresFontes.ANSI_VERDE)
			.setarBanner(

					"\n"
					+ "  8888888888 Y88b   d88P 8888888888 888b     d888 8888888b.  888       .d88888b.  \r\n"
					+ "  888         Y88b d88P  888        8888b   d8888 888   Y88b 888      d88P\" \"Y88b \r\n"
					+ "  888          Y88o88P   888        88888b.d88888 888    888 888      888     888 \r\n"
					+ "  8888888       Y888P    8888888    888Y88888P888 888   d88P 888      888     888 \r\n"
					+ "  888           d888b    888        888 Y888P 888 8888888P\"  888      888     888 \r\n"
					+ "  888          d88888b   888        888  Y8P  888 888        888      888     888 \r\n"
					+ "  888         d88P Y88b  888        888   \"   888 888        888      Y88b. .d88P \r\n"
					+ "  8888888888 d88P   Y88b 8888888888 888       888 888        88888888  \"Y88888P\"  \r\n"
					+ "                                                                                \r\n"
					+ "                                                                                \n\n"
									
				);
							
	}
~~~
![comando](https://user-images.githubusercontent.com/121516171/211831049-7fedf44f-9444-4cac-9740-adec3a8bc9d6.png)

<a name="respostas"></a>
## Respostas Sistema
Para utilizar das respostas do sistema configuradas, utilize o objeto __RespostasSistema__.

<a name="respostas-obtencao"></a>
### Obtenção
Obtenha pelo método estático do objeto __AplicacaoBlackScreen__:
~~~java
RespostasSistema respostasSistema = AplicacaoBlackScreen.getRespostasSistema();
~~~

Métodos
- imprimirBanner
- imprimirIndicador
- imprimirMensagem
- imprimirMensagemErro

<a name="respostas-utilizacao"></a>
### Utilização prática
Dado a classe controladora abaixo
~~~java
import br.com.efigueredo.blackscreen.anotacoes.Comando;
import br.com.efigueredo.blackscreen.sistema.AplicacaoBlackScreen;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.RespostasSistema;

public class Controlador {
	
	private RespostasSistema respostasSistema;
	
	public Controlador() {
		this.respostasSistema = AplicacaoBlackScreen.getRespostasSistema();
	}
	
	@Comando(nome = "imprimirBanner")
	public void comando1() {	
		this.respostasSistema.imprimirBanner();
	}
	
	@Comando(nome = "imprimirIndicador")
	public void comando2() {	
		this.respostasSistema.imprimirIndicador();
	}
	
	@Comando(nome = "imprimirMensagem")
	public void comando3(String mensagem) {	
		this.respostasSistema.imprimirMensagem(mensagem);
	}
	
	@Comando(nome = "imprimirErro")
	public void comando4(String erro) {	
		this.respostasSistema.imprimirMensagemErro(erro);
	}
	
}
~~~
![comando](https://user-images.githubusercontent.com/121516171/211836901-ffe8a832-2166-4d7a-aafa-7567df72d95f.png)

<a name="inversao"></a>
## Inversão de controle e injeção de dependências
Para utilizar a inversão de controle e injeção de dependências do Black Screen, utilize a anotação __@Injecao__ no construtor das classes que deseja implementar a funcionalidade.

> Leia o Readme do ContainerIoc responsável pela funcionalidade em https://github.com/emanoelfigueredo/container-ioc#readme

~~~java
import br.com.efigueredo.container.anotacao.Injecao;

public class Controlador {
	
	private UsuarioService service;
	
	@Injecao
	public Controlador(UsuarioService service) {
		this.service = service;
	}

}

~~~

<a id="tecnologias"></a>
# Tecnologias utilizadas

- Java 1.8
    - Reflection API
    - Reflections
    - ContainerIoc - https://github.com/emanoelfigueredo/container-ioc
    - JUnit
    - Mockito

<a id="licenca"></a>
# Licença

Black Screen é licenciado pelo MIT

<a id="autor"></a>
# Autor
<a href="https://github.com/emanoelfigueredo"><img src="https://avatars.githubusercontent.com/u/121516171?s=400&u=5a20eb0276bef0e61432f40e90b64a56df953ec9&v=4" width=115><br><sub>Emanoel Figueredo</sub></a>
