package com.electronic.store.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.electronic.store.exception.BadApiException;
import com.electronic.store.services.FileService;

@Service
public class FileServiceImpl implements FileService {
	private Logger logger=LoggerFactory.getLogger(FileServiceImpl.class);

	@Override
	public String uploadFile(MultipartFile file, String path) throws IOException {
		// TODO Auto-generated method stub
		String originalFileName=file.getOriginalFilename();
		logger.info("FileName : {}",originalFileName);
		String fileName=UUID.randomUUID().toString();
		String extension=originalFileName.substring(originalFileName.lastIndexOf("."));
		String fileNameWithExtension=fileName+extension;
		String fullPathWithFileName=path+fileNameWithExtension;
		
		if(extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.contentEquals(".jpeg")) {
			
			//file save
			File folder=new File(path);
			if(!folder.exists()) {
				
				//create folder
				folder.mkdirs();
			}
			
			//upload
			
			Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
			return fileNameWithExtension; 
		}
		else {
			 throw new BadApiException("File with this " + extension +"not allowed");
		}
		
		
	}

	@Override
	public InputStream getReosurce(String path, String name) throws FileNotFoundException {
		// TODO Auto-generated method stub
		String fullpath=path+File.separator+name;
		InputStream inputStream=new FileInputStream(fullpath);
		return inputStream;
	}

}
