package br.edu.infnet.josecsjuniorapi.exceptions;

public class AlteracaoNaoAutorizadaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AlteracaoNaoAutorizadaException(String mensagem)
	{
		super(mensagem);
	}	
}