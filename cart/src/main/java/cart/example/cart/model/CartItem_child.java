package cart.example.cart.model;

import jakarta.persistence.*;


@Entity
@DiscriminatorValue("child")

public class CartItem_child  extends org.utils.model.CartItem {


}

