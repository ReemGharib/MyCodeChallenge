package com.retail.discount.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Data
@Entity
@Table(name="usertype")

public class UserType {
    @Id
    @Column(name = "id")
    String id;
    @Column(name = "type")
    String type;

    @OneToMany(mappedBy = "userType", targetEntity = User.class, fetch = FetchType.LAZY)
    @JsonIgnore
    List<User> userList;
}
