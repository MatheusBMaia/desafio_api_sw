package com.b2w.apidesafiosw.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.b2w.apidesafiosw.models.Planet;
import com.b2w.apidesafiosw.repositories.PlanetRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PlanetRepositoryTests {

	 @Autowired
	 PlanetRepository repository;
	    
	 Planet planeta1, planeta2;

	 @Before
	 public void setUp() {
	     planeta1 = repository.save(new Planet("saturno", "arido","montanhoso"));
	     planeta2 = repository.save(new Planet("hellno", "arido","apocalítico"));
	 }
	    
	    @After
	    public void tearDown() {
	    	repository.delete(planeta1);
	    	repository.delete(planeta2);
	    }
	    
	    @Test
	    public void test_salvar_planeta() {
	        Planet planeta = repository.save(new Planet("SaturnoNoId","SAT","URNO"));
	        assertThat(!planeta.getId().isEmpty());
	        repository.delete(planeta);
	    }

	    @Test
	    public void test_buscar_pelo_id() {
	    	List<Planet> response = repository.findByNome("saturno");
	    	
	        Planet result = repository.findOneById(response.get(0).getId());
	        assertThat(result).extracting("nome").contains("saturno");
	    }
	    
	    @Test
	    public void test_buscar_pelo_nome() {
	    	List<Planet> result = repository.findByNome("saturno");
	    	assertThat(result).hasSize(1).extracting("nome").contains("saturno");
	    }

	    @Test
	    public void testa_buscar_todos_planetas() {
	        List<Planet> result = repository.findAll();
	        assertThat(result).isNotEmpty();
	    }
	    
	    @Test
	    public void testa_deletar_planeta() {
	    	List<Planet> planeta = repository.findByNome("hellno");
	    	repository.delete(planeta.get(0));
	    	List<Planet> response = repository.findByNome("hellno");
	    	
	    	assertThat(planeta).isNotEmpty();
	    	assertThat(response).isEmpty();
	}

}
