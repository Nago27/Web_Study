package egovframework.example.main.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.example.main.service.BoardUtilService;
import egovframework.example.main.service.CommentVO;
import egovframework.example.main.service.BoardFileVO;

@Service("boardUtilService")
public class BoardUtilServiceImpl extends EgovAbstractServiceImpl implements BoardUtilService{
	@Resource(name="boardDAO")
	private BoardDAO boardDAO;
	
	@Resource(name="boardUtilDAO")
	private BoardUtilDAO boardUtilDAO;
	
	@Autowired
	private ServletContext servletContext; // 실제 경로 얻기

	@Override
	public List<BoardFileVO> selectFileList(int postId) throws Exception {
		return boardUtilDAO.selectFileList(postId);
	}

	@Override
	public void downloadFile(int fileId, HttpServletResponse resp)
			throws FileNotFoundException, IOException {
		// DB 조회 (없으면 예외 처리)
		BoardFileVO file = boardUtilDAO.selectFile(fileId);
		if(file == null) {
			throw new FileNotFoundException("첨부파일 정보가 없습니다. (fileId=" + fileId + ")");
		}
		
		// 파일 경로 추적
		String relativePath = file.getFilePath();
		String absolutePath = servletContext.getRealPath(relativePath);
		if(absolutePath == null) {
			 throw new FileNotFoundException("경로 매핑에 실패했습니다: " + relativePath);
		}
		File diskFile = new File(absolutePath);
		if (!diskFile.exists()) {
            throw new FileNotFoundException("서버에 파일이 존재하지 않습니다: " + absolutePath);
        }
		
		
		// 응답 헤더 세팅
		resp.setContentType("application/octet-stream");
		// 다운로드 시 원본 파일명으로 
		String encodedName = URLEncoder.encode(file.getFileName(), "UTF-8").replaceAll("\\+", "%20");
		resp.setHeader("Content-Disposition", "attachment; filename=\"" + encodedName + "\"");
		resp.setContentLengthLong(file.getFileSize());
		
		// 스트림 복사
		try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(diskFile));
		       BufferedOutputStream out = new BufferedOutputStream(resp.getOutputStream())) {
			 byte[] buffer = new byte[8192];
	         int bytesRead;
	         while ((bytesRead = in.read(buffer)) != -1) {
	        	out.write(buffer, 0, bytesRead);
	         }
	         	out.flush();
		}
	}
	
	@Override
	public void deleteFiles(int fileId) throws IOException {
		BoardFileVO fileVO = boardUtilDAO.selectFile(fileId);
		// 서버 내 실제 파일 삭제 
		if (fileVO != null) {
			String fullPath = servletContext.getRealPath(fileVO.getFilePath());
			File file = new File(fullPath);
			if(file.exists()) file.delete();
		}
		
		// DB에서 삭제
		boardUtilDAO.deleteFiles(fileId);
	}
	
	@Override
	public void insertComment(CommentVO vo) throws Exception {
		boardUtilDAO.insertComment(vo);
		boardDAO.updateCommentCnt(vo.getPostId());
	}

	@Override
	public List<CommentVO> commentList(int postId) throws Exception {
		return boardUtilDAO.commentList(postId);
	}

	@Override
	public void deleteComment(Map<String, Object> params) throws Exception{
		boardUtilDAO.deleteComment(params);
		boardDAO.updateCommentCnt((Integer)params.get("postId"));
	}
}
