package br.edu.infnet.josecsjuniorapi.exceptions;

public class IdInvalidoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IdInvalidoException(String mensagem)
	{
		super(mensagem);
	}	
}