package com.b2w.apidesafiosw.client;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.b2w.apidesafiosw.client.APIStarWarsService;
import com.b2w.apidesafiosw.models.APIStarWars;
import com.b2w.apidesafiosw.responses.APIStarWarsResponse;

public class APIStarWarsServiceTests {

	APIStarWarsService api;
	RestTemplate rest;
	final String url = "https://swapi.co/api/planets";
	int Tatooine, Stewjon, inexistente;

	@Before
	public void setUp() {
		rest = mock(RestTemplate.class);
		api = new APIStarWarsService();
	}

	@Test
	public void test_qde_de_aparicoes_de_planetas_inexistentes() {
		String nome = "Inexistente";
		when(rest.getForObject(url + "?search=" + nome, APIStarWarsResponse.class))
				.thenReturn(new APIStarWarsResponse());

		inexistente = api.retornaNumeroAparicoes(nome);

		Assert.assertNotNull(inexistente);
		Assert.assertEquals(0, inexistente);
	}

	@Test
	public void test_qde_de_aparicoes_de_planetas_com_sucesso() {
		String nomeTatooine = "Tatooine";
		
		APIStarWars sw = new APIStarWars();
		sw.setFilms(Arrays.asList("Filme 1", "Filme 2", "Filme 3", "Filme 4"));
		APIStarWarsResponse starWarsAPIResponse = new APIStarWarsResponse();
		starWarsAPIResponse.setResults(Arrays.asList(sw));
		
		when(rest.getForObject(url + "?search=" + nomeTatooine, APIStarWarsResponse.class))
				.thenReturn(starWarsAPIResponse);
		
		String nomeStewjon = "Stewjon";
		
		APIStarWars sw2 = new APIStarWars();
		sw2.setFilms(Arrays.asList("Filme 1"));
		APIStarWarsResponse starWarsAPIResponse2 = new APIStarWarsResponse();
		starWarsAPIResponse2.setResults(Arrays.asList(sw2));
		
		when(rest.getForObject(url + "?search=" + nomeStewjon, APIStarWarsResponse.class))
				.thenReturn(starWarsAPIResponse2);
		
		Tatooine = api.retornaNumeroAparicoes("Tatooine");
		Stewjon = api.retornaNumeroAparicoes("Stewjon");

		Assert.assertNotNull(Tatooine);
		Assert.assertEquals(5, Tatooine);

		Assert.assertNotNull(Stewjon);
		Assert.assertEquals(0, Stewjon);
	}

}
