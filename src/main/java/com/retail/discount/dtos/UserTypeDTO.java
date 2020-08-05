package com.retail.discount.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserTypeDTO {
    String id;
    String type;
   // List<UserDTO> userDTOList;
}
