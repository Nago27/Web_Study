package egovframework.example.main.impl;

import java.util.List;
import java.util.Map;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.example.main.service.BoardFileVO;
import egovframework.example.main.service.CommentVO;

@Repository("boardUtilDAO")
public class BoardUtilDAO  extends EgovAbstractMapper{
	
	/** 게시판 내 첨부파일 조회 */
	public List<BoardFileVO> selectFileList(int postId) {
		return selectList("BoardUtilMapper.selectFileList", postId);
	}
	
	/** 단일 파일 조회 (다운로드) */
	public BoardFileVO selectFile(int fileId) {
		return selectOne("BoardUtilMapper.selectFile", fileId);
	}
	
	/** 파일 삭제 */
	public void deleteFiles(int fileId) {
		delete("BoardUtilMapper.deleteFiles", fileId);
	}
	
	/** 댓글 등록 */
	public void insertComment(CommentVO vo) {
		insert("BoardUtilMapper.insertComment", vo);
	}
	
	/** 댓글 리스트 조회 */
	public List<CommentVO> commentList(int postId) {
		return selectList("BoardUtilMapper.commentList", postId);
	}
	
	/** 댓글 삭제 */
	public int deleteComment(Map<String, Object> params) {
		return delete("BoardUtilMapper.deleteComment", params);
	}
}
