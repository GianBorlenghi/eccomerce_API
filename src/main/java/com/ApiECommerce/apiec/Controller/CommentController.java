package com.ApiECommerce.apiec.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ApiECommerce.apiec.DAO.AuthDao;
import com.ApiECommerce.apiec.DAO.ICommentDao;
import com.ApiECommerce.apiec.Exception.BusinessException;
import com.ApiECommerce.apiec.Model.Comment;
import com.ApiECommerce.apiec.Repository.IUserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

	@Autowired
	private ICommentDao commentDao;
	
	@Autowired 
	private IUserRepository userRepo;
	
	@Autowired
	private AuthDao authDao;
	
	@PostMapping("/addComment/{id_product}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addComment(
			@PathVariable(value = "id_product") Long id_product,
			@RequestBody Comment comment){

		    
		    commentDao.addComment(comment,id_product);
		    return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/deleteComment/{id_comment}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> deleteComment(
			@PathVariable (value="id_comment") Long id_comment){
		
		commentDao.deleteComment(id_comment);
		return new ResponseEntity<>("Comentario eliminado", HttpStatus.OK);
	}
	
	@PutMapping("/editComment/{id_comment}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> editComment(
			@PathVariable(value="id_comment") Long id_comment,
			@RequestParam(value="comment") String comment
			){
		
		Comment comment2 = commentDao.findCommentById(id_comment);
		if(userRepo.findByUsernameUser(authDao.getUsername()).equals(comment2.getUser())){
			comment2.setComment(comment);
			commentDao.saveComment(comment2);
			return new ResponseEntity<>("Comentario modificado", HttpStatus.OK);	
		}else {
			throw new BusinessException("Usted no es el propietario de este comentario, por ende, no puede modificarlo.", "P-403", HttpStatus.FORBIDDEN);
		}
		

	}

}
