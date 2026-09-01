package academy.devdojo.service;

import academy.devdojo.domain.Anime;
import academy.devdojo.repository.AnimeHardCodedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AnimeServiceTest {
    @InjectMocks
    private AnimeService service;
    @Mock
    private AnimeHardCodedRepository repository;
    private List<Anime> animeList;

    @BeforeEach
    void init() {
        var deathNote = Anime.builder().id(1L).name("Death note").build();
        var aot = Anime.builder().id(2L).name("aot").build();
        var nanatsuNoTaisai = Anime.builder().id(3L).name("Nanatsu no taisai").build();

        animeList = new ArrayList<>(List.of(deathNote, aot, nanatsuNoTaisai));
    }

    @Test
    @DisplayName("FindAll returns as list with all animes when argument is null")
    @Order(1)
    void findAll_ReturnsAllAnimes_WhenArgumentIsNull() {
        when(repository.findAll()).thenReturn(animeList);

        var animes = service.findAll(null);

        Assertions.assertThat(animes).isNotNull().hasSameElementsAs(animeList);
    }

    @Test
    @DisplayName("FindAll returns with list found object when name exists")
    @Order(2)
    void findByName_ReturnsFoundAnimeInLIst_WhenNameIsFound() {
        var anime = animeList.getFirst();
        var expectedAnime = singletonList(anime);

        when(repository.findByName(anime.getName())).thenReturn(expectedAnime);

        var animeFound = service.findAll(anime.getName());

        Assertions.assertThat(animeFound).containsAll(expectedAnime);
    }

    @Test
    @DisplayName("FindAll returns empty list when name is not found")
    @Order(3)
    void findByName_ReturnsEmptyList_WhenNameIsNotFound() {
        var name = "not-found-name";
        when(repository.findByName(name)).thenReturn(emptyList());

        var animes = service.findAll(name);

        Assertions.assertThat(animes).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("FindById returns anime when id is found")
    @Order(4)
    void findById_ReturnsAnimeById_WhenSuccessful() {
        var expectedAnime = animeList.getFirst();

        when(repository.findById(expectedAnime.getId())).thenReturn(Optional.of(expectedAnime));

        var animeFound = service.findByIdOrThrowNotFound(expectedAnime.getId());

        Assertions.assertThat(animeFound).isEqualTo(expectedAnime);
    }

    @Test
    @DisplayName("FindById throwsResponseStatusException when anime is not found")
    @Order(5)
    void findById_ThrowsResponseStatusException_WhenAnimeIsNotFound() {
        var expectedAnime = animeList.getFirst();
        when(repository.findById(expectedAnime.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.findByIdOrThrowNotFound(expectedAnime.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("save creates a anime")
    @Order(6)
    void save_CreatesAnime_WhenSuccessful() {
        var animeToSave = Anime.builder().id(22L).name("Solo Level").build();
        when(repository.save(animeToSave)).thenReturn(animeToSave);

        var animeSaved = service.save(animeToSave);

        Assertions.assertThat(animeSaved).isEqualTo(animeToSave).hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("delete removes a anime")
    @Order(7)
    void delete_RemoveAnime_WhenSuccessful() {
        var animeToDelete = animeList.getFirst();

        when(repository.findById(animeToDelete.getId())).thenReturn(Optional.of(animeToDelete));
        doNothing().when(repository).delete(animeToDelete);

        Assertions.assertThatNoException().isThrownBy(() -> service.delete(animeToDelete.getId()));
    }

    @Test
    @DisplayName("delete throws ResponseStatusException when anime is not found")
    @Order(8)
    void delete_ResponseStatusException_WhenAnimeIsNotFound() {
        var animeToDelete = animeList.getFirst();

        when(repository.findById(animeToDelete.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.delete(animeToDelete.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("update updates a anime")
    @Order(9)
    void update_updatesAnime_WhenSuccessful() {
        var animeToUpdate = this.animeList.getFirst();
        animeToUpdate.setName("Aniplex");

        when(repository.findById(animeToUpdate.getId())).thenReturn(Optional.of(animeToUpdate));
        doNothing().when(repository).update(animeToUpdate);


        Assertions.assertThatNoException()
                .isThrownBy(() -> service.update(animeToUpdate));
    }

    @Test
    @DisplayName("update throws ResponseStatusException when anime is not found")
    @Order(10)
    void update_ResponseStatusException_WhenAnimeIsNotFound() {
        var animeToUpdate = this.animeList.getFirst();

        when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.update(animeToUpdate))
                .isInstanceOf(ResponseStatusException.class);
    }


}