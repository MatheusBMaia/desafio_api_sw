package com.b2w.apidesafiosw.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.b2w.apidesafiosw.models.Planet;
import com.b2w.apidesafiosw.responses.PlanetResponse;
import com.b2w.apidesafiosw.services.PlanetService;
import com.b2w.apidesafiosw.models.ResponseModel;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin(origins  = "http://localhost:8080")
@RequestMapping( path = "/planets" )
public class PlanetController {
	
	@Autowired
	private PlanetService planetService;
	
	
	@ApiOperation(
			value="Listar todos os planetas", 
			response=PlanetResponse.class, 
			notes="Realiza a busca de todos os planetas armazenados na API.")
	@ApiResponses(value= {
			@ApiResponse(
					code=200, 
					message="Retorna uma lista de planetas.",
					response=PlanetResponse.class
					)
	})
	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public ResponseEntity< List<PlanetResponse>> listarTodos() {
		return ResponseEntity.ok( planetService.listarTodos() ) ;
	}
	
	
	@ApiOperation(
			value="Retorna planeta pelo id", 
			response=PlanetResponse.class, 
			notes="Busca e retorna os dados do planeta que contenha o id informado na path.")
	@ApiResponses(value= {
			@ApiResponse(
					code=200, 
					message="Retorna os dados de um planeta.",
					response=PlanetResponse.class
					)
	})
	@ResponseStatus(HttpStatus.OK)
	@GetMapping( path = "/{id}")
	public ResponseEntity< PlanetResponse> listarPorId(@PathVariable( name = "id" ) String id) {		
		return ResponseEntity.ok( planetService.listarPorId(id) );
	}
	
	
	@ApiOperation(
			value="Retorna planetas pelo nome", 
			response=PlanetResponse.class, 
			notes="Realiza a busca todos os planetas que contenham o nome informado na path.")
	@ApiResponses(value= {
			@ApiResponse(
					code=200, 
					message="Retorna uma lista de planetas.",
					response=PlanetResponse.class
					)
	})
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("nome/{nome}") 
	public @ResponseBody List<PlanetResponse> listarPorNome(@PathVariable("nome") String nome) {
		List<PlanetResponse> planetas = planetService.listarPorNome(nome);
		return planetas;
	}
	
	@Autowired
	PlanetService planetaService;

	@ApiOperation(
			value="Cadastrar um novo planeta", 
			response=ResponseModel.class, 
			notes="Realiza o cadastro de um novo planeta na API.")
	@ApiResponses(value= {
			@ApiResponse(
					code=200, 
					message="Realiza o retorno um Response com uma mensagem de sucesso.",
					response=ResponseModel.class
					)
	})
	@ResponseStatus(HttpStatus.OK)
	@PostMapping
	public ResponseEntity<PlanetResponse> cadastrar(@Valid @RequestBody Planet planeta, BindingResult result){
		if (result.hasErrors()) {
			List<String> erros = new ArrayList<String>();
			result.getAllErrors().forEach(erro -> erros.add(erro.getDefaultMessage()));
			return ResponseEntity.badRequest().body( new PlanetResponse(erros)) ;
		} 

		return ResponseEntity.ok( planetService.cadastrar(planeta) );
		
	}
	
	
	@ApiOperation(
			value="Atualizar os dados de um planeta", 
			response=ResponseModel.class, 
			notes="Realiza a atualização dos dados de um planeta já inserido na API.")
	@ApiResponses(value= {
			@ApiResponse(
					code=200, 
					message="Realiza o retorno um Response com uma mensagem de sucesso.",
					response=ResponseModel.class
					)
	})
	@ResponseStatus(HttpStatus.OK)
	@PutMapping( path = "/{id}")
	public ResponseEntity<PlanetResponse> atualizar(@PathVariable( name = "id" ) String id, @Valid @RequestBody Planet planeta, BindingResult result){
		if (result.hasErrors()) {
			List<String> erros = new ArrayList<String>();
			result.getAllErrors().forEach(erro -> erros.add(erro.getDefaultMessage()));
			return ResponseEntity.badRequest().body( new PlanetResponse(erros)) ;
		}
		
		planeta.setId(id);
		return ResponseEntity.ok( planetService.atualizar(planeta)  );
		
	}
	
	
	@ApiOperation(
			value="Remover planeta pelo id", 
			response=ResponseModel.class, 
			notes="Realiza a exclusão de um registro com os dados de planeta.")
	@ApiResponses(value= {
			@ApiResponse(
					code=200, 
					message="Retorna um Response com uma mensagem de sucesso.",
					response=ResponseModel.class
					)
	})
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping( path = "/{id}" )
	public ResponseEntity<PlanetResponse> remover( @PathVariable( name = "id" ) String id ){
		planetService.remover(id);
		return ResponseEntity.ok( new PlanetResponse());
	}
}
