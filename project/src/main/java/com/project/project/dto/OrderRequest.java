package com.project.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
    private Long userId;
    private String shippingAddress;
}
