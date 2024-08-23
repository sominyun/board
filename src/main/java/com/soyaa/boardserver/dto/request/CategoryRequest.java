package com.soyaa.boardserver.dto.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class CategoryRequest {
    @NonNull
    private int id;
    @NonNull
    private String name;
}