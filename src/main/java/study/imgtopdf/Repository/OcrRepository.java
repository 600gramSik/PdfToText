package study.imgtopdf.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.imgtopdf.Entity.ExtractedText;

@Repository
public interface OcrRepository extends JpaRepository<ExtractedText, Long> {
}
