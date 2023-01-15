package br.com.efigueredo.blackscreen.comandos.anotacoes;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * <h4>Anotação para métodos que devem atuar como comandos do sistema.</h4>
 * 
 * Defina métodos que podem ser invocados na linha de comando por seu nome,
 * parâmetros e valores.
 * 
 * @author Emanoel
 * @since 1.0.0
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface Comando {

	/**
	 * Nome do comando.
	 * 
	 * Essa propriedade define qual nome terá o comando que o método representará.
	 * Na linha de comando, para executar o método, basta diginar o nome.
	 *
	 * @return O nome do comando.
	 */
	public String nome();

	/**
	 * Parâmetros de comando.
	 * 
	 * Dessa forma é possível ramificar o mesmo comando para ser executado de
	 * diferentes formas.
	 *
	 * @return Array de strings contendo os vários parâmetros do mesmo comando.
	 */
	public String[] parametros() default {};

}
