package com.hyj.edu.entity.chapter;

import lombok.Data;

import java.io.Serializable;
@Data
public class VideoVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String title;

    private String videoSourceId;
}
