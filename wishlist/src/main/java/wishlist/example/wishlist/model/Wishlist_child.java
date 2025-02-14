package wishlist.example.wishlist.model;

import jakarta.persistence.*;


@Entity
@DiscriminatorValue("child")
public class Wishlist_child extends org.utils.model.Wishlist{


}
