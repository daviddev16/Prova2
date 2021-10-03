package org.david.models;

/**
 * Classe que define os atributos fundamentais do modelo
 * @author David Duarte Pinheiro
 */
public abstract class Pessoa {

  private final String cpf;
  private final String name;

  public Pessoa(String cpf, String name) {
	this.cpf = cpf;
	this.name = name;
  }

  public String getCPF() {
	return this.cpf;
  }

  public String getName() {
	return name;
  }

  /**
   * formata o cpf e o nome em um texto legível.
   */
  @Override
  public String toString() {
	return "{id=" + getCPF() + ",name=" + getName() + "}";
  }

}
