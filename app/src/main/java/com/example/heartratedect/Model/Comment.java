package com.example.heartratedect.Model;

public class Comment {

	private int commentId;// comment表对应的主键Id
	private String commentA;// 评论的一方
	private String commentB;// 被评论的一方
	private String commentContent;// 评论的内容
	private String commentDate;// 评论的日期
	private int shuoId;// 评论在哪个说说下

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getCommentA() {
		return commentA;
	}

	public void setCommentA(String commentA) {
		this.commentA = commentA;
	}

	public String getCommentB() {
		return commentB;
	}

	public void setCommentB(String commentB) {
		this.commentB = commentB;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public String getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}

	public int getShuoId() {
		return shuoId;
	}

	public void setShuoId(int shuoId) {
		this.shuoId = shuoId;
	}

}
