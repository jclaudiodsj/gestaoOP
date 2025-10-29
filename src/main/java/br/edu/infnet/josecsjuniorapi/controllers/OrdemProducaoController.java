package br.edu.infnet.josecsjuniorapi.controllers;

import java.util.List;

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
import br.edu.infnet.josecsjuniorapi.model.domain.service.OrdemProducaoService;

@RestController
@RequestMapping("/api/ordensProducao")
public class OrdemProducaoController {

	private final OrdemProducaoService ordemProducaoService;
	
	public OrdemProducaoController(OrdemProducaoService ordemProducaoService)
	{
		this.ordemProducaoService = ordemProducaoService;
	}
	 
	@PostMapping
	public OrdemProducao incluir(@RequestBody OrdemProducao ordemProducao)
	{
		OrdemProducao novaOrdemProducao = ordemProducaoService.incluir(ordemProducao);
		
		return novaOrdemProducao;
	}
	
	@PutMapping("/{id}")
	public OrdemProducao alterar(@PathVariable Integer id, @RequestBody OrdemProducao ordemProducao)
	{
		return ordemProducaoService.alterar(id, ordemProducao);
	}
	
	@DeleteMapping("/{id}")
	public void excluir(@PathVariable Integer id)
	{
		ordemProducaoService.excluir(id);
	}
	
	@GetMapping
	public List<OrdemProducao> obterLista()
	{
		return ordemProducaoService.obterLista();
	}
	
	@GetMapping("/{id}")
	public OrdemProducao obterPorId(@PathVariable Integer id)
	{
		return ordemProducaoService.obterPorId(id);
	}
	
	@PatchMapping("/{id}/apontarProducao")
	public OrdemProducao apontarProducao(@PathVariable Integer id, @RequestBody Double producaoExecutada)
	{
		return ordemProducaoService.apontarProducao(id, producaoExecutada);
	}
	
	@PatchMapping("/{id}/encerrar")
	public OrdemProducao encerrar(@PathVariable Integer id)
	{
		return ordemProducaoService.encerrar(id);
	}
	
	@PatchMapping("/{id}/cancelar")
	public OrdemProducao cancelar(@PathVariable Integer id)
	{
		return ordemProducaoService.cancelar(id);
	}
}
