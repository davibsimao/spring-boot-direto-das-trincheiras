package academy.devdojo.repository;

import academy.devdojo.commons.AnimeUtils;
import academy.devdojo.domain.Anime;
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
class AnimeHardCodedRepositoryTest {
    @InjectMocks
    private AnimeHardCodedRepository repository;

    @Mock
    private AnimeData animeData;
    private List<Anime> animeList;
    @InjectMocks
    private AnimeUtils animeUtils;

    @BeforeEach
    void init() {
        animeList = animeUtils.newAnimeList();

    }

    @Test
    @DisplayName("FindAll returns as list with all animes")
    @Order(1)
    void findAll_ReturnAllAnimes_WhenSuccessful() {
        when(animeData.getAnimes()).thenReturn(animeList);

        var animes = repository.findAll();

        Assertions.assertThat(animes).isNotNull().hasSameElementsAs(animeList);
    }

    @Test
    @DisplayName("FindById returns empty when name is null")
    @Order(2)
    void findById_ReturnsAnimeById_WhenSuccessful() {
        when(animeData.getAnimes()).thenReturn(animeList);

        var expectedAnime = animeList.getFirst();

        var anime = repository.findById(expectedAnime.getId());

        Assertions.assertThat(anime).isPresent().contains(expectedAnime);
    }


    @Test
    @DisplayName("FindByName returns anime with given id")
    @Order(3)
    void findByName_ReturnsEmptyList_WhenNameIsNull() {
        when(animeData.getAnimes()).thenReturn(animeList);

        var animes = repository.findByName(null);

        Assertions.assertThat(animes).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("FindByName returns list with object found when name exists")
    @Order(4)
    void findByName_ReturnFoundAnimeInList_WhenNameIsFound() {
        when(animeData.getAnimes()).thenReturn(animeList);

        var expectedAnime = animeList.getFirst();

        var anime = repository.findByName(expectedAnime.getName());

        Assertions.assertThat(anime).hasSize(1).contains(expectedAnime);
    }

    @Test
    @DisplayName("save creates a producer")
    @Order(5)
    void save_CreatesProducer_WhenSuccessful() {
        when(animeData.getAnimes()).thenReturn(animeList);

        var animeToSave = animeUtils.newAnimeToSave();

        var anime = repository.save(animeToSave);

        Assertions.assertThat(anime).isEqualTo(animeToSave).hasNoNullFieldsOrProperties();

        var animeSavedOptional = repository.findById(animeToSave.getId());

        Assertions.assertThat(animeSavedOptional).isPresent().contains(animeToSave);

    }

    @Test
    @DisplayName("delete removes a anime")
    @Order(6)
    void delete_RemoveProducer_WhenSuccessful() {
        when(animeData.getAnimes()).thenReturn(animeList);
        var animeToDelete = animeList.getFirst();

        repository.delete(animeToDelete);

        var anime = repository.findAll();

        Assertions.assertThat(anime).isNotEmpty().doesNotContain(animeToDelete);
    }

    @Test
    @DisplayName("update updates a anime")
    @Order(7)
    void update_updatesProducer_WhenSuccessful() {
        when(animeData.getAnimes()).thenReturn(animeList);

        var animeToUpdate = this.animeList.getFirst();

        animeToUpdate.setName("Boku no hero");

        repository.update(animeToUpdate);

        Assertions.assertThat(this.animeList).contains(animeToUpdate);

        var animeUpdatedOptional = repository.findById(animeToUpdate.getId());

        Assertions.assertThat(animeUpdatedOptional).isPresent();

        Assertions.assertThat(animeUpdatedOptional.get().getName()).isEqualTo(animeToUpdate.getName());

    }
}