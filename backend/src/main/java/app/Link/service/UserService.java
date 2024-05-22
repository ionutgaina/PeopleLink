package app.Link.service;

import app.Link.model.User;
import app.Link.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public void saveUser(User user) {
        repository.save(user);
    }

    public void disconnect(User user) {
        var storedUser = repository.findByUsername(user.getUsername()).orElse(null);
        repository.save(storedUser);
    }

    public List<User> findUsers() {
        return repository.findAll();
    }
}
