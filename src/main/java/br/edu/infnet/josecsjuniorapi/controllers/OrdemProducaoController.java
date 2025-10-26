package br.edu.infnet.josecsjuniorapi.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@GetMapping
	public List<OrdemProducao> obterLista()
	{
		return ordemProducaoService.obterLista();
	}
}
