package org.david.utils;

import java.util.Map;

import org.david.exceptions.RegisterValidationException;
import org.david.models.Piloto;
import org.david.options.DefaultOptionProperty;
import org.david.options.IDelivery;
import org.david.options.Option;
import org.david.options.Property;

/**
 * Essa classe é composta de métodos que auxiliarão na implementação
 * da aplicação.
 * 
 * @author David Duarte Pinheiro
 */
public final class Options {

  /**
   * Essa função estática tem como principal funcionalidade facilitar a
   * implementação das opções na aplicação real.
   * 
   * @param properties todos os dados de input e requisitos
   * @param deliveryHandler comunicação após o input
   * @param description a descrição da opção, anteriormente citada na {@link Option#getDescription()}
   * @return retorna uma nova instancia do {@link DefaultOptionProperty}
   */
  public static DefaultOptionProperty create(DataRegister<Property> properties,
	  IDelivery<Map<String, Object>> deliveryHandler, String description) {
	DefaultOptionProperty optionProperty = new DefaultOptionProperty();
	optionProperty.setDescription(description);
	if (properties != null) {
	  properties.forEach(property -> optionProperty.addProperty(property));
	}
	return optionProperty.onOk(deliveryHandler);
  }

  /**
   * Essa função estática tem como principal funcionalidade facilitar a
   * implementação das opções na aplicação real.
   * 
   * Caso a opção selecionada seja informativa, ou seja, não precisa de
   * nenhum input, não é necessário configurar as opção de dados de input e requisitos.
   * 
   * @param deliveryHandler comunicação após o input
   * @param description a descrição da opção, anteriormente citada na {@link Option#getDescription()}
   * @return retorna uma nova instancia do {@link DefaultOptionProperty}
   */
  public static DefaultOptionProperty create(IDelivery<Map<String, Object>> deliveryHandler, String description) {
	return create(null, deliveryHandler, description);
  }

  /**
   * 
   * Essa função providência os dados de input atráves de uma lista de parametros
   * que futuramente poderão ser adicionados sem precisar se preocupar com o {@link DataRegister} 
   * 
   * @return retorna uma instacia do DataRegister criada.
   */
  public static DataRegister<Property> of(Property... properties) {
	DataRegister<Property> propertiesList = new DataRegister<>(8);
	for (int i = 0; i < properties.length; i++) {
	  try {
		propertiesList.dynamicallyAdd(properties[i]);
	  } catch (RegisterValidationException e) {
		e.printStackTrace();
	  }
	}
	return propertiesList;
  }

  /**
   * Cria um novo dado de input e requisição
   */
  public static Property ask(String key, String message, Class<?> requiredValueType) {
	return new Property(key, requiredValueType, message);
  }

  /**
   * Cria um piloto atráves dos dados recebido pelo usuário, vindos pelo
   * {@link DefaultOptionProperty#execute(java.util.Scanner)}
   */
  public static Piloto createPilot(Map<String, Object> deliveryHandlerMap) {
	if (deliveryHandlerMap == null)
	  throw new NullPointerException("Mapa nulo.");

	return new Piloto((String) deliveryHandlerMap.get(Keys.CPF_KEY), (String) deliveryHandlerMap.get(Keys.NAME_KEY),
		(String) deliveryHandlerMap.get(Keys.LICENSE_KEY));
  }
}
