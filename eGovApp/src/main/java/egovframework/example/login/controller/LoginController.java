package egovframework.example.login.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.example.cmmn.LoginVO;
import egovframework.example.login.service.LoginService;

@Controller
public class LoginController {
	@Resource(name="loginService")
	private LoginService loginService;
	
	/* 로그인 화면 */
	@RequestMapping(value="/LoginUsr.do")
	public String showLoginPage() throws Exception {
		return "LoginUsr";
	}
	
	/* 로그인 처리 */
	@RequestMapping(value="/LogCheck.do")
	public String LoginCheck(@ModelAttribute("loginVO") LoginVO vo, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		LoginVO result = loginService.actionLogin(vo);
		
		
		if(result != null && result.getUser_id() != null && !result.getUser_id().equals("")) {
			request.getSession().setAttribute("loginUser", result);
			return "redirect:/main.do";
		} else { /* 로그인 실패시 */
			model.addAttribute("message", "로그인 정보가 올바르지 않습니다.");
			return "forward:/LoginUsr.do";
		}
	}
	
	/* 로그아웃 처리 */
	@RequestMapping(value="/Logout.do")
	public String Logout(HttpSession session) {
		session.invalidate();
		return "redirect:/LoginUsr.do";
	}
}
