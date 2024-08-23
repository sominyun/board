package com.soyaa.boardserver.mapper;


import com.soyaa.boardserver.dto.TagDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagMapper {
    public int register(TagDTO tagDTO);

    public void updateTags(TagDTO tagDTO);

    public void deletePostTag(int tagId);

    public void createPostTag(Integer tagId, Integer postId);

}