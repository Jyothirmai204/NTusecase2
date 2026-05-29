package com.ems.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseStructure<T> {

    private String status;   // success / error
    private String message;  // description
    private T data;          // actual data
}
