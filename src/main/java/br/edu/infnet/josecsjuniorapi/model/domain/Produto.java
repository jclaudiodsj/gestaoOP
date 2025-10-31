package br.edu.infnet.josecsjuniorapi.model.domain;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotNull(message = "O código do produto é obrigatório.")
	@Length(message = "Código da produto não pode exceder 20 caracteres.")
	private String codigo;
	@Length(message = "Descrição do produto não pode exceder 50 caracteres.")
	private String descricao;
	
	@Override
	public String toString()
	{
		return String.format(
		        "[Produto: %s | Descrição Produto: %s]",
		        codigo,
		        descricao
		    );
	}
	
	public Integer getId() {
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
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
