package br.edu.infnet.josecsjuniorapi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.infnet.josecsjuniorapi.model.domain.OrdemProducao;

@Repository
public interface OrdemProducaoRepository extends JpaRepository<OrdemProducao, Integer> {

}
