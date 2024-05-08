package com.example.demo.repository;

import com.example.demo.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserTokenRepository  extends JpaRepository<Token, Integer> {
    @Query("""
select t from Token t join t.user u 
where u.id = :userId and (t.revoked = false or t.revoked = false)
""")
    List<Token> findAllValidTokensByUser(Integer userId);

}
