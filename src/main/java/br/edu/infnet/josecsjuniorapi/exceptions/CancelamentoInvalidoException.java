package br.edu.infnet.josecsjuniorapi.exceptions;

public class CancelamentoInvalidoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CancelamentoInvalidoException(String mensagem)
	{
		super(mensagem);
	}	
}