package com.ApiECommerce.apiec.DAO;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.ApiECommerce.apiec.Exception.BusinessException;
import com.ApiECommerce.apiec.Exception.RequestException;
import com.ApiECommerce.apiec.Model.Comment;
import com.ApiECommerce.apiec.Model.Product;
import com.ApiECommerce.apiec.Model.User;
import com.ApiECommerce.apiec.Repository.ICommentRepository;
import com.ApiECommerce.apiec.Repository.IUserRepository;

@Service
public class CommentDao implements ICommentDao{

	@Autowired
	private ICommentRepository commentRepo;
	
	@Autowired
	private IProductDao productDao;
	
	@Autowired 
	private IUserRepository userRepo;
	
	@Autowired
	private AuthDao authDao;
	@Override
	@PreAuthorize("hasRole('USER')")
	public void addComment(Comment comment, Long product_id) {
		
		String[] stringComment = comment.getComment().toLowerCase().split(" ");
		String[] badWords = new String[]{"fuck","fucker","cock","motherfucker","bitch"};
		
		
		for(String word : stringComment) {
			for(String badWord: badWords) {
				if(word.toLowerCase().contains(badWord)) {
					throw new RequestException("Porfavor revise su comentario, tiene palabras ofensivas.", HttpStatus.FORBIDDEN, "P-403");
				}
			}
		}
		
		
		    comment.setProduct(productDao.findProductById(product_id));
		    comment.setUser(userRepo.findByUsernameUser(authDao.getUsername()));
		    commentRepo.save(comment);
		
	}

	@Override
	public void deleteComment(Long id) {
		

		    
		    User user = userRepo.findByUsernameUser(authDao.getUsername());
			Comment comment = findCommentById(id);
			List<Product> listProductXUser = user.getProduct();
			
			boolean product_user= listProductXUser
					.stream()
					.anyMatch(p-> p.getId_product().equals(comment.getProduct().getId_product()));
			System.out.println(product_user);
			
			
		    if(user.getId_user().equals(comment.getUser().getId_user()) || product_user ) {
		    	commentRepo.delete(comment);
		    }else {
		    	throw new BusinessException	("Usted no puede borrar un comentario que no es suyo, ni tampoco si el producto no le pertenece.", "P-403", HttpStatus.FORBIDDEN);
		    }
		
	}


	@Override
	public Comment findCommentById(Long id) {
		return commentRepo.findById(id)
				.orElseThrow(
						()->new RequestException("Comentario no existente.",HttpStatus.NOT_FOUND,"P-404")
						);
	}

	@Override
	public void saveComment(Comment comment) {

		commentRepo.save(comment);
		
	}

}
