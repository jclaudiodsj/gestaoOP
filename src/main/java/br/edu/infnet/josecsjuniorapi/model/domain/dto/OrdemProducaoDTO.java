package br.edu.infnet.josecsjuniorapi.model.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.edu.infnet.josecsjuniorapi.model.domain.StatusOrdem;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record OrdemProducaoDTO(
	    @Positive(message = "O ID da ordem deve ser um número positivo.")
	    Integer id,

	    @NotBlank(message = "O código da ordem é obrigatório.")
	    @Size(max = 20, message = "O código da ordem deve ter no máximo 20 caracteres.")
	    String codigo,

	    @NotNull(message = "A data planejada é obrigatória.")
	    @FutureOrPresent(message = "A data planejada deve ser hoje ou uma data futura.")
	    LocalDate dataPlanejada,

	    @PastOrPresent(message = "A data de início não pode estar no futuro.")
	    LocalDateTime dataInicio,

	    @PastOrPresent(message = "A data de encerramento não pode estar no futuro.")
	    LocalDateTime dataEncerramento,

	    StatusOrdem status,

	    @NotBlank(message = "O código da estação é obrigatório.")
	    @Size(max = 20, message = "O código da estação deve ter no máximo 20 caracteres.")
	    String codigoEstacao,

	    @NotBlank(message = "O código do produto é obrigatório.")
	    @Size(max = 20, message = "O código do produto deve ter no máximo 20 caracteres.")
	    String codigoProduto,

	    @NotNull(message = "A quantidade planejada é obrigatória.")
	    @DecimalMin(value = "0.0", inclusive = false, message = "A quantidade planejada deve ser maior que zero.")
	    Double quantidadePlanejada,

	    @NotNull(message = "A quantidade executada é obrigatória.")
	    @DecimalMin(value = "0.0", inclusive = true, message = "A quantidade executada deve ser maior ou igual a zero.")
	    Double quantidadeExecutada
	) {}
