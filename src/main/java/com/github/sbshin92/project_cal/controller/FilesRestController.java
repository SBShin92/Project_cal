package com.github.sbshin92.project_cal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.sbshin92.project_cal.data.vo.FileVO;
import com.github.sbshin92.project_cal.service.FileService;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequestMapping("/files")
@RestController
public class FilesRestController {
	@Autowired
	private FileService fileService;
	
	@Value("${file.upload-dir}")
	private String FILE_PATH;

	@GetMapping("/download/{fileId}")
	public ResponseEntity<Resource> fileDownload(@PathVariable Integer fileId) {
	    FileVO fileVO = fileService.getFileById(fileId);
	    try {
	        Path filePath = Paths.get(FILE_PATH, fileVO.getFileName());
	        Resource resource = new UrlResource(filePath.toUri());

	        if (resource.exists() || resource.isReadable()) {
	            String originalFilename = fileVO.getOriginalFileName();
	            String encodedFilename = URLEncoder.encode(originalFilename, StandardCharsets.UTF_8.toString())
	                                               .replaceAll("\\+", "%20");

	            return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFilename + "\"; filename*=UTF-8''" + encodedFilename)
	                .body(resource);
	        } else {
	            throw new RuntimeException("Could not read file ");
	        }
	    } catch (MalformedURLException | UnsupportedEncodingException e) {
	        throw new RuntimeException("Error: " + e.getMessage());
	    }
	}
}