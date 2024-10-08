package study.imgtopdf.Util;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PdfOcrExtractor {
    public String extractTextFromPdf(String pdfFilePath) throws IOException, TesseractException {
        //이 파일 경로를 기반으로 File객체를 생성해서 PDF파일에 접근할 수 있도록 한다.
        File pdfFile = new File(pdfFilePath);
        //PDF파일을 메모리에 로드한다. pdfFile로 지정된 파일을 읽어서 PDF문서로 로드함으로써 파일에 포함된 페이지나 내용에 접근 가능
        PDDocument document = PDDocument.load(pdfFile);
        //PDF문서를 이미지로 렌더링 해준다.
        PDFRenderer pdfRenderer = new PDFRenderer(document);

        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("/Users/user/Downloads");

        StringBuilder extractedText = new StringBuilder();

        //PDF 페이지 수 만큼 반복해서 이미지로 변환 후 ORC적용
        for (int page = 0; page < document.getNumberOfPages(); page++) {
            //dpi는 해상도
            BufferedImage image = pdfRenderer.renderImageWithDPI(page, 300);
            //ORC을 사용하여 이미지에서 텍스트를 추출해준다
            String text = tesseract.doOCR(image);
            extractedText.append(text).append("\n");
        }
        document.close();
        return extractedText.toString();
    }
}