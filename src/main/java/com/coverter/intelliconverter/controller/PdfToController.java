package com.coverter.intelliconverter.controller;


	import java.io.File;
	import java.io.IOException;
	import java.util.List;


	import org.springframework.beans.factory.annotation.Autowired;

	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.*;
	import org.springframework.web.multipart.MultipartFile;

import com.coverter.intelliconverter.service.PdfToServices;


	import java.nio.file.Files;


	@RestController
	public class PdfToController{

	    @Autowired
	    private PdfToServices pdfToService;
		@PostMapping(value = "/pdf-to-image", consumes = "multipart/form-data")
		public ResponseEntity<List<String>> convertPdfToImages(@RequestParam("file") MultipartFile file) {
			try {
				// Save PDF temporarily
				File tempFile = Files.createTempFile("uploaded", ".pdf").toFile();
				file.transferTo(tempFile);

				// Convert to images
				List<String> base64Images = pdfToService.convertPdfToImages(tempFile);

				return ResponseEntity.ok(base64Images);
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(500).body(null);
			}
		}
	

	    
	    
	    @PostMapping("/pdf-to-text")
	    public ResponseEntity<String> convertToText(@RequestPart("file") MultipartFile file) {
	        try {
	            File tempFile = File.createTempFile("pdf", ".pdf");
	            file.transferTo(tempFile);
	            String text = pdfToService.extractText(tempFile);
	            return ResponseEntity.ok(text);
	        } catch (IOException e) {
	            return ResponseEntity.internalServerError().body("Error processing PDF: " + e.getMessage());
	        }
	    }
	}