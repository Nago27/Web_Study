package egovframework.example.login.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.example.cmmn.LoginVO;
import egovframework.example.login.service.LoginService;

@Controller
public class RegisterController {
	@Resource(name="loginService")
	private LoginService loginService;
	
	/* 회원가입 화면 */
	@RequestMapping(value="/RegisterPage.do")
	public String showRegisterPage() throws Exception {
		return "RegisterPage";
	}
	
	/* '가입하기' 클릭시 */
	@RequestMapping(value="/RegisterCheck.do")
	public String doJoin(@ModelAttribute("loginVO") LoginVO vo, HttpServletRequest request, Model model) throws Exception {
		// 아이디 중복 확인
		LoginVO existing = loginService.searchId(vo);
		if (existing != null) {
			request.setAttribute("joinError", "이미 사용중인 아이디입니다.");
			return "forward:/RegisterPage.do";
		}
		
		// 비밀번호 확인 검증
		if (!vo.getPassword().equals(request.getParameter("confirmPwd"))) {
			request.setAttribute("joinError", "비밀번호가 일치하지 않습니다.");
			return "forward:/RegisterPage.do";
		}
		
		// 회원 가입 처리
		loginService.registerMember(vo);
		
		return "redirect:/LoginUsr.do";
	} 
}
