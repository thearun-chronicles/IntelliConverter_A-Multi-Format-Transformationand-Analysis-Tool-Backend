package com.coverter.intelliconverter.service;


	import java.io.File;
	import java.io.IOException;
	import java.util.List;

	public interface PdfToServices {

		public List<String> convertPdfToImages(File pdfFile) throws IOException;
		public String extractText(File pdfFile) throws IOException ;
		
	}