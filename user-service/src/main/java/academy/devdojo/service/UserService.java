package academy.devdojo.service;

import academy.devdojo.domain.User;
import academy.devdojo.exception.EmailAlreadyExistsException;
import academy.devdojo.exception.NotFoundException;
import academy.devdojo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

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
        assertEmailDoesNotExists(user.getEmail());
        return repository.save(user);
    }

    public void delete(Long id) {
        User user = findByIdOrThrowNotFound(id);
        repository.delete(user);
    }

    public void update(User userToUpdate) {
        assertUserExists(userToUpdate.getId());
        findByEmailAndIdNot(userToUpdate.getEmail(), userToUpdate.getId());
        repository.save(userToUpdate);
    }

    public void assertUserExists(Long id) {
        findByIdOrThrowNotFound(id);
    }

    public void assertEmailDoesNotExists(String email) {
        if (repository.existsByEmail(email)) throwEmailExistsException(email);
    }

    public void findByEmailAndIdNot(String email, Long id) {
        repository.findByEmailAndIdNot(email, id)
                .ifPresent(user -> throwEmailExistsException(user.getEmail()));
    }

    private void throwEmailExistsException(String email) {
        throw new EmailAlreadyExistsException("E-mail %s already exists".formatted(email));
    }

}
