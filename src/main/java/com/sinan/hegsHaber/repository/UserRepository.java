package com.sinan.hegsHaber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sinan.hegsHaber.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Kullanicı adi ile kullaniciyi bulma
    Optional<User> findByUsername(String username);// Kullanıcı adı ile kullanıcıyı bul sprşng oto anlıyor

    // Kullanicı adinin var olup olmadigini kontrol etme
    boolean existsByUsername(String username);
}