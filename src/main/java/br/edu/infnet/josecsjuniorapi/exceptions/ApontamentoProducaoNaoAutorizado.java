package br.edu.infnet.josecsjuniorapi.exceptions;

public class ApontamentoProducaoNaoAutorizado extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ApontamentoProducaoNaoAutorizado(String mensagem)
	{
		super(mensagem);
	}	
}