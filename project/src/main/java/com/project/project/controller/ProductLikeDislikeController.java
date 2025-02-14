package com.project.project.controller;

import com.project.project.model.ProductLikeDislike;
import com.project.project.repository.ProductLikeDislikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/product-likes-dislikes")
public class ProductLikeDislikeController {

    @Autowired
    private ProductLikeDislikeRepository productLikeDislikeRepository;

    @PostMapping
    public ResponseEntity<ProductLikeDislike> addLikeDislike(@RequestBody ProductLikeDislike productLikeDislike) {
        ProductLikeDislike savedRecord = productLikeDislikeRepository.save(productLikeDislike);
        return ResponseEntity.ok(savedRecord);
    }

    @GetMapping("/user/{userId}/product/{productId}")
    public ResponseEntity<Object> getUserLikeDislikeForProduct(@PathVariable Long userId, @PathVariable Long productId) {
        return productLikeDislikeRepository.findByUserIdAndProductId(userId, productId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductLikeDislike> updateLikeDislike(@PathVariable Long id, @RequestBody ProductLikeDislike productLikeDislike) {
        productLikeDislike.setId(id);
        ProductLikeDislike updatedRecord = productLikeDislikeRepository.save(productLikeDislike);
        return ResponseEntity.ok(updatedRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLikeDislike(@PathVariable Long id) {
        productLikeDislikeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductLikeDislike>> getLikesDislikesForProduct(@PathVariable Long productId) {
        List<ProductLikeDislike> records = productLikeDislikeRepository.findByProductId(productId);
        return ResponseEntity.ok(records);
    }
}