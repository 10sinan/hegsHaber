package com.sinan.hegsHaber.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sinan.hegsHaber.entity.Friendship;
import com.sinan.hegsHaber.entity.User;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, UUID> {
    List<Friendship> findByRequester(User requester);
    //@Query ile custom sorgular yazabiliriz
    @Query("select f from Friendship f where f.status = :status and (f.requester = :user or f.receiver = :user)")// Kullanici ve durum ile ara
    List<Friendship> findAllByUserAndStatus(@Param("user") User user, @Param("status") Friendship.Status status);//

    @Query("select count(f) > 0 from Friendship f where (f.requester = :u1 and f.receiver = :u2) or (f.requester = :u2 and f.receiver = :u1)")
    boolean existsAnyDirection(@Param("u1") User u1, @Param("u2") User u2);

    List<Friendship> findByReceiver(User receiver);// Receiver a gore ara

    List<Friendship> findByRequesterAndStatus(User requester, Friendship.Status status);// istek atan  ve duruma gore ara

    List<Friendship> findByReceiverAndStatus(User receiver, Friendship.Status status);// istek alan ve duruma gore ara

    boolean existsByRequesterAndReceiver(User requester, User receiver);// istek atan  ve istek alan a gore var mi diye bak
}
