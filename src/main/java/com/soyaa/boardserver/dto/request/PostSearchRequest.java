package com.soyaa.boardserver.dto.request;
import com.soyaa.boardserver.dto.CategoryDTO.SortStatus;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostSearchRequest {
    private int id;
    private String name;
    private String contents;
    private int views;
    private int categoryId;
    private int userId;
    private SortStatus sortStatus;
}
