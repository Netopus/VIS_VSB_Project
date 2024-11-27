package cz.vsb.tuo.kel0060.repository;

import Entities.MangaCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MangaCategoryRepository extends JpaRepository<MangaCategory, Integer> {
}
