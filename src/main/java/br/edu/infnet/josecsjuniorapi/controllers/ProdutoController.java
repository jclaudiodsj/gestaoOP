package br.edu.infnet.josecsjuniorapi.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

	private final ProdutoService produtoService;
	
	public ProdutoController(ProdutoService produtoService)
	{
		this.produtoService = produtoService;
	}
	 
	@PostMapping
	public ResponseEntity<ProdutoDTO> incluir(@RequestBody ProdutoDTO produtoDTO)
	{
		Produto produto = new Produto();
		
		produto.setCodigo(produtoDTO.codigo());           
		produto.setDescricao(produtoDTO.descricao());
		produto.setAtivo(produtoDTO.ativo());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.toDTO(produtoService.incluir(produto)));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProdutoDTO> alterar(@PathVariable Integer id, @RequestBody ProdutoDTO produtoDTO)
	{
		Produto produto = new Produto();
		
		produto.setCodigo(produtoDTO.codigo());           
		produto.setDescricao(produtoDTO.descricao());
		
		return ResponseEntity.ok(produtoService.toDTO(produtoService.alterar(id, produto))); 
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Integer id)
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
	public ResponseEntity<ProdutoDTO> obterPorId(@PathVariable Integer id)
	{
		Produto produto = produtoService.obterPorId(id);
		
		if(produto == null)
			return ResponseEntity.noContent().build();
		
		return ResponseEntity.ok(produtoService.toDTO(produto));
	}
	
	@PatchMapping("/{id}/ativar")
	public ResponseEntity<ProdutoDTO> ativar(@PathVariable Integer id)
	{
		return ResponseEntity.ok(produtoService.toDTO(produtoService.ativar(id)));
	}
	
	@PatchMapping("/{id}/desativar")
	public ResponseEntity<ProdutoDTO> desativar(@PathVariable Integer id)
	{
		return ResponseEntity.ok(produtoService.toDTO(produtoService.desativar(id)));
	}
}
