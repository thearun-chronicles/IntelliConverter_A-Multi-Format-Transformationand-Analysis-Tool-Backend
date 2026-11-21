package com.coverter.intelliconverter.service;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;




@Service
public class PdfToServicesImplementation implements PdfToServices {

	   public List<String> convertPdfToImages(File pdfFile) throws IOException {
	        List<String> base64Images = new ArrayList<>();
	        PDDocument document = PDDocument.load(pdfFile);
	        PDFRenderer pdfRenderer = new PDFRenderer(document);

	        for (int page = 0; page < document.getNumberOfPages(); page++) {
	            BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300); // Render with high DPI
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();
	            ImageIO.write(bim, "png", baos);
	            byte[] bytes = baos.toByteArray();
	            base64Images.add(Base64.getEncoder().encodeToString(bytes)); // Convert to Base64
	        }

	        document.close();
	        return base64Images;
	    }
    
    
    
    public String extractText(File pdfFile) throws IOException {
	      try (PDDocument document = PDDocument.load(pdfFile)) {
	            PDFTextStripper textStripper = new PDFTextStripper();
	            return textStripper.getText(document);
	        }
	    }

    
    
}