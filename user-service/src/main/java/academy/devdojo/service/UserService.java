package academy.devdojo.service;

import academy.devdojo.domain.User;
import academy.devdojo.exception.NotFoundException;
import academy.devdojo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public List<User> findAll(String email) {
        return email == null ? repository.findAll() : repository.findByEmail(email);
    }

    public User findByIdOrThrowNotFound(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("user not found"));
    }

    public User save(User user) {
        return repository.save(user);
    }

    public void delete(Long id) {
        User user = findByIdOrThrowNotFound(id);
        repository.delete(user);
    }

    public void update(User userToUpdate) {
        assertUserExists(userToUpdate.getId());
        repository.save(userToUpdate);
    }

    public void assertUserExists(Long id) {
        findByIdOrThrowNotFound(id);
    }
}
