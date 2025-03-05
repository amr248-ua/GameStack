package com.miproyecto.gamestack.dto;

import lombok.Data;

@Data
public class RawgPlatform {
    private PlatformDetail platform;

    @Data
    public static class PlatformDetail {
        private String name;
    }
}
