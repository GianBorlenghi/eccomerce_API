package com.ApiECommerce.apiec.Controller;


import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ApiECommerce.apiec.DAO.AuthDao;
import com.ApiECommerce.apiec.DAO.IBrandDao;
import com.ApiECommerce.apiec.DAO.ICategoryDao;
import com.ApiECommerce.apiec.DAO.IFileDao;
import com.ApiECommerce.apiec.DAO.IProductDao;
import com.ApiECommerce.apiec.Exception.BusinessException;
import com.ApiECommerce.apiec.Model.File;
import com.ApiECommerce.apiec.Model.Product;
import com.ApiECommerce.apiec.Repository.IUserRepository;
import com.cloudinary.Cloudinary;
import com.google.common.net.HttpHeaders;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor

public class ProductController {

	
	@Autowired
	private IProductDao prodService;
	
	@Autowired 
	private IBrandDao brandDao;
	
	@Autowired
	private ICategoryDao categoryDao;

	@Autowired
	private IUserRepository userRepo;
	@Autowired
	private AuthDao authDao;
	
	@Autowired
	private IFileDao fileDao;
	
	
	
	public String ucFirst(String str) {
	    if (str == null || str.isEmpty()) {
	        return str;
	    } else {
	        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	    }
	}
	
	@PostMapping(value = {"/create"}, consumes= {org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE})
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> createProduct(
			@RequestPart(value = "product", required= true) Product product,
			@RequestPart(value = "files",required= true) MultipartFile[] files) throws IOException {
		
			product.setTitle(ucFirst(product.getTitle()));
			product.setDescription(ucFirst(product.getDescription()));
			product.setDate_published(LocalDateTime.now());
			product.setUser(userRepo.findByUsernameUser(authDao.getUsername()));
			
			prodService.saveProduct(product);
				
			List<File> fileNames  = new ArrayList<>();
			Arrays.asList(files)
			.stream()
			.forEach(
					file->{
						File file2 = new File();
						Map result;
						try {
							result = fileDao.upload(file);
							file2.setFile_name(result.get("original_filename").toString());
							file2.setUrl(result.get("url").toString());
							file2.setPublic_id(result.get("public_id").toString());
							file2.setProduct(product);
							fileDao.saveFile(file2);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					);
			product.setFiles_product(fileNames);
			
			return new ResponseEntity<>(HttpStatus.CREATED);

	}


	@GetMapping("/findProductById/{id}")
	public Product findProductById(@PathVariable(value = "id") Long id) {
			Product prod =prodService.findProductById(id); 
		return prod;
		
}
	

	@PutMapping("/editProduct/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> editProduct(
							@PathVariable (value= "id") Long id,
							@Valid @RequestParam(value= "price",required=true) double price,
							@Valid @RequestParam(value = "title") String title,
							@Valid @RequestParam(value = "description",required=false) String description )
		{

		try {
			Product product = prodService.findProductById(id);
			
			if(product.getPrice() == price && product.getTitle().equals(title) && product.getDescription().equals(description)) {
				
				return new ResponseEntity<>("Sin cambios en producto "+product.getTitle(), HttpStatus.OK);
				
			}
			
			if(product.getPrice()!=price && price != 0) {
				product.setPrice(price);
			}
			if(!product.getTitle().equals(title) && title != null) {
				product.setTitle(title);
			}
			if(!product.getDescription().equals(description) && description!=null) {
				product.setDescription(description);
			}
			prodService.saveProduct(product);
			return new ResponseEntity<>("Producto editado correctamente", HttpStatus.OK);
		}catch(ConstraintViolationException e) {
			throw new BusinessException("qweqw",description, HttpStatus.OK);
		}
		}
	
	@DeleteMapping("/deleteProduct/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> deleteProduct(
			@PathVariable (value = "id") Long id){
			Product product = prodService.findProductById(id);
			prodService.deleteProduct(id);
			List<File> product_file = product.getFiles_product();
			product_file.forEach(file->{
				try {
					fileDao.delete(file.getPublic_id().toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			return new ResponseEntity<>("Producto con id "+id+" elminiado.", HttpStatus.OK);
		
	}
}
