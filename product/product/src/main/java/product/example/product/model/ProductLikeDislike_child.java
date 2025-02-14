package product.example.product.model;
import jakarta.persistence.*;
import lombok.*;
import org.utils.model.ProductLikeDislike;
import product.example.product.model.Product_child;
import org.utils.model.User;

@Entity
@DiscriminatorValue("child")
public class ProductLikeDislike_child extends org.utils.model.ProductLikeDislike{


    public static boolean isLike(ProductLikeDislike productLikeDislike) {
       return productLikeDislike.getLike();
    }
}
