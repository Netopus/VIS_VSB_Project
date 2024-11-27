package cz.vsb.tuo.kel0060.repository;

import Entities.AuthorManga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorMangaRepository extends JpaRepository<AuthorManga, Integer> {
}
