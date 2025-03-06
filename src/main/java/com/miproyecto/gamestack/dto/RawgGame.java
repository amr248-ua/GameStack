package com.miproyecto.gamestack.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RawgGame {
    private int id;
    private String name;
    private List<RawgGenre> genres;
    private List<RawgPlatform> platforms;
    private String released;
    private String background_image;
    private List<RawgTag> tags;


    public Date getReleased() {
        return new Date();
    }
}
