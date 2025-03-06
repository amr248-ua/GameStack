package com.miproyecto.gamestack.dto;

import lombok.Data;
import java.util.List;

@Data
public class RawgGameDetails {
    private String description;
    private List<RawgDeveloper> developers;
    private List<RawgPublisher> publishers;
}
