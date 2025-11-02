package br.edu.infnet.josecsjuniorapi.model.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import br.edu.infnet.josecsjuniorapi.exceptions.AlteracaoNaoAutorizadaException;
import br.edu.infnet.josecsjuniorapi.exceptions.EstacaoNaoEncontradaException;
import br.edu.infnet.josecsjuniorapi.exceptions.IdInvalidoException;
import br.edu.infnet.josecsjuniorapi.model.domain.Estacao;
import br.edu.infnet.josecsjuniorapi.model.domain.dto.EstacaoDTO;
import br.edu.infnet.josecsjuniorapi.model.repository.EstacaoRepository;

@Service
public class EstacaoService implements CrudService<Estacao, Integer> {

	private final EstacaoRepository estacaoRepository;
	
	public EstacaoService(EstacaoRepository estacaoRepository)
	{
		this.estacaoRepository = estacaoRepository;
	}
	
	@Override
	public Estacao incluir(Estacao estacao) {
		 
		if(estacao == null)
			throw new IllegalArgumentException("Estação não pode ser nula!");
		
		if(estacao.getId() != null && estacao.getId() != 0)
			throw new IllegalArgumentException("Id é gerado automaticamente!");
		
		if(estacao.getCodigo().isBlank())
			throw new IllegalArgumentException("Código da estação deve ser indicado!");
		
		return estacaoRepository.save(estacao);
	}

	@Override
	public List<Estacao> obterLista() {
		
		return estacaoRepository.findAll();
	}

	@Override
	public Estacao alterar(Integer id, Estacao estacao) {

		if(estacao == null)
			throw new IllegalArgumentException("Estação não pode ser nula!");
		
		Estacao estacaoEncontrada = this.obterPorId(id);
		
		if(!Objects.equals(estacaoEncontrada.getCodigo(), estacao.getCodigo()))
			throw new AlteracaoNaoAutorizadaException("Código da estação de produção não pode ser alterado!");
		
		estacaoEncontrada.setDescricao(estacao.getDescricao());
		estacaoEncontrada.setLatitude(estacao.getLatitude());
		estacaoEncontrada.setLongitude(estacao.getLongitude());
		
		return estacaoRepository.save(estacaoEncontrada);
	}

	@Override
	public void excluir(Integer id) {
		
		Estacao estacaoEncontrada = this.obterPorId(id);
		
		estacaoRepository.delete(estacaoEncontrada);
	}
	
	@Override
	public Estacao obterPorId(Integer id) {
		
		if(id == null || id <= 0)
			throw new IdInvalidoException("Id deve ser indicado e maior que zero!");
		
		return estacaoRepository.findById(id).orElseThrow(() -> new EstacaoNaoEncontradaException("Não foi encontrada estação com Id " + id + "!"));
	}
	
	public Estacao obterPorCodigo(String codigo) {
		
		if(codigo == null || codigo.trim().isEmpty())
			throw new IdInvalidoException("Código deve ser indicado!");
		
		return estacaoRepository.findByCodigo(codigo).orElseThrow(() -> new EstacaoNaoEncontradaException("Não foi encontrada estação com código (" + codigo + ")!"));
	}
	
	public Estacao ativar(Integer id) {

		Estacao estacaoEncontrada = this.obterPorId(id);
		
		estacaoEncontrada.setAtivo(true);
		
		return estacaoRepository.save(estacaoEncontrada);
	}
	
	public Estacao desativar(Integer id) {

		Estacao estacaoEncontrada = this.obterPorId(id);
		
		estacaoEncontrada.setAtivo(false);
		
		return estacaoRepository.save(estacaoEncontrada);
	}
	
	public EstacaoDTO toDTO(Estacao estacao) 
	{
	    return new EstacaoDTO(
	    		estacao.getId(),
	    		estacao.getCodigo(),
	    		estacao.getDescricao(),
	    		estacao.getAtivo(),
	    		estacao.getLatitude(),
	    		estacao.getLongitude()
	    );
	}
}
