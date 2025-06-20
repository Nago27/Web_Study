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
public class BoardServicceImpl extends EgovAbstractServiceImpl implements BoardService {
	@Resource(name="boardDAO")
	private BoardDAO boardDAO;
	
	@Override
	public void insertBoard(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		boardDAO.insertBoard(vo);
	}
	
	@Override
	public void insertAttachment(BoardFileVO file) throws Exception {
		// TODO Auto-generated method stub
		boardDAO.insertAttachment(file);
	}

	@Override
	public List<BoardView> boardList(BoardView vo) throws Exception {
		// TODO Auto-generated method stub
		return boardDAO.boardList(vo);
	}

	@Override
	public int boardListTotCnt(BoardView vo) throws Exception {
		// TODO Auto-generated method stub
		return boardDAO.boardListTotCnt(vo);
	}

	@Override
	public BoardVO boardView(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
