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

import br.edu.infnet.josecsjuniorapi.model.domain.Estacao;
import br.edu.infnet.josecsjuniorapi.model.domain.dto.EstacaoDTO;
import br.edu.infnet.josecsjuniorapi.model.service.EstacaoService;

@RestController
@RequestMapping("/api/estacoes")
public class EstacaoController {

	private final EstacaoService estacaoService;
	
	public EstacaoController(EstacaoService estacaoService)
	{
		this.estacaoService = estacaoService;
	}
	 
	@PostMapping
	public ResponseEntity<EstacaoDTO> incluir(@RequestBody EstacaoDTO estacaoDTO)
	{
		Estacao estacao = new Estacao();
		
		estacao.setCodigo(estacaoDTO.codigo());           
		estacao.setDescricao(estacaoDTO.descricao());
		estacao.setAtivo(estacaoDTO.ativo());
		estacao.setLatitude(estacaoDTO.latitude());
		estacao.setLongitude(estacaoDTO.longitude());		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(estacaoService.toDTO(estacaoService.incluir(estacao)));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EstacaoDTO> alterar(@PathVariable Integer id, @RequestBody EstacaoDTO estacaoDTO)
	{
		Estacao estacao = new Estacao();
		
		estacao.setCodigo(estacaoDTO.codigo());           
		estacao.setDescricao(estacaoDTO.descricao());
		estacao.setLatitude(estacaoDTO.latitude());
		estacao.setLongitude(estacaoDTO.longitude());		
		
		return ResponseEntity.ok(estacaoService.toDTO(estacaoService.alterar(id, estacao))); 
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Integer id)
	{
		estacaoService.excluir(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	public ResponseEntity<List<EstacaoDTO>> obterLista()
	{
		List<Estacao> listaEstacoes = estacaoService.obterLista();
		
		if(listaEstacoes.isEmpty())
			return ResponseEntity.noContent().build();
		
		return ResponseEntity.ok(listaEstacoes.stream().map(estacaoService::toDTO).toList());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EstacaoDTO> obterPorId(@PathVariable Integer id)
	{
		Estacao estacao = estacaoService.obterPorId(id);
		
		if(estacao == null)
			return ResponseEntity.noContent().build();
		
		return ResponseEntity.ok(estacaoService.toDTO(estacao));
	}
	
	@PatchMapping("/{id}/ativar")
	public ResponseEntity<EstacaoDTO> ativar(@PathVariable Integer id)
	{
		return ResponseEntity.ok(estacaoService.toDTO(estacaoService.ativar(id)));
	}
	
	@PatchMapping("/{id}/desativar")
	public ResponseEntity<EstacaoDTO> desativar(@PathVariable Integer id)
	{
		return ResponseEntity.ok(estacaoService.toDTO(estacaoService.desativar(id)));
	}
}
