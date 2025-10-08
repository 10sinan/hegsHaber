package com.sinan.hegsHaber.repository.social;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinan.hegsHaber.entity.social.Pet_types;

public interface PetRepository extends JpaRepository<Pet_types, Long> {
}
