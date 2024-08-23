package com.soyaa.boardserver.mapper;

import com.soyaa.boardserver.dto.CategoryDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {
    public int register(CategoryDTO productDTO);

    public void updateCategory(CategoryDTO categoryDTO);

    public void deleteCategory(int categoryId);
}
