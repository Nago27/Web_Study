package egovframework.example.main.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public interface BoardUtilService {
	/** 게시판 내 첨부파일 조회 */
	public List<BoardFileVO> selectFileList(int postId) throws Exception;
	
	/** 파일 다운로드 */
	public void downloadFile(int fileId, HttpServletResponse resp) throws FileNotFoundException, IOException;
	
	/** 파일 삭제 */
	public void deleteFiles(int fileId) throws IOException;
	
	/** 댓글 등록 */
	public void insertComment(CommentVO vo) throws Exception;
	
	/** 댓글 리스트 조회 */
	public List<CommentVO> commentList(int postId) throws Exception;
	
	/** 댓글 삭제 */
	public void deleteComment(Map<String, Object> params) throws Exception;
}
