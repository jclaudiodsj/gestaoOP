package br.edu.infnet.josecsjuniorapi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import br.edu.infnet.josecsjuniorapi.model.domain.OrdemProducao;
import br.edu.infnet.josecsjuniorapi.model.domain.OrdemProducaoApontamento;
import br.edu.infnet.josecsjuniorapi.model.service.OrdemProducaoService;

@Order(4)
@Component
public class OrdemProducaoApontamentoLoader implements ApplicationRunner {

	private final OrdemProducaoService ordemProducaoService;
		
	public OrdemProducaoApontamentoLoader(OrdemProducaoService ordemProducaoService)
	{
		this.ordemProducaoService = ordemProducaoService;
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		System.out.println("Carregamento automático dos apontamentos de ordens de produção...");
		
		Collection<OrdemProducao> ordens = lerArquivoCSV("OrdemProducaoApontamento-listagem.csv");
		
		ordens.forEach(System.out::println);
	}
	
	public Collection<OrdemProducao> lerArquivoCSV(String caminhoArquivo) {
        
		int n = 0;
		
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) 
        {                 	    	
            boolean primeiraLinha = true; //Ignorar o cabeçalho
            String linha;
            String[] campos = null;
            
            while ((linha = br.readLine()) != null) {
            	
            	n++;    
            	
                if (primeiraLinha) {
                    primeiraLinha = false;                                        
                    continue;
                }

                campos = linha.split(";");

                if (campos.length < 4) {
                    System.out.println("Linha ignorada (campos insuficientes): " + linha);
                    continue;
                }

                String ordemCodigo = campos[0];
                Double quantidade = Double.valueOf(campos[1]);
                Double temperatura = Double.valueOf(campos[2]);
                LocalDateTime data = LocalDateTime.parse(campos[3], DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                
                OrdemProducao ordemProducao = ordemProducaoService.obterPorCodigo(ordemCodigo);                
                
                if(ordemProducao == null)
                {
                	String message = "Linha ignorada ";
                	
                	if(ordemProducao == null)
                		message += "(Ordem de produção não encontrada)";
                	
                	System.out.println(message + ": " + linha);
                }
                else
                {
                	OrdemProducaoApontamento apontamento = new OrdemProducaoApontamento();
                	apontamento.setQuantidade(quantidade);
            		apontamento.setTemperatura(temperatura);
            		apontamento.setData(data);
            		apontamento.setOrdemProducao(ordemProducao);
            		
            		ordemProducaoService.addApontamento(ordemProducao, apontamento);
                }
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Erro ao converter valores numéricos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao ler/interpretar o arquivo na linha (" + n + "): " + e.getMessage());
        }
                
        Collection<OrdemProducao> ordens = ordemProducaoService.obterLista();
        
        return ordens;
    }
}
