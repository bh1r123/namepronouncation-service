package com.wellsfargo.response;

import java.util.List;

public class NameSearchResponse {
	List<EmpRecordResponse> npsList ;
	public List<EmpRecordResponse> getNpsList() {
		return npsList;
	}
	public void setNpsList(List<EmpRecordResponse> npsList) {
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
