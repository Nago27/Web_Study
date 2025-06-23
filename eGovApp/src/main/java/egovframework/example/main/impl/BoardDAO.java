package egovframework.example.main.impl;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.example.main.service.BoardFileVO;
import egovframework.example.main.service.BoardVO;
import egovframework.example.main.service.BoardView;

@Repository("boardDAO")
public class BoardDAO extends EgovAbstractMapper {

	/* 게시글 작성 */
	public void insertBoard(BoardVO vo) throws Exception{
		insert("BoardMapper.insertBoard", vo);
	}
	
	/* 첨부파일 추가 */
	public void insertAttachment(BoardFileVO file) throws Exception {
		insert("BoardMapper.insertAttachment", file);
	}
	
	/* 게시글 리스트 불러오기 */
	public List<BoardView> boardList(BoardView vo) throws Exception {
		return selectList("BoardMapper.boardList", vo);
	}
	
	/* 게시글 총 갯수 조회 */
	public Integer boardListTot (BoardView vo) throws Exception {
		return (Integer) selectOne("BoardMapper.boardListTot", vo);
	}
	
	/* 조회수 증가 */
	public void viewCnt(int boardId) throws Exception {
		update("BoardMapper.viewCnt", boardId);
	}
	
	/* 게시글 내용 확인 */ 
	public BoardVO boardDetail(BoardVO vo) throws Exception {
		return selectOne("BoardMapper.boardDetail", vo);
	}
	
	/* 게시글 삭제 */
	public void deleteBoard(BoardVO vo) throws Exception {
		
	}
}
