package academy.devdojo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("v1/animes")
public class AnimesController {

    @GetMapping()
    public List<String> listAll() {

        return List.of("naruto", "nanatsu", "demon slayer", "attack on titan");
    }
}
