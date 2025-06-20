package egovframework.example.login.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.jasypt.encryption.StringEncryptor;

import egovframework.example.cmmn.LoginVO;
import egovframework.example.login.service.LoginService;


@Service("loginService")
public class LoginServiceImpl extends EgovAbstractServiceImpl implements LoginService{
	
	@Resource(name="LoginDAO")
	private LoginDAO loginDAO;
	
	/* 개인정보 암호화 */
	@Autowired
    private StringEncryptor jasyptEncryptor;
	
	/* 패스워드 암호화 */
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	/* 로그인 처리 */
	@Override
	public LoginVO actionLogin(LoginVO vo) throws Exception {
		
		LoginVO loginVO = loginDAO.actionLogin(vo);
		if (loginVO == null) {
			return null;
		}
		
		// 비밀번호 단방향 해시 비교
		if (!passwordEncoder.matches(vo.getPassword(), loginVO.getPassword())) {
			return null;
		}
		
		// 개인정보 복호화
		if(loginVO != null) {
			loginVO.setBirth(jasyptEncryptor.decrypt(loginVO.getBirth()));
			loginVO.setName(jasyptEncryptor.decrypt(loginVO.getName()));
			loginVO.setEmail(jasyptEncryptor.decrypt(loginVO.getEmail()));
			if (loginVO.getPhone() != null) { 
				loginVO.setPhone(jasyptEncryptor.decrypt(loginVO.getPhone()));
			}
		}
		
		return loginVO;
	}

	/* 아이디 중복 확인 */
	@Override
	public LoginVO searchId(LoginVO vo) throws Exception {
		LoginVO loginVO = loginDAO.searchId(vo);
		return loginVO;
	}

	/* 비밀번호 찾기 
	@Override
	public LoginVO searchPassword(LoginVO vo) throws Exception {
		LoginVO loginVO = loginDAO.searchPassword(vo);
		return loginVO;
	}
	*/
	
	/* 개인정보 암호화 처리 */
	@Override
	public void registerMember(LoginVO vo) throws Exception {
		// 비밀번호 단방향 암호화
		String hashed = passwordEncoder.encode(vo.getPassword());
		vo.setPassword(hashed);
		
		// 개인정보 암호화 (생년월일, 이름, 이메일, 전화번호)
		vo.setBirth(jasyptEncryptor.encrypt(vo.getBirth()));
		vo.setName(jasyptEncryptor.encrypt(vo.getName()));
		vo.setEmail(jasyptEncryptor.encrypt(vo.getEmail()));
		if (vo.getPhone() != null) { 
			vo.setPhone(jasyptEncryptor.encrypt(vo.getPhone()));
		}
        
		loginDAO.registerMember(vo);
	}

}
