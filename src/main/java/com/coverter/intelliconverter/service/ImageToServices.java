package com.coverter.intelliconverter.service;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.coverter.intelliconverter.util.TesseractUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import javax.imageio.ImageIO;

@Service
public class ImageToServices {
	   public String extractText(MultipartFile file) throws Exception {
	        // Convert MultipartFile to File
	        File imageFile = convertMultipartFileToFile(file);

	        // Use TesseractUtil for OCR
	        return TesseractUtil.performOCR(imageFile);
	    }

	    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
	        File tempFile = File.createTempFile("uploaded-", file.getOriginalFilename());
	        file.transferTo(tempFile);
	        return tempFile;
	    }

    public byte[] convertImageToPdf(MultipartFile file) throws IOException {
        // Load the image
        BufferedImage image = ImageIO.read(file.getInputStream());
        PDDocument document = new PDDocument();

        // Create a PDF page
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        // Add the image and text to the PDF page
        PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, file.getBytes(), "converted_image");
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.beginText();
            contentStream.setFont(org.apache.pdfbox.pdmodel.font.PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(50, 750);
            contentStream.showText("Your image is converted to PDF");
            contentStream.endText();

            contentStream.drawImage(pdImage, 100, 200, image.getWidth() / 2, image.getHeight() / 2);
        }

        // Save PDF to temporary file
        File tempFile = File.createTempFile("converted_", ".pdf");
        try (OutputStream outputStream = new FileOutputStream(tempFile)) {
            document.save(outputStream);
        }
        document.close();

        // Return PDF as byte array
        return Files.readAllBytes(tempFile.toPath());
    }
}
