package academy.devdojo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Anime {
    private Long id;
    private String name;
    @Getter
    private static List<Anime> animes = new ArrayList<>();

    static {
        Anime naruto = new Anime(1L, "naruto");
        Anime demonSlayer = new Anime(2L, "demon slayer");
        Anime dragonBall = new Anime(3L, "dragon ball");
        Anime attackOnTitan = new Anime(4L, "attack on titan");
        animes.addAll(List.of(naruto, demonSlayer, dragonBall, attackOnTitan));
    }

}