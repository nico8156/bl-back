package com.blogdelivres.bl.repository;

import com.blogdelivres.bl.entity.UserBis;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserBis, Long> {

    Optional<UserBis> findFirstByEmail(String email);
    Optional<UserBis> findByUsername(String username);

}
