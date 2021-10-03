package org.david;

import java.util.Scanner;

public class Main {

  public static void main(String[] args) {

	final int MAX_ELEMENTS = 20; /* valor inicial */
	Scanner scanner = new Scanner(System.in);
	Application application = new Application(scanner, MAX_ELEMENTS);
 	application.execute();
  
  }
}
