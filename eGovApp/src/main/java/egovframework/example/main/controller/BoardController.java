package egovframework.example.main.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import egovframework.example.cmmn.LoginVO;
import egovframework.example.main.service.BoardUtilService;
import egovframework.example.main.service.BoardFileVO;
import egovframework.example.main.service.BoardService;
import egovframework.example.main.service.BoardVO;
import egovframework.example.main.service.CommentVO;

@Controller
@RequestMapping("/board")
public class BoardController {
	/* 로그 확인용 */
	//private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	/** Service 호출부 */
	@Resource(name="boardService")
	private BoardService boardService;
	
	@Resource(name="boardUtilService")
	private BoardUtilService boardUtilService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;

	
	/** Controller 구현부 */
	
	/* 작성시 첫 페이지 */
	@RequestMapping(value="/WritePage.do")
	public String showWritePage(HttpServletRequest request, Model model) {
		// 세션에서 로그인 정보 꺼내기
		HttpSession session = request.getSession();
		LoginVO loginUser = (session != null) ? (LoginVO) session.getAttribute("loginUser") : null;	
		if (loginUser == null || loginUser.getUser_id() == null) {
			return "redirect:/LoginUsr.do";
		}
				
		model.addAttribute("name", loginUser.getName());
		model.addAttribute("author", loginUser.getUser_id());
		
		return "PostWrite";
	}
	
	/* 수정 페이지 */
	@RequestMapping(value="/EditPage.do")
	public String showEditPage(@RequestParam("boardId") int boardId, HttpServletRequest request, Model model) throws Exception {
		// 세션에서 로그인 정보 꺼내기
		HttpSession session = request.getSession();
		LoginVO loginUser = (session != null) ? (LoginVO) session.getAttribute("loginUser") : null;	
		if (loginUser == null || loginUser.getUser_id() == null) {
			return "redirect:/LoginUsr.do";
		}
		
		// 글 기본 정보 
		BoardVO board = new BoardVO();
		board.setBoardId(boardId);
		BoardVO post = boardService.boardDetail(board);
		model.addAttribute("post", post);
		
		// 작성자가 아닌 경우 처리 
		if(!loginUser.getUser_id().equals(post.getAuthor())) {
			return "redirect:/board/boardDetail.do?boardId=" + boardId;
		}
		
		// 작성자 정보
		model.addAttribute("name", loginUser.getName());
		model.addAttribute("author", loginUser.getUser_id());
		
		// 첨부파일 확인 여부
		model.addAttribute("existFiles", boardUtilService.selectFileList(boardId));
		
		return "PostWrite";
	}
	
	/* 게시글 작성 및 수정 */
	@RequestMapping(value={"/PostWrite.do", "/PostEdit.do"})
	public String saveBoard(
			@ModelAttribute BoardVO boardVO, 
			@RequestParam("uploadFiles") List<MultipartFile> uploadFiles, 
			@RequestParam(value="deleteFileIds", required = false) String deleteFileIds,
			HttpServletRequest request, HttpSession session
		) throws Exception {
		
		// 분기점: boardId가 있으면 수정, 없으면 새로 작성
		if(boardVO.getBoardId() > 0) {
			boardService.updateBoard(boardVO);
		} else {
			boardService.insertBoard(boardVO);
		}
		
		// 삭제 파일들 처리
		if(deleteFileIds != null && !deleteFileIds.isEmpty()) {
			String[] ids = deleteFileIds.split(",");
			for (String s : ids) {
				int id = Integer.parseInt(s);
				boardUtilService.deleteFiles(id);
			}
		}
		
		// 파일 경로 처리
		int postId = boardVO.getBoardId();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/upload/");
		String datePath = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
		File uploadDir = new File(realPath, datePath);
		if(!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		
		
		// 파일 등록 (다수 파일 1개씩)
		List<BoardFileVO> existing = boardUtilService.selectFileList(postId);
		int seq = (existing != null ? existing.size() : 0) + 1;
		
		for (MultipartFile mf : uploadFiles) {
			if (mf != null && !mf.isEmpty()) {
				// UUID_원본이름
				String savedName = UUID.randomUUID().toString() + "_" + mf.getOriginalFilename();
				File dest = new File(uploadDir, savedName);
				mf.transferTo(dest);
				
				// DB에 저장할 파일 경로
				String dbPath = "/resources/upload/" + datePath + "/" + savedName;
				BoardFileVO fileVO = new BoardFileVO();
				fileVO.setPostId(postId);
				fileVO.setFileSeq(seq++);
				fileVO.setFileName(mf.getOriginalFilename());
				fileVO.setFilePath(dbPath);
				fileVO.setFileSize(mf.getSize());
				
				boardService.insertAttachment(fileVO);
			}
		}
		
		return "redirect:/main.do";
	}
	
	/* 게시글 내용 확인 페이지 */
	@RequestMapping(value="/BoardDetail.do")
	public String showBoardDetail(@ModelAttribute BoardVO boardVO, @RequestParam(value="downloadError", required=false) String downloadError,
			HttpServletRequest request, Model model) throws Exception {
		// 세션에서 로그인 정보 꺼내기
		HttpSession session = request.getSession();
		LoginVO loginUser = (session != null) ? (LoginVO) session.getAttribute("loginUser") : null;
				
		if (loginUser == null || loginUser.getUser_id() == null) {
			return "redirect:/LoginUsr.do";
		}
		
		// 조회수 증가
		int b_id = boardVO.getBoardId();
		boardService.viewCnt(b_id);
		
		// 게시글 매퍼 호출
		BoardVO detail = boardService.boardDetail(boardVO);
		
		// 첨부파일 리스트
		List<BoardFileVO> files = boardUtilService.selectFileList(b_id);
		if (downloadError != null) {
			model.addAttribute("downloadErrorMsg", "파일이 없거나 전송 중 오류가 발생했습니다.");
	    }
		
		// 댓글 리스트
		if(detail.getAllowComment() == 1) {
			List<CommentVO> comments = boardUtilService.commentList(b_id);
			model.addAttribute("commentList", comments);
		}
		
		// model 담기
		model.addAttribute("name", loginUser.getName());
		model.addAttribute("boardData", detail);
		model.addAttribute("fileList", files);
		
		return "BoardDetail";
	}
	
	/* 게시판 삭제 + (첨부파일) */
	@RequestMapping(value="/deleteBoard.do")
	public String deleteBoard(@RequestParam("boardId") int boardId, HttpServletRequest request) throws Exception {
		List<BoardFileVO> files = boardUtilService.selectFileList(boardId);
		
		// 서버 내 실제 파일 삭제
		String basePath = request.getServletContext().getRealPath("/");
		for (BoardFileVO f : files) {
			String fullPath = basePath + f.getFilePath();
			File file = new File(fullPath);
			if(file.exists()) file.delete();
		}
		
		// DB 상에서 삭제
		boardService.removeBoard(boardId);
		
		return "redirect:/main.do";
	}
	
	/* 첨부파일 다운로드 mapping */
	@RequestMapping(value="/download.do")
	public void download(@ModelAttribute BoardFileVO fileVO, HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			boardUtilService.downloadFile(fileVO.getFileId(), response);
		} catch (IOException e) {
			String redirect = request.getContextPath()
                    + "/board/view.do?postId=" + fileVO.getPostId()
                    + "&downloadError=1";
			response.sendRedirect(redirect);
		}
	}
	
	/* 댓글 등록 */
	@RequestMapping(value="/addComment.do")
	public String addComment(@ModelAttribute CommentVO comment, @RequestParam("postId") int postId, HttpSession session) throws Exception {
		LoginVO loginUser = (LoginVO) session.getAttribute("loginUser");
		comment.setUserId(loginUser.getUser_id());
		comment.setPostId(postId);
		
		boardUtilService.insertComment(comment);
		
		return "redirect:/board/BoardDetail.do?boardId=" + postId;
	}
	
	/* 댓글 삭제 */
	@RequestMapping(value="/deleteComment.do")
	public String deleteComment(@RequestParam("commentId") int commentId, @RequestParam("postId") int postId, 
				HttpSession session) throws Exception{
		String userId = ((LoginVO)session.getAttribute("loginUser")).getUser_id();
		
		Map<String, Object> params = new HashMap<>();
		params.put("commentId", commentId);
		params.put("postId", postId);
		params.put("userId", userId);
		
		boardUtilService.deleteComment(params);
		
		return "redirect:/board/BoardDetail.do?boardId=" + postId;
	}
	
	
}
