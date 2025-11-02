package br.edu.infnet.josecsjuniorapi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import br.edu.infnet.josecsjuniorapi.model.domain.Estacao;
import br.edu.infnet.josecsjuniorapi.model.service.EstacaoService;

@Order(1)
@Component
public class EstacaoLoader implements ApplicationRunner {

	private final EstacaoService estacaoService;
		
	public EstacaoLoader(EstacaoService estacaoService)
	{
		this.estacaoService = estacaoService;
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		System.out.println("Carregamento automático das estações...");
		
		Collection<Estacao> estacoes = lerArquivoCSV("Estacao-listagem.csv");
		
		estacoes.forEach(System.out::println);
	}
	
	public Collection<Estacao> lerArquivoCSV(String caminhoArquivo) {
        
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

                if (campos.length < 2) {
                    System.out.println("Estacao ignorada (campos insuficientes): " + linha);
                    continue;
                }

                Estacao estacao = new Estacao();
                
                estacao.setCodigo(campos[0]);
                estacao.setDescricao(campos[1]);
                estacao.setAtivo(Boolean.parseBoolean(campos[2]));
                estacao.setLatitude(Double.parseDouble(campos[3]));
                estacao.setLongitude(Double.parseDouble(campos[4]));
                
                estacaoService.incluir(estacao);
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Erro ao converter valores numéricos: " + e.getMessage());
        }
        
        Collection<Estacao> estacoes = estacaoService.obterLista();
        
        return estacoes;
    }
}
