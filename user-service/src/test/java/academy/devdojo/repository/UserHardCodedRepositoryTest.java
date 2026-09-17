package academy.devdojo.repository;

import academy.devdojo.commons.UserUtils;
import academy.devdojo.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserHardCodedRepositoryTest {

    @InjectMocks
    private UserHardCodedRepository repository;
    @Mock
    private UserData userData;
    private List<User> userList;
    @InjectMocks
    private UserUtils userUtils;
    @BeforeEach
    void init() {
        userList = userUtils.newUserList();
    }

    @Test
    @DisplayName("FindAll returns a list with all users")
    @Order(1)
    void findAll_ReturnAllUsers_WhenSuccessful() {
        when(userData.getUsers()).thenReturn(userList);

        var users = repository.findAll();

        Assertions.assertThat(users)
                .isNotNull()
                .hasSameElementsAs(userList);
    }

    @Test
    @DisplayName("FindById returns user when id exists")
    @Order(2)
    void findById_ReturnsUserById_WhenSuccessful() {
        when(userData.getUsers()).thenReturn(userList);

        var expectedUser = userList.getFirst();

        var user = repository.findById(expectedUser.getId());

        Assertions.assertThat(user)
                .isPresent()
                .contains(expectedUser);
    }

    @Test
    @DisplayName("FindByEmail returns empty list when email is null")
    @Order(3)
    void findByEmail_ReturnsEmptyList_WhenEmailIsNull() {
        when(userData.getUsers()).thenReturn(userList);

        var users = repository.findByEmail(null);

        Assertions.assertThat(users)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("FindByEmail returns list with user when email exists")
    @Order(4)
    void findByEmail_ReturnsFoundUserInList_WhenEmailIsFound() {
        when(userData.getUsers()).thenReturn(userList);

        var expectedUser = userList.getFirst();

        var users = repository.findByEmail(expectedUser.getEmail());

        Assertions.assertThat(users)
                .hasSize(1)
                .contains(expectedUser);
    }

    @Test
    @DisplayName("save creates a user")
    @Order(5)
    void save_CreatesUser_WhenSuccessful() {
        when(userData.getUsers()).thenReturn(userList);

        var userToSave = userUtils.newUserToSave();

        var user = repository.save(userToSave);

        Assertions.assertThat(user)
                .isEqualTo(userToSave)
                .hasNoNullFieldsOrProperties();

        var userSavedOptional = repository.findById(userToSave.getId());

        Assertions.assertThat(userSavedOptional)
                .isPresent()
                .contains(userToSave);
    }

    @Test
    @DisplayName("delete removes a user")
    @Order(6)
    void delete_RemoveUser_WhenSuccessful() {
        when(userData.getUsers()).thenReturn(userList);

        var userToDelete = userList.getFirst();

        repository.delete(userToDelete);

        var users = repository.findAll();

        Assertions.assertThat(users)
                .isNotEmpty()
                .doesNotContain(userToDelete);
    }

    @Test
    @DisplayName("update updates a user")
    @Order(7)
    void update_UpdatesUser_WhenSuccessful() {
        when(userData.getUsers()).thenReturn(userList);

        var userToUpdate = userList.getFirst();
        userToUpdate.setFirstName("Davi");

        repository.update(userToUpdate);

        Assertions.assertThat(userList)
                .contains(userToUpdate);

        var userUpdatedOptional = repository.findById(userToUpdate.getId());

        Assertions.assertThat(userUpdatedOptional)
                .isPresent();

        Assertions.assertThat(userUpdatedOptional.get().getFirstName())
                .isEqualTo(userToUpdate.getFirstName());
    }
}