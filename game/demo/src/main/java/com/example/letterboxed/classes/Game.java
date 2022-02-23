package com.example.letterboxed.classes;

import java.util.List;

public class Game {
    private String id;
    private Integer width;
    private Integer height;
    private List<String> letters;

    public Game(){

    }

    public Game(String id, Integer width, Integer height, List<String> letters) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.letters = letters;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id; 
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getWidth() {
        return width;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getHeight() {
        return height;
    }

    public void setLetters(List<String> letters) {
        this.letters = letters;
    }
    
    public List<String> getLetters() {
        return letters;
    }
}