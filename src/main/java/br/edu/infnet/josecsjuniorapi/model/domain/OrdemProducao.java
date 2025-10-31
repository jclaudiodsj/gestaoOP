package br.edu.infnet.josecsjuniorapi.model.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class OrdemProducao extends Ordem{
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "produto_id")
	private Produto produto;
	private double quantidadePlanejada;
	private double quantidadeExecutada;
	
	@Override
	public String toString()
	{
		return String.format(
		        "%s | %s | Qtd Planejada: %.2f | Qtd Executada: %.2f ",
		        super.toString(),
		        produto != null ? produto.toString() : "N/A",
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
