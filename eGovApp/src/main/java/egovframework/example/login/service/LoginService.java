package egovframework.example.login.service;

import egovframework.example.cmmn.LoginVO;

public interface LoginService {
	
	/* 로그인 처리 */
	public LoginVO actionLogin(LoginVO vo) throws Exception;
	
	/* 아이디 중복 확인 */
	public LoginVO searchId(LoginVO vo) throws Exception;

	/* 비밀번호 찾기 
	public LoginVO searchPassword(LoginVO vo) throws Exception; */
	
	/* 회원가입 */
	public void registerMember(LoginVO vo) throws Exception;
}
