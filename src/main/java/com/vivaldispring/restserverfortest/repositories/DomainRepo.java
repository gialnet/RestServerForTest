package com.vivaldispring.restserverfortest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DomainRepo extends JpaRepository<DomainsTB,String> {

    Optional<DomainsTB> findById(String id);
}
