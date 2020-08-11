package com.b2w.apidesafiosw.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.b2w.apidesafiosw.client.APIStarWarsService;
import com.b2w.apidesafiosw.models.Planet;
import com.b2w.apidesafiosw.repositories.PlanetRepository;
import com.b2w.apidesafiosw.responses.PlanetResponse;
import com.b2w.apidesafiosw.services.PlanetService;

@Service	
public class PlanetServiceImpl implements PlanetService {

	@Autowired
	private PlanetRepository planetRepository ;
	private APIStarWarsService apisw ; 
	
	@Autowired
	public PlanetServiceImpl(PlanetRepository repository, APIStarWarsService apisw) {
		super();
		this.planetRepository = repository;
		this.apisw = apisw;
	}
	
	@Override
	public List<PlanetResponse> listarTodos() {
		List<Planet> planets = planetRepository.findAll();
		return retornaListPlanetResponse(planets) ;
	}
	
	@Override
	public List<PlanetResponse> listarPorNome( String nome){
		List<Planet> planets = planetRepository.findByNome(nome);
		return retornaListPlanetResponse(planets);
	}
	
	@Override
	public PlanetResponse listarPorId(String id) {
		Planet planet = planetRepository.findOneById(id);
		Integer aparicoes = AparicoesSW(planet.getNome()); 
		return geraPlanetResponse(planet, aparicoes) ;
	}
	
	@Override
	public PlanetResponse cadastrar(Planet planeta) {
		Planet planet = planetRepository.save(planeta);
		Integer aparicoes = AparicoesSW(planet.getNome()); 
		return geraPlanetResponse(planet, aparicoes);
	}

	@Override
	public PlanetResponse atualizar(Planet planeta) {
		Planet planet = planetRepository.save(planeta);
		Integer aparicoes = AparicoesSW(planet.getNome()); 
		return geraPlanetResponse(planet, aparicoes);
	}

	@Override
	public void remover(String id) {
		planetRepository.deleteById(id);

	}
	
	private PlanetResponse geraPlanetResponse( Planet planet, Integer numAparicoes ) {
		return new PlanetResponse( planet, numAparicoes );
	}
	
	private List<PlanetResponse> retornaListPlanetResponse( List<Planet> planets){
		List<PlanetResponse> planetsResponse = new ArrayList<PlanetResponse>();
		for (Planet planet:planets) {
			Integer numeroAparicoes = AparicoesSW(planet.getNome());
			planetsResponse.add(geraPlanetResponse(planet, numeroAparicoes));
		}
		return planetsResponse ;
	}
	
	private Integer AparicoesSW(String nome) {
		return apisw.retornaNumeroAparicoes(nome);
	}

}
