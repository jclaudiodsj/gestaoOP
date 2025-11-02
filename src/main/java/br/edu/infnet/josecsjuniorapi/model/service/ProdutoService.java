package br.edu.infnet.josecsjuniorapi.model.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import br.edu.infnet.josecsjuniorapi.exceptions.AlteracaoNaoAutorizadaException;
import br.edu.infnet.josecsjuniorapi.exceptions.IdInvalidoException;
import br.edu.infnet.josecsjuniorapi.exceptions.ProdutoNaoEncontradoException;
import br.edu.infnet.josecsjuniorapi.model.domain.Produto;
import br.edu.infnet.josecsjuniorapi.model.domain.dto.ProdutoDTO;
import br.edu.infnet.josecsjuniorapi.model.repository.ProdutoRepository;

@Service
public class ProdutoService implements CrudService<Produto, Integer> {

	private final ProdutoRepository produtoRepository;
	
	public ProdutoService(ProdutoRepository produtoRepository)
	{
		this.produtoRepository = produtoRepository;
	}
	
	@Override
	public Produto incluir(Produto produto) {
		 
		if(produto == null)
			throw new IllegalArgumentException("Produto não pode ser nulo!");
		
		if(produto.getId() != null && produto.getId() != 0)
			throw new IllegalArgumentException("Id é gerado automaticamente!");
		
		if(produto.getCodigo().isBlank())
			throw new IllegalArgumentException("Código do produto deve ser indicado!");
		
		return produtoRepository.save(produto);
	}

	@Override
	public List<Produto> obterLista() {
		
		return produtoRepository.findAll();
	}

	@Override
	public Produto alterar(Integer id, Produto produto) {

		if(produto == null)
			throw new IllegalArgumentException("Produto não pode ser nulo!");
		
		Produto produtoEncontrado = this.obterPorId(id);
		
		if(!Objects.equals(produtoEncontrado.getCodigo(), produto.getCodigo()))
			throw new AlteracaoNaoAutorizadaException("Código do produto não pode ser alterado!");
		
		produtoEncontrado.setDescricao(produto.getDescricao());
		
		return produtoRepository.save(produtoEncontrado);
	}

	@Override
	public void excluir(Integer id) {
		
		Produto produtoEncontrado = this.obterPorId(id);
		
		produtoRepository.delete(produtoEncontrado);
	}
	
	@Override
	public Produto obterPorId(Integer id) {
		
		if(id == null || id <= 0)
			throw new IdInvalidoException("Id deve ser indicado e maior que zero!");
		
		return produtoRepository.findById(id).orElseThrow(() -> new ProdutoNaoEncontradoException("Não foi encontrado produto com Id " + id + "!"));
	}
	
	public Produto obterPorCodigo(String codigo) {
		
		if(codigo == null || codigo.trim().isEmpty())
			throw new IdInvalidoException("Código deve ser indicado!");
		
		return produtoRepository.findByCodigo(codigo).orElseThrow(() -> new ProdutoNaoEncontradoException("Não foi encontrado produto com código (" + codigo + ")!"));
	}
	
	public Produto ativar(Integer id) {

		Produto estacaoEncontrada = this.obterPorId(id);
		
		estacaoEncontrada.setAtivo(true);
		
		return produtoRepository.save(estacaoEncontrada);
	}
	
	public Produto desativar(Integer id) {

		Produto produtoEncontrado = this.obterPorId(id);
		
		produtoEncontrado.setAtivo(false);
		
		return produtoRepository.save(produtoEncontrado);
	}
	
	public ProdutoDTO toDTO(Produto produto) 
	{
	    return new ProdutoDTO(
	    		produto.getId(),
	    		produto.getCodigo(),
	    		produto.getDescricao(),
	    		produto.getAtivo()
	    );
	}
}
