package academy.devdojo.repository;

import academy.devdojo.domain.Anime;
import external.dependency.Connection;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Log4j2
public class AnimeHardCodedRepository {
    private final AnimeData animeData;
    @Qualifier(value = "connectionMongoDB")
    private Connection connection;

    public List<Anime> findAll() {
        return animeData.getAnimes();
    }

    public Optional<Anime> findById(Long id) {
        return animeData.getAnimes().stream().filter(anime -> anime.getId().equals(id)).findFirst();
    }

    public List<Anime> findByName(String name) {
        log.debug(connection);
        return animeData.getAnimes().stream().filter(p -> p.getName().equalsIgnoreCase(name)).toList();
    }

    public Anime save(Anime anime) {
        animeData.getAnimes().add(anime);
        return anime;
    }

    public void delete(Anime anime){
        animeData.getAnimes().remove(anime);

    }

    public void update(Anime anime) {
        delete(anime);
        save(anime);

    }

}
