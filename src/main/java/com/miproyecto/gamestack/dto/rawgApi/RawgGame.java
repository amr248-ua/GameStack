package com.miproyecto.gamestack.dto.rawgApi;

import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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


    public LocalDate getReleased() {
        return LocalDate.parse(this.released, DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
