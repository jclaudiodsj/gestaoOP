package br.edu.infnet.josecsjuniorapi.model.domain;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

@Entity
public class Estacao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message = "O código da estação é obrigatório.")
	@Length(message = "Código da estação não pode exceder 20 caracteres.")
	private String codigo;
	
	@Length(message = "Descrição da estação não pode exceder 50 caracteres.")
	private String descricao;
	
	private boolean ativo;
	
	private Double latitude;
	private Double longitude;
	
	@OneToMany(mappedBy = "estacao", cascade = CascadeType.ALL, orphanRemoval = true)	
	private List<OrdemProducao> ordens;
	
	@Override
	public String toString()
	{
		return String.format(
		        "[Estação: %s | Descrição Estação: %s | Ativo: %s | Latitude: %.6f | Longitude: %.6f]",
		        codigo,
		        descricao,
		        (ativo ? "Sim" : "Não"),
		        latitude,
		        longitude
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
	public boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	} 
	public List<OrdemProducao> getOrdens() {
		return ordens;
	}
	public void setOrdens(List<OrdemProducao> ordens) {
		this.ordens = ordens;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	} 
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	} 
}
