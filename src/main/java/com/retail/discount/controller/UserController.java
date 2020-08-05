package com.retail.discount.controller;

import com.retail.discount.dtos.UserDTO;
import com.retail.discount.entity.User;
import com.retail.discount.repository.UserRepository;
import com.retail.discount.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    UserService service;
    @Autowired
    UserRepository userRepository;

    @GetMapping("getUser/{id}")
    public UserDTO getUser(@PathVariable("id")String id)throws Exception{
        return service.getUser(id);
    }

    @GetMapping("getAllUsers")
    public List<UserDTO> getAll(){
        return service.getAll();
    }

    @PostMapping("addUser/{userTypeId}")
    public UserDTO addUser(@RequestBody UserDTO userDTO, @PathVariable("userTypeId") String userTypeId)throws Exception{
        return service.createUser(userDTO, userTypeId);
    }
}
