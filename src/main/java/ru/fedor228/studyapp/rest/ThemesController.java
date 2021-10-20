package ru.fedor228.studyapp.rest;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class ThemesController {
    // $TEXT = Некоторых текст.
    // $INDEX = Индекс сообщения, с 0.
    // $ENC_TEXT = URL_encoded некий текст.
    // Часть на 3.
    private List<String> themes = new ArrayList<>();

    /* curl http://localhost:8080/themes -H 'Content-Type:
   text/plain' */
    @GetMapping("themes")
    public List<String> getThemes() {
        return themes;
    }
    /* curl -X POST http://localhost:8080/themes -H 'Content-Type:
   text/plain' -d "$TEXT" */
    @PostMapping("themes")
    public void addThemes(@RequestBody String text) {
        themes.add(text);
    }
    /* curl http://localhost:8080/themes/$INDEX -H 'Content-Type:
   text/plain' */
    @GetMapping("themes/{index}")
    public ResponseEntity<String> getThemes(@PathVariable("index") Integer index) {
        try {
            return ResponseEntity.ok(themes.get(index));
        } catch (IndexOutOfBoundsException ex) {
            return ResponseEntity.notFound().build();
        }
    }
    /* curl -X DELETE http://localhost:8080/themes/$INDEX -H 'Content-Type:
   text/plain' */
    @DeleteMapping("themes/{index}")
    public ResponseEntity<String> delete(@PathVariable("index") Integer index) {
        try {
            return ResponseEntity.ok(themes.remove((int) index));
        } catch (IndexOutOfBoundsException ex) {
            return ResponseEntity.notFound().build();
        }
    }
    /* curl -X DELETE http://localhost:8080/themes -H 'Content-Type:
   text/plain' */
    @DeleteMapping("themes/{index}")
    public void deleteAll() {
        themes.clear();
    }
    /* curl -X POST http://localhost:8080/themes/$INDEX -H 'Content-Type:
   text/plain' -d "$TEXT" */
    @PostMapping("themes/{index}")
    public ResponseEntity<String> updateThemes(
            @PathVariable("index") Integer i,
            @RequestBody String theme) {
        try {
            return ResponseEntity.ok(themes.set(i, theme));
        } catch (IndexOutOfBoundsException ex) {
            return ResponseEntity.notFound().build();
        }
    }


}
