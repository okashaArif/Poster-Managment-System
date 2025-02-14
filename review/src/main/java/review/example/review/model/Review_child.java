package review.example.review.model;


import jakarta.persistence.*;


@Entity
@DiscriminatorValue("child")
public class Review_child extends org.utils.model.Review{



}