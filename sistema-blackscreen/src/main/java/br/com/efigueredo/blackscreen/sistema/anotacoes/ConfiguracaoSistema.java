package br.com.efigueredo.blackscreen.sistema.anotacoes;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import br.com.efigueredo.blackscreen.sistema.configuracoes.ConfiguracaoSistemaTipo;

/**
 * <h4>Anotação para classes de configuração do sistema.</h4>
 * 
 * O sistema usará a anotação e seu atributo tipo para poder obter a
 * configuração inserida pelo usuário.
 * 
 * @author Emanoel
 * @since 1.0.0
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface ConfiguracaoSistema {

	/**
	 * O atributo que diferencia os tipos de configuração que o sistema suporta.
	 * 
	 * Use as constantes do enum {@linkplain ConfiguracaoSistemaTipo} para indicar o
	 * tipo de configuração do sistema.
	 *
	 * @return Constante de {@linkplain ConfiguracaoSistemaTipo} que indica o tipo
	 *         de configuração do sistema.
	 */
	public ConfiguracaoSistemaTipo tipo();

}
