package com.ApiECommerce.apiec.DAO;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.web.multipart.MultipartFile;

import com.ApiECommerce.apiec.Model.File;

import jakarta.annotation.Resource;

public interface IFileDao {

	//public void store(MultipartFile img);
//	public org.springframework.core.io.Resource loadAsResource(String filename);
//	public void init();
	public void saveFile(File file);
	//public Stream<Path> loadAll();
	public Map upload(MultipartFile file) throws IOException;
	public Map delete(String id) throws IOException;
	
}
