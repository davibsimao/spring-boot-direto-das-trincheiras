package academy.devdojo.repository;

import academy.devdojo.domain.Anime;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AnimeHardCodedRepository {
    @Getter
    private static final List<Anime> ANIMES = new ArrayList<>();
    static {
        Anime naruto = Anime.builder().id(1L)
                .name("naruto")
                .build();

        Anime demonSlayer = Anime.builder().id(2L)
                .name("demon slayer")
                .build();

        Anime dragonBall = Anime.builder().id(3L)
                .name("dragon ball")
                .build();

        ANIMES.addAll(List.of(naruto, demonSlayer, dragonBall));
    }


    public List<Anime> findAll() {
        return ANIMES;
    }

    public Optional<Anime> findById(Long id) {
        return ANIMES.stream().filter(anime -> anime.getId().equals(id)).findFirst();
    }

    public List<Anime> findByName(String name) {
        return ANIMES.stream().filter(p -> p.getName().equalsIgnoreCase(name)).toList();
    }

    public Anime save(Anime anime) {
        ANIMES.add(anime);
        return anime;
    }

    public void delete(Anime anime){
        ANIMES.remove(anime);

    }

    public void update(Anime anime) {
        delete(anime);
        save(anime);

    }

}
