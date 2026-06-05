package com.novapay.auth_service.mapper;


import com.novapay.auth_service.dto.request.RegisterRequest;
import com.novapay.auth_service.dto.request.UserRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    UserRequest mapToUserRequest(RegisterRequest registerRequest);
}
