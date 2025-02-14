package com.project.project.controller;

import com.project.project.model.Product;
import com.project.project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product savedProduct = productRepository.save(product);
        return ResponseEntity.ok(savedProduct);
    }

    @GetMapping
    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        Product updatedProduct = productRepository.save(product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category) {
        // Fetch products by category logic here
        return productRepository.findAll(); // Modify as needed
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String query) {
        // Search logic here
        return productRepository.findAll(); // Modify as needed
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<String> getProductReviews(@PathVariable Long id) {
        // Fetch product reviews logic here
        return ResponseEntity.ok("Product reviews fetched");
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<String> likeProduct(@PathVariable Long id) {
        // Like product logic here
        return ResponseEntity.ok("Product liked");
    }

    @PostMapping("/{id}/unlike")
    public ResponseEntity<String> unlikeProduct(@PathVariable Long id) {
        // Unlike product logic here
        return ResponseEntity.ok("Product unliked");
    }
}