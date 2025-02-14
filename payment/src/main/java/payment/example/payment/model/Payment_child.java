package payment.example.payment.model;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("child")

public class Payment_child extends org.utils.model.Payment{

}