package com.kdatalab.bridge.adminpage.projectregistration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProjectRegistrationDto {
    Integer projectId;
    String projectType;
    String projectName;
    String projectContent;
    Integer workDateInMinutes;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    LocalDate projectStartDate;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    LocalDate projectEndDate;
    Integer pointPerImage;
    Integer taskUnit;
    List<MultipartFile> files;
}
