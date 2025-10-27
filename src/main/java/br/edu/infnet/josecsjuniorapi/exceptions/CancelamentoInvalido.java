package br.edu.infnet.josecsjuniorapi.exceptions;

public class CancelamentoInvalido extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CancelamentoInvalido(String mensagem)
	{
		super(mensagem);
	}	
}