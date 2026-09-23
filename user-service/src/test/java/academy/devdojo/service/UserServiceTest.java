package academy.devdojo.service;

import academy.devdojo.commons.UserUtils;
import academy.devdojo.domain.User;
import academy.devdojo.repository.UserHardCodedRepository;
import academy.devdojo.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    @InjectMocks
    private UserService service;
    @Mock
    private UserHardCodedRepository repository;
    @Mock
    private UserRepository userRepository;
    private List<User> userList;
    @InjectMocks
    private UserUtils userUtils;
    @BeforeEach
    void init() {
        userList = userUtils.newUserList();
    }
    @Test
    @DisplayName("FindAll returns a list with all users when argument is null")
    @Order(1)
    void findAll_ReturnsAllUsers_WhenArgumentIsNull() {
        when(userRepository.findAll()).thenReturn(userList);

        var users = service.findAll(null);

        Assertions.assertThat(users)
                .isNotNull()
                .hasSameElementsAs(userList);
    }

    @Test
    @DisplayName("FindAll returns list with found user when email exists")
    @Order(2)
    void findByEmail_ReturnsFoundUserInList_WhenEmailIsFound() {
        var user = userList.getFirst();
        var expectedUsersFound = singletonList(user);

        when(repository.findByEmail(user.getEmail()))
                .thenReturn(expectedUsersFound);

        var usersFound = service.findAll(user.getEmail());

        Assertions.assertThat(usersFound)
                .containsAll(expectedUsersFound);
    }

    @Test
    @DisplayName("FindAll returns empty list when email is not found")
    @Order(3)
    void findByEmail_ReturnsEmptyList_WhenEmailIsNotFound() {
        var email = "not-found@gmail.com";

        when(repository.findByEmail(email))
                .thenReturn(emptyList());

        var users = service.findAll(email);

        Assertions.assertThat(users)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("FindById returns user when id is found")
    @Order(4)
    void findById_ReturnsUserById_WhenSuccessful() {
        var expectedUser = userList.getFirst();

        when(repository.findById(expectedUser.getId()))
                .thenReturn(Optional.of(expectedUser));

        var user = service.findByIdOrThrowNotFound(expectedUser.getId());

        Assertions.assertThat(user)
                .isEqualTo(expectedUser);
    }

    @Test
    @DisplayName("FindById throws ResponseStatusException when user is not found")
    @Order(5)
    void findById_ThrowsResponseStatusException_WhenUserIsNotFound() {
        var expectedUser = userList.getFirst();

        when(repository.findById(expectedUser.getId()))
                .thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.findByIdOrThrowNotFound(expectedUser.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("save creates a user")
    @Order(6)
    void save_CreatesUser_WhenSuccessful() {
        var userToSave = userUtils.newUserToSave();

        when(repository.save(userToSave))
                .thenReturn(userToSave);

        var savedUser = service.save(userToSave);

        Assertions.assertThat(savedUser)
                .isEqualTo(userToSave)
                .hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("delete removes a user")
    @Order(7)
    void delete_RemoveUser_WhenSuccessful() {
        var userToDelete = userList.getFirst();

        when(repository.findById(userToDelete.getId()))
                .thenReturn(Optional.of(userToDelete));

        doNothing().when(repository).delete(userToDelete);

        Assertions.assertThatNoException()
                .isThrownBy(() -> service.delete(userToDelete.getId()));
    }

    @Test
    @DisplayName("delete throws ResponseStatusException when user is not found")
    @Order(8)
    void delete_ResponseStatusException_WhenUserIsNotFound() {
        var userToDelete = userList.getFirst();

        when(repository.findById(userToDelete.getId()))
                .thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.delete(userToDelete.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("update updates a user")
    @Order(9)
    void update_UpdatesUser_WhenSuccessful() {
        var userToUpdate = userList.getFirst();
        userToUpdate.setFirstName("Davi");

        when(repository.findById(userToUpdate.getId()))
                .thenReturn(Optional.of(userToUpdate));

        doNothing().when(repository).update(userToUpdate);

        Assertions.assertThatNoException()
                .isThrownBy(() -> service.update(userToUpdate));
    }

    @Test
    @DisplayName("update throws ResponseStatusException when user is not found")
    @Order(10)
    void update_ResponseStatusException_WhenUserIsNotFound() {
        var userToUpdate = userList.getFirst();

        when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.update(userToUpdate))
                .isInstanceOf(ResponseStatusException.class);
    }
}