package app.Link.service;

import app.Link.dto.user.UserRegisterDto;
import app.Link.model.User;
import app.Link.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository repository;

    public void registerUser(UserRegisterDto userRegisterDto) throws Exception {
        if (repository.findByUsername(userRegisterDto.getUsername()).isPresent()) {
            throw new Exception("Username already exists");
        }
        User user = new User();
        user.setUsername(userRegisterDto.getUsername());
        user.setPassword(userRegisterDto.getPassword());
        repository.save(user);
    }

    public void authenticateUser(String username, String password) throws Exception {
        User user = repository.findByUsername(username).orElse(null);
        if (user != null && user.getPassword().equals(password)) {
            return;
        }
        throw new Exception("Invalid username or password");
    }

    public List<User> findUsers() {
        return repository.findAll();
    }
}
