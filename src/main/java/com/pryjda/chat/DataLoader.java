package com.pryjda.chat;

import com.pryjda.chat.entity.Role;
import com.pryjda.chat.entity.User;
import com.pryjda.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataLoader implements ApplicationRunner {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        User userNo1 = new User();
        userNo1.setUsername("Raphael");
        userNo1.setPassword(passwordEncoder.encode("1"));
        userNo1.setEnabled(true);

        User userNo2 = new User();
        userNo2.setUsername("John");
        userNo2.setPassword(passwordEncoder.encode("2"));
        userNo2.setEnabled(true);

        Role userRole = new Role();
        userRole.setRole("ROLE_USER");

        Role adminRole = new Role();
        adminRole.setRole("ROLE_ADMIN");

        userNo1.getRoles().add(adminRole);
        userNo1.getRoles().add(userRole);
        userNo2.getRoles().add(userRole);

        if (!isAlreadyCreated(userNo1)) {
            userRepository.save(userNo1);
        }
        if (!isAlreadyCreated(userNo2)) {
            userRepository.save(userNo2);
        }
    }

    private boolean isAlreadyCreated(User user) {
        return userRepository.findAll().stream()
                .filter(item -> item.getUsername().equals(user.getUsername()))
                .findAny()
                .isPresent();
    }
}
