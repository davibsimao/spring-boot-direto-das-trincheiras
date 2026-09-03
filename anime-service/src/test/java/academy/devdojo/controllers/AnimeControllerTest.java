package academy.devdojo.controllers;


import academy.devdojo.domain.Anime;
import academy.devdojo.repository.AnimeData;
import academy.devdojo.repository.AnimeHardCodedRepository;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(controllers = AnimeController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "academy.devdojo")
class AnimeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AnimeData animeData;
    @SpyBean
    private AnimeHardCodedRepository repository;
    private List<Anime> animeList;
    @Autowired
    private ResourceLoader resourceLoader;

    @BeforeEach
    void init() {
        var deathNote = Anime.builder().id(1L).name("Death note").build();
        var aot = Anime.builder().id(2L).name("aot").build();
        var nanatsuNoTaisai = Anime.builder().id(3L).name("Nanatsu no taisai").build();

        animeList = new ArrayList<>(List.of(deathNote, aot, nanatsuNoTaisai));
    }


    @Test
    @DisplayName("GET v1/animes returns as list with all animes when argument is null")
    @Order(1)
    void findAll_ReturnsAllAnimes_WhenArgumentIsNull() throws Exception {
        when(animeData.getAnimes()).thenReturn(animeList);

        var response = readResourceFile("anime/get-anime-null-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/animes?name=Death note  returns list with found object when name exists")
    @Order(2)
    void findAll_ReturnsFoundProducerInLIst_WhenNameIsFound() throws Exception {
        when(animeData.getAnimes()).thenReturn(animeList);

        var name = "Death Note";

        var response = readResourceFile("anime/get-anime-death_note-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes").param("name",name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/animes?name=x returns empty list when name is not found")
    @Order(3)
    void findAll_ReturnsEmptyList_WhenNameIsNotFound() throws Exception {
        when(animeData.getAnimes()).thenReturn(animeList);

        var name = "x";

        var response = readResourceFile("anime/get-anime-x-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes").param("name",name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/animes/1 returns anime when id is found")
    @Order(4)
    void findById_ReturnsAnimeById_WhenSuccessful() throws Exception {
        when(animeData.getAnimes()).thenReturn(animeList);

        var id = 3L;

        var response = readResourceFile("anime/get-anime-by-id-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/animes/99 throwsResponseStatusException 404 when producer is not found")
    @Order(5)
    void findById_ThrowsResponseStatusException_WhenAnimeIsNotFound() throws Exception {
        when(animeData.getAnimes()).thenReturn(animeList);

        var id = 99L;

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes/{id}",id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("anime not Found"));
    }

    @Test
    @DisplayName("POST v1/animes save creates a anime")
    @Order(6)
    void save_CreatesAnime_WhenSuccessful() throws Exception {
        var animeToSave = Anime.builder().id(99L).name("one piece").build();

        var request = readResourceFile("anime/post-request-anime-200.json");
        var response = readResourceFile("anime/post-response-anime-201.json");

        when(repository.save(ArgumentMatchers.any())).thenReturn(animeToSave);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/animes")
                .content(request)
                .header("x-api-key", "v1")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("PUT v1/animes updates a producer")
    @Order(7)
    void update_updatesAnime_WhenSuccessful() throws Exception {
        when(animeData.getAnimes()).thenReturn(animeList);

        var request = readResourceFile("anime/put-request-anime-200.json");

        mockMvc.perform(MockMvcRequestBuilders
                .put("/v1/animes")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("PUT v1/animes throws ResponseStatusException when anime is not found")
    @Order(8)
    void update_ResponseStatusException_WhenAnimeIsNotFound() throws Exception {
        when(animeData.getAnimes()).thenReturn(animeList);

        var request = readResourceFile("anime/put-request-anime-404.json");

        mockMvc.perform(MockMvcRequestBuilders
                .put("/v1/animes")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("anime not Found"));
    }

    @Test
    @DisplayName("DELETE v1/anime/1 removes a anime")
    @Order(9)
    void delete_RemoveAnime_WhenSuccessful() throws Exception {
        when(animeData.getAnimes()).thenReturn(animeList);

        var id = animeList.getFirst().getId();

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/v1/animes/{id}",id)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("DELETE v1/animes/99 throws ResponseStatusException when anime is not found")
    @Order(10)
    void delete_ResponseStatusException_WhenAnimesNotFound() throws Exception {
        when(animeData.getAnimes()).thenReturn(animeList);

        var id = 9999L;

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/animes/{id}",id)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("anime not Found"));
    }



    private String readResourceFile(String fileName) throws IOException {
        var file = resourceLoader.getResource("classpath:%s".formatted(fileName)).getFile();
        return new String(Files.readAllBytes(file.toPath()));
    }
}