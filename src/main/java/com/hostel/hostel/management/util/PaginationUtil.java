package com.hostel.hostel.management.util;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

public final class PaginationUtil {

    private PaginationUtil() {}

    public static HttpHeaders generatePaginationHttpHeaders(
            UriComponentsBuilder uriBuilder,
            Page<?> page
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", Long.toString(page.getTotalElements()));
        return headers;
    }
}

