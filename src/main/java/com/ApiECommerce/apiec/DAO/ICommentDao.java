package com.ApiECommerce.apiec.DAO;

import com.ApiECommerce.apiec.Model.Comment;

public interface ICommentDao {

	public void addComment(Comment comment,Long product_id);
	
	public void deleteComment(Long id);
		
	public Comment findCommentById(Long id);
	
	public void saveComment(Comment comment);
}
