package br.edu.infnet.josecsjuniorapi.exceptions;

public class OrdemProducaoInvalidaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OrdemProducaoInvalidaException(String mensagem)
	{
		super(mensagem);
	}	
}
