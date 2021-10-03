package org.david.options;

import java.util.Scanner;

/**
 * Essa interface é responsável por servir de padrão para
 * futuras classes que auxiliaram no processo de criação de opções
 * 
 * O programa terá uma lista de opções, para que opções de "comandos"
 * sejam adicionadas dinamicamente. A interfaçe Option<E> serve para
 * armazenar todas as informações necessárias que a aplicação usará
 * para executar o programa com o input do usuario.
 * 
 * @author David Duarte Pinheiro
 */
public interface Option<E> {

  /**
   * 
   * Este método é responsável por executar os subcomandos e manter
   * o programa em estado de receber inputs ou não.
   * O 'execute()' será executado antes de qualquer ação do usuário.
   * 
   * @param scanner input do usuário
   * @return retorna um valor generico, que futuramente será usado
   * para definir o rumo do programa.
   */
  E execute(Scanner scanner);

  /**
   * Este método quando chamado, é usado para dizer ao programa se essa
   * opção é visualmente visível no menu.
   * 
   * @return verdadeiro caso a descrição não seja nula.
   */
  default boolean isDisplayable() {
	return getDescription() != null;
  }

  /**
   * Pega a descrição fornecida futuramente pelo {@link DefaultOptionProperty},
   * ou por qualquer outra classe que herde Option<E>.
   * 
   * @return Retorna uma descrição, caso ela exista (por padrão o valor é null).
   */
  default String getDescription() {
	return null;
  }

  /**
   * Esse método é usado futuramente para executar uma opção selecionada.
   * 
   * @param option A opção que será executada
   * @param scanner o input do usuário.
   * @return Retorna o valor generico fornecido pela execução do metodo {@link Option#execute(Scanner)}
   */
  public static <E> E consumeOption(Option<E> option, Scanner scanner) {
	if (option == null)
	  throw new NullPointerException("A opção passada é nula.");

	E optionResult = option.execute(scanner);
	return optionResult;
  }
}
