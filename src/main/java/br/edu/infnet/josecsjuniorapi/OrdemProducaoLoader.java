package br.edu.infnet.josecsjuniorapi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import br.edu.infnet.josecsjuniorapi.model.domain.Estacao;
import br.edu.infnet.josecsjuniorapi.model.domain.OrdemProducao;
import br.edu.infnet.josecsjuniorapi.model.domain.Produto;
import br.edu.infnet.josecsjuniorapi.model.service.EstacaoService;
import br.edu.infnet.josecsjuniorapi.model.service.OrdemProducaoService;
import br.edu.infnet.josecsjuniorapi.model.service.ProdutoService;

@Order(3)
@Component
public class OrdemProducaoLoader implements ApplicationRunner {

	private final OrdemProducaoService ordemProducaoService;
	private final EstacaoService estacaoService;
	private final ProdutoService produtoService;
		
	public OrdemProducaoLoader(OrdemProducaoService ordemProducaoService, EstacaoService estacaoService, ProdutoService produtoService)
	{
		this.ordemProducaoService = ordemProducaoService;
		this.estacaoService = estacaoService;
		this.produtoService = produtoService;
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		System.out.println("Carregamento automático das ordens de produção...");
		
		Collection<OrdemProducao> ordens = lerArquivoCSV("OrdemProducao-listagem.csv");
		
		ordens.forEach(System.out::println);
	}
	
	public Collection<OrdemProducao> lerArquivoCSV(String caminhoArquivo) {
        
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) 
        {
            
            boolean primeiraLinha = true; //Ignorar o cabeçalho
            String linha;
            String[] campos = null;
            
            while ((linha = br.readLine()) != null) {
            	
                if (primeiraLinha) {
                    primeiraLinha = false;                                        
                    continue;
                }

                campos = linha.split(";");

                if (campos.length < 5) {
                    System.out.println("Linha ignorada (campos insuficientes): " + linha);
                    continue;
                }

                OrdemProducao ordem = new OrdemProducao();
                
                ordem.setCodigo(campos[0]);                
                String codigoEstacao = campos[1];
                ordem.setDataPlanejada(LocalDate.parse(campos[2], DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                String codigoProduto = campos[3];
                ordem.setQuantidadePlanejada(Double.valueOf(campos[4]));
                                
                Estacao estacao = estacaoService.obterPorCodigo(codigoEstacao);
                Produto produto = produtoService.obterPorCodigo(codigoProduto);
                
                if(estacao == null || produto == null)
                {
                	String message = "Linha ignorada ";
                	
                	if(estacao == null)
                		message += "(Estação não encontrada)";
                	
                	if(estacao == null)
                		message += "(Produto não encontrado)";
                	
                	System.out.println(message + ": " + linha);
                }
                else
                {
                	ordem.setEstacao(estacao);
                    ordem.setProduto(produto);
                    
                    ordemProducaoService.incluir(ordem);
                }
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Erro ao converter valores numéricos: " + e.getMessage());
        }
        
        Collection<OrdemProducao> ordens = ordemProducaoService.obterLista();
        
        return ordens;
    }
}
