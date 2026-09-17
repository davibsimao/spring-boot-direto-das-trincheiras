package academy.devdojo.repository;

import academy.devdojo.domain.User;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserData {
    @Getter
    private final List<User> users = new ArrayList<>();

    {
        var hugoSouza = User.builder().id(1L).firstName("Hugo").lastName("Souza").email("hugosouza@gmail.com").build();

        var daviSimao = User.builder().id(2L).firstName("Davi").lastName("Simao").email("davisimao@gmail.com").build();

        var  pedroVenetillo = User.builder().id(3L).firstName("pedro").lastName("venetilo").email("pedrovenetilo@gmail.com").build();

        users.addAll(List.of(hugoSouza, daviSimao, pedroVenetillo));
    }

}
