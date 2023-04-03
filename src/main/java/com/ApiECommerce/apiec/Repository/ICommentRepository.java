package com.ApiECommerce.apiec.Repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ApiECommerce.apiec.Model.Comment;

@Repository
public interface ICommentRepository extends CrudRepository<Comment, Long>{
	
}
