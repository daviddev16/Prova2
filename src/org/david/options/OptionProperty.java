package org.david.options;

import java.util.HashMap;

/**
 * Essa classe é usada como uma Option<E> normal, porem ela tambem possui
 * um registro de mapeamento. O mapeamento é fundamental na comunicação entre
 * o input do usuário, e o restante do codigo que irá executar de fato as 
 * ações da Option<E>.
 * 
 * @author David Duarte Pinheiro
 */
public abstract class OptionProperty<E> implements Option<E> {

  private HashMap<String, Object> optionProperties;

  public OptionProperty() {
	optionProperties = new HashMap<>();
  }

  /**
   * Retorna o valor da chave.
   * 
   * @param key a chave para acessar um valor específico.
   * @return o valor desejado caso ele exista.
   */
  public Object getProperty(String key) {
	return optionProperties.get(key);
  }

  /**
   * Seta o valor da chave.
   * 
   * @param key a chave para acessar um valor específico.
   * @param value o valor que poderá ser acessado futuramente
   */
  public void setProperty(String key, Object value) {
	optionProperties.put(key, value);
  }

  /**
   * Retorna se a chave informada contém um valor.
   * 
   * @param key a chave para a checagem.
   */
  public boolean contains(String key) {
	return optionProperties.containsKey(key);
  }
  
  /**
   * Remove uma chave e um valor do mapa.
   * 
   * @param key a chave para a remoção
   */
  public void removeProperty(String key) {
	optionProperties.remove(key);
  }
  
  /**
   * Limpa o mapa inteiro.
   */
  public void clear() {
	optionProperties.clear();
  }
}
