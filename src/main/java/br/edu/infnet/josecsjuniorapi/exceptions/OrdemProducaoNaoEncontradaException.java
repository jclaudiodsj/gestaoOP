package br.edu.infnet.josecsjuniorapi.exceptions;

public class OrdemProducaoNaoEncontradaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OrdemProducaoNaoEncontradaException(String mensagem)
	{
		super(mensagem);
	}	
}
