package com.miproyecto.gamestack.dto.rawgApi;

import lombok.Data;
import java.util.List;

@Data
public class RawgGameDetails {
    private String description_raw;
    private List<RawgDeveloper> developers;
    private List<RawgPublisher> publishers;
}
