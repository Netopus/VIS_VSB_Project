package cz.vsb.tuo.kel0060.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
