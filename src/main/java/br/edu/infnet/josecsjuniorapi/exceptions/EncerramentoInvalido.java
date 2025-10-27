package br.edu.infnet.josecsjuniorapi.exceptions;

public class EncerramentoInvalido extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EncerramentoInvalido(String mensagem)
	{
		super(mensagem);
	}	
}
