package org.david.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.david.exceptions.RegisterValidationException;
import org.david.models.Pessoa;
import org.david.models.Piloto;
import org.david.utils.DataRegister;
import org.david.utils.Utils;
import org.junit.jupiter.api.Test;

/**
 * Teste do Registrador.
 * @author David Duarte Pinheiro
 */
class RegistradorTest {

  private static Pessoa[] examplesPessoas = {
	  new Piloto("10913", "A"), new Piloto("95220", "D"),
	  new Piloto("42819", "B"), new Piloto("58219", "E"),
	  new Piloto("69710", "C"), new Piloto("64921", "F")
  };

  @Test
  void dataRegister_size() throws RegisterValidationException {

	DataRegister<Pessoa> registrador = new DataRegister<Pessoa>(6);
	assertEquals(0, registrador.size());

	Arrays.stream(examplesPessoas).forEach(exPessoa -> registrador.add(exPessoa));

	assertEquals(6, registrador.size());

	registrador.remove(examplesPessoas[2]);
	registrador.remove(examplesPessoas[4]);

	assertEquals(4, registrador.size());

	Arrays.stream(examplesPessoas).filter(pessoa -> registrador.contains(pessoa)).forEach(pessoa -> {
	  try {
		registrador.remove(pessoa);
	  } catch (RegisterValidationException e) {
		e.printStackTrace();
	  }
	});

	assertEquals(0, registrador.size());
  }
  
  @Test
  void dataRegister_revalidate() throws RegisterValidationException {

	DataRegister<Pessoa> registrador = new DataRegister<>(6);

	Arrays.stream(examplesPessoas).forEach(exPessoa -> registrador.add(exPessoa));
	
	registrador.remove(examplesPessoas[0]);
	registrador.remove(examplesPessoas[1]);
	registrador.remove(examplesPessoas[3]);

	assertEquals(registrador.get(0), examplesPessoas[2]);
	assertEquals(registrador.get(1), examplesPessoas[4]);

	registrador.expand(4);

	assertEquals(10, registrador.capacity());
  
	registrador.remove(examplesPessoas[4]);
	assertNotEquals(registrador.get(1), examplesPessoas[4]);
	
	registrador.clear();
	
	assertEquals(0, registrador.size());
  }
  
  @Test
  void utils_stringJoiner() {
	
	DataRegister<Pessoa> registrador = new DataRegister<>(6);
	Arrays.stream(examplesPessoas).forEach(exPessoa -> registrador.add(exPessoa));

	System.out.println(Utils.join(registrador, ", "));
	/* ou */
	registrador.see("Test->");
  }

}
