package com.aliw.pretemoica.repository;

import com.aliw.pretemoica.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // tu peux ajouter des méthodes personnalisées ici
}