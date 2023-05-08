//package org.example.structure.chatgpt;
//
//import java.io.*;
//import org.apache.poi.xwpf.converter.pdf.*;
//import org.apache.poi.xwpf.usermodel.*;
//import com.itextpdf.text.*;
//import com.itextpdf.text.pdf.*;
//public class WordToPdfConverter {
//    public static void main(String[] args) throws Exception {
//        // Word文件路径
//        String wordFile = "path/to/word/file.docx";
//        // PDF文件路径
//        String pdfFile = "path/to/pdf/file.pdf";
//        // 创建Word文档对象
//        FileInputStream fis = new FileInputStream(wordFile);
//        XWPFDocument document = new XWPFDocument(fis);
//        // 创建PDF文档对象
//        OutputStream out = new FileOutputStream(pdfFile);
//        PdfWriter writer = PdfWriter.getInstance(document, out);
//        // 设置字体
//        writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
//        HeaderFooter header = new HeaderFooter(new Phrase("Header"), false);
//        HeaderFooter footer = new HeaderFooter(new Phrase("Footer"), false);
//        document.setHeader(header);
//        document.setFooter(footer);
//        XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider();
//        fontProvider.register("path/to/font.ttf");
//        // 创建PDF转换器
//        PdfOptions options = PDFViaITextOptions.create().fontProvider(fontProvider).build();
//        PdfConverter.getInstance().convert(document, out, options);
//        // 关闭输入输出流
//        fis.close();
//        out.close();
//        System.out.println("Word文件转换为PDF文件成功！");
//    }
//}