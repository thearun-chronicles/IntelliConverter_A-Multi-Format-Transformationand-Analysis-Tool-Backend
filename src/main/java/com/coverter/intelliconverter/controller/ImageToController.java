package com.coverter.intelliconverter.controller;



import com.coverter.intelliconverter.datatransferobject.ImageToDataTransferObject;
import com.coverter.intelliconverter.service.ImageToServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping("/convert")
public class ImageToController {

    @Autowired
    private ImageToServices  imageToServices;

    @PostMapping("/image-to-pdf")
    public ResponseEntity<?> convertImageToPdf(@RequestParam("file") MultipartFile file) {
    
        try {
          
            byte[] pdfContent = imageToServices.convertImageToPdf(file);

          
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=converted_image.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfContent);
        } catch (Exception e) {
            
        	ImageToDataTransferObject errorResponse = new ImageToDataTransferObject(
                    "Failed to convert image to PDF", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    

    @PostMapping("/image-to-text")
    public ResponseEntity<String> convertImageToText(@RequestPart("file") MultipartFile file) {
        try {
            String extractedText = imageToServices.extractText(file);
            return ResponseEntity.ok(extractedText);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
