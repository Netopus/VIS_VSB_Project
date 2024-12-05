package cz.vsb.tuo.kel0060.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Entities.Chapter;
import jakarta.persistence.Entity;

@Entity
public interface ChapterRepository extends JpaRepository<Chapter, Long> {

}
