package br.edu.infnet.josecsjuniorapi.model.domain.dto;

public record ProdutoDTO
(
    Integer id,
    String codigo,
    String descricao,
    Boolean ativo
) {}
