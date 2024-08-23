package com.soyaa.boardserver.service;

import com.soyaa.boardserver.dto.CategoryDTO;

public interface CategoryService {
    void register(String accountId, CategoryDTO categoryDTO);

    void update(CategoryDTO categoryDTO);

    void delete(int categoryId);
}
