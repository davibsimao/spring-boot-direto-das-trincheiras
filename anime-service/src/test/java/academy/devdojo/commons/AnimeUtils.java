package academy.devdojo.commons;

import academy.devdojo.domain.Anime;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnimeUtils {

    public List<Anime> newAnimeList() {
        var deathNote = Anime.builder().id(1L).name("Death note").build();
        var aot = Anime.builder().id(2L).name("aot").build();
        var nanatsuNoTaisai = Anime.builder().id(3L).name("Nanatsu no taisai").build();

        return new ArrayList<>(List.of(deathNote, aot, nanatsuNoTaisai));
    }

    public Anime newAnimeToSave() {
        return Anime.builder().id(99L).name("one piece").build();
    }
}