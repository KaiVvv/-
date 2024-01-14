package com.briup.cms.common.model.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author YuYan
 * @date 2023-12-07 11:11:53
 */
@Data
@NoArgsConstructor
public class CommentDeleteParam {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("type")
    private String type;

}
