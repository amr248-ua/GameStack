package com.miproyecto.gamestack.dto.rawgApi;
import lombok.Data;
import java.util.List;

@Data
public class RawgResponse {
    private List<RawgGame> results;
    private String next;
}
