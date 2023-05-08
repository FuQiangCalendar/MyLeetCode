package org.example.structure.office;

import com.aspose.words.PdfSaveOptions;
import com.aspose.words.SaveFormat;
import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import org.docx4j.Docx4J;
import org.docx4j.convert.out.FOSettings;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.fonts.Mapper;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Name Word2Pdf
 * @Description
 * @Author qfu1
 * @Date 2023-02-13
 */
public class Word2Pdf {
    private static final Logger logger = LoggerFactory.getLogger(Word2Pdf.class);

    public static void main(String[] args) {
        String wordPath = "C:\\Users\\qfu1\\Desktop\\处置尽调取处置公告错误数据问题描述.docx";
        /*String pdfPath = "C:\\Users\\qfu1\\Desktop\\处置尽调取处置公告错误数据问题描述.pdf";
        word2Pdf(wordPath, pdfPath);*/
        String documents4jWordToPdfPath = "C:\\Users\\qfu1\\Desktop\\处置尽调取处置公告错误数据问题描述documents4jWordToPdfPath.pdf";
        documents4jWordToPdf(wordPath, documents4jWordToPdfPath);
        /*String docx4jWordToPdfPath = "C:\\Users\\qfu1\\Desktop\\处置尽调取处置公告错误数据问题描述docx4jWordToPdfPath.pdf";
        docx4jWordToPdf(wordPath, docx4jWordToPdfPath);*/
    }

    public static void word2Pdf (String wordPath, String pdfPath) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(pdfPath)) {
            com.aspose.words.Document doc = new com.aspose.words.Document(wordPath);
//            FileOutputStream fileOutputStream = new FileOutputStream(pdfPath);
            PdfSaveOptions options = new PdfSaveOptions();
            options.setUpdateFields(false);
            options.setSaveFormat(SaveFormat.PDF);
            doc.save(fileOutputStream, options);

//            fileOutputStream.close();

					/*FileOutputStream fileOutputStream1 = new FileOutputStream(pdfRealNamePath);
					doc.save(fileOutputStream1, SaveFormat.PDF);
					fileOutputStream1.close();*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过docx4j 实现word转pdf
     *
     * @param sourcePath 源文件地址 如 /root/example.doc
     * @param targetPath 目标文件地址 如 /root/example.pdf
     */
    public static void docx4jWordToPdf(String sourcePath, String targetPath) {
        try (OutputStream os = new FileOutputStream(targetPath)){
            WordprocessingMLPackage pkg = Docx4J.load(new File(sourcePath));
            Mapper fontMapper = new IdentityPlusMapper();
            fontMapper.put("隶书", PhysicalFonts.get("LiSu"));
            fontMapper.put("宋体", PhysicalFonts.get("SimSun"));
            fontMapper.put("微软雅黑", PhysicalFonts.get("Microsoft Yahei"));
            fontMapper.put("黑体", PhysicalFonts.get("SimHei"));
            fontMapper.put("楷体", PhysicalFonts.get("KaiTi"));
            fontMapper.put("新宋体", PhysicalFonts.get("NSimSun"));
            fontMapper.put("华文行楷", PhysicalFonts.get("STXingkai"));
            fontMapper.put("华文仿宋", PhysicalFonts.get("STFangsong"));
            fontMapper.put("仿宋", PhysicalFonts.get("FangSong"));
            fontMapper.put("幼圆", PhysicalFonts.get("YouYuan"));
            fontMapper.put("华文宋体", PhysicalFonts.get("STSong"));
            fontMapper.put("华文中宋", PhysicalFonts.get("STZhongsong"));
            fontMapper.put("等线", PhysicalFonts.get("SimSun"));
            fontMapper.put("等线 Light", PhysicalFonts.get("SimSun"));
            fontMapper.put("华文琥珀", PhysicalFonts.get("STHupo"));
            fontMapper.put("华文隶书", PhysicalFonts.get("STLiti"));
            fontMapper.put("华文新魏", PhysicalFonts.get("STXinwei"));
            fontMapper.put("华文彩云", PhysicalFonts.get("STCaiyun"));
            fontMapper.put("方正姚体", PhysicalFonts.get("FZYaoti"));
            fontMapper.put("方正舒体", PhysicalFonts.get("FZShuTi"));
            fontMapper.put("华文细黑", PhysicalFonts.get("STXihei"));
            fontMapper.put("宋体扩展", PhysicalFonts.get("simsun-extB"));
            fontMapper.put("仿宋_GB2312", PhysicalFonts.get("FangSong_GB2312"));
            fontMapper.put("新細明體", PhysicalFonts.get("SimSun"));
            //解决宋体（正文）和宋体（标题）的乱码问题
            PhysicalFonts.put("PMingLiU", PhysicalFonts.get("SimSun"));
            PhysicalFonts.put("新細明體", PhysicalFonts.get("SimSun"));
            pkg.setFontMapper(fontMapper);

            Docx4J.toPDF(pkg, os);
            /*FOSettings foSettings = Docx4J.createFOSettings();
            foSettings.setWmlPackage(pkg);
            Docx4J.toFO(foSettings, os, Docx4J.FLAG_EXPORT_PREFER_XSL);*/
        } catch (Exception e) {
            logger.error("[docx4j] word转pdf失败:{}", e.toString());
        }
    }


    /**
     * 通过documents4j 实现word转pdf
     *
     * @param sourcePath 源文件地址 如 /root/example.doc
     * @param targetPath 目标文件地址 如 /root/example.pdf
     */
    public static void documents4jWordToPdf(String sourcePath, String targetPath) {
        File inputWord = new File(sourcePath);
        File outputFile = new File(targetPath);
        try  {
            InputStream docxInputStream = new FileInputStream(inputWord);
            OutputStream outputStream = new FileOutputStream(outputFile);
            IConverter converter = LocalConverter.builder().build();
            converter.convert(docxInputStream)
                    .as(DocumentType.DOCX)
                    .to(outputStream)
                    .as(DocumentType.PDF).execute();
            outputStream.close();
        } catch (Exception e) {
            logger.error("[documents4J] word转pdf失败:{}", e.toString());
        }

    }
}
