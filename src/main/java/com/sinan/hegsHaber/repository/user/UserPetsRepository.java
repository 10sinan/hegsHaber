package com.sinan.hegsHaber.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinan.hegsHaber.entity.user.UserPets;

import java.util.List;
import java.util.UUID;

public interface UserPetsRepository extends JpaRepository<UserPets, Long> {
    List<UserPets> findByUserUuid(UUID userUuid);

    List<UserPets> findByUserUuidAndIsActive(UUID userUuid, boolean isActive);
}
