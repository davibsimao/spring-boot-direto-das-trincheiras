package academy.devdojo.service;

import academy.devdojo.domain.User;
import academy.devdojo.repository.UserHardCodedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserHardCodedRepository repository;

    public List<User> findAll(String email) {
        return email == null ? repository.findAll() : repository.findByEmail(email);
    }

    public User findByIdOrThrowNotFound(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
    }

    public User save (User user) {
        return repository.save(user);
    }

    public void delete (Long id) {
        User user = findByIdOrThrowNotFound(id);
        repository.delete(user);
    }

    public void update (User userToUpdate) {
        assertUserExists(userToUpdate.getId());
        repository.update(userToUpdate);
    }

    public void assertUserExists(Long id) {
        findByIdOrThrowNotFound(id);
    }
}
