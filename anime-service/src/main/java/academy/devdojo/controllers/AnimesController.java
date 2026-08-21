package academy.devdojo.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("v1/animes")
@Slf4j
public class AnimesController {

    @GetMapping()
    public List<String> listAll() {
        log.info(Thread.currentThread().getName());
        return List.of("naruto", "nanatsu", "demon slayer", "attack on titan");
    }
}
