package com.zehraarslan.userservice.mapper;

import com.zehraarslan.userservice.dto.UserDto;
import com.zehraarslan.userservice.entity.User;
import com.zehraarslan.userservice.request.UserSaveRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User convertToUser(UserSaveRequest request);
    UserDto convertToUserDto(User user);

    List<UserDto> convertToUserDtos(List<User> users);
}
