package com.example.demo.Repositories;


import com.example.demo.Entities.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio,Long> {
    Optional<Portfolio> findByUserId(Long id);
}
