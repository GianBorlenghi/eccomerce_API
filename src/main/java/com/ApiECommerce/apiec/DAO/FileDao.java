package com.ApiECommerce.apiec.DAO;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ApiECommerce.apiec.Model.File;
import com.ApiECommerce.apiec.Repository.IFileRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import jakarta.transaction.Transactional;

@Service
public class FileDao implements IFileDao {
	
	@Autowired
	private IFileRepository fileRepo;

	private final Path rootLocation = Paths.get("uploads");
	
	private Cloudinary cloudinary;
	
	private Map<String, String> valuesMap = new HashMap<>();
	
	
	
	public FileDao() {
		  valuesMap.put("cloud_name", "dkst5erda");
		  valuesMap.put("api_key" ,"794886247175969");
		  valuesMap.put("api_secret", "QdmJxsEeSaW6OwoVlenzUjAvLqQ");
		  cloudinary = new Cloudinary(valuesMap);
	}

	public Map upload(MultipartFile file) throws IOException {
		java.io.File file2 = convert(file);
		Map result = cloudinary.uploader().upload(file2, ObjectUtils.emptyMap());
		file2.delete();
		return result;
	}
	
	public java.io.File convert ( MultipartFile files) throws IOException {
		java.io.File file = new java.io.File(files.getOriginalFilename());
		FileOutputStream fo = new FileOutputStream(file);
		fo.write(files.getBytes());
		fo.close();
		return file;
	}
	
	public Map delete(String id) throws IOException {
		Map result = cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
		return result;
	}
	/*@Override
	public void init() {
		try {
			boolean exist = Files.isDirectory(rootLocation);
			if(!exist) {
				Files.createDirectory(rootLocation);
			}
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void store(MultipartFile img) {
	
		try {
			Files.copy(img.getInputStream(), this.rootLocation.resolve(img.getOriginalFilename()));
			System.out.println(img.getInputStream().toString()+ " " +rootLocation);
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public org.springframework.core.io.Resource loadAsResource(String filename) {
		try {
			Path file = rootLocation.resolve(filename);
			org.springframework.core.io.Resource resource = new UrlResource(file.toUri());
			if(resource.exists() || resource.isReadable()) {
				
				return resource;
			}else {
				throw new RuntimeException("No se puede leer el archivo.");
			}

		}catch(MalformedURLException e) {
			e.printStackTrace();
		}
	
		return null;

	}
	
*/
	@Override
	@Transactional
	public void saveFile(File file) {
		
		fileRepo.save(file);
		
		
	}
/*
	@Override
	public Stream<Path> loadAll() {

		try {
			return Files.walk(this.rootLocation, 1)
					.filter(path -> !path.equals(this.rootLocation))
					.map(this.rootLocation::relativize);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
*/


}
