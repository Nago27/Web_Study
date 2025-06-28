package egovframework.example.main.impl;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import egovframework.example.main.service.BoardFileVO;
import egovframework.example.main.service.BoardService;
import egovframework.example.main.service.BoardVO;
import egovframework.example.main.service.BoardView;

@Service("boardService")
public class BoardServiceImpl extends EgovAbstractServiceImpl implements BoardService {
	@Resource(name="boardDAO")
	private BoardDAO boardDAO;
	
	@Override
	public void insertBoard(BoardVO vo) throws Exception {
		boardDAO.insertBoard(vo);
	}
	
	@Override
	public void insertAttachment(BoardFileVO file) throws Exception {
		boardDAO.insertAttachment(file);
	}

	@Override
	public List<BoardView> boardList(BoardView vo) throws Exception {
		return boardDAO.boardList(vo);
	}

	@Override
	public Integer boardListTot(BoardView vo) throws Exception {
		return boardDAO.boardListTot(vo);
	}
	
	@Override
	public void viewCnt(int boardId) throws Exception {
		boardDAO.viewCnt(boardId);
	}
	
	@Override
	public BoardVO boardDetail(BoardVO vo) throws Exception {
		return boardDAO.boardDetail(vo);
	}

	@Override
	public void updateCommentCnt(int boardId) throws Exception {
		boardDAO.updateCommentCnt(boardId);
	}

	@Override
	public void updateBoard(BoardVO vo) throws Exception {
		boardDAO.updateBoard(vo);
	}

	@Override
	public void removeBoard(int boardId) throws Exception {
		boardDAO.deleteBoard(boardId);
		boardDAO.resetBoardId();
	}
}
