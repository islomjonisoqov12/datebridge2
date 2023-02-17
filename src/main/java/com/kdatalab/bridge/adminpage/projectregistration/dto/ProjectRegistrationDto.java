package com.kdatalab.bridge.adminpage.projectregistration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProjectRegistrationDto {
    Integer projectId;
    String projectType;
    String projectName;
    String projectContent;
    String workDateInMinutes;
    String projectStartDate;
    String projectEndDate;
    Integer pointPerImage;
    Integer taskUnit;
    List<MultipartFile> files;
}
