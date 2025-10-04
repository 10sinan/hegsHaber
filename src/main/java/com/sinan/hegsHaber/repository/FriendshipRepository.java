package com.sinan.hegsHaber.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sinan.hegsHaber.entity.Friendship;
import com.sinan.hegsHaber.entity.User;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    List<Friendship> findByFollower(User follower);

    @Query("select count(f) > 0 from Friendship f where (f.follower = :u1 and f.following = :u2) or (f.follower = :u2 and f.following = :u1)")
    boolean existsAnyDirection(@Param("u1") User u1, @Param("u2") User u2);

    List<Friendship> findByFollowing(User following);// Takip edenleri listelemek için ekledim

    boolean existsByFollowerAndFollowing(User follower, User following);// Takip ilişkisi var mı kontrolü için ekledim

    Friendship findByFollowerAndFollowing(User follower, User following);// Takip ilişkisini bulmak için ekledim
}
