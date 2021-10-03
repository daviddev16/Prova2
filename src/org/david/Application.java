package org.david;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import org.david.exceptions.RegisterValidationException;
import org.david.models.Piloto;
import org.david.options.Option;
import org.david.utils.DataRegister;
import org.david.utils.Keys;
import org.david.utils.Options;
import org.david.utils.Utils;

import static org.david.utils.Options.create;
import static org.david.utils.Options.ask;
import static org.david.utils.Options.of;;

/**
 * 
 * É nessa classe que começa a real implementação.
 *  
 * @author David Duarte Pinheiro
 */
public class Application {

  /**
   * Limite de expansão por comando 
   */
  public static final int EXPANSION_LIMIT = 10;

  /**
   * Todas as opções que a aplicação possui.
   */
  private final DataRegister<Option<Boolean>> globalOptions;

  /**
   * Lista dos pilotos.
   */
  private final DataRegister<Piloto> pilots;

  /**
   * Input do usuário.
   */
  private final Scanner scanner;

  public Application(final Scanner scanner, final int initialCapacity) {
	this.pilots = new DataRegister<>();
	this.globalOptions = new DataRegister<>();
	this.scanner = scanner;
	setupAllOptions();
  }

  /**
   * Aqui é aonde todas as opções serão adicionadas
   * para que o programa possa processar o input do
   * usuário de forma dinamica.
   */
  private void setupAllOptions() {

	/* cadastro de piloto */
	addOption(
		create(of(
			ask(Keys.NAME_KEY, "Digite o nome do piloto: ", String.class),
			ask(Keys.CPF_KEY, "Digite o cpf do piloto: ", String.class),
			ask(Keys.LICENSE_KEY, "Digite a licensa do piloto: ", String.class)), handlerMap -> {

			  Piloto newPilot = Options.createPilot(handlerMap);
			  System.out.println("Novo piloto adicionado [" + newPilot.getCPF() + "].");
			  try {
				pilots.add(newPilot);
			  }catch(IndexOutOfBoundsException e) {
				System.out.println(e.getMessage());
			  }
			  return true;

			}, "<Cadastrar novo piloto>"));

	/* listagem dos pilotos */
	addOption(
		create(handlerMap -> {
		  if(!pilots.isEmpty()) {
			pilots.forEach(System.out::println);
		  }else {
			System.out.println("Nenhum piloto foi cadastrado.");
		  }

		  return true;

		}, "<Listar pilotos>"));

	/* Consultar piloto pelo CPF */
	addOption(
		create(of(
			ask(Keys.CPF_KEY, "Digite o CPF para consulta: ", String.class)), handlerMap -> { 
			  String cpf = (String)handlerMap.get(Keys.CPF_KEY);
			  if(cpf == null || cpf.isEmpty()) {
				System.out.println("CPF inválido.");
			  }else {
				for(int i = 0; i < pilots.size(); i++) {
				  if(pilots.get(i).getCPF().equals(cpf)) {
					System.out.println(pilots.get(i));
					break;
				  }
				}
			  }
			  System.out.println("Não achei nada.");
			  return true;

			}, "<Listar piloto pelo CPF>"));

	/* Expansão da lista dos pilotos  */
	addOption(
		create(of(
			ask(Keys.EXPAND_KEY, "Digite o quanto você quer aumentar a lista: ", Integer.class)), handlerMap -> { 
			  Integer more = (Integer)handlerMap.get(Keys.EXPAND_KEY);
			  if(more != null) {
				if(more > EXPANSION_LIMIT) {
				  System.out.println("Você não pode expandir mais de" + EXPANSION_LIMIT + " de uma vez.");
				  return true;
				}
				try {
				  pilots.expand(more);
				} catch (RegisterValidationException | IllegalArgumentException e) {
				  System.out.println(e.getMessage());
				}
			  }
			  else {
				System.out.println("Valor inválido.");
			  }
			  return true;

			}, "<Expandir lista de armazenamento>"));


	/* encerrar o programa */
	addOption(create((handlerMap) -> closeApplication(), "<Encerrar aplicação>"));

  }

  /**
   * Mostra um menu com todas as opções adicionadas
   * na aplicação.
   */
  private void showGlobalMenu() {
	System.out.println("\n****\nMENU");
	AtomicInteger position = new AtomicInteger(0);
	globalOptions.forEach((option) -> {
	  if (option.isDisplayable()) {
		System.out.println(position.incrementAndGet() - 1 + " - " + option.getDescription());
	  }
	});
	System.out.println("Tamanho da lista: " + pilots.size() + "/" + pilots.capacity());
	System.out.println("****");
  }

  /**
   * método utilizado para ler a opção selecionada
   * e executar ela caso sejá válida.
   */
  private boolean selectGlobalOptions() {

	int selectedOptionPosition = treatValue(scanner);

	if (!isValidOption(selectedOptionPosition)) {
	  System.out.println("Opção inválida.");
	  return true;
	}

	Option<Boolean> selectedOption = globalOptions.get(selectedOptionPosition);
	return processOption(selectedOption);
  }

  /**
   * tratar a seleção de opção
   */
  private Integer treatValue(Scanner scanner) {
	Integer inputInt = null;
	do {
	  try {
		inputInt = (Integer) Utils.getScannerByType(Integer.class, scanner);
	  }catch(Exception e) {
		System.out.println("Você digitou um valor que não corresponde ao tipo requerido.\n por favor, tente novamente.");
	  }
	} while (inputInt == null);
	return inputInt;
  }

  /**
   * Processa as opções
   */
  private boolean processOption(Option<Boolean> option) {
	return Option.consumeOption(option, scanner);
  }

  /**
   * Verifica se a opção selecionada pelo usuário
   * é valida.
   */
  private boolean isValidOption(int selectedOption) {
	if (selectedOption >= 0 && globalOptions.valid(selectedOption))
	  return true;

	return false;
  }

  /**
   * Adiciona opção no sistema
   */
  public void addOption(Option<Boolean> option) {

	if (option == null)
	  throw new NullPointerException("A aplicação não pode conter opções nulas.");

	try {
	  globalOptions.dynamicallyAdd(option);
	} catch (RegisterValidationException e) {
	  e.printStackTrace();
	}
  }

  /**
   * Executa a aplicação
   */
  public void execute() {
	do {
	  showGlobalMenu();
	} while (selectGlobalOptions());
  }

  /**
   * retorna falso para a opção de encerrar aplicação
   */
  private boolean closeApplication() {
	System.out.println("Aplicação encerrada.");
	return false;
  }


  public DataRegister<Option<Boolean>> getGlobalOptions() {
	return globalOptions;
  }

  public DataRegister<Piloto> getPilots() {
	return pilots;
  }

  public Scanner getScanner() {
	return scanner;
  }

}
