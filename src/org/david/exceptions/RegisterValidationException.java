package org.david.exceptions;

import org.david.utils.DataRegister;

/**
 * @author David Duarte Pinheiro
 */
public class RegisterValidationException extends Exception {

  private static final long serialVersionUID = -4463772415687583214L;

  private int exceptionCode;

  /**
   * Essa exceção é lançada quando o DataRegister não consegue revalidar sua
   * propria lista.
   */
  public RegisterValidationException(int exceptionCode) {
	super("Falha na revalidação [" + exceptionCode + "].");
	this.exceptionCode = exceptionCode;
  }

  /**
   * @return {@link DataRegister#EXPANSION} ou {@link DataRegister#REMOVAL}
   */
  public int getExceptionCode() {
	return exceptionCode;
  }

}
