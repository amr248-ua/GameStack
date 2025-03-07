package com.miproyecto.gamestack.dto.rawgApi;

import lombok.Data;

@Data
public class RawgPlatform {
    private PlatformDetail platform;

    @Data
    public static class PlatformDetail {
        private String name;
    }
}
