package br.edu.infnet.josecsjuniorapi.exceptions;

public class EncerramentoInvalidoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EncerramentoInvalidoException(String mensagem)
	{
		super(mensagem);
	}	
}
