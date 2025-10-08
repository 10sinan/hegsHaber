package com.sinan.hegsHaber.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sinan.hegsHaber.entity.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    // Kullanicı adi ile kullaniciyi bulma
    Optional<User> findByUsername(String username);// Kullanıcı adı ile kullanıcıyı bul sprşng oto anlıyor

    // Kullanicı adinin var olup olmadigini kontrol etme
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    List<User> findByUsernameContainingIgnoreCase(String username);// kullanıcı adına göre ara büyük küçük dikkate alma

    User findByEmail(String email);
}