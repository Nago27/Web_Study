package egovframework.example.cmmn;

import java.io.Serializable;

/*
 * @Class Name : LoginVO.java
 * @Description : 로그인 정보를 담는 VO 클래스
 * 
 * */

public class LoginVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/* 가입자 ID */
	private String user_id;
	
	/* 사용자 이름 */
	private String name;
	
	/* 비밀번호 */
	private String password;
	
	/* 생년월일 */
	private String birth;
	
	/* 이메일 */
	private String email;
	
	/* 전화번호 */
	private String phone;
	
	/* 사용자 구분 */
	private String user_se;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUser_se() {
		return user_se;
	}

	public void setUser_se(String user_se) {
		this.user_se = user_se;
	}

}
