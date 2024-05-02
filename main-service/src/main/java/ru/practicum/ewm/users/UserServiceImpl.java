package ru.practicum.ewm.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.users.dto.UserDto;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDto addNewUser(UserDto userDto) {
        log.info("USER_СЕРВИС: Отправлен запрос на добавление нового пользователя - {}", userDto.getName());
        return UserMapper.toUserDto(userRepository.save(UserMapper.fromUserDto(userDto)));
    }

    @Override
    public List<UserDto> findUsers(int[] ids, int from, int size) {
        log.info("USER_СЕРВИС: Отправлен запрос на поиск пользователей с Id - {}", ids);
        List<User> userList = (ids != null) ? userRepository.findAllByIdIn(ids, createPageRequest(from, size)) :
                userRepository.findAll(createPageRequest(from, size)).getContent();
        return UserMapper.toUserDtoList(userList);
    }

    @Override
    public void removeUser(int userId) {
        log.info("USER_СЕРВИС: Отправлен запрос на удаление пользователя с Id - {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Нет пользователя с ID: " + userId));
        userRepository.delete(user);
    }

    private PageRequest createPageRequest(int from, int size) {
        Sort sortBy = Sort.by("Id").ascending();
        int page = (from < size) ? 0 : from / size;
        return PageRequest.of(page, size, sortBy);
    }
}
