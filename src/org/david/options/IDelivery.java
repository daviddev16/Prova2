package org.david.options;

/**
 *
 * Essa classe enviará para a expressão todos os dados
 * coletados depois da validação dos inputs do usuário,
 * permitindo que a implementação do programa venha.
 * 
 * <blockquote><pre>
 * {@code
 * 
 * addOption(create((handlerMap) -> closeApplication(), "<Encerrar aplicação>"));
 * 
 * }
 * </pre></blockquote>
 *
 * @author David Duarte Pinheiro
 */
public interface IDelivery<E> {

  /**
   * Entrega os dados para a implementação
   * @param data O mapa das informações salvas.
   * @return verdadeiro para manter em estado de execução.
   */
  public Boolean receive(E data);

}
