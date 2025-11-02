package br.edu.infnet.josecsjuniorapi.model.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import br.edu.infnet.josecsjuniorapi.exceptions.AlteracaoNaoAutorizadaException;
import br.edu.infnet.josecsjuniorapi.exceptions.ApontamentoProducaoInvalidoException;
import br.edu.infnet.josecsjuniorapi.exceptions.ApontamentoProducaoNaoAutorizado;
import br.edu.infnet.josecsjuniorapi.exceptions.EncerramentoInvalidoException;
import br.edu.infnet.josecsjuniorapi.exceptions.IdInvalidoException;
import br.edu.infnet.josecsjuniorapi.exceptions.OrdemProducaoNaoEncontradaException;
import br.edu.infnet.josecsjuniorapi.model.domain.OrdemProducao;
import br.edu.infnet.josecsjuniorapi.model.domain.StatusOrdem;
import br.edu.infnet.josecsjuniorapi.model.domain.dto.OrdemProducaoDTO;
import br.edu.infnet.josecsjuniorapi.model.repository.OrdemProducaoRepository;

@Service
public class OrdemProducaoService implements CrudService<OrdemProducao, Integer> {

	private final OrdemProducaoRepository ordemProducaoRepository;
	
	public OrdemProducaoService(OrdemProducaoRepository ordemProducaoRepository)
	{
		this.ordemProducaoRepository = ordemProducaoRepository;
	}
	
	@Override
	public OrdemProducao incluir(OrdemProducao ordemProducao) {
		 
		if(ordemProducao == null)
			throw new IllegalArgumentException("Ordem de produção não pode ser nula!");
		
		if(ordemProducao.getId() != null && ordemProducao.getId() != 0)
			throw new IllegalArgumentException("Id é gerado automaticamente!");
		
		if(ordemProducao.getCodigo().isBlank())
			throw new IllegalArgumentException("Código da ordem deve ser indicado!");
		
		if(ordemProducao.getEstacao() == null)
			throw new IllegalArgumentException("Estação deve ser indicada!");
			
		if(ordemProducao.getDataPlanejada() == null)
			throw new IllegalArgumentException("Data planejada deve ser indicada!");
		
		if(ordemProducao.getDataPlanejada().isBefore(LocalDate.now()))
			throw new IllegalArgumentException("Data planejada deve ser maior ou igual a data atual!");
		
		if(ordemProducao.getDataCriacao() != null)
			throw new IllegalArgumentException("Data criação é gerada automaticamente!");
		
		if(ordemProducao.getDataInicio() != null)
			throw new IllegalArgumentException("Data inicio é gerenciado automaticamente!");
		
		if(ordemProducao.getDataEncerramento() != null)
			throw new IllegalArgumentException("Data encerramento é gerenciado automaticamente!");
		
		if(ordemProducao.getStatus() != null)
			throw new IllegalArgumentException("Status é gerado automaticamente!");

		if(ordemProducao.getProduto() == null)
			throw new IllegalArgumentException("Produto deve ser indicada!");
		
		if(ordemProducao.getQuantidadePlanejada() <= 0)
			throw new IllegalArgumentException("Quantidade planejada deve ser maior que zero!");
		
		if(ordemProducao.getQuantidadeExecutada() != 0)
			throw new IllegalArgumentException("Quantidade executada é gerenciada automaticamente!");
		
		ordemProducao.setDataCriacao(LocalDateTime.now());
		ordemProducao.setStatus(StatusOrdem.CRIADO);
		ordemProducao.setQuantidadeExecutada(0);
		
		return ordemProducaoRepository.save(ordemProducao);
	}

	@Override
	public List<OrdemProducao> obterLista() {
		
		return ordemProducaoRepository.findAll();
	}

