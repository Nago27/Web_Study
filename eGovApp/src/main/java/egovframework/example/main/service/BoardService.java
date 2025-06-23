package egovframework.example.main.service;

import java.util.List;

public interface BoardService {

	/* 게시글 작성 */
	public void insertBoard(BoardVO vo) throws Exception;
	
	/* 첨부파일 추가 */
	public void insertAttachment(BoardFileVO file) throws Exception;
	
	/* 게시글 리스트 불러오기 */
	public List<BoardView> boardList(BoardView vo) throws Exception;
	
	/* 게시글 총 갯수 조회 */
	public Integer boardListTot(BoardView vo) throws Exception;
	
	/* 조회수 증가 */
	public void viewCnt(int b_id) throws Exception;
	
	/* 게시글 내용 확인 */
	public BoardVO boardDetail(BoardVO vo) throws Exception;
	
	/* 게시글 삭제 */
	public void deleteBoard(BoardVO vo) throws Exception;
}