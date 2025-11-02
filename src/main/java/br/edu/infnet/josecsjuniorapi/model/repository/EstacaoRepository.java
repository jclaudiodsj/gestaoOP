package br.edu.infnet.josecsjuniorapi.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.infnet.josecsjuniorapi.model.domain.Estacao;

@Repository
public interface EstacaoRepository extends JpaRepository<Estacao, Integer> {

	Optional<Estacao> findByCodigo(String codigo);	
}
