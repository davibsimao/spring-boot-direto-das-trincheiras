package academy.devdojo.repository;

import academy.devdojo.domain.Producer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProducerHardCodedRepositoryTest {
    @InjectMocks
    private ProducerHardCodedRepository repository;
    @Mock
    private ProducerData producerData;
    private final List<Producer> producerList = new ArrayList<>();

    @BeforeEach
    void init() {
        var ufotable = Producer.builder().id(1L).name("Ufotable").createdAt(LocalDateTime.now()).build();
        var witStudio = Producer.builder().id(2L).name("Wit Studio").createdAt(LocalDateTime.now()).build();
        var studioGhibli = Producer.builder().id(3L).name("Studio Ghibli").createdAt(LocalDateTime.now()).build();
        producerList.addAll(List.of(ufotable, witStudio, studioGhibli));

    }

    @Test
    @DisplayName("FindAll returns as list with all producers")
    @Order(1)
    void findAll_ReturnAllProducers_WhenSuccessful() {
        when(producerData.getProducers()).thenReturn(producerList);

        var producers = repository.findAll();

        Assertions.assertThat(producers).isNotNull().hasSameElementsAs(producerList);
    }

    @Test
    @DisplayName("FindByName returns empty when name is null")
    @Order(2)
    void findById_ReturnsProducerById_WhenSuccessful() {
        when(producerData.getProducers()).thenReturn(producerList);

        var expectedProducer = producerList.getFirst();

        var producers = repository.findById(expectedProducer.getId());

        Assertions.assertThat(producers).isPresent().contains(expectedProducer);
    }

    @Test
    @DisplayName("findById returns producer with given id")
    @Order(3)
    void findByName_ReturnsEmptyList_WhenNameIsNull() {
        when(producerData.getProducers()).thenReturn(producerList);

        var producers = repository.findByName(null);

        Assertions.assertThat(producers).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("FindByName returns list with object found when name exists")
    @Order(4)
    void findByName_ReturnFoundProducerInList_WhenNameIsFound() {
        when(producerData.getProducers()).thenReturn(producerList);
        var expectedProducer = producerList.getFirst();

        var producers = repository.findByName(expectedProducer.getName());
        Assertions.assertThat(producers).hasSize(1).contains(expectedProducer);
    }

    @Test
    @DisplayName("save creates a producer")
    @Order(5)
    void save_CreatesProducer_WhenSuccessful() {
        when(producerData.getProducers()).thenReturn(producerList);

        var producerTosave = Producer.builder().id(99L).name("mappa").createdAt(LocalDateTime.now()).build();
        var producer = repository.save(producerTosave);

        Assertions.assertThat(producer).isEqualTo(producerTosave).hasNoNullFieldsOrProperties();

        var producerSavedOptional = repository.findById(producerTosave.getId());
        Assertions.assertThat(producerSavedOptional).isPresent().contains(producerTosave);

    }

    @Test
    @DisplayName("delete removes a producer")
    @Order(6)
    void delete_RemoveProducer_WhenSuccessful() {
        when(producerData.getProducers()).thenReturn(producerList);
        var producerToDelete = producerList.getFirst();

        repository.delete(producerToDelete);

        var producers = repository.findAll();

        Assertions.assertThat(producers).isNotEmpty().doesNotContain(producerToDelete);
    }

    @Test
    @DisplayName("update updates a producer")
    @Order(7)
    void update_updatesProducer_WhenSuccessful() {
        when(producerData.getProducers()).thenReturn(producerList);

        var producerToUpdate = this.producerList.getFirst();
        producerToUpdate.setName("Aniplex");

        repository.update(producerToUpdate);

        Assertions.assertThat(this.producerList).contains(producerToUpdate);

        var producerUpdatedOptional = repository.findById(producerToUpdate.getId());

        Assertions.assertThat(producerUpdatedOptional).isPresent();
        Assertions.assertThat(producerUpdatedOptional.get().getName()).isEqualTo(producerToUpdate.getName())    ;

    }
}