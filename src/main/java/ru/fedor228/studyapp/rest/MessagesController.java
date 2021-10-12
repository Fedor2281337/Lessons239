package ru.fedor228.studyapp.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MessagesController {
    // $TEXT = Некоторых текст.
    // $INDEX = Индекс сообщения, с 0.
    // $ENC_TEXT = URL_encoded некий текст.
    // Часть на 3.
    private List<String> messages = new ArrayList<>();

    /* curl http://localhost:8080/messages -H 'Content-Type:
   text/plain' */
    @GetMapping("messages")
    public List<String> getMessages() {
        return messages;
    }
    /* curl -X POST http://localhost:8080/messages -H 'Content-Type:
   text/plain' -d "$TEXT" */
    @PostMapping("messages")
    public void addMessage(@RequestBody String text) {
        messages.add(text);
    }
    /* curl http://localhost:8080/messages/$INDEX -H 'Content-Type:
   text/plain' */
    @GetMapping("messages/{index}")
    public ResponseEntity<String> getMessage(@PathVariable("index") Integer index) {
        try {
            return ResponseEntity.ok(messages.get(index));
        } catch (IndexOutOfBoundsException ex) {
            return ResponseEntity.notFound().build();
        }
    }
    /* curl -X DELETE http://localhost:8080/messages/$INDEX -H 'Content-Type:
   text/plain' */
    @DeleteMapping("messages/{index}")
    public ResponseEntity<String> deleteText(@PathVariable("index") Integer index) {
        try {
            return ResponseEntity.ok(messages.remove((int) index));
        } catch (IndexOutOfBoundsException ex) {
            return ResponseEntity.notFound().build();
        }
    }
    /* curl -X POST http://localhost:8080/messages/$INDEX -H 'Content-Type:
   text/plain' -d "$TEXT" */
    @PostMapping("messages/{index}")
    public ResponseEntity<String> updateMessage(
            @PathVariable("index") Integer i,
            @RequestBody String message) {
        try {
            return ResponseEntity.ok(messages.set(i, message));
        } catch (IndexOutOfBoundsException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    //  Часть на 4.

    @GetMapping("messages/count")
    /* curl http://localhost:8080/messages/count -H 'Content-Type:
   text/plain' */
    public int getMessagesCount() {
        return messages.size();
    }
    /* curl -X POST http://localhost:8080/messages/$INDEX/create -H 'Content-Type:
   text/plain' -d "$TEXT" */
    @PostMapping("messages/{index}/create")
    public void createMessage(
            @PathVariable("index") Integer i,
            @RequestBody String message) {
        messages.add(i, message);
    }
    /* curl http://localhost:8080/messages/search/$ENC_TEXT -H 'Content-Type:
   text/plain' */
    @GetMapping("messages/search/{text}")
    public ResponseEntity<List<String>> createMessage(
            @PathVariable("index") String msg) {
        List<String> strings = messages.stream().filter(e -> e != null && e.contains(msg)).collect(Collectors.toList());
        if (!strings.isEmpty())
            return ResponseEntity.ok(strings);
        else
            return ResponseEntity.notFound().build();
    }
    /* curl -X DELETE http://localhost:8080/messages/search/$ENC_TEXT -H 'Content-Type:
   text/plain' */
    @DeleteMapping("messages/search/{text}")
    public ResponseEntity<List<String>> deleteMessages(
            @PathVariable("index") String msg) {
        List<String> strings = messages.stream().filter(e -> e != null && e.contains(msg)).collect(Collectors.toList());
        if (!strings.isEmpty()) {
            messages.removeIf(strings::contains);
            return ResponseEntity.ok(strings);
        } else
            return ResponseEntity.notFound().build();
    }

}