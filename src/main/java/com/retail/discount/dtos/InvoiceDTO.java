package com.retail.discount.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Data
public class InvoiceDTO {
    private String id;
    private double totalPrice;
    private String priceAfterDiscount;
    private UserDTO userDTO;


}
