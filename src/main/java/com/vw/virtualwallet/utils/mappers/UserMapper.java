package com.vw.virtualwallet.utils.mappers;

import com.vw.virtualwallet.api.models.requests.UserRequest;
import com.vw.virtualwallet.api.models.responses.UserResponse;
import com.vw.virtualwallet.persistence.entities.UserApp;

import java.util.stream.Collectors;

/**
 * UserMapper class provides methods to map between UserRequest, UserResponse, and UserApp entities.
 * It is used to convert data between different layers of the application.
 *
 * @author caito
 *
 */
public class UserMapper {

    /**
     * Maps a UserRequest to a UserApp entity.
     *
     * @param request the UserRequest to map
     * @return a UserApp entity with the data from the request
     */
    public static UserApp mapToEntity(UserRequest request) {
        return UserApp.builder()
                .name(request.getNane())
                .surname(request.getSurname())
                .address(request.getAddress())
                .dni(request.getDni())
                .telephone(request.getTelephone())
                .email(request.getEmail())
                .build();
    }

    /**
     * Maps a UserApp entity to a UserResponse DTO.
     *
     * @param user the UserApp entity to map
     * @return a UserResponse DTO with the data from the user entity
     */
    public static UserResponse mapToDto(UserApp user) {
        return UserResponse.builder()
                .id(user.getId())
                .nane(user.getName())
                .surname(user.getSurname())
                .address(user.getAddress())
                .dni(user.getDni())
                .telephone(user.getTelephone())
                .email(user.getEmail())
                .roles(user.getRoles().stream().map(RoleMapper::mapToDto).collect(Collectors.toSet()))
                .accontNumber(user.getAccount().getAccountNumber())
                .alias(user.getAccount().getAlias())
                .balance(user.getAccount().getBalance())
                .build();
    }
}
