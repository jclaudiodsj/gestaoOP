package br.edu.infnet.josecsjuniorapi.model.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record EstacaoDTO
(
	@Positive(message = "O ID deve ser um número positivo.")
    Integer id,
    
    @NotBlank(message = "O código da estação é obrigatório.")
    @Size(max = 20, message = "O código da estação deve ter no máximo 20 caracteres.")
    String codigo,
    
    @NotBlank(message = "A descrição da estação é obrigatória.")
    @Size(max = 50, message = "A descrição da estação deve ter no máximo 50 caracteres.")
    String descricao,
    
    @NotNull(message = "O campo 'ativo' é obrigatório.")
    Boolean ativo,
    
    @NotNull(message = "A latitude é obrigatória.")
    Double latitude,
    
    @NotNull(message = "A longitude é obrigatória.")    
	Double longitude
) {}
