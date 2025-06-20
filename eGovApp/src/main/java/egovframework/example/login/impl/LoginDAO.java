package egovframework.example.login.impl;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;

import org.springframework.stereotype.Repository;

import egovframework.example.cmmn.LoginVO;

@Repository("LoginDAO")
public class LoginDAO extends EgovAbstractMapper {
	
	public LoginVO actionLogin(LoginVO vo) throws Exception {
		return selectOne("LoginMapper.actionLogin", vo);
	} 
	
	public LoginVO searchId(LoginVO vo) throws Exception {
		return selectOne("LoginMapper.searchId", vo);
	}
	
	/*
	public LoginVO searchPassword(LoginVO vo) throws Exception {
		return selectOne("LoginMapper.searchPassword", vo);
	}
	*/
	
	public void registerMember(LoginVO vo) throws Exception {
		insert("LoginMapper.registerMember", vo);
	}
}
