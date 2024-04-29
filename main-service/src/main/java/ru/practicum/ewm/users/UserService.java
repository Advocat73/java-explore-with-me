package ru.practicum.ewm.users;

import ru.practicum.ewm.users.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto addNewUser(UserDto userDto);

    List<UserDto> findUsers(int[] ids, int from, int size);

    void removeUser(int userId);
}
