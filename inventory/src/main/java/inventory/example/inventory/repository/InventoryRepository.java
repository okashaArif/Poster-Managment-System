package inventory.example.inventory.repository;


import inventory.example.inventory.model.Inventory_child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory_child, Long> {
    // Custom query methods can be defined here if needed
}