package study.imgtopdf.Controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.web.bind.annotation.*;
import study.imgtopdf.Service.OcrService;

import java.io.IOException;

@RestController
@RequestMapping("/api/ocr")
@RequiredArgsConstructor
public class OcrController {
    final private OcrService ocrService;

    @PostMapping("/extract")
    public String extractText(@RequestParam String pdfFilePath) {
        try {
            return ocrService.extractTextFromPdf(pdfFilePath);
        } catch (IOException | TesseractException e) {
            e.printStackTrace();
            return "Error";
        }
    }

//    @GetMapping("/text/{id}")
//    public String getText(@PathVariable Long id) {
//        return ocrService.getTextById(id);
//    }

}
