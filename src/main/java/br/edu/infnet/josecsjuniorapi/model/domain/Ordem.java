package br.edu.infnet.josecsjuniorapi.model.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;

@MappedSuperclass
public abstract class Ordem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String codigo;
	@Transient
	private Estacao estacao;
	private LocalDate dataPlanejada;
	private LocalDateTime dataCriacao;
	private LocalDateTime dataEncerramento;
	private StatusOrdem status;
	
	@Override
	public String toString()
	{
		return String.format(
		        "[Id: %d | Código: %s | %s | Data Planejada: %s | Data Criação: %s | Data Execução: %s | Ativo: %b]",
		        id,
		        codigo,
		        estacao != null ? estacao.toString() : "N/A",
		        dataPlanejada != null ? dataPlanejada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A",
		        dataCriacao != null ? dataCriacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) : "N/A",
		        dataEncerramento != null ? dataEncerramento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) : "N/A",
        		status
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
	public Estacao getEstacao() {
		return estacao;
	}
	public void setEstacao(Estacao estacao) {
		this.estacao = estacao;
	}
	public LocalDate getDataPlanejada() {
		return dataPlanejada;
	}
	public void setDataPlanejada(LocalDate dataPlanejada) {
		this.dataPlanejada = dataPlanejada;
	}
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	public LocalDateTime getDataEncerramento() {
		return dataEncerramento;
	}
	public void setDataEncerramento(LocalDateTime dataEncerramento) {
		this.dataEncerramento = dataEncerramento;
	}
	public StatusOrdem getStatus() {
		return status;
	}
	public void setStatus(StatusOrdem status) {
		this.status = status;
	}
}
