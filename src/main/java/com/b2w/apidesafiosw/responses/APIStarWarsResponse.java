package com.b2w.apidesafiosw.responses;

import java.util.List;

import com.b2w.apidesafiosw.models.APIStarWars;

public class APIStarWarsResponse {
	List<APIStarWars> results;
	
	public List<APIStarWars> getResults() {
		return results;
	}

	public void setResults(List<APIStarWars> results) {
		this.results = results;
	}
}
