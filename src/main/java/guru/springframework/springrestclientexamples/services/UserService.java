package guru.springframework.springrestclientexamples.services;

import guru.springframework.springrestclientexamples.domain.User;

import java.util.List;

public interface UserService {
    List<User> getUsers(int limit);
}
