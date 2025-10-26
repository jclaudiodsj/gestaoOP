package br.edu.infnet.josecsjuniorapi.model.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import br.edu.infnet.josecsjuniorapi.interfaces.CrudService;
import br.edu.infnet.josecsjuniorapi.model.domain.OrdemProducao;

@Service
public class OrdemProducaoService implements CrudService<OrdemProducao, Integer> {

	private final Map<Integer, OrdemProducao> mapa = new ConcurrentHashMap<Integer, OrdemProducao>();
	private final AtomicInteger nextId = new AtomicInteger(1);
	
	@Override
	public OrdemProducao incluir(OrdemProducao ordemProducao) {
		 
		ordemProducao.setId(nextId.getAndIncrement());
		
		mapa.put(ordemProducao.getId(), ordemProducao);    
		
		return ordemProducao;
	}

	@Override
	public List<OrdemProducao> obterLista() {
		
		return new ArrayList<OrdemProducao>(mapa.values());
	}

	@Override
	public OrdemProducao alterar(Integer id, OrdemProducao ordemProducao) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void exluir(Integer id) {
		mapa.remove(id);		
	}
}
