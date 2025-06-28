package egovframework.example.main.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.example.cmmn.LoginVO;
import egovframework.example.main.service.BoardService;
import egovframework.example.main.service.BoardView;

@Controller
public class MainController {
	@Resource(name="boardService")
	private BoardService boardService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;
	
	@RequestMapping(value="/main.do")
	public String mainPage(@ModelAttribute("searchVO") BoardView searchVO, HttpServletRequest request, Model model) throws Exception {
		// 세션에서 로그인 정보 꺼내기
		HttpSession session = request.getSession();
		LoginVO loginUser = (session != null) ? (LoginVO) session.getAttribute("loginUser") : null;
		
		if (loginUser == null || loginUser.getUser_id() == null) {
			return "redirect:/LoginUsr.do";
		}
		
		// 페이지 레코드
		searchVO.setPageUnit(propertyService.getInt("pageUnit")); 
        searchVO.setPageSize(propertyService.getInt("pageSize"));
        
        // PaginationInfo 세팅
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
        paginationInfo.setPageSize(searchVO.getPageSize());

        // VO에 first/last index 설정
        searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
        searchVO.setRecordCntPerPage(paginationInfo.getRecordCountPerPage());

        // 목록 + 총건수 조회
        List<BoardView> list = boardService.boardList(searchVO);
        int tot = boardService.boardListTot(searchVO);
        paginationInfo.setTotalRecordCount(tot);
        
        int boardNo = tot - ((searchVO.getPageIndex()- 1) * searchVO.getPageSize());

        // Model에 결과 담기
        model.addAttribute("name", loginUser.getName());
        model.addAttribute("resultList", list);
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("boardNo", boardNo);
		
		return "main";
	}
}
