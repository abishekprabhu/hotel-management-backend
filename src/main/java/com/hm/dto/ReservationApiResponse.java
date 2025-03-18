package com.hm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Excludes null fields in the response
public class ReservationApiResponse {
    private String code;
    private String message;
    private Object data;

    // Constructor without data
    public ReservationApiResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
;