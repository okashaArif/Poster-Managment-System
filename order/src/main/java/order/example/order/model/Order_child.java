package order.example.order.model;

import jakarta.persistence.*;





@Entity
@DiscriminatorValue("child")

public class Order_child extends org.utils.model.Order {

}