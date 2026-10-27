package com.schemeai;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface SavedSchemeRepository extends JpaRepository<SavedScheme,Long> {
 List<SavedScheme> findByUserId(Long userId); Optional<SavedScheme> findByUserIdAndSchemeId(Long userId,Long schemeId);
 void deleteByUserIdAndSchemeId(Long userId,Long schemeId);
}