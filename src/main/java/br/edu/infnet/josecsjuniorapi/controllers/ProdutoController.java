package br.edu.infnet.josecsjuniorapi.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.infnet.josecsjuniorapi.model.domain.Produto;
import br.edu.infnet.josecsjuniorapi.model.domain.dto.ProdutoDTO;
import br.edu.infnet.josecsjuniorapi.model.service.ProdutoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

	private final ProdutoService produtoService;
	
	public ProdutoController(ProdutoService produtoService)
	{
		this.produtoService = produtoService;
	}
	 
	@PostMapping
	public ResponseEntity<ProdutoDTO> incluir(@Valid @RequestBody ProdutoDTO produtoDTO)
	{
		Produto produto = new Produto();
		
		produto.setCodigo(produtoDTO.codigo());           
		produto.setDescricao(produtoDTO.descricao());
		produto.setAtivo(produtoDTO.ativo());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.toDTO(produtoService.incluir(produto)));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProdutoDTO> alterar(
			@PathVariable
	        @NotNull(message = "O ID é obrigatório.")
	        @Positive(message = "O ID deve ser um número positivo.")
			Integer id,
			
			@Valid 
			@RequestBody 
			ProdutoDTO produtoDTO)
	{
		Produto produto = new Produto();
		
		produto.setCodigo(produtoDTO.codigo());           
		produto.setDescricao(produtoDTO.descricao());
		
		return ResponseEntity.ok(produtoService.toDTO(produtoService.alterar(id, produto))); 
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(
			@PathVariable
	        @NotNull(message = "O ID é obrigatório.")
	        @Positive(message = "O ID deve ser um número positivo.")
			Integer id)
	{
		produtoService.excluir(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	public ResponseEntity<List<ProdutoDTO>> obterLista()
	{
		List<Produto> listaProdutos = produtoService.obterLista();
		
		if(listaProdutos.isEmpty())
			return ResponseEntity.noContent().build();
		
		return ResponseEntity.ok(listaProdutos.stream().map(produtoService::toDTO).toList());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProdutoDTO> obterPorId(
			@PathVariable
	        @NotNull(message = "O ID é obrigatório.")
	        @Positive(message = "O ID deve ser um número positivo.")
			Integer id)
	{
		Produto produto = produtoService.obterPorId(id);
		
		if(produto == null)
			return ResponseEntity.noContent().build();
		
		return ResponseEntity.ok(produtoService.toDTO(produto));
	}
	
	@PatchMapping("/{id}/ativar")
	public ResponseEntity<ProdutoDTO> ativar(
			@PathVariable
	        @NotNull(message = "O ID é obrigatório.")
	        @Positive(message = "O ID deve ser um número positivo.")
			Integer id)
	{
		return ResponseEntity.ok(produtoService.toDTO(produtoService.ativar(id)));
	}
	
	@PatchMapping("/{id}/desativar")
	public ResponseEntity<ProdutoDTO> desativar(
			@PathVariable
	        @NotNull(message = "O ID é obrigatório.")
	        @Positive(message = "O ID deve ser um número positivo.")
			Integer id)
	{
		return ResponseEntity.ok(produtoService.toDTO(produtoService.desativar(id)));
	}
}
