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

import br.edu.infnet.josecsjuniorapi.model.domain.OrdemProducao;
import br.edu.infnet.josecsjuniorapi.model.service.OrdemProducaoService;

@RestController
@RequestMapping("/api/ordensProducao")
public class OrdemProducaoController {

	private final OrdemProducaoService ordemProducaoService;
	
	public OrdemProducaoController(OrdemProducaoService ordemProducaoService)
	{
		this.ordemProducaoService = ordemProducaoService;
	}
	 
	@PostMapping
	public ResponseEntity<OrdemProducao> incluir(@RequestBody OrdemProducao ordemProducao)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(ordemProducaoService.incluir(ordemProducao));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<OrdemProducao> alterar(@PathVariable Integer id, @RequestBody OrdemProducao ordemProducao)
	{
		return ResponseEntity.ok(ordemProducaoService.alterar(id, ordemProducao)); 
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Integer id)
	{
		ordemProducaoService.excluir(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	public ResponseEntity<List<OrdemProducao>> obterLista()
	{
		List<OrdemProducao> listaOrdens = ordemProducaoService.obterLista();
		
		if(listaOrdens.isEmpty())
			return ResponseEntity.noContent().build();
		
		return ResponseEntity.ok(listaOrdens);  
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OrdemProducao> obterPorId(@PathVariable Integer id)
	{
		return ResponseEntity.ok(ordemProducaoService.obterPorId(id));
	}
	
	@PatchMapping("/{id}/apontarProducao")
	public ResponseEntity<OrdemProducao> apontarProducao(@PathVariable Integer id, @RequestBody Double producaoExecutada)
	{
		return ResponseEntity.ok(ordemProducaoService.apontarProducao(id, producaoExecutada));
	}
	
	@PatchMapping("/{id}/encerrar")
	public ResponseEntity<OrdemProducao> encerrar(@PathVariable Integer id)
	{
		return ResponseEntity.ok(ordemProducaoService.encerrar(id));
	}
	
	@PatchMapping("/{id}/cancelar")
	public ResponseEntity<OrdemProducao> cancelar(@PathVariable Integer id)
	{
		return ResponseEntity.ok(ordemProducaoService.cancelar(id));
	}
}
