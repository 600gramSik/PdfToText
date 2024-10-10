//package study.imgtopdf.Service;
//
//import lombok.RequiredArgsConstructor;
//import net.sourceforge.tess4j.Tesseract;
//import net.sourceforge.tess4j.TesseractException;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.rendering.PDFRenderer;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import study.imgtopdf.Entity.ExtractedText;
//import study.imgtopdf.Repository.OcrRepository;
//
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class OcrService {
//    private final OcrRepository ocrRepository;
//    public String extractTextFromPdf(String pdfFilePath) throws IOException, TesseractException {
//        //이 파일 경로를 기반으로 File객체를 생성해서 PDF파일에 접근할 수 있도록 한다.
//        File pdfFile = new File(pdfFilePath);
//        //PDF파일을 메모리에 로드한다. pdfFile로 지정된 파일을 읽어서 PDF문서로 로드함으로써 파일에 포함된 페이지나 내용에 접근 가능
//        PDDocument document = PDDocument.load(pdfFile);
//        //PDF문서를 이미지로 렌더링 해준다.
//        PDFRenderer pdfRenderer = new PDFRenderer(document);
//
//        Tesseract tesseract = new Tesseract();
//        //tesseract.setDatapath("/Users/user/Downloads/kor.traineddata");
//        tesseract.setDatapath("/Users/user/Downloads");
//        tesseract.setLanguage("kor");
//        System.setProperty("jna.library.path", "/opt/homebrew/opt/tesseract/lib");
//
//        StringBuilder extractedText = new StringBuilder();
//
//        //PDF 페이지 수 만큼 반복해서 이미지로 변환 후 ORC적용
//        for (int page = 0; page < document.getNumberOfPages(); page++) {
//            //dpi는 해상도
//            BufferedImage image = pdfRenderer.renderImageWithDPI(page, 300);
//            //ORC을 사용하여 이미지에서 텍스트를 추출해준다
//            String text = tesseract.doOCR(image);
//            extractedText.append(text).append("\n");
//        }
//        // 추출된 텍스트를 저장
//        ExtractedText extracted = ExtractedText.of(extractedText.toString());
//        ocrRepository.save(extracted);
//
//        // 텍스트 데이터를 반환
//        return extracted.getText();
//    }
//    //ID로 텍스트 조회
//    public String getTextById(Long id) {
//        return ocrRepository.findById(id)
//                .map(ExtractedText::getText)
//                .orElse("해당 ID의 텍스트를 찾을 수 없습니다.");
//    }
//}

package study.imgtopdf.Service;
import java.util.regex.*;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
@Service
public class OcrService {
    public String extractTextFromPdf(String pdfFilePath) throws IOException, TesseractException {
        // Tesseract 설정
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("/Users/user/Downloads/");
        tesseract.setLanguage("kor");
        System.setProperty("jna.library.path", "/opt/homebrew/opt/tesseract/lib");

        // PDF에서 OCR로 텍스트 추출 (예시: 단일 이미지 처리)
        File imageFile = new File(pdfFilePath);
        String fullText = tesseract.doOCR(imageFile);

        // 정규 표현식을 사용하여 한글만 추출
        String hangulOnly = extractHangul(fullText);

        return hangulOnly;  // 한글만 반환
    }

    // 정규 표현식으로 한글만 추출하는 메서드
    private String extractHangul(String text) {
        // 한글만 추출하는 정규 표현식
        Pattern hangulPattern = Pattern.compile("[가-힣0-9]+");
        Matcher matcher = hangulPattern.matcher(text);

        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            result.append(matcher.group()).append(" ");
        }

        return result.toString().trim();
    }
}
