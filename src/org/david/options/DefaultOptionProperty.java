package org.david.options;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.david.exceptions.RegisterValidationException;
import org.david.utils.DataRegister;
import org.david.utils.Utils;

/**
 * Essa classe é de fato a real implementação da execução da Opção,
 * possuindo um sistema de mapeamento de input, que permite que o 
 * usuário consiga digitar diversos inputs em uma mesma opção, usando
 * a função {@link DefaultOptionProperty#addProperty(Property)} você 
 * consegue informar ao programa exatamente o que você está esperando
 * do usuário, valindo o input e executando o comando.
 * 
 * Essa classe não possui tipo genérico, então por isso, o tipo passado
 * para a {@link OptionProperty} vai ser Boolean, permitindo que no fim
 * da execução do método {@link Option#execute(Scanner)} ele retorne um
 * valor booleano, que será usado para saber se o programa irá ser encerrado
 * ou não.
 * 
 * @author David Duarte Pinheiro
 */
public class DefaultOptionProperty extends OptionProperty<Boolean> {

  /**
   * Esse DataRegister é usado para registrar todas as requisições esperadas
   * vindas do usuário até o {@link IDelivery}.
   * @see Property como funciona a property.
   */
  private final DataRegister<Property> requiredProperties;

  /**
   * Essa variável é responsável por fazer a comunicação entre o Input do usuário
   * e o método de execução da opção selecionada atual.
   */
  private IDelivery<Map<String, Object>> deliveryHandler;
  

  /**
   * A variável description irá armazenar a descrição que futuramente será 
   * visivél no menu do usuário.
   */
  private String description;

  /**
   * Classe responsável por criar futuramente todas as opções do sistema.
   */
  public DefaultOptionProperty() {
	requiredProperties = new DataRegister<>(5);
  }

  /**
   * Essa função irá adicionar na lista uma nova pre-requisição do usuário.
   * @see Property como funciona a property.
   */
  public void addProperty(Property property) {
	try {
	  requiredProperties.dynamicallyAdd(property);
	} catch (RegisterValidationException e) {
	  e.printStackTrace();
	}
  }
  
  /**
   * A função execute será usada para cobras as pre-requisições que
   * futuramente serão definidas com a função {@link DefaultOptionProperty#addProperty(Property)}
   * 
   * @return verdadeiro para manter o programa em estado ativo.
   */
  public Boolean execute(Scanner scanner) {

	Map<String, Object> data = new HashMap<>();

	for (int i = 0; i < requiredProperties.size(); i++) {
	  Property property = requiredProperties.get(i);
	  System.out.println(property.getMessage());
	  Object value = treatValue(property.getRequiredType(), scanner);
	  data.put(property.getKey(), value);
	}

	if (deliveryHandler != null) {
	  return deliveryHandler.receive(data);
	}

	return true; /* continue */
  }

  /**
   * Função necessária para o tratamento do input que o codigo
   * irá receber.
   * 
   * @param requiredType tipo requirido nesse input
   * @return retorna um valor válido.
   */
  private Object treatValue(Class<?> requiredType, Scanner scanner) {
	Object value = null;
	do {
	  try {
		value = Utils.getScannerByType(requiredType, scanner);
	  }catch(Exception e) {
		System.out.println("Você digitou um valor que não corresponde ao tipo requerido.\n por favor, tente novamente.");
	  }
	} while (value == null);
	return value;
  }

  /**
   * Essa função é resposável pela ultima parte da execução das opções,
   * fazendo o link entre o que o usuário escreveu, e a real implementação
   * do programa.
   * 
   */
  public DefaultOptionProperty onOk(IDelivery<Map<String, Object>> deliveryHandler) {
	if (deliveryHandler == null)
	  return this;

	this.deliveryHandler = deliveryHandler;
	return this;
  }

  /**
   * Setar a descrição.
   * @throws NullPointerException caso a descrição passada sejá nula.
   * 
   */
  public void setDescription(String description) {
	if (description == null)
	  throw new NullPointerException("Descrição não pode ser nula.");

	this.description = description;
  }

  public DataRegister<Property> getRequiredProperties() {
	return requiredProperties;
  }
  
  @Override
  public String getDescription() {
	return description;
  }


}