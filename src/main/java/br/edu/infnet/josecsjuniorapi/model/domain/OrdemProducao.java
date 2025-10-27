package br.edu.infnet.josecsjuniorapi.model.domain;

public class OrdemProducao extends Ordem{
	private Produto produto;
	private double quantidadePlanejada;
	private double quantidadeExecutada;
	
	@Override
	public String toString()
	{
		return String.format(
		        "%s | %s | Qtd Planejada: %.2f | Qtd Executada: %.2f ",
		        super.toString(),
		        produto.toString(),
		        quantidadePlanejada,
		        quantidadeExecutada
		    );
	}
		
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
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
}
