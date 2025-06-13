package com.vw.virtualwallet.services.impl;

import com.vw.virtualwallet.api.exceptions.customs.BadRequestException;
import com.vw.virtualwallet.api.exceptions.customs.NotFoundException;
import com.vw.virtualwallet.api.models.requests.UserRequest;
import com.vw.virtualwallet.api.models.responses.UserResponse;
import com.vw.virtualwallet.persistence.entities.Account;
import com.vw.virtualwallet.persistence.entities.Role;
import com.vw.virtualwallet.persistence.entities.UserApp;
import com.vw.virtualwallet.persistence.repositories.AccountRepository;
import com.vw.virtualwallet.persistence.repositories.RoleRepository;
import com.vw.virtualwallet.persistence.repositories.UserRepository;
import com.vw.virtualwallet.services.contracts.UserService;
import com.vw.virtualwallet.services.helpers.ValidationHelper;
import com.vw.virtualwallet.utils.enums.RoleName;
import com.vw.virtualwallet.utils.logs.WriteLog;
import com.vw.virtualwallet.utils.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * UserServiceImpl provides the implementation of the UserService interface.
 * It handles user-related operations such as creating a user and validating user data.
 *
 * @author caito
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    /**
     * Creates a new user based on the provided UserRequest.
     * It validates the user data and throws BadRequestException if any validation fails.
     *
     * @param request the UserRequest containing user data
     */
    @Override
    @Transactional
    public void createUser(UserRequest request) {
        log.info(WriteLog.logInfo("CreateUser service"));
        validateUser(request);
        var user = UserMapper.mapToEntity(request);
        Set<Role> roles = new HashSet<>();
        var role = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new NotFoundException("Role not found"));
        roles.add(role);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        var newUser = userRepository.save(user);
        log.info(WriteLog.logInfo("Generate account.. for user " + newUser.getId()));
        var account = Account.builder()
                .user(newUser)
                .accountNumber(UUID.randomUUID().toString())
                .alias(user.getFullName().trim().replace(" ","."))
                .balance(BigDecimal.ZERO)
                .build();
        accountRepository.save(account);
    }


    /**
     * Retrieves a user by their email.
     * If the user is not found, it throws NotFoundException.
     *
     * @param email the email of the user to retrieve
     * @return UserResponse containing user data
     */
    @Override
    @Transactional(readOnly = true)
    public UserResponse getByEmail(String email) {
        log.info(WriteLog.logInfo("getByEmail service"));
        return UserMapper.mapToDto(userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + email)));
    }

    @Override
    public UserResponse getUser(Authentication authentication) {
        log.info(WriteLog.logInfo("getUser service"));
        UserApp user = (UserApp) authentication.getPrincipal();
        return UserMapper.mapToDto(userRepository.findById(user.getId())
                .orElseThrow(() ->new NotFoundException("user not found with id: " + user.getId())));
    }

    /**
     * Retrieves a paginated list of users.
     * It returns a Page of UserResponse objects.
     *
     * @param page the page number to retrieve
     * @param size the number of users per page
     * @return Page<UserResponse> containing user data
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> getUsers(int page, int size) {
        log.info(WriteLog.logInfo("getUsers service"));
        PageRequest pr = PageRequest.of(page, size);
        return userRepository.findAll(pr).map(UserMapper::mapToDto);
    }

    /**
     * Updates an existing user based on the provided email and UserRequest.
     * It validates the user data and throws BadRequestException if any validation fails.
     *
     * @param email   the email of the user to update
     * @param request the UserRequest containing updated user data
     * @return UserResponse containing updated user data
     */
    @Override
    @Transactional
    public UserResponse updateUser(String email, UserRequest request, Authentication authentication) {
        log.info(WriteLog.logInfo("updateUser service"));
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + email));
        var principal = (UserApp) authentication.getPrincipal();
        //Filter A user cannot update the data of another user
        if (!user.getId().equals(principal.getId())) {
            throw new BadRequestException(List.of("You can only update your own user data"));
        }

        if (request.getNane() != null && !request.getNane().isEmpty()) {
            user.setName(request.getNane());
        }
        if (request.getSurname() != null && !request.getSurname().isEmpty()) {
            user.setSurname(request.getSurname());
        }
        if (request.getAddress() != null && !request.getAddress().isEmpty()) {
            user.setAddress(request.getAddress());
        }
        if (request.getDni() != null && !request.getDni().isEmpty()) {
            if (!userRepository.existsByDniAndIdNot(user.getDni(), user.getId())) {
                throw new BadRequestException(List.of("DNI already exists"));
            }
            user.setDni(request.getDni());
        }
        if (request.getTelephone() != null && !request.getTelephone().isEmpty()) {
            user.setTelephone(request.getTelephone());
        }
        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            if (!userRepository.existsByEmailAndIdNot(user.getEmail(), user.getId())) {
                throw new BadRequestException(List.of("Email already exists"));
            }
            if (!ValidationHelper.validateEmail(request.getEmail())) {
                throw new BadRequestException(List.of("Invalid Email format"));
            }
            user.setEmail(request.getEmail());
        }
        return UserMapper.mapToDto(userRepository.save(user));
    }

    /**
     * Deletes a user by their email.
     * @param  email
     * return void
     */
    @Override
    public void deleteUser(String email) {
        log.info(WriteLog.logInfo("deleteUser service"));
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + email));
        userRepository.delete(user);
    }

    /**
     * Validates the user data from the UserRequest.
     * It checks for required fields and formats, and throws BadRequestException if any validation fails.
     *
     * @param request the UserRequest containing user data
     */
    private void validateUser(UserRequest request) {
        log.info(WriteLog.logInfo("validateUser..."));
        List<String> errors = new ArrayList<>();
        //validate name
        if (request.getNane() == null || request.getNane().isEmpty()) {
            errors.add("Name is required");
        }
        //validate surname
        if (request.getSurname() == null || request.getSurname().isEmpty()) {
            errors.add("Surname is required");
        }
        //validate address
        if (request.getAddress() == null || request.getAddress().isEmpty()) {
            errors.add("Address is required");
        }
        //validate dni
        if (request.getDni() == null || request.getDni().isEmpty()) {
            errors.add("DNI is required");
        } else if (userRepository.existsByDni(request.getDni())) {
            errors.add("DNI already exists");
        }
        //validate email
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            errors.add("Email is required");
        } else if (userRepository.existsByEmail(request.getEmail())) {
            errors.add("Email already exists");
        } else if (!ValidationHelper.validateEmail(request.getEmail())) {
            errors.add("Invalid Email format");
        }
        //validate password
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            errors.add("Password is required");
        } else if (!ValidationHelper.validatePassword(request.getPassword())) {
            errors.add("Invalid Password format");
        } else if (!request.getPassword().equals(request.getConfirmPassword())) {
            errors.add("Passwords do not match");
        }
        if (!errors.isEmpty()) {
            throw new BadRequestException(errors);
        }
    }
}
