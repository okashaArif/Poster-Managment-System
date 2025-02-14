package com.project.project.controller;

import com.project.project.model.Inventory;
import com.project.project.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryRepository inventoryRepository;

    @PostMapping
    public ResponseEntity<Inventory> addInventory(@RequestBody Inventory inventory) {
        Inventory savedInventory = inventoryRepository.save(inventory);
        return ResponseEntity.ok(savedInventory);
    }

    @GetMapping
    public List<Inventory> listInventory() {
        return inventoryRepository.findAll();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Inventory> getInventory(@PathVariable Long productId) {
        return inventoryRepository.findById(productId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable Long productId, @RequestBody Inventory inventory) {
        inventory.setId(productId);
        Inventory updatedInventory = inventoryRepository.save(inventory);
        return ResponseEntity.ok(updatedInventory);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long productId) {
        inventoryRepository.deleteById(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<Inventory>> getLowStockItems() {
        // Logic to list low stock items
        return ResponseEntity.ok(inventoryRepository.findAll()); // Modify as needed
    }
}