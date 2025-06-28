package egovframework.example.main.service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommentVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 댓글 ID */
	private int commentId;
	
	/** 게시글 ID (참조) */
	private int postId;
	
	/** 댓글 작성자 (참조) */
	private String userId;
	
	/** 댓글 내용 */
	private String content;
	
	/** 작성 일자 */
	private LocalDateTime createdAt;
	
	// format
	public String getFormatDate() {
		return createdAt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
	}
	/** GET/SET */
	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
}
