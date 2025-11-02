package br.edu.infnet.josecsjuniorapi.model.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.infnet.josecsjuniorapi.model.domain.OrdemProducao;
import br.edu.infnet.josecsjuniorapi.model.domain.StatusOrdem;

@Repository
public interface OrdemProducaoRepository extends JpaRepository<OrdemProducao, Integer> {

	List<OrdemProducao> findByStatus(StatusOrdem status);
    List<OrdemProducao> findByDataPlanejada(LocalDate dataPlanejada);
    List<OrdemProducao> findByDataInicioBetween(LocalDateTime inicio, LocalDateTime fim);
    List<OrdemProducao> findByDataEncerramentoBetween(LocalDateTime inicio, LocalDateTime fim);
    
    @Query("""
           select op
             from OrdemProducao op
            where op.quantidadePlanejada > 0
              and (op.quantidadeExecutada * 100.0 / op.quantidadePlanejada) >= :percent
           """)
    List<OrdemProducao> findComPercentualProducaoMinimo(@Param("percent") double percent);
    
    List<OrdemProducao> findByProdutoCodigo(String codigoProduto);
    List<OrdemProducao> findByEstacaoCodigo(String codigoEstacao);
    List<OrdemProducao> findTop5ByEstacaoCodigoAndStatusInOrderByDataPlanejadaAsc(String codigoEstacao, Collection<StatusOrdem> statuses);
    List<OrdemProducao> findTop5ByStatusOrderByDataEncerramentoDesc(StatusOrdem status);
}
