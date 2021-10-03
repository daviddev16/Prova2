package org.david.utils;

import java.io.IOException;
import java.util.Scanner;

/**
 *  
 *  Essa classe fornece alguns métodos que auxiliam no
 *  desenvolvimento do projeto todo.
 *  
 * @author David Duarte Pinheiro
 */
public final class Utils {

  /**
   * Junta os elementos da lista de forma encaixada.
   * 
   * @param elements o DataRegister dos elementos
   * @param charSequence o separador
   */
  public static String join(DataRegister<?> elements, CharSequence charSequence) {
	String joinedString = new String();
	for (int i = 0; i < elements.size(); i++) {
	  joinedString += elements.get(i).toString() + ((i == elements.size() - 1) ? "" : charSequence);
	}
	return joinedString.trim();
  }

  /**
   * Essa classe verifica o tipo pre-requisitado pela aplicação e
   * converte o input do usuário para o formato certo.
   */
  public static Object getScannerByType(Class<?> type, Scanner scanner) {
	if (type == Integer.class) {
	  return Integer.parseInt(scanner.next());
	} else if (type == Double.class) {
	  return Double.parseDouble(scanner.next());
	} else if (type == Short.class) {
	  return Short.parseShort(scanner.next());
	} else if (type == Long.class) {
	  return Long.parseLong(scanner.next());
	} else if (type == Float.class) {
	  return Float.parseFloat(scanner.next());
	} else if (type == Byte.class) {
	  return Byte.parseByte(scanner.next());
	} else if (type == Boolean.class) {
	  return Boolean.parseBoolean(scanner.next());
	}
	return scanner.next();
  }

  /**
   * Limpa o console
   */
  /* não funciona no eclipse :((( */
  public static void clearConsole(Scanner in) throws InterruptedException, IOException {
    if (System.getProperty("os.name").contains("Windows"))
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    else
        System.out.print("\033[H\033[2J");
    System.out.flush();
}

}
