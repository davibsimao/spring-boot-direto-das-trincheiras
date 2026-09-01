package academy.devdojo.service;

import academy.devdojo.domain.Producer;
import academy.devdojo.repository.ProducerHardCodedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProducerServiceTest {
    @InjectMocks
    private ProducerService service;
    @Mock
    private ProducerHardCodedRepository repository;
    private List<Producer> producerList;

    @BeforeEach
    void init() {
        var ufotable = Producer.builder().id(1L).name("Ufotable").createdAt(LocalDateTime.now()).build();
        var witStudio = Producer.builder().id(2L).name("Wit Studio").createdAt(LocalDateTime.now()).build();
        var studioGhibli = Producer.builder().id(3L).name("Studio Ghibli").createdAt(LocalDateTime.now()).build();
        producerList = new ArrayList<>(List.of(ufotable, witStudio, studioGhibli));
    }

    @Test
    @DisplayName("FindAll returns as list with all producers when argument is null")
    @Order(1)
    void findAll_ReturnsAllProducers_WhenArgumentIsNull() {
        when(repository.findAll()).thenReturn(producerList);

        var producers = service.findAll(null);

        Assertions.assertThat(producers).isNotNull().hasSameElementsAs(producerList);
    }

    @Test
    @DisplayName("FindAll returns with list found object when name exists")
    @Order(2)
    void findByName_ReturnsFoundProducerInLIst_WhenNameIsFound() {
        var producer = producerList.getFirst();
        var expectedProducersFound = singletonList(producer);

        when(repository.findByName(producer.getName())).thenReturn(expectedProducersFound);
        var producersFound = service.findAll(producer.getName());

        Assertions.assertThat(producersFound).containsAll(expectedProducersFound);
    }

    @Test
    @DisplayName("FindAll returns empty list when name is not found")
    @Order(3)
    void findByName_ReturnsEmptyList_WhenNameIsNotFound() {
        var name = "not-found";
        when(repository.findByName(name)).thenReturn(emptyList());

        var producers = service.findAll(name);

        Assertions.assertThat(producers).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("FindById returns empty when name is null")
    @Order(4)
    void findById_ReturnsProducerById_WhenSuccessful() {
        var expectedProducer = producerList.getFirst();
        when(repository.findById(expectedProducer.getId())).thenReturn(Optional.of(expectedProducer));

        var producers = service.findByIdOrThrowNotFound(expectedProducer.getId());

        Assertions.assertThat(producers).isEqualTo(expectedProducer);
    }

    @Test
    @DisplayName("FindById throwsResponseStatusException when producer is not found")
    @Order(5)
    void findById_ThrowsResponseStatusException_WhenProducerIsNotFound() {
        var expectedProducer = producerList.getFirst();
        when(repository.findById(expectedProducer.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.findByIdOrThrowNotFound(expectedProducer.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("save creates a producer")
    @Order(6)
    void save_CreatesProducer_WhenSuccessful() {
        var producerTosave = Producer.builder().id(99L).name("mappa").createdAt(LocalDateTime.now()).build();
        when(repository.save(producerTosave)).thenReturn(producerTosave);

        var savedProducer = repository.save(producerTosave);

        Assertions.assertThat(savedProducer).isEqualTo(producerTosave).hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("delete removes a producer")
    @Order(7)
    void delete_RemoveProducer_WhenSuccessful() {
        var producerToDelete = producerList.getFirst();

        when(repository.findById(producerToDelete.getId())).thenReturn(Optional.of(producerToDelete));
        doNothing().when(repository).delete(producerToDelete);
        repository.delete(producerToDelete);

        Assertions.assertThatNoException().isThrownBy(() -> service.delete(producerToDelete.getId()));
    }

    @Test
    @DisplayName("delete throws ResponseStatusException when producer is not found")
    @Order(8)
    void delete_ResponseStatusException_WhenProducerIsNotFound() {
        var producerToDelete = producerList.getFirst();

        when(repository.findById(producerToDelete.getId())).thenReturn(Optional.empty());


        Assertions.assertThatException()
                .isThrownBy(() -> service.delete(producerToDelete.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("update updates a producer")
    @Order(9)
    void update_updatesProducer_WhenSuccessful() {
        var producerToUpdate = this.producerList.getFirst();
        producerToUpdate.setName("Aniplex");

        when(repository.findById(producerToUpdate.getId())).thenReturn(Optional.of(producerToUpdate));
        doNothing().when(repository).update(producerToUpdate);

        Assertions.assertThatNoException().isThrownBy(() -> service.update(producerToUpdate));
    }

    @Test
    @DisplayName("update throws ResponseStatusException when producer is not found")
    @Order(10)
    void update_ResponseStatusException_WhenProducerIsNotFound() {
        var producerToUpdate = this.producerList.getFirst();
        when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());


        Assertions.assertThatException()
                .isThrownBy(() -> service.delete(producerToUpdate.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }
}