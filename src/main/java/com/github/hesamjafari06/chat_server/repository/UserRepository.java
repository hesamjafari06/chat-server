package com.github.hesamjafari06.chat_server.repository;

import com.github.hesamjafari06.chat_server.dto.UserSearchDto;
import com.github.hesamjafari06.chat_server.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsername(String username);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByUserId(String userId);

    @Query("""
    SELECT new com.github.hesamjafari06.chat_server.dto.UserSearchDto(u.userId, u.username)
    FROM UserEntity u
    WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :username, '%'))
""")
    List<UserSearchDto> findByUsernameContainingIgnoreCase(@Param("username") String username);
}
