package org.david.utils;

import org.david.exceptions.RegisterValidationException;
import org.david.options.Iterateable;

/**
 * Classe que gerencia vetores
 * @author David Duarte Pinheiro
 */
@SuppressWarnings("unchecked")
public final class DataRegister<E> {

  /* 
   * quando a lista não acha nenhum espaço livre.
   */
  public static final int SEM_ESPACO = -1;

  /* 
   * capacidade inicial padrão
   */
  public static final int INITIAL_CAPACITY = 2;

  /* codigos de erro */
  public static final int EXPANSION = 0;
  public static final int REMOVAL = 1;

  /* 
   * quandos espaços adicionar quando a lista
   * precisar adicionar dinamicamente.
   */
  public static final int DYNAMICALLY_EXPANSION = 2;

  private Object[] elements;
  private int count;

  public DataRegister(int initialCapacity) {
	this.elements = new Object[initialCapacity];
  }

  public DataRegister() {
	this(INITIAL_CAPACITY);
  }

  /**
   * Adiciona um elemento na lista se possível.
   * 
   * @param element objeto para ser adicionado.
   * @throws NullPointerException se 'element' for igual a null 
   * @throws IndexOutOfBoundsException caso a lista esteja cheia
   * 
   **/
  public void add(E element) {

	if (element == null)
	  throw new NullPointerException("Nâo é possível adicionar um valor nulo.");

	int proximoIndexVazio = findFirst();

	if (proximoIndexVazio == SEM_ESPACO)
	  throw new IndexOutOfBoundsException("Registrador não possui espaço.");

	synchronized (elements) {
	  elements[proximoIndexVazio] = element;
	  count++;
	}
  }

  /**
   * Adiciona um elemento dinamicamente, aumentando a lista se precisar.
   * 
   * @param element objeto para ser adicionado.
   * @throws RegisterValidationException caso a lista não consiga ser revalidada. 
   * @throws NullPointerException se 'element' for igual a null 
   * 
   **/
  public void dynamicallyAdd(E element) throws RegisterValidationException {

	if (element == null)
	  throw new NullPointerException("Nâo é possível adicionar um valor nulo.");

	int proximoIndexVazio = findFirst();

	if (proximoIndexVazio == SEM_ESPACO) {
	  expand(DYNAMICALLY_EXPANSION);
	  proximoIndexVazio = findFirst();
	}

	synchronized (elements) {
	  elements[proximoIndexVazio] = element;
	  count++;
	}

  }

  /**
   * Remove o elemento passado caso ele exista.
   * 
   * @param element objeto para ser adicionado.
   * @throws NullPointerException se 'element' for igual a null 
   * @throws RegisterValidationException caso a lista não consiga ser revalidada. 
   * 
   **/
  public void remove(E element) throws RegisterValidationException {

	if (element == null)
	  throw new NullPointerException("Não é possível remover uma valor nulo.");

	synchronized (elements) {
	  for (int i = 0; i < elements.length; i++) {
		if (element.equals(elements[i])) {
		  elements[i] = null;
		  count--;
		  break;
		}
	  }
	}

	if (!revalidate())
	  throw new RegisterValidationException(REMOVAL);
  }

  /**
   * Checa se o objeto passado contém na lista.
   * 
   * @param element objeto para ser adicionado.
   * @return falso caso o objeto passado seja nulo ou se o element não existe. 
   **/
  public boolean contains(E element) {

	if (element == null)
	  return false;

	for (int i = 0; i < elements.length; i++) {
	  if (element.equals(elements[i])) {
		return true;
	  }
	}

	return false;
  }

  /**
   * Expande a lista em 'n' elementos.
   * 
   * @param expandir com quantos elementos a lista vai ser expandida.
   * @throws IllegalArgumentException Caso expandir < 0
   * @throws RegisterValidationException caso a lista não consiga ser revalidada. 
   **/
  public void expand(int expandir) throws RegisterValidationException {

	if (expandir < 0)
	  throw new IllegalArgumentException("Você não pode expandir com valores negativos.");

	Object[] novaListaExpandida = new Object[elements.length + expandir];

	for (int i = 0; i < elements.length; i++) {
	  novaListaExpandida[i] = elements[i];
	}

	if (!revalidate())
	  throw new RegisterValidationException(EXPANSION);

	elements = novaListaExpandida;
  }

