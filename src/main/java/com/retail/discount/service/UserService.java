package com.retail.discount.service;

import com.retail.discount.dtos.UserDTO;
import com.retail.discount.dtos.UserTypeDTO;
import com.retail.discount.entity.User;
import com.retail.discount.entity.UserType;
import com.retail.discount.repository.UserRepository;
import com.retail.discount.repository.UserTypeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The user service
 */
@Log4j2
@Service
public class UserService {
    private final UserRepository repository;
    private final UserTypeRepository userTypeRepository;

    public UserService(UserRepository repository, UserTypeRepository userTypeRepository) {
        this.repository = repository;
        this.userTypeRepository = userTypeRepository;
    }

    /**
     * @param id
     * @return the user dto
     */
    public UserDTO getUser(String id)   {

        log.info("Find user by id {}", id);
        User user=this.findById(id);

        return UserDTO.builder().id(user.getId())
                                .email(user.getEmail())
                                .firstName(user.getFirstName())
                                .lastName(user.getLastName())
                                .phone(user.getPhone())
                                .startDate(user.getStartDate())
                                .userTypeDTO(UserTypeDTO.builder()
                                        .id(user.getUserType().getId())
                                        .type(user.getUserType().getType())
                                        .build())
                                .build();
    }

    /**
     * @return all user DTOs
     */
    public List<UserDTO> getAll(){
        log.info("Getting all Users");
        return repository.findAll().stream().map(user -> {
            return UserDTO.builder().startDate(user.getStartDate())
                    .phone(user.getPhone())
                    .lastName(user.getLastName())
                    .firstName(user.getFirstName())
                    .email(user.getEmail())
                    .id(user.getId())
                    .userTypeDTO(UserTypeDTO.builder()
                            .id(user.getUserType().getId())
                            .type(user.getUserType().getType())
                            .build())
                    .build();
        }).collect(Collectors.toList());
    }

    /**
     * create user dto
     * @param userDTO
     * @param userTypeId
     * @return
     */
    public UserDTO createUser(UserDTO userDTO, String userTypeId){

        log.info("Find user type by id {}" + userTypeId);
        UserType userType = this.findUserTypeById(userTypeId);

        User user = User.builder().id(UUID.randomUUID().toString())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .phone(userDTO.getPhone())
                .startDate(userDTO.getStartDate())
                .userType(userType)
                .build();

        repository.save(user);
        userDTO.setId(user.getId());
        userDTO.setUserTypeDTO(UserTypeDTO.builder()
                .id(userType.getId())
                .type(userType.getType())
                .build());
        return userDTO;
    }

    /**
     * find user by id
     * @param id
     * @return user
     */
    private User findById (String id){
        return  this.repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("USER_DOES_NOT_EXIST " + id));
    }

    /**
     * find user type by id
     * @param id
     * @return userTpe
     */
    private UserType findUserTypeById(String id){
        return this.userTypeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException());
    }
}
