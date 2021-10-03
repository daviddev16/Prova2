package org.david.options;

/**
 * 
 * Essa classe é fundamental, através dela é possível informar
 * ao programa exatamente o que o usuário deve fornecer a ele, 
 * caso o contrário o 'execute()' tentará denovo até que a informação
 * corresponda.
 * 
 * @author David Duarte Pinheiro
 *
 * @see DefaultOptionProperty
 *
 */
public class Property {

  /**
   * A chave é o recurso que o programa utilizará para fazer o mapeamento dos 
   * valores corretamente. Depois para a implementação só será necessário saber
   * qual é a chave utilizada para ter acesso a informação. 
   */
  private final String key;
  
  /**
   * Essa variável tem o intuito de dizer ao program que o usuário deve enviar
   * um determinado tipo como input. Ou seja, se o programa espera um int, o usuário
   * deverá inserir um int.
   */
  private final Class<?> requiredType;

  /**
   * Essa será a variável que enviará uma mensagem antes do usuário
   * inserir o input dele.
   */
  private final String message;

  public Property(String key, Class<?> requiredType, String message) {
	this.key = key;
	this.message = message;
	this.requiredType = requiredType;
  }

  public String getKey() {
	return key;
  }

  public String getMessage() {
	return message;
  }

  public Class<?> getRequiredType() {
	return requiredType;
  }
}