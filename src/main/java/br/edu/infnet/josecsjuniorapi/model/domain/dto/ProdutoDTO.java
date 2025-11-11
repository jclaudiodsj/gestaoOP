package br.edu.infnet.josecsjuniorapi.model.domain.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProdutoDTO
(
	@Positive(message = "O ID deve ser um número positivo.")
	Integer id,
	
	@NotNull(message = "O código do produto é obrigatório.")
	@Length(message = "Código da produto não pode exceder 20 caracteres.")
    String codigo,
    
    @NotNull(message = "Descrição da estação é obrigatório.")
	@Length(message = "Descrição da estação não pode exceder 50 caracteres.")
    String descricao,
    
    @NotNull(message = "O campo 'ativo' é obrigatório.")
    Boolean ativo
) {}
