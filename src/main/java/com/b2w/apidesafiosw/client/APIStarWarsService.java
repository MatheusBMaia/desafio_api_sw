package com.b2w.apidesafiosw.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.b2w.apidesafiosw.responses.APIStarWarsResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class APIStarWarsService {
	
	private RestTemplate rest ;
	private String APISWSUrl ;
	protected static final Logger LOGGER = LoggerFactory.getLogger(APIStarWarsService.class);
	
	@Autowired
	public APIStarWarsService() {
		this.rest = new RestTemplate();
		this.APISWSUrl = "https://swapi.dev/api/planets/" ;
	}
	
	private String getUrl(String planeta) {
		return APISWSUrl + "?search=" + planeta;
	}
	
	public Integer retornaNumeroAparicoes(String planeta) {
		try {
			String url = getUrl(planeta) ;
			APIStarWarsResponse apiSwResp ;
			System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,TLSv1");
			apiSwResp = rest.getForObject(url, APIStarWarsResponse.class);
			return apiSwResp.getResults().stream().findFirst().get().getFilms().size();
		} catch(Exception e) {
			LOGGER.info("Ocorreu um erro ao pesquisar as aparições em filmes: " + e.getMessage());
			return 0;
		}
	}
}
