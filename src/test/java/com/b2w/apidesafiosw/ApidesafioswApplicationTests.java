package com.b2w.apidesafiosw;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.b2w.apidesafiosw.responses.PlanetResponse;
import com.b2w.apidesafiosw.models.Planet;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ApidesafioswApplicationTests {

	@LocalServerPort
	private int port;

	private static final String BASE_PATH = "http://localhost:";

	@Autowired
	private TestRestTemplate restTemplate;
	
	private ObjectMapper MAPPER = new ObjectMapper();

	@Before
	public void setUp() {
		restTemplate = new TestRestTemplate();
	}

	@Test
	public void test_criar_planeta_com_parametros_corretos() {
		PlanetResponse response = criaPlanetaCorretamente();
	
		assertEquals( null, response.getErros());
		String id = response.getId();
		restTemplate.delete(BASE_PATH + port + "/planets/" + "/" + id);
	}
	
	@Test
	public void test_criar_planeta_com_parametros_incorretos() {
		PlanetResponse response = criaPlanetaIncorretamente();

		Assert.assertNotNull( response.getErros());
	}
	
	@Test
	public void test_buscar_planetas_por_id() {
		List<String> ids = criaPlanetasParaTeste();
		
		String idTest = ids.stream().findFirst().get().toString();
		PlanetResponse planeta = descobrePlanetaPorId(idTest);
		
		Assert.assertEquals("Tatooine", planeta.getNome());
		Assert.assertEquals("arid", planeta.getClima());
		Assert.assertEquals("desert", planeta.getTerreno());

		deletaPlanetasParaTeste(ids);
	}
	
	@Test
	public void test_busca_planets() {
		List<String> ids = criaPlanetasParaTeste();
		
		List<PlanetResponse> planets = pegaListaDePlanetas("");

		Assert.assertNotNull(planets);
		
		deletaPlanetasParaTeste(ids);
	}

	@Test
	public void testa_deletar_planeta_comparando_apos_acao() {
		List<String> ids = criaPlanetasParaTeste();

		String idTest = ids.stream().findFirst().get().toString();
		PlanetResponse planeta = descobrePlanetaPorId(idTest);
		restTemplate.delete(BASE_PATH + port + "/planets/" + "/" + idTest);
		PlanetResponse planetaAtual = descobrePlanetaPorId(idTest);

		Assert.assertNotSame(planeta, planetaAtual);
		Assert.assertNull(planetaAtual.getNome());
		
		deletaPlanetasParaTeste(ids);
	}

	@Test
	public void test_buscar_pelo_nome_incorreto() {
		List<String> ids = criaPlanetasParaTeste();
		
		List<PlanetResponse> responses = pegaListaDePlanetas("nome/Inexistente");
		
		Assert.assertTrue(responses.isEmpty());
		
		deletaPlanetasParaTeste(ids);
	}
	
	@Test
	public void test_buscar_pelo_nome() {
		List<String> ids = criaPlanetasParaTeste();
		
		List<PlanetResponse> responses = pegaListaDePlanetas("nome/Tatooine");
		
		Assert.assertEquals(responses.stream().findFirst().get().getClima(),"arid");
		
		deletaPlanetasParaTeste(ids);
	}

	private PlanetResponse criaPlanetaIncorretamente() {
		Planet planeta = new Planet();

		planeta.setNome("aaaaaaa");
		planeta.setClima("bbbbbbb");

		return criaPlaneta(planeta);
	}
	
	private PlanetResponse criaPlanetaCorretamente() {
		Planet planeta = new Planet("Yavin IV", "temperate, tropical", "jungle, rainforests");
		return criaPlaneta(planeta);
	}
	
	private List<String> criaPlanetasParaTeste() {
		Planet planeta = new Planet("Tatooine", "arid", "desert");
		Planet planeta2 = new Planet("Kashyyyk", "tropical", "jungle, forests, lakes, rivers");
		Planet planeta3 = new Planet("Bespin", "temperate", "gas giant");
		PlanetResponse response = criaPlaneta(planeta);
		PlanetResponse response2 = criaPlaneta(planeta2);
		PlanetResponse response3 = criaPlaneta(planeta3);
		
		List<String> ids = new ArrayList<String>();
		ids.add(response.getId());
		ids.add(response2.getId());
		ids.add(response3.getId());
		
		return ids;
	}
	
	private void deletaPlanetasParaTeste(List<String> ids) {
		for (String id: ids) {
			restTemplate.delete(BASE_PATH + port + "/planets/" + id, Planet.class);
		}
	}

	private PlanetResponse criaPlaneta(Planet planeta) {
		return restTemplate.postForObject(BASE_PATH + port + "/planets/", planeta, PlanetResponse.class);
	}

	private PlanetResponse descobrePlanetaPorId(String id) {
		try {
			PlanetResponse planeta = restTemplate.getForObject(BASE_PATH + port + "/planets/" + id, PlanetResponse.class);
			return planeta;
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

	private List<PlanetResponse> pegaListaDePlanetas(String path) {
		try {
			String response = restTemplate.getForObject(BASE_PATH + port + "/planets/" + path, String.class);
			List<PlanetResponse> planetas = MAPPER.readValue(response,MAPPER.getTypeFactory().constructCollectionType(List.class, PlanetResponse.class));
			return planetas;
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}
}

