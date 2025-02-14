package user.example.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
    private Long userId;
    private String shippingAddress;
}
