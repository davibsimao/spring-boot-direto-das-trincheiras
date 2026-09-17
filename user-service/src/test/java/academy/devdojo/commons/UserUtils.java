package academy.devdojo.commons;

import academy.devdojo.domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserUtils {

    public List<User> newUserList() {
        var pedro = User.builder().id(1L).firstName("Pedro").lastName("Venetilo").email("pedrovenetilo@gmail.com").build();
        var joao = User.builder().id(2L).firstName("Joao").lastName("Silva").email("joao.silva@gmail.com").build();
        var maria = User.builder().id(3L).firstName("Maria").lastName("Souza").email("maria.souza@gmail.com").build();

        return new ArrayList<>(List.of(pedro, joao, maria));
    }

    public User newUserToSave() {
        return User.builder().id(99L).firstName("Hugo").lastName("Ferreira").email("hugo.ferreira@gmail.com").build();
    }
}