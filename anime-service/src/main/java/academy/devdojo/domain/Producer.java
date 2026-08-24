package academy.devdojo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class Producer {
    private Long id;
    @JsonProperty("name")
    private String name;
    private LocalDateTime createdAt;
    @Getter
    private static List<Producer> producers = new ArrayList<>();

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
        
        producers.addAll(List.of(mappa, kyotoAnimation, madHouse));
    }
}