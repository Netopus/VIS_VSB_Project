package cz.vsb.tuo.kel0060.repository;


import Entities.Author;
import jakarta.persistence.Entity;

import org.springframework.data.jpa.repository.JpaRepository;

@Entity
public interface AuthorRepository extends JpaRepository<Author, Long> {

}