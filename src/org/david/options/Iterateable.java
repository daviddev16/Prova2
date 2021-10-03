package org.david.options;

import org.david.utils.DataRegister;

/**
 * permite que seja usado a expressão lamda para iteração dos
 * elementos do {@link DataRegister}.
 * 
 * @author David Duarte Pinheiro
 */
public interface Iterateable<E> {

  public void execute(E iterate);

}
