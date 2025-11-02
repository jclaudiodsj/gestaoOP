package br.edu.infnet.josecsjuniorapi.exceptions.handler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.edu.infnet.josecsjuniorapi.exceptions.AlteracaoNaoAutorizadaException;
import br.edu.infnet.josecsjuniorapi.exceptions.ApontamentoProducaoInvalidoException;
import br.edu.infnet.josecsjuniorapi.exceptions.ApontamentoProducaoNaoAutorizado;
import br.edu.infnet.josecsjuniorapi.exceptions.CancelamentoInvalidoException;
import br.edu.infnet.josecsjuniorapi.exceptions.EncerramentoInvalidoException;
import br.edu.infnet.josecsjuniorapi.exceptions.EstacaoNaoEncontradaException;
import br.edu.infnet.josecsjuniorapi.exceptions.IdInvalidoException;
import br.edu.infnet.josecsjuniorapi.exceptions.OrdemProducaoNaoEncontradaException;
import br.edu.infnet.josecsjuniorapi.exceptions.ProdutoNaoEncontradoException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e)
	{
	    Map<String, String> mapa = new HashMap<String, String>();

	    e.getBindingResult().getAllErrors().forEach((erro) -> { 
	    	
	    	String nomeCampo = ((FieldError) erro).getField();	    	
	    	String mensagemErro = erro.getDefaultMessage();
	    	
	    	mapa.put(nomeCampo, mensagemErro);
		});
	    
	    return new ResponseEntity<Map<String,String>>(mapa, HttpStatus.BAD_REQUEST);
	}
	
	
	//--------------
	@ExceptionHandler(AlteracaoNaoAutorizadaException.class)
	public ResponseEntity<Map<String, String>> handleAlteracaoNaoAutorizadaException(AlteracaoNaoAutorizadaException e)
	{
		Map<String, String> mapa = new HashMap<String, String>();
		
		mapa.put("timestamp", LocalDateTime.now().toString());
		mapa.put("status", HttpStatus.CONFLICT.toString());
		mapa.put("error", e.getMessage());
		mapa.put("detail", "Um dos argumentos indicados não pode ser alterado!");
		
		return new ResponseEntity<Map<String,String>>(mapa, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(ApontamentoProducaoInvalidoException.class)
	public ResponseEntity<Map<String, String>> handleApontamentoProducaoInvalidoException(ApontamentoProducaoInvalidoException e)
	{
		Map<String, String> mapa = new HashMap<String, String>();
		
		mapa.put("timestamp", LocalDateTime.now().toString());
		mapa.put("status", HttpStatus.UNPROCESSABLE_ENTITY.toString());
		mapa.put("error", e.getMessage());
		mapa.put("detail", "Apontamento de produção não pode ser executado!");
		
		return new ResponseEntity<Map<String,String>>(mapa, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler(ApontamentoProducaoNaoAutorizado.class)
	public ResponseEntity<Map<String, String>> handleApontamentoProducaoNaoAutorizado(ApontamentoProducaoNaoAutorizado e)
	{
		Map<String, String> mapa = new HashMap<String, String>();
		
		mapa.put("timestamp", LocalDateTime.now().toString());
		mapa.put("status", HttpStatus.UNPROCESSABLE_ENTITY.toString());
		mapa.put("error", e.getMessage());
		mapa.put("detail", "Apontamento de produção não pode ser executado!");
		
		return new ResponseEntity<Map<String,String>>(mapa, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler(CancelamentoInvalidoException.class)
	public ResponseEntity<Map<String, String>> handleCancelamentoInvalidoException(CancelamentoInvalidoException e)
	{
		Map<String, String> mapa = new HashMap<String, String>();
		
		mapa.put("timestamp", LocalDateTime.now().toString());
		mapa.put("status", HttpStatus.CONFLICT.toString());
		mapa.put("error", e.getMessage());
		mapa.put("detail", "Ordem não pode ser cancelada!");
		
		return new ResponseEntity<Map<String,String>>(mapa, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(EncerramentoInvalidoException.class)
	public ResponseEntity<Map<String, String>> handleEncerramentoInvalidoException(EncerramentoInvalidoException e)
	{
		Map<String, String> mapa = new HashMap<String, String>();
		
		mapa.put("timestamp", LocalDateTime.now().toString());
		mapa.put("status", HttpStatus.CONFLICT.toString());
		mapa.put("error", e.getMessage());
		mapa.put("detail", "Ordem não pode ser encerrada!");
		
		return new ResponseEntity<Map<String,String>>(mapa, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(EstacaoNaoEncontradaException.class)
	public ResponseEntity<Map<String, String>> handleEstacaoNaoEncontradaException(EstacaoNaoEncontradaException e)
	{
		Map<String, String> mapa = new HashMap<String, String>();
		
		mapa.put("timestamp", LocalDateTime.now().toString());
		mapa.put("status", HttpStatus.NOT_FOUND.toString());
		mapa.put("error", e.getMessage());
		mapa.put("detail", "Estação não encontrada!");
		
		return new ResponseEntity<Map<String,String>>(mapa, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(IdInvalidoException.class)
	public ResponseEntity<Map<String, String>> handleIdInvalidoException(IdInvalidoException e)
	{
		Map<String, String> mapa = new HashMap<String, String>();
		
		mapa.put("timestamp", LocalDateTime.now().toString());
		mapa.put("status", HttpStatus.BAD_REQUEST.toString());
		mapa.put("error", e.getMessage());
		mapa.put("detail", "Id informado não aceito!");
		
		return new ResponseEntity<Map<String,String>>(mapa, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(OrdemProducaoNaoEncontradaException.class)
	public ResponseEntity<Map<String, String>> handleOrdemProducaoNaoEncontradaException(OrdemProducaoNaoEncontradaException e)
	{
		Map<String, String> mapa = new HashMap<String, String>();
		
		mapa.put("timestamp", LocalDateTime.now().toString());
		mapa.put("status", HttpStatus.NOT_FOUND.toString());
		mapa.put("error", e.getMessage());
		mapa.put("detail", "Ordem de produção não encontrada!");
		
		return new ResponseEntity<Map<String,String>>(mapa, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ProdutoNaoEncontradoException.class)
	public ResponseEntity<Map<String, String>> handleProdutoEncontradoException(ProdutoNaoEncontradoException e)
	{
		Map<String, String> mapa = new HashMap<String, String>();
		
		mapa.put("timestamp", LocalDateTime.now().toString());
		mapa.put("status", HttpStatus.NOT_FOUND.toString());
		mapa.put("error", e.getMessage());
		mapa.put("detail", "Produto não encontrado!");
		
		return new ResponseEntity<Map<String,String>>(mapa, HttpStatus.NOT_FOUND);
	}
}
