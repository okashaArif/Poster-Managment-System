package com.project.project.repository;

import com.project.project.model.ProductLikeDislike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductLikeDislikeRepository extends JpaRepository<ProductLikeDislike, Long> {
    Optional<Object> findByUserIdAndProductId(Long userId, Long productId);

    List<ProductLikeDislike> findByProductId(Long productId);
    // Custom query methods can be defined here if needed
}