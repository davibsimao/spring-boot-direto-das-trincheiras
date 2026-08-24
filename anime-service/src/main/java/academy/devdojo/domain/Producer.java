package academy.devdojo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Producer {
    private Long id;
    @JsonProperty("name")
    private String name;
    @Getter
    private static List<Producer> producers = new ArrayList<>();

    static {
        Producer mappa = new Producer(1L, "Mappa");
        Producer kyotoAnimation = new Producer(2L, "Kyoto Animation");
        Producer madHouse = new Producer(3L, "MadHouse");
        producers.addAll(List.of(mappa, kyotoAnimation, madHouse));
    }
}