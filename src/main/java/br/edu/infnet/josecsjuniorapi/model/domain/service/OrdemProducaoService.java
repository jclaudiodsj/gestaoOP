package br.edu.infnet.josecsjuniorapi.model.domain.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import br.edu.infnet.josecsjuniorapi.exceptions.AlteracaoNaoAutorizada;
import br.edu.infnet.josecsjuniorapi.exceptions.ApontamentoProducaoInvalido;
import br.edu.infnet.josecsjuniorapi.exceptions.EncerramentoInvalido;
import br.edu.infnet.josecsjuniorapi.exceptions.IdInvalidoException;
import br.edu.infnet.josecsjuniorapi.exceptions.OrdemProducaoNaoEncontradaException;
import br.edu.infnet.josecsjuniorapi.interfaces.CrudService;
import br.edu.infnet.josecsjuniorapi.model.domain.OrdemProducao;
import br.edu.infnet.josecsjuniorapi.model.domain.StatusOrdem;

@Service
public class OrdemProducaoService implements CrudService<OrdemProducao, Integer> {

	private final Map<Integer, OrdemProducao> mapa = new ConcurrentHashMap<Integer, OrdemProducao>();
	private final AtomicInteger nextId = new AtomicInteger(1);
	
	@Override
	public OrdemProducao incluir(OrdemProducao ordemProducao) {
		 
		if(ordemProducao == null)
			throw new IllegalArgumentException("Ordem de produção não pode ser nula!");
		
		if(ordemProducao.getId() != 0)
			throw new IllegalArgumentException("Id é gerado automaticamente!");
		
		if(ordemProducao.getCodigo().isBlank())
			throw new IllegalArgumentException("Código da ordem deve ser indicado!");
		
		if(ordemProducao.getEstacao() == null)
			throw new IllegalArgumentException("Estação deve ser indicada!");
		
		if(ordemProducao.getEstacao().getCodigo().isBlank())
			throw new IllegalArgumentException("Código da estação deve ser indicado!");
	
		if(ordemProducao.getDataPlanejada() == null)
			throw new IllegalArgumentException("Data planejada deve ser indicada!");
		
		if(ordemProducao.getDataPlanejada().isBefore(LocalDate.now()))
			throw new IllegalArgumentException("Data planejada deve ser maior ou igual a data atual!");
		
		if(ordemProducao.getDataCriacao() != null)
			throw new IllegalArgumentException("Data criação é gerada automaticamente!");
		
		if(ordemProducao.getDataEncerramento() != null)
			throw new IllegalArgumentException("Data encerramento é gerenciado automaticamente!");
		
		if(ordemProducao.getStatus() != null)
			throw new IllegalArgumentException("Status é gerado automaticamente!");

		if(ordemProducao.getProduto() == null)
			throw new IllegalArgumentException("Produto deve ser indicada!");
		
		if(ordemProducao.getProduto().getCodigo().isBlank())
			throw new IllegalArgumentException("Código do produto deve ser indicado!");
		
		if(ordemProducao.getQuantidadePlanejada() <= 0)
			throw new IllegalArgumentException("Quantidade planejada deve ser maior que zero!");
		
		if(ordemProducao.getQuantidadeExecutada() != 0)
			throw new IllegalArgumentException("Quantidade executada é gerenciada automaticamente!");
		
		ordemProducao.setId(nextId.getAndIncrement());
		ordemProducao.setDataCriacao(LocalDateTime.now());
		ordemProducao.setStatus(StatusOrdem.CRIADO);
		ordemProducao.setQuantidadeExecutada(0);
		
		mapa.put(ordemProducao.getId(), ordemProducao);    
		
		return ordemProducao;
	}

	@Override
	public List<OrdemProducao> obterLista() {
		
		return new ArrayList<OrdemProducao>(mapa.values());
	}

	@Override
	public OrdemProducao alterar(Integer id, OrdemProducao ordemProducao) {

		if(ordemProducao == null)
			throw new IllegalArgumentException("Ordem de produção não pode ser nula!");
		
		OrdemProducao ordemProducaoEncontrado = this.obterPorId(ordemProducao.getId());
		
		if(!ordemProducaoEncontrado.getStatus().equals(StatusOrdem.CRIADO))
			throw new AlteracaoNaoAutorizada("Ordens de produção iniciadas, encerradas ou canceladas não podem ser alteradas!");
		
		if(!Objects.equals(ordemProducaoEncontrado.getCodigo(), ordemProducao.getCodigo()))
			throw new AlteracaoNaoAutorizada("Código da ordem de produção não pode ser alterado!");
		
		if(!Objects.equals(ordemProducaoEncontrado.getEstacao().getCodigo(), ordemProducao.getEstacao().getCodigo()))
			throw new AlteracaoNaoAutorizada("Estação da ordem de produção não pode ser alterado!");
		
		//if(!ordemProducaoEncontrado.getDataCriacao().equals(ordemProducao.getDataCriacao()))
		//	throw new AlteracaoNaoAutorizada("Data de criação da ordem de produção não pode ser alterada!");
		
		if(!ordemProducaoEncontrado.getStatus().equals(ordemProducao.getStatus()))
			throw new AlteracaoNaoAutorizada("Status da ordem de produção não pode ser alterado diretamente!");
				
		if(!ordemProducaoEncontrado.getProduto().getCodigo().equals(ordemProducao.getProduto().getCodigo()))
			throw new AlteracaoNaoAutorizada("Produto da ordem de produção não pode ser alterado!");
		
		if(ordemProducaoEncontrado.getQuantidadeExecutada() != ordemProducao.getQuantidadeExecutada())
			throw new AlteracaoNaoAutorizada("Quantidade executada não pode ser alterada!");
		
		if(ordemProducao.getQuantidadePlanejada() <= 0)
			throw new AlteracaoNaoAutorizada("Quantidade planejada deve ser maior que zero!");
		
		ordemProducaoEncontrado.setQuantidadePlanejada(ordemProducao.getQuantidadePlanejada());
		
		if(ordemProducao.getDataPlanejada().isBefore(LocalDate.now()))
			throw new AlteracaoNaoAutorizada("Data planejada deve ser maior ou igual a data atual!");
		
		ordemProducaoEncontrado.setDataPlanejada(ordemProducao.getDataPlanejada());
		
		return ordemProducaoEncontrado;
	}

	@Override
	public void excluir(Integer id) {
		
		OrdemProducao ordemProducaoEncontrado = this.obterPorId(id);
		
		mapa.remove(ordemProducaoEncontrado.getId());		
	}
	
	@Override
	public OrdemProducao obterPorId(Integer id) {
		
		if(id == null || id <= 0)
			throw new IdInvalidoException("Id deve ser indicado e maior que zero!");
		
		OrdemProducao ordemProducao = mapa.get(id);	
		
		if(ordemProducao == null)
			throw new OrdemProducaoNaoEncontradaException("Não foi encontrada ordem de produção com Id " + id + "!");
		
		return ordemProducao;
	}

	public OrdemProducao apontarProducao(Integer id, Double producaoExecutada) {

		OrdemProducao ordemProducao = this.obterPorId(id);
		
		if(producaoExecutada <= 0)
			throw new ApontamentoProducaoInvalido("Produção executada deve ser maior que zero!");
		
		if(ordemProducao.getStatus().equals(StatusOrdem.CANCELADO))
			throw new ApontamentoProducaoInvalido("Ordem de produção cancelada não pode ter produção apontada!");
		
		if(ordemProducao.getStatus().equals(StatusOrdem.ENCERRADO))
			throw new ApontamentoProducaoInvalido("Ordem de produção encerrada não pode ter produção apontada!");
		
		if(ordemProducao.getStatus().equals(StatusOrdem.CRIADO))
			ordemProducao.setStatus(StatusOrdem.INICIADO);
		
		ordemProducao.setQuantidadeExecutada(ordemProducao.getQuantidadeExecutada() + producaoExecutada);
		
		return ordemProducao;
	}

	public OrdemProducao encerrar(Integer id) {

		OrdemProducao ordemProducao = this.obterPorId(id);
		
		if(ordemProducao.getStatus().equals(StatusOrdem.CANCELADO))
			throw new EncerramentoInvalido("Ordem de produção cancelada não pode ser encerrada!");
		
		if(ordemProducao.getStatus().equals(StatusOrdem.ENCERRADO))
			throw new EncerramentoInvalido("Ordem de produção já encerrada!");
		
		if(ordemProducao.getStatus().equals(StatusOrdem.CRIADO) && ordemProducao.getQuantidadeExecutada() == 0)
			throw new EncerramentoInvalido("Ordem de produção não pode ser encerrada sem nenhum apontamento de produção executada!");
		
		ordemProducao.setStatus(StatusOrdem.ENCERRADO);
		
		return ordemProducao;
	}
	
	public OrdemProducao cancelar(Integer id) {

		OrdemProducao ordemProducao = this.obterPorId(id);
		
		if(ordemProducao.getStatus().equals(StatusOrdem.CANCELADO))
			throw new ApontamentoProducaoInvalido("Ordem de produção já cancelada!");
		
		if(ordemProducao.getStatus().equals(StatusOrdem.ENCERRADO))
			throw new ApontamentoProducaoInvalido("Ordem de produção encerrada não pode ser cancelada!");
		
		if(ordemProducao.getStatus().equals(StatusOrdem.INICIADO))
			throw new ApontamentoProducaoInvalido("Ordem de produção já iniciada!");
		
		ordemProducao.setStatus(StatusOrdem.CANCELADO);
		
		return ordemProducao;
	}
}
