package ru.fedor228.studyapp.rest;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fedor228.studyapp.Theme;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ThemesController {
    private final List<Theme> themes = new ArrayList<>();

    /* curl http://localhost:8080/themes -H 'Content-Type:
   text/plain' */
    @GetMapping("themes")
    public List<String> getThemes() {
        return themes.stream().map(e -> e.name).collect(Collectors.toList());
    }
    /* curl -X POST http://localhost:8080/themes -H 'Content-Type:
   text/plain' -d "$TEXT" */
    @PostMapping("themes")
    public void addThemes(@RequestBody String text) {
        themes.add(new Theme(text));
    }
    /* curl http://localhost:8080/themes/$INDEX -H 'Content-Type:
   text/plain' */
    @GetMapping("themes/{index}")
    public ResponseEntity<Theme> getThemes(@PathVariable("index") Integer index) {
        if (themes.size() <= index || index < 0)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(themes.get(index));
    }
    /* curl -X DELETE http://localhost:8080/themes/$INDEX -H 'Content-Type:
   text/plain' */
    @DeleteMapping("themes/{index}")
    public ResponseEntity<Theme> delete(@PathVariable("index") Integer index) {
        try {
            return ResponseEntity.ok(themes.remove((int) index));
        } catch (IndexOutOfBoundsException ex) {
            return ResponseEntity.notFound().build();
        }
    }
    /* curl -X DELETE http://localhost:8080/themes -H 'Content-Type:
   text/plain' */
    @DeleteMapping("themes")
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
            Theme t = themes.get(i);
            String ret = t.name;
            t.name = theme;
            return ResponseEntity.ok(ret);
        } catch (IndexOutOfBoundsException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("themes/count")
    /* curl http://localhost:8080/themes/count -H 'Content-Type:
   text/plain' */
    public int getThemesCount() {
        return themes.size();
    }

    /* curl http://localhost:8080/themes/$INDEX -H 'Content-Type:
   text/plain' */
    @PostMapping("themes/comment/{index}/{author}")
    public void addComment(@PathVariable("index") Integer i, @PathVariable("author") String author, @RequestBody String text) {
        if (themes.size() > i || i >= 0) {
            themes.get(i).comments.add(new Theme.Comment(author, text));
        }
    }

}
