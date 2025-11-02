package br.edu.infnet.josecsjuniorapi.model.domain.dto;

import java.time.LocalDateTime;

public record OrdemProducaoApontamentoDTO 
(
	Integer id,
	Double quantidade,
	Double temperatura,
	LocalDateTime data
) {}
