package br.edu.infnet.josecsjuniorapi.model.domain;

public class OrdemProducao {
	private Integer id;
	private String codigo;
	private String produto;
	private double quantidadePlanejada;
	private double quantidadeExecutada;
	private String data;
	private boolean ativo;
	
	@Override
	public String toString()
	{
		return "Id: " + id + ", Código: " + codigo + ", Produto: " + produto + ", Quantidade Planejada: " + quantidadePlanejada + ", Quantidade Executada: " + quantidadeExecutada + ", Data: " + data + ", Ativo: " + ativo;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getProduto() {
		return produto;
	}
	public void setProduto(String produto) {
		this.produto = produto;
	}
	public double getQuantidadePlanejada() {
		return quantidadePlanejada;
	}
	public void setQuantidadePlanejada(double quantidadePlanejada) {
		this.quantidadePlanejada = quantidadePlanejada;
	}
	public double getQuantidadeExecutada() {
		return quantidadeExecutada;
	}
	public void setQuantidadeExecutada(double quantidadeExecutada) {
		this.quantidadeExecutada = quantidadeExecutada;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
}
