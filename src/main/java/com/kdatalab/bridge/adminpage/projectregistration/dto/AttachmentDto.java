package com.kdatalab.bridge.adminpage.projectregistration.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttachmentDto {
    private Integer attachmentId;
    private Integer projectId;
    private Integer taskId;
    private String fileName;
    private String filePath;
    private Long fileSize;
    private String fileExt;
    private String regUser;
    private String regDate;
    private String modUser;
    private String modDate;
}
