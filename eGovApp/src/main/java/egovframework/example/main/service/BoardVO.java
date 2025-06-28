package egovframework.example.main.service;

import java.io.Serializable;
import java.time.LocalDateTime;

public class BoardVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9058583582229849659L;
	
	/* 게시판 ID */
	private int boardId;
	
	/* 작성자 */
	private String author;
	
	/* 글 제목 */
	private String title;
	
	/* 글 본문 */
	private String content;
	
	/* 생성 일자 */
	private LocalDateTime createdAt;
	
	/* 댓글 허용 여부 */
	private int allowComment;
	
	/* 댓글수  */
	private int commentCnt;
	
	/* 조회수 */
	private int views;

	/** GET/SET */
	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int board_id) {
		this.boardId = board_id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public int getAllowComment() {
		return allowComment;
	}

	public void setAllowComment(int allowComment) {
		this.allowComment = allowComment;
	}

	public int getCommentCnt() {
		return commentCnt;
	}

	public void setCommentCnt(int commentCnt) {
		this.commentCnt = commentCnt;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}
}
