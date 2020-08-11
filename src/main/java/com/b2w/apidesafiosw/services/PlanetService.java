package com.b2w.apidesafiosw.services;

import java.util.List;

import com.b2w.apidesafiosw.models.Planet;
import com.b2w.apidesafiosw.responses.PlanetResponse;

public interface PlanetService {
       
	List<PlanetResponse> listarTodos();
	
	List<PlanetResponse> listarPorNome( String nome);
	
	PlanetResponse listarPorId( String id ) ;
	
	PlanetResponse cadastrar( Planet planeta ) ;

	PlanetResponse atualizar( Planet planeta ) ;
	
	void remover( String id	 ) ;
}
