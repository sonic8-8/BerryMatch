package com.gongcha.berrymatch.user;

import com.gongcha.berrymatch.springSecurity.constants.ProviderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query("select u from User u where u.username = :username")
    List<User> findAllByUsername(String username);

    Optional<User> findByNickname(String nickname);

    @Query("select u from User u where u.username = :username and u.providerInfo = :providerInfo")
    Optional<User> findByOAuthInfo(@Param("username") String username,
                                   @Param("providerInfo") ProviderInfo providerInfo);

}
