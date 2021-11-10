package ru.fedor228.studyapp;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Theme {
    public String name;
    public List<Comment> comments;

    public static class Comment {
        String author;
        Date time;
        String text;
        public Comment(String author, String text) {
            time = Date.from(Instant.now(Clock.systemUTC()));
            this.text = text;
            this.author = author;
        }
    }
    public Theme() {
        comments = new ArrayList<>();
    }

    public Theme(String name) {
        this();
        this.name = name;
    }
}
