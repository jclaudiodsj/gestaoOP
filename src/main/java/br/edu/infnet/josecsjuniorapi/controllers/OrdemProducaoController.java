package br.edu.infnet.josecsjuniorapi.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.infnet.josecsjuniorapi.model.domain.Estacao;
import br.edu.infnet.josecsjuniorapi.model.domain.OrdemProducao;
import br.edu.infnet.josecsjuniorapi.model.domain.OrdemProducaoApontamento;
import br.edu.infnet.josecsjuniorapi.model.domain.StatusOrdem;
import br.edu.infnet.josecsjuniorapi.model.domain.dto.OrdemProducaoApontamentoDTO;
import br.edu.infnet.josecsjuniorapi.model.domain.dto.OrdemProducaoDTO;
import br.edu.infnet.josecsjuniorapi.model.service.EstacaoService;
import br.edu.infnet.josecsjuniorapi.model.service.OrdemProducaoService;
import br.edu.infnet.josecsjuniorapi.model.service.ProdutoService;
import br.edu.infnet.josecsjuniorapi.model.service.WeatherService;

@RestController
@RequestMapping("/api/ordensProducao")
public class OrdemProducaoController {

	private final OrdemProducaoService ordemProducaoService;
	private final EstacaoService estacaoService;
	private final ProdutoService produtoService;
	private final WeatherService weatherService;
	
	public OrdemProducaoController(OrdemProducaoService ordemProducaoService, EstacaoService estacaoService, ProdutoService produtoService, WeatherService weatherService)
	{
		this.ordemProducaoService = ordemProducaoService;
		this.estacaoService = estacaoService;
		this.produtoService = produtoService;
		this.weatherService = weatherService;
	}
	 
