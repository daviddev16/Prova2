package org.david.models;

/**
 * Classe piloto.
 * @author David Duarte Pinheiro
 */
public final class Piloto extends Pessoa {

  private String license;
  
  public Piloto(String cpf, String name) {
	super(cpf, name);
  }

  public Piloto(String cpf, String name, String license) {
	super(cpf, name);
	this.license = license;
  }

  public String getLicense() {
	return license;
  }

  @Override
  public String toString() {
	return "{cpf=" + getCPF() + ", name=" + getName() + ", license="+getLicense()+"}";
  }
  
}
