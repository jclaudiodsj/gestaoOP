package br.edu.infnet.josecsjuniorapi.model.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Entity
public class OrdemProducao extends Ordem{
	
	@ManyToOne
	@JoinColumn(name = "produto_id", nullable = false)
	@NotNull(message = "O produto da ordem é obrigatório.")	
	private Produto produto;	
	
	@NotNull(message = "Quantidade planejada é obrigatória.")
	@DecimalMin(value = "0.0", inclusive = false, message = "Quantidade planejada deve ser maior que zero.")
	private double quantidadePlanejada;
	@DecimalMin(value = "0.0", inclusive = true, message = "Quantidade executada deve ser maior ou igual a zero.")
	private double quantidadeExecutada;
	
	@OneToMany(mappedBy = "ordemProducao", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)	
	private List<OrdemProducaoApontamento> apontamentos;
	
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
	public List<OrdemProducaoApontamento> getApontamentos() {
		return apontamentos;
	}
	public void setApontamentos(List<OrdemProducaoApontamento> apontamentos) {
		this.apontamentos = apontamentos;
	}	
}
