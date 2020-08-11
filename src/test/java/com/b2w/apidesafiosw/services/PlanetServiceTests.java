package com.b2w.apidesafiosw.services;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.b2w.apidesafiosw.client.APIStarWarsService;
import com.b2w.apidesafiosw.models.Planet;
import com.b2w.apidesafiosw.responses.PlanetResponse;
import com.b2w.apidesafiosw.repositories.PlanetRepository;
import com.b2w.apidesafiosw.services.PlanetService;
import com.b2w.apidesafiosw.services.impl.PlanetServiceImpl;

public class PlanetServiceTests {

	@Mock
	private APIStarWarsService apisw;
	@Mock
	private PlanetRepository repository;
	
	private PlanetService service;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		service = new PlanetServiceImpl(repository,apisw);
	}
	
	@Test
	public void test_adicionar_planeta_corretamente() {
		Planet planeta = new Planet("a","b","c");
		planeta.setId("AAA123");
		when(repository.save(planeta)).thenReturn(planeta);
		
		PlanetResponse response = service.cadastrar(planeta);
		
		Assert.assertEquals(planeta.getId(), response.getId());
	}
	
	@Test
	public void test_adicionar_planeta_incorretamente() {
		String id;
		
		Planet planeta = new Planet();
		planeta.setId("AAA123");
		planeta.setNome("aaaa");
		when(repository.save(planeta)).thenReturn(new Planet());
	
		PlanetResponse response = service.cadastrar(planeta);
		try {
			id = response.getId();
		} catch (Exception e) {
			id = null;
		}		
		Assert.assertNotEquals(planeta.getId(), id);
	}
	
	@Test
	public void test_listar_planetas() {
		List<Planet> planetas = new ArrayList<Planet>();
		planetas.add(new Planet("aaa","bbb","ccc"));
		planetas.add(new Planet("bbb","ccc","ddd"));
		
		when(repository.findAll()).thenReturn(planetas);
		when(apisw.retornaNumeroAparicoes("aaa")).thenReturn(5);
		when(apisw.retornaNumeroAparicoes("bbb")).thenReturn(3);
		
		List<PlanetResponse> responses = service.listarTodos();
		
		int planeta1 = responses.get(0).getNumAparicoes();
		int planeta2 = responses.get(1).getNumAparicoes();
		
		Assert.assertNotNull(responses);
		Assert.assertEquals(5,planeta1);
		Assert.assertEquals(3,planeta2);
	}
	
	@Test
	public void test_buscar_planetas_por_nome() {
		List<Planet> planetas = new ArrayList<Planet>();
		planetas.add(new Planet("aaa","bbb","ccc"));
		planetas.add(new Planet("aaa","ccc","ddd"));
		
		when(repository.findByNome("aaa")).thenReturn(planetas);
		when(apisw.retornaNumeroAparicoes("aaa")).thenReturn(5);
		
		List<PlanetResponse> responses = service.listarPorNome("aaa");
		
		int planeta1 = responses.get(0).getNumAparicoes();
		int planeta2 = responses.get(1).getNumAparicoes();
		
		Assert.assertNotNull(responses);
		Assert.assertEquals(5,planeta1);
		Assert.assertEquals(5,planeta2);
	}
	
	@Test
	public void test_buscar_planetas_por_id() {
		Planet planeta = new Planet("aaa","bbb","ccc");
		planeta.setId("AAA123");
		
		when(repository.findOneById(planeta.getId())).thenReturn(planeta);
		when(apisw.retornaNumeroAparicoes(planeta.getNome())).thenReturn(5);
		
		PlanetResponse response = service.listarPorId(planeta.getId());
		
		int aparicoes = response.getNumAparicoes();
		
		Assert.assertNotNull(response);
		Assert.assertEquals(5,aparicoes);
	}

}
