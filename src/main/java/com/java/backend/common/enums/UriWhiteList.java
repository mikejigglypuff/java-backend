package com.java.backend.common.enums;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UriWhiteList {
    AUTH("/auth"),
    SWAGGER("/swagger-ui"),
    API_DOCS("/api-docs");

    private final String uriHeader;

    public static boolean isWhiteList(String uri) {
        return Arrays.stream(UriWhiteList.values())
                .map(UriWhiteList::getUriHeader)
                .anyMatch(uri::startsWith);
    }
}
