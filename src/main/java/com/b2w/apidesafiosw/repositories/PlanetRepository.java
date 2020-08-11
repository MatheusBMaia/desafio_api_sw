package com.b2w.apidesafiosw.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.b2w.apidesafiosw.models.Planet;

public interface PlanetRepository extends MongoRepository<Planet, String> {

	public Planet findOneById(String id);
	public List<Planet> findByNome(String nome);
}
