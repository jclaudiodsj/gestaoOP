package br.edu.infnet.josecsjuniorapi.model.domain.dto;

public record EstacaoDTO
(
    Integer id,
    String codigo,
    String descricao,
    Boolean ativo
) {}
