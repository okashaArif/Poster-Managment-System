package user.example.user.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;



@Entity
@DiscriminatorValue("child")
@JsonIgnoreProperties({"orders", "wishlist"})
public class User_child extends org.utils.model.User {

}
