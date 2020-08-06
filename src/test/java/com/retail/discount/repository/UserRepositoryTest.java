package com.retail.discount.repository;

import com.retail.discount.domain.User;
import com.retail.discount.domain.UserType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenSaveUserOk(){

        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .email("myEmail")
                .firstName("Nana")
                .lastName("Nana")
                .phone("00303003")
                .userType(UserType.builder()
                .id("01d8829e-f887-4f1c-8d33-471863eddc7a")
                .build())
                .build();

        user = userRepository.save(user);
        Optional<User> optionalUser = userRepository.findById(user.getId());

        Assert.assertNotNull(optionalUser.isPresent());
        Assert.assertEquals(user.getFirstName(), optionalUser.get().getFirstName());
        Assert.assertEquals(user.getEmail(), optionalUser.get().getEmail());
    }
}
