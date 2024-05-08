package com.example.demo.repository;

import com.example.demo.model.Token;
import com.example.demo.model.UserMob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Query("""
select t from Token t inner join WebAdmin w on t.admin.id = w.id 
where w.id = :adminId and (t.revoked = false or t.revoked=false)
""")
    List<Token> findAllValidTokensByAdmin(Integer adminId);

    Optional<Token> findByToken(String token);
}
