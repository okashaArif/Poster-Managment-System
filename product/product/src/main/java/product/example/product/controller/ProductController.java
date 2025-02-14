package product.example.product.controller;

import jakarta.transaction.Transactional;
import org.utils.model.Product;
import product.example.product.model.Product_child;
import product.example.product.repository.ProductRepository;
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

    @Transactional
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        System.out.println("Received Product: " + product);
        Product savedProduct = productRepository.save(product);
        System.out.println("Saved Product: " + savedProduct);
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


}