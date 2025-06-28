package egovframework.example.main.service;

import java.io.Serializable;

public class BoardFileVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/* 첨부파일 id */
	private int fileId;
	
	/* 게시글 id (외래키) */
	private int postId;
	
	/* 게시글 내 첨부파일 순서 */
	private int fileSeq;
	
	/* 파일 이름 */
	private String fileName;
	
	/* 파일 경로 */
	private String filePath;
	
	/* 파일 크기 */
	private long fileSize;

	/** GET/SET */
	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int getFileSeq() {
		return fileSeq;
	}

	public void setFileSeq(int fileSeq) {
		this.fileSeq = fileSeq;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	
}
