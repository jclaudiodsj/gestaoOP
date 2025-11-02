package br.edu.infnet.josecsjuniorapi.model.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.edu.infnet.josecsjuniorapi.model.domain.StatusOrdem;

public record OrdemProducaoDTO(
	    Integer id,
	    String codigo,
	    LocalDate dataPlanejada,
	    LocalDateTime dataInicio,
	    LocalDateTime dataEncerramento,
	    StatusOrdem status,
	    String codigoEstacao,
	    String codigoProduto,
	    Double quantidadePlanejada,
	    Double quantidadeExecutada
	) {}
