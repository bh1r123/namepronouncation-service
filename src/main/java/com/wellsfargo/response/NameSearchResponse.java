package com.wellsfargo.response;

import java.util.List;

import com.wellsafrgo.model.NPSDomain;

public class NameSearchResponse {
	List<NPSDomain> npsList ;
	public List<NPSDomain> getNpsList() {
		return npsList;
	}
	public void setNpsList(List<NPSDomain> npsList) {
		this.npsList = npsList;
	}
	public int getSearchNameCount() {
		return searchNameCount;
	}
	public void setSearchNameCount(int searchNameCount) {
		this.searchNameCount = searchNameCount;
	}
	int searchNameCount;

}
