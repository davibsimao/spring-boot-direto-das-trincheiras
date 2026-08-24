package academy.devdojo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Anime {
    private Long id;
    private String name;

    public static List<Anime> getAnimes() {
        Anime naruto = new Anime(1L, "naruto");
        Anime demonSlayer = new Anime(2L, "demon slayer");
        Anime dragonBall = new Anime(3L, "dragon ball");
        Anime attackOnTitan = new Anime(4L, "attack on titan");

        return List.of(naruto, demonSlayer, dragonBall, attackOnTitan);
    }
}