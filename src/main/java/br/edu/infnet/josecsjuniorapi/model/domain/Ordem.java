package br.edu.infnet.josecsjuniorapi.model.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;

@MappedSuperclass
public abstract class Ordem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message = "O código da ordem é obrigatório.")
	@Length(message = "Código da ordem não pode exceder 20 caracteres.")
	private String codigo;
	
	@ManyToOne
	@JoinColumn(name = "estacao_id", nullable = false)
	@NotNull(message = "A estação da ordem é obrigatória.")	
	private Estacao estacao;
	
	@NotNull(message = "Data planejada é obrigatória.")
	private LocalDate dataPlanejada;
	private LocalDateTime dataCriacao;
	private LocalDateTime dataInicio;
	private LocalDateTime dataEncerramento;
	private StatusOrdem status;
	
	@Override
	public String toString()
	{
		return String.format(
		        "[Id: %d | Código: %s | Estação: %s | Data Planejada: %s | Data Criação: %s | Data Inicio: %s | Data Encerramento: %s | Status: %s]",
		        id,
		        codigo,
		        estacao != null ? estacao.toString() : "N/A",
		        dataPlanejada != null ? dataPlanejada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A",
		        dataCriacao != null ? dataCriacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) : "N/A",
		        dataInicio != null ? dataInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) : "N/A",		
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
	public LocalDateTime getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(LocalDateTime dataInicio) {
		this.dataInicio = dataInicio;
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