	@PostMapping
	public ResponseEntity<OrdemProducaoDTO> incluir(@RequestBody OrdemProducaoDTO ordemProducaoDTO)
	{
		OrdemProducao ordemProducao = new OrdemProducao();
		
		ordemProducao.setCodigo(ordemProducaoDTO.codigo());           
		ordemProducao.setDataPlanejada(ordemProducaoDTO.dataPlanejada());
		ordemProducao.setQuantidadePlanejada(ordemProducaoDTO.quantidadePlanejada());
		ordemProducao.setEstacao(estacaoService.obterPorCodigo(ordemProducaoDTO.codigoEstacao()));
		ordemProducao.setProduto(produtoService.obterPorCodigo(ordemProducaoDTO.codigoProduto()));
		          
        return ResponseEntity.status(HttpStatus.CREATED).body(ordemProducaoService.toDTO(ordemProducaoService.incluir(ordemProducao)));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<OrdemProducaoDTO> alterar(@PathVariable Integer id, @RequestBody OrdemProducaoDTO ordemProducaoDTO)
	{
		OrdemProducao ordemProducao = new OrdemProducao();
		
		ordemProducao.setId(id);
		ordemProducao.setCodigo(ordemProducaoDTO.codigo());           
		ordemProducao.setDataPlanejada(ordemProducaoDTO.dataPlanejada());
		ordemProducao.setDataInicio(ordemProducaoDTO.dataInicio());
		ordemProducao.setDataEncerramento(ordemProducaoDTO.dataEncerramento());
		ordemProducao.setStatus(ordemProducaoDTO.status());
		ordemProducao.setEstacao(estacaoService.obterPorCodigo(ordemProducaoDTO.codigoEstacao()));
		ordemProducao.setProduto(produtoService.obterPorCodigo(ordemProducaoDTO.codigoProduto()));
		ordemProducao.setQuantidadePlanejada(ordemProducaoDTO.quantidadePlanejada());
		ordemProducao.setQuantidadeExecutada(ordemProducaoDTO.quantidadeExecutada());
		
		return ResponseEntity.ok(ordemProducaoService.toDTO(ordemProducaoService.alterar(id, ordemProducao))); 
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Integer id)
	{
		ordemProducaoService.excluir(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	public ResponseEntity<List<OrdemProducaoDTO>> obterLista()
	{
		List<OrdemProducao> listaOrdens = ordemProducaoService.obterLista();
		
		if(listaOrdens.isEmpty())
			return ResponseEntity.noContent().build();
		
		return ResponseEntity.ok(listaOrdens.stream().map(ordemProducaoService::toDTO).toList());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OrdemProducaoDTO> obterPorId(@PathVariable Integer id)
	{
		OrdemProducao ordemProducao = ordemProducaoService.obterPorId(id);
		
		if(ordemProducao == null)
			return ResponseEntity.noContent().build();
		
		return ResponseEntity.ok(ordemProducaoService.toDTO(ordemProducao));
	}
	
	@GetMapping("/{id}/apontamentos")
	public ResponseEntity<List<OrdemProducaoApontamentoDTO>> apontamentos(@PathVariable Integer id)
	{
		OrdemProducao ordemProducao = ordemProducaoService.obterPorId(id);
		
		List<OrdemProducaoApontamento> listaApontamentos = ordemProducaoService.obterApontamentos(ordemProducao);
		
		if(listaApontamentos.isEmpty())
			return ResponseEntity.noContent().build();
		
		return ResponseEntity.ok(listaApontamentos.stream().map(ordemProducaoService::toApontamentoDTO).toList());
	}
	
	@PatchMapping("/{id}/apontarProducao")
	public ResponseEntity<OrdemProducaoDTO> apontarProducao(@PathVariable Integer id, @RequestBody Double producaoExecutada)
	{
		OrdemProducao ordemProducao = ordemProducaoService.obterPorId(id);
		Estacao estacao = ordemProducao.getEstacao();
		
		Double temperatura = weatherService.obterTemperaturaCelsius(estacao.getLatitude(), estacao.getLongitude());
		
		System.out.printf("Temperatura atual registrada: %.2f°C%n", temperatura);
		
		return ResponseEntity.ok(ordemProducaoService.toDTO(ordemProducaoService.apontarProducao(id, producaoExecutada, temperatura)));
	}
	
	@PatchMapping("/{id}/encerrar")
	public ResponseEntity<OrdemProducaoDTO> encerrar(@PathVariable Integer id)
	{
		return ResponseEntity.ok(ordemProducaoService.toDTO(ordemProducaoService.encerrar(id)));
	}
	
	@PatchMapping("/{id}/cancelar")
	public ResponseEntity<OrdemProducaoDTO> cancelar(@PathVariable Integer id)
	{
		return ResponseEntity.ok(ordemProducaoService.toDTO(ordemProducaoService.cancelar(id)));
	}
	
	
	// 1) Lista de ordens de produção com status X
    // GET /api/ordens-producao/obterPorStatus?status=CRIADO
    @GetMapping("/obterPorStatus")
    public ResponseEntity<List<OrdemProducaoDTO>> obterPorStatus(@RequestParam StatusOrdem status) 
    {
		List<OrdemProducao> listaOrdens = ordemProducaoService.obterPorStatus(status);
		
		if(listaOrdens.isEmpty())
			return ResponseEntity.noContent().build();
		
		return ResponseEntity.ok(listaOrdens.stream().map(ordemProducaoService::toDTO).toList());
    }

    // 2) Lista de ordens de produção planejadas para a data X
    // GET /api/ordens-producao/obterPorDataPlanejada?data=2025-11-02
    @GetMapping("/obterPorDataPlanejada")
    public ResponseEntity<List<OrdemProducaoDTO>> obterPorDataPlanejada(@RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate data) 
    {
        List<OrdemProducao> listaOrdens = ordemProducaoService.obterPorDataPlanejada(data);
		
		if(listaOrdens.isEmpty())
			return ResponseEntity.noContent().build();
		
		return ResponseEntity.ok(listaOrdens.stream().map(ordemProducaoService::toDTO).toList());
    }

    // 3) Lista de ordens de produção iniciadas entre X e Y
    // GET /api/ordens-producao/obterPorDataInicioPeriodo?de=2025-11-01T00:00:00&ate=2025-11-02T23:59:59
    @GetMapping("/obterPorDataInicioPeriodo")
    public ResponseEntity<List<OrdemProducaoDTO>> obterPorDataInicioPeriodo(
    		@RequestParam("de") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime de, 
    		@RequestParam("ate") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime ate) 
    {
        List<OrdemProducao> listaOrdens = ordemProducaoService.obterPorDataInicioPeriodo(de, ate);
		
		if(listaOrdens.isEmpty())
			return ResponseEntity.noContent().build();
		
		return ResponseEntity.ok(listaOrdens.stream().map(ordemProducaoService::toDTO).toList());
    }

    // 4) Lista de ordens de produção encerradas entre X e Y
    // GET /api/ordens-producao/obterPorDataEncerramentoPeriodo?de=2025-11-01T00:00:00&ate=2025-11-02T23:59:59
    @GetMapping("/obterPorDataEncerramentoPeriodo")
    public ResponseEntity<List<OrdemProducaoDTO>> obterPorDataEncerramentoPeriodo(
            @RequestParam("de") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime de,
            @RequestParam("ate") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime ate) 
    {
        List<OrdemProducao> listaOrdens = ordemProducaoService.obterPorDataEncerramentoPeriodo(de, ate);
		
		if(listaOrdens.isEmpty())
			return ResponseEntity.noContent().build();
		
		return ResponseEntity.ok(listaOrdens.stream().map(ordemProducaoService::toDTO).toList());
    }

    // 5) Lista de ordens de produção que já produziram X% da quantidade planejada
    // GET /api/ordens-producao/obterPorProducaoPercentualRealizada?min=60
    @GetMapping("/obterPorProducaoPercentualRealizada")
    public ResponseEntity<List<OrdemProducaoDTO>> obterPorProducaoPercentualRealizada(@RequestParam("min") double percentualMinimo) 
    {
        List<OrdemProducao> listaOrdens = ordemProducaoService.obterPorProducaoPercentualRealizada(percentualMinimo);
		
		if(listaOrdens.isEmpty())
			return ResponseEntity.noContent().build();
		
		return ResponseEntity.ok(listaOrdens.stream().map(ordemProducaoService::toDTO).toList());
    }

    // 6) Lista de ordens de produção do produto com código X
    // GET /api/ordens-producao/obterPorCodigoProduto/ABC123
    @GetMapping("/obterPorCodigoProduto/{codigoProduto}")
    public ResponseEntity<List<OrdemProducaoDTO>> obterPorCodigoProduto(@PathVariable String codigoProduto) 
    {
        List<OrdemProducao> listaOrdens = ordemProducaoService.obterPorCodigoProduto(codigoProduto);
		
		if(listaOrdens.isEmpty())
			return ResponseEntity.noContent().build();
		
		return ResponseEntity.ok(listaOrdens.stream().map(ordemProducaoService::toDTO).toList());
    }

    // 7) Lista de ordens de produção da estação com código X
    // GET /api/ordens-producao/obterPorCodigoEstacao/EST-01
    @GetMapping("/obterPorCodigoEstacao/{codigoEstacao}")
    public ResponseEntity<List<OrdemProducaoDTO>> obterPorCodigoEstacao(@PathVariable String codigoEstacao) 
    {
        List<OrdemProducao> listaOrdens = ordemProducaoService.obterPorCodigoEstacao(codigoEstacao);
		
		if(listaOrdens.isEmpty())
			return ResponseEntity.noContent().build();
		
		return ResponseEntity.ok(listaOrdens.stream().map(ordemProducaoService::toDTO).toList());
    }

    // 8) Top 5 próximas ordens de produção pendentes para estação X
    // GET /api/ordens-producao/obterProximas5/EST-01
    @GetMapping("/obterProximas5/{codigoEstacao}")
    public ResponseEntity<List<OrdemProducaoDTO>> obterProximas5(@PathVariable String codigoEstacao) 
    {
        List<OrdemProducao> listaOrdens = ordemProducaoService.obterProximas5(codigoEstacao);
		
		if(listaOrdens.isEmpty())
			return ResponseEntity.noContent().build();
		
		return ResponseEntity.ok(listaOrdens.stream().map(ordemProducaoService::toDTO).toList());
    }

    // 9) Top 5 últimas ordens de produção finalizadas
    // GET /api/ordens-producao/obterUltimas5Encerradas
    @GetMapping("/obterUltimas5Encerradas")
    public ResponseEntity<List<OrdemProducaoDTO>> obterUltimas5Encerradas() 
    {
        List<OrdemProducao> listaOrdens = ordemProducaoService.obterUltimas5Encerradas();
		
		if(listaOrdens.isEmpty())
			return ResponseEntity.noContent().build();
		
		return ResponseEntity.ok(listaOrdens.stream().map(ordemProducaoService::toDTO).toList());
    }
}
