package ru.practicum.ewm.users;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByIdIn(int[] users);

    List<User> findAllByIdIn(int[] users, PageRequest pageRequest);
}