	@Override
	public OrdemProducao alterar(Integer id, OrdemProducao ordemProducao) {

		if(ordemProducao == null)
			throw new IllegalArgumentException("Ordem de produção não pode ser nula!");
		
		OrdemProducao ordemProducaoEncontrado = this.obterPorId(id);
		
		if(!ordemProducaoEncontrado.getStatus().equals(StatusOrdem.CRIADO))
			throw new AlteracaoNaoAutorizadaException("Ordens de produção iniciadas, encerradas ou canceladas não podem ser alteradas!");
		
		if(!Objects.equals(ordemProducaoEncontrado.getCodigo(), ordemProducao.getCodigo()))
			throw new AlteracaoNaoAutorizadaException("Código da ordem de produção não pode ser alterado!");
		
		if(!Objects.equals(ordemProducaoEncontrado.getEstacao().getCodigo(), ordemProducao.getEstacao().getCodigo()))
			throw new AlteracaoNaoAutorizadaException("Estação da ordem de produção não pode ser alterado!");
		
		if(!ordemProducaoEncontrado.getStatus().equals(ordemProducao.getStatus()))
			throw new AlteracaoNaoAutorizadaException("Status da ordem de produção não pode ser alterado diretamente!");
			
		if(!ordemProducaoEncontrado.getProduto().getCodigo().equals(ordemProducao.getProduto().getCodigo()))
			throw new AlteracaoNaoAutorizadaException("Produto da ordem de produção não pode ser alterado!");
		
		if(ordemProducaoEncontrado.getQuantidadeExecutada() != ordemProducao.getQuantidadeExecutada())
			throw new AlteracaoNaoAutorizadaException("Quantidade executada não pode ser alterada!");
		
		if(ordemProducao.getQuantidadePlanejada() <= 0)
			throw new AlteracaoNaoAutorizadaException("Quantidade planejada deve ser maior que zero!");
		
		ordemProducaoEncontrado.setQuantidadePlanejada(ordemProducao.getQuantidadePlanejada());
		
		if(ordemProducao.getDataPlanejada().isBefore(LocalDate.now()))
			throw new AlteracaoNaoAutorizadaException("Data planejada deve ser maior ou igual a data atual!");
		
		ordemProducaoEncontrado.setDataPlanejada(ordemProducao.getDataPlanejada());
		
		return ordemProducaoRepository.save(ordemProducaoEncontrado);
	}

	@Override
	public void excluir(Integer id) {
		
		OrdemProducao ordemProducaoEncontrado = this.obterPorId(id);
		
		ordemProducaoRepository.delete(ordemProducaoEncontrado);
	}
	
	@Override
	public OrdemProducao obterPorId(Integer id) {
		
		if(id == null || id <= 0)
			throw new IdInvalidoException("Id deve ser indicado e maior que zero!");
		
		return ordemProducaoRepository.findById(id).orElseThrow(() -> new OrdemProducaoNaoEncontradaException("Não foi encontrada ordem de produção com Id " + id + "!"));
	}

	public OrdemProducao apontarProducao(Integer id, Double producaoExecutada, Double temperatura) {

		OrdemProducao ordemProducao = this.obterPorId(id);
		
		if(producaoExecutada <= 0)
			throw new ApontamentoProducaoInvalidoException("Produção executada deve ser maior que zero!");
		
		if(ordemProducao.getStatus().equals(StatusOrdem.CANCELADO))
			throw new ApontamentoProducaoInvalidoException("Ordem de produção cancelada não pode ter produção apontada!");
		
		if(ordemProducao.getStatus().equals(StatusOrdem.ENCERRADO))
			throw new ApontamentoProducaoInvalidoException("Ordem de produção encerrada não pode ter produção apontada!");
		
		if(!ordemProducao.getEstacao().getAtivo())
			throw new ApontamentoProducaoNaoAutorizado("Ordem de produção de uma estação inativa não pode ser apontada!");
		
		if(!ordemProducao.getProduto().getAtivo())
			throw new ApontamentoProducaoNaoAutorizado("Ordem de produção de um produto inativo não pode ser apontada!");
		
		if(ordemProducao.getStatus().equals(StatusOrdem.CRIADO))
		{
			ordemProducao.setStatus(StatusOrdem.INICIADO);
			ordemProducao.setDataInicio(LocalDateTime.now());
		}
		
		ordemProducao.setQuantidadeExecutada(ordemProducao.getQuantidadeExecutada() + producaoExecutada);
		
		return ordemProducaoRepository.save(ordemProducao);
	}

