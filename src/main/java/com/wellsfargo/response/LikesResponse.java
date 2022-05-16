package com.wellsfargo.response;

import java.util.List;

public class LikesResponse {

	int totalLikes;
	List<String> empIdList;

	public int getTotalLikes() {
		return totalLikes;
	}

	public void setTotalLikes(int totalLikes) {
		this.totalLikes = totalLikes;
	}

	public List<String> getEmpIdList() {
		return empIdList;
	}

	public void setEmpIdList(List<String> empIdList) {
		this.empIdList = empIdList;
	}
}
