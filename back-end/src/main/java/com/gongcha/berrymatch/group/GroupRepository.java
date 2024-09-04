package com.gongcha.berrymatch.group;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository  extends JpaRepository<UserGroup,Long> {

    Optional<UserGroup> findByGroupCord(String groupCord);




}
