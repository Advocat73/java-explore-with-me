package ru.practicum.ewm.users;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.users.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {
    public static User fromUserDto(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        return user;
    }

    public static UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto userDto = new UserDto();
        if (user.getId() != null) {
            userDto.setId(user.getId());
        }
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    public static List<UserDto> toUserDtoList(List<User> users) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : users) {
            userDtoList.add(toUserDto(user));
        }
        return userDtoList;
    }
}
