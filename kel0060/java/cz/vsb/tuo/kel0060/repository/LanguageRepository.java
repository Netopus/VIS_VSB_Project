package cz.vsb.tuo.kel0060.repository;

import Entities.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {
    // The `findById` method is already provided by JpaRepository.
    // However, we can add custom methods if needed.

    // Optionally add custom queries if needed:
    // Example: List<Language> findByName(String name);
}
