package br.edu.infnet.josecsjuniorapi.model.domain.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

public record OrdemProducaoApontamentoDTO 
(
	@NotNull(message = "O ID do apontamento é obrigatório.")
    @Positive(message = "O ID do apontamento deve ser um número positivo.")
    Integer id,

    @NotNull(message = "A quantidade é obrigatória.")
    @DecimalMin(value = "0.0", inclusive = false, message = "A quantidade deve ser maior que zero.")
    Double quantidade,

    @NotNull(message = "A temperatura é obrigatória.")
    @DecimalMin(value = "-90.0", message = "A temperatura mínima permitida é -90°C.")
    @DecimalMax(value = "60.0", message = "A temperatura máxima permitida é 60°C.")
    Double temperatura,

    @NotNull(message = "A data do apontamento é obrigatória.")
    @PastOrPresent(message = "A data do apontamento não pode estar no futuro.")
    LocalDateTime data
) {}
