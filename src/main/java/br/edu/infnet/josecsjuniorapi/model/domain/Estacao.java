package br.edu.infnet.josecsjuniorapi.model.domain;

public class Estacao {
	private String codigo;
	private String descricao;
	
	public Estacao(String codigo, String descricao)
	{
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	@Override
	public String toString()
	{
		return String.format(
		        "[Estação: %s | Descrição Estação: %s]",
		        codigo,
		        descricao
		    );
	}
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
