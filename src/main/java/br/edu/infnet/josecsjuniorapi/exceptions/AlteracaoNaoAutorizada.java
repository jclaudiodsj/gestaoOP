package br.edu.infnet.josecsjuniorapi.exceptions;

public class AlteracaoNaoAutorizada extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AlteracaoNaoAutorizada(String mensagem)
	{
		super(mensagem);
	}	
}