package org.peerstock.server.converter;

import org.peerstock.server.domain.User;
import org.peerstock.server.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserConverter extends AbstractConverter implements Converter<User, UserDto> {
    @Override
    public UserDto toDto(User user) {
        return getModelMapper().map(user, UserDto.class);
    }

    @Override
    public User toDomain(UserDto userDto) {
        return getModelMapper().map(userDto, User.class);
    }
}
