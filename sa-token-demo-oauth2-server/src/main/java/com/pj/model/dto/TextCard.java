package com.pj.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author 离狐千慕
 * @version 1.0
 * @date 2024/1/31 16:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TextCard {
    private String title;
    private String description;
    private String url;
    private String btnTxt;
}