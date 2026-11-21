package com.coverter.intelliconverter.util;

import net.sourceforge.tess4j.Tesseract;

import java.io.File;

public class TesseractUtil {

    private static final String TESSDATA_PATH = "src/main/resources/tessdata";

    public static String performOCR(File imageFile) throws Exception {
        Tesseract tesseract = new Tesseract();

        // Set Tesseract data path
        tesseract.setDatapath(TESSDATA_PATH);
        tesseract.setLanguage("eng");

        // Perform OCR
        return tesseract.doOCR(imageFile);
    }
}
