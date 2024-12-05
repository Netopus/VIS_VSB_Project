package cz.vsb.tuo.kel0060.repository;

import Entities.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {
}