package egovframework.example.main.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import egovframework.example.cmmn.LoginVO;
import egovframework.example.main.service.BoardFileVO;
import egovframework.example.main.service.BoardService;
import egovframework.example.main.service.BoardVO;

@Controller
public class BoardController {
	@Resource(name="boardService")
	private BoardService boardService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;
	
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
	
	@RequestMapping(value="/PostWrite.do")
	public String doWrite(
			@ModelAttribute BoardVO boardVO, 
			@RequestParam("uploadFiles") List<MultipartFile> uploadFiles, 
			HttpServletRequest request
		) throws Exception {
		
		// 게시글 등록
		boardService.insertBoard(boardVO);
		
		// 파일 저장 준비
		int postId = boardVO.getBoardId();
		String realPath = request.getSession().getServletContext().getRealPath("resources/upload/");
		
		// 날짜 별 폴더 경로 생성
		String datePath = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
		File uploadDir = new File(realPath, datePath);
		if(!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		
		// 파일 처리 (다수 파일 1개씩)
		int seq = 1;
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
	
	@RequestMapping(value="/BoardView.do")
	public String BoardViewPage() throws Exception {
		return "BoardView";
	}
}