	public OrdemProducao encerrar(Integer id) {

		OrdemProducao ordemProducao = this.obterPorId(id);
		
		if(ordemProducao.getStatus().equals(StatusOrdem.CANCELADO))
			throw new EncerramentoInvalidoException("Ordem de produção cancelada não pode ser encerrada!");
		
		if(ordemProducao.getStatus().equals(StatusOrdem.ENCERRADO))
			throw new EncerramentoInvalidoException("Ordem de produção já encerrada!");
		
		if(ordemProducao.getStatus().equals(StatusOrdem.CRIADO) && ordemProducao.getQuantidadeExecutada() == 0)
			throw new EncerramentoInvalidoException("Ordem de produção não pode ser encerrada sem nenhum apontamento de produção executada!");
		
		ordemProducao.setStatus(StatusOrdem.ENCERRADO);
		ordemProducao.setDataEncerramento(LocalDateTime.now());
		
		return ordemProducaoRepository.save(ordemProducao);
	}
	
	public OrdemProducao cancelar(Integer id) {

		OrdemProducao ordemProducao = this.obterPorId(id);
		
		if(ordemProducao.getStatus().equals(StatusOrdem.CANCELADO))
			throw new ApontamentoProducaoInvalidoException("Ordem de produção já cancelada!");
		
		if(ordemProducao.getStatus().equals(StatusOrdem.ENCERRADO))
			throw new ApontamentoProducaoInvalidoException("Ordem de produção encerrada não pode ser cancelada!");
		
		if(ordemProducao.getStatus().equals(StatusOrdem.INICIADO))
			throw new ApontamentoProducaoInvalidoException("Ordem de produção já iniciada!");
		
		ordemProducao.setStatus(StatusOrdem.CANCELADO);
		
		return ordemProducaoRepository.save(ordemProducao);
	}
	
	public List<OrdemProducao> obterPorStatus(StatusOrdem status)
	{
		return ordemProducaoRepository.findByStatus(status);
	}
	
	public List<OrdemProducao> obterPorDataPlanejada(LocalDate dataPlanejada)
	{
		return ordemProducaoRepository.findByDataPlanejada(dataPlanejada);
	}
	
	public List<OrdemProducao> obterPorDataInicioPeriodo(LocalDateTime inicio, LocalDateTime fim)
	{
		return ordemProducaoRepository.findByDataInicioBetween(inicio, fim);
	}
    
	public List<OrdemProducao> obterPorDataEncerramentoPeriodo(LocalDateTime inicio, LocalDateTime fim)
	{
		return ordemProducaoRepository.findByDataEncerramentoBetween(inicio, fim);
	}
	
	public List<OrdemProducao> obterPorProducaoPercentualRealizada(double producaoPercentualRealizada)
	{
		return ordemProducaoRepository.findComPercentualProducaoMinimo(producaoPercentualRealizada);
	}

	public List<OrdemProducao> obterPorCodigoProduto(String codigoProduto)
	{
		return ordemProducaoRepository.findByProdutoCodigo(codigoProduto);
	}
	
	public List<OrdemProducao> obterPorCodigoEstacao(String codigoEstacao)
	{
		return ordemProducaoRepository.findByEstacaoCodigo(codigoEstacao);
	}
    
	public List<OrdemProducao> obterProximas5(String codigoEstacao)
	{
		return ordemProducaoRepository.findTop5ByEstacaoCodigoAndStatusInOrderByDataPlanejadaAsc(codigoEstacao, List.of(StatusOrdem.CRIADO));
	}
	
	public List<OrdemProducao> obterUltimas5Encerradas()
	{
		return ordemProducaoRepository.findTop5ByStatusOrderByDataEncerramentoDesc(StatusOrdem.ENCERRADO);
	}
	
	public OrdemProducaoDTO toDTO(OrdemProducao ordem) 
	{
	    return new OrdemProducaoDTO(
	        ordem.getId(),
	        ordem.getCodigo(),
	        ordem.getDataPlanejada(),
	        ordem.getDataInicio(),
	        ordem.getDataEncerramento(),
	        ordem.getStatus(),
	        ordem.getEstacao() != null ? ordem.getEstacao().getCodigo() : "",
	        ordem.getProduto() != null ? ordem.getProduto().getCodigo() : "",	        
	        ordem.getQuantidadePlanejada(),
	        ordem.getQuantidadeExecutada()
	    );
	}
}