  /**
   * Revalida a lista caso necessário. 
   * Este método é capaz de reorganizar a lista de forma linear,
   * Não permitindo valores nulos em sequência de valores reais.
   * 
   * <blockquote>
   * <b>Para cada item na lista:</b>
   * <br>
   * Caso o elemento anterior seja nulo, o item analisado trocará
   * para a posição do elemento nulo anterior.
   * </blockquote>
   * 
   * @return retorna falso caso algum erro tenha ocorrido durante 
   * o processo de iteração da lista.
   * 
   **/
  private boolean revalidate() {
	try {
	  synchronized (elements) {
		for (int i = 1; i < elements.length; i++) {
		  if (elements[i] != null && !containPrevious(i) /* elemento anterior nulo */) {
			swapPositions(i);
		  }
		}
	  }
	  return true;
	} catch (Exception e) {
	  return false;
	}
  }

  /**
   * Verifica se contém um elemento anterior ao index passado.
   * 
   * @param index posição atual.
   * @return verdadeiro vaso o index != 0 e o elemento anterior não seja nulo.
   **/
  private boolean containPrevious(int index) {
	if (index != 0 && elements[index - 1] != null)
	  return true;

	return false;
  }

  /**
   * Troca de posição com o elemento anterior.
   * 
   * @param index elemento atual.
   **/
  private void swapPositions(int index) {
	Object refObject = elements[index];
	elements[index - 1] = refObject;
	elements[index] = null;
  }


  /**
   * Procura um espaço vazio na lista. Caso nenhum espaço seja encontrado,
   * o metodo retornará a constante {@link DataRegister#SEM_ESPACO}.
   **/
  private int findFirst() {
	if (elements.length > 0) {
	  for (int i = 0; i < elements.length; i++) {
		if (elements[i] == null) {
		  return i;
		}
	  }
	}

	return SEM_ESPACO;
  }

  /**
   * Pega um elemento pelo index pasado.
   * @return o elemento na posição index.
   * @param index posição na lista.
   * @throws IndexOutOfBoundsException caso o index passado seja menor que zero 
   * ou maior que o {@link DataRegister#tamanho()}.
   **/
  public E get(int index) {

	if(index < 0 || index > size())
	  throw new IndexOutOfBoundsException(index + " fora dos limites.");

	return (E) elements[index];
  }


  /**
   * Exerce uma ação para todos os elementos da lista.
   * 
   * @param consumer implementação da interface ou expressão.
   * @see <a href="https://mkyong.com/java8/java-8-consumer-examples/">Veja como usar a classe Consumer aqui.</a>
   *
   * @throws NullPointerException caso o consumer seja nulo. 
   **/
  public void forEach(Iterateable<E> consumer) {

	if (consumer == null)
	  throw new NullPointerException("Null consumer.");

	synchronized (elements) {
	  for (int i = 0; i < size(); i++) {
		consumer.execute((E)elements[i]);
	  }
	}
  }

  /**
   * Limpa a lista
   * @throws RegisterValidationException caso a lista não consiga ser revalidada. 
   **/
  public void clear() throws RegisterValidationException {
	synchronized (elements) {
	  for(int i = 0; i < elements.length; i++) {
		if(elements[i] != null) {
		  elements[i] = null;
		  count--;
		}
	  }
	}
  }

  /**
   * @param index posição na lista.
   * @return Se o elemento do index existe na lista.
   **/
  public boolean valid(int index) {
	for(int i = 0; i < elements.length; i++) {
	  if(i == index && elements[i] != null) {
		return true;
	  }
	}
	return false;
  }

  /**
   * Escreve todos os elements da lista.
   * @param before string antes da lista
   **/
  public void see(String before) {
	String joinedString = Utils.join(this, ", ");
	System.out.println(before + "[" + joinedString + "]");
  }

  /**
   * Retorna se a lista está vazia.
   **/
  public boolean isEmpty() {
	return size() == 0;
  }

  /**
   * @return Capacidade total da lista.
   **/
  public int capacity() {
	return elements.length;
  }

  /**
   * @return Quantos elementos a lista tem.
   **/
  public int size() {
	return count;
  }
}
