package egovframework.example.main.service;

import java.io.Serializable;

public class BoardView implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/* 검색조건 */
    private String searchType;
    
    /* 검색어 */
    private String searchText;

	/* 현재 페이지 번호 */
	private int pageIndex = 1;
	
	/* 한 페이지에 보여줄 레코드 수 */
	private int pageUnit = 10;
	
	/* 페이징 네비게이터 크기 */
	private int pageSize = 10;
	
	/* 시작 인덱스 */
	private int firstIndex = 1;
	
	/* 마지막페이지 인덱스 */
    private int lastIndex = 1;
    
    /* pageUnit과 같은 역활 */
    private int recordCntPerPage;

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageUnit() {
		return pageUnit;
	}

	public void setPageUnit(int pageUnit) {
		this.pageUnit = pageUnit;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getFirstIndex() {
		return firstIndex;
	}

	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}

	public int getLastIndex() {
		return lastIndex;
	}

	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}

	public int getRecordCntPerPage() {
		return recordCntPerPage;
	}

	public void setRecordCntPerPage(int recordCntPerPage) {
		this.recordCntPerPage = recordCntPerPage;
	}
}
