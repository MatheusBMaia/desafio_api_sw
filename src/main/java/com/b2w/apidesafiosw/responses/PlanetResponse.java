package com.b2w.apidesafiosw.responses;

import java.util.List;

import com.b2w.apidesafiosw.models.Planet;


public class PlanetResponse {
	
	private List<String> erros ;
	
	private String id;

	private String nome;
	private String clima;
	private String terreno;
	private Integer numAparicoes;
	
	public PlanetResponse() {
		super() ;
	}

	public PlanetResponse(Planet planet, Integer numAparicoes) {
		super();
		id = planet.getId();
		nome = planet.getNome();
		clima = planet.getClima();
		terreno = planet.getTerreno();
		this.numAparicoes = numAparicoes ;
	}
	
	public PlanetResponse(List<String> erros) {
		this.erros = erros ;
	}

	public List<String> getErros() {
		return erros;
	}

	public void setErros(List<String> erros) {
		this.erros = erros;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getClima() {
		return clima;
	}

	public void setClima(String clima) {
		this.clima = clima;
	}

	public String getTerreno() {
		return terreno;
	}

	public void setTerreno(String terreno) {
		this.terreno = terreno;
	}

	public Integer getNumAparicoes() {
		return numAparicoes;
	}

	public void setNumAparicoes(Integer numAparicoes) {
		this.numAparicoes = numAparicoes;
	}
	

	@Override
	public String toString() {
		return "Planetas{" + 
				", nome='" + nome + '\'' +
				", clima='" + clima + '\'' +
				", terreno='" + terreno + '\'' +
				", aparicoes='" + numAparicoes + '\'' +				
				'}';
	}
}
