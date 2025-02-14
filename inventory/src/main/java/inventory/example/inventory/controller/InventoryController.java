package inventory.example.inventory.controller;


import inventory.example.inventory.model.Inventory_child;
import inventory.example.inventory.repository.InventoryRepository;
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
    public ResponseEntity<Inventory_child> addInventory(@RequestBody Inventory_child inventory) {
        Inventory_child savedInventory = inventoryRepository.save(inventory);
        return ResponseEntity.ok(savedInventory);
    }

    @GetMapping
    public List<Inventory_child> listInventory() {
        return inventoryRepository.findAll();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Inventory_child> getInventory(@PathVariable Long productId) {
        return inventoryRepository.findById(productId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Inventory_child> updateInventory(@PathVariable Long productId, @RequestBody Inventory_child inventory) {
        inventory.setId(productId);
        Inventory_child updatedInventory = inventoryRepository.save(inventory);
        return ResponseEntity.ok(updatedInventory);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long productId) {
        inventoryRepository.deleteById(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<Inventory_child>> getLowStockItems() {
        // Logic to list low stock items
        return ResponseEntity.ok(inventoryRepository.findAll()); // Modify as needed
    }
}