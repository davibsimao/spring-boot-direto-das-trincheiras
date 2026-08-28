package academy.devdojo.repository;

import academy.devdojo.domain.Producer;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProducerHardCodedRepository {
    @Getter
    private static final List<Producer> PRODUCERS = new ArrayList<>();
    
    static {
        Producer mappa = Producer.builder().id(1L)
                .name("Mappa")
                .createdAt(LocalDateTime.now())
                .build();

        Producer kyotoAnimation = Producer.builder().id(2L)
                .name("Kyoto Animation")
                .createdAt(LocalDateTime.now())
                .build();

        Producer madHouse = Producer.builder().id(3L)
                .name("MadHouse")
                .createdAt(LocalDateTime.now())
                .build();

        PRODUCERS.addAll(List.of(mappa, kyotoAnimation, madHouse));
    }

    public List<Producer> findAll() {
        return PRODUCERS;
    }

    public Optional<Producer> findById(Long id) {
        return PRODUCERS.stream().filter(producer -> producer.getId().equals(id)).findFirst();
    }

    public List<Producer> findByName(String name) {
        return PRODUCERS.stream().filter(p -> p.getName().equalsIgnoreCase(name)).toList();
    }

    public Producer save(Producer producer) {
        PRODUCERS.add(producer);
        return producer;
    }

    public void delete(Producer producer){
        PRODUCERS.remove(producer);

    }

    public void update(Producer producer) {
        delete(producer);
        save(producer);

    }

}
