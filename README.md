![banner git](https://user-images.githubusercontent.com/121516171/211707781-cfce9e4d-39d5-4e70-8046-c830dfabfdc6.png)

<p align="center">
<img src="https://img.shields.io/badge/Testes-79%20sucessos%2C%200%20falhas-green?style=for-the-badge&logo=appveyor">
<img src="https://img.shields.io/badge/Licen%C3%A7a-MIT-yellowgreen?style=for-the-badge&logo=appveyor">
<img src="https://img.shields.io/badge/Windows-0078D6?style=for-the-badge&logo=windows&logoColor=white">
<img src="https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=linux&logoColor=black">
<img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white">
</p>

## Descrição
Framework para aplicações comand-line.

- Crie comandos com parâmetros e atributos baseado em anotação;
- Personalize o console;
- Utilize a injeção de dependências.

## Como utilizar

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
 
 ### Criar comandos
 Numa classe controladora, crie um método de qualquer nome. Anote com __@Comandoo__. Passando como parâmetro da anotação o nome do comando.
 
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



