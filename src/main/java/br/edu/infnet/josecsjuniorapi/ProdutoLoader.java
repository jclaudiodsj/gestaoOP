package br.edu.infnet.josecsjuniorapi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import br.edu.infnet.josecsjuniorapi.model.domain.Produto;
import br.edu.infnet.josecsjuniorapi.model.service.ProdutoService;

@Order(2)
@Component
public class ProdutoLoader implements ApplicationRunner {

	private final ProdutoService produtoService;
		
	public ProdutoLoader(ProdutoService produtoService)
	{
		this.produtoService = produtoService;
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		System.out.println("Carregamento automático dos produtos...");
		
		Collection<Produto> produtos = lerArquivoCSV("Produto-listagem.csv");
		
		produtos.forEach(System.out::println);
	}
	
	public Collection<Produto> lerArquivoCSV(String caminhoArquivo) {
        
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
                    System.out.println("Produto ignorado (campos insuficientes): " + linha);
                    continue;
                }

                Produto produto = new Produto();
                
                produto.setCodigo(campos[0]);
                produto.setDescricao(campos[1]);
                produto.setAtivo(Boolean.parseBoolean(campos[2]));
                
                produtoService.incluir(produto);
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Erro ao converter valores numéricos: " + e.getMessage());
        }
        
        Collection<Produto> produtos = produtoService.obterLista();
        
        return produtos;
    }
}
