package br.edu.infnet.josecsjuniorapi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import br.edu.infnet.josecsjuniorapi.model.domain.OrdemProducao;
import br.edu.infnet.josecsjuniorapi.model.domain.service.OrdemProducaoService;

@Component
public class OrdemProducaoLoader implements ApplicationRunner {

	private final OrdemProducaoService ordemProducaoService;
	
	public OrdemProducaoLoader(OrdemProducaoService ordemProducaoService)
	{
		this.ordemProducaoService = ordemProducaoService;
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		System.out.println("Carregamento automático das ordens de produção...");
		
		Collection<OrdemProducao> ordens = lerArquivoCSV("OrdemProducao-listagem.csv");
		
		ordens.forEach(System.out::println);
	}
	
	public Collection<OrdemProducao> lerArquivoCSV(String caminhoArquivo) {
        
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            
            boolean primeiraLinha = true; //Ignorar o cabeçalho
            String linha;
            String[] campos = null;

            while ((linha = br.readLine()) != null) {
            	
                if (primeiraLinha) {
                    primeiraLinha = false;                                        
                    continue;
                }

                campos = linha.split(";");

                if (campos.length < 6) {
                    System.out.println("Linha ignorada (campos insuficientes): " + linha);
                    continue;
                }

                OrdemProducao ordem = new OrdemProducao();
                
                ordem.setCodigo(campos[0]);
                ordem.setProduto(campos[1]);
                ordem.setQuantidadePlanejada(Double.valueOf(campos[2]));
                ordem.setQuantidadeExecutada(Double.valueOf(campos[3]));
                ordem.setData(campos[4]);
                ordem.setAtivo(Boolean.valueOf(campos[5]));
                
                ordemProducaoService.incluir(ordem);
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
