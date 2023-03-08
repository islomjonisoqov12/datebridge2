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
    int workDateInMinutes;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    LocalDate projectStartDate;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    LocalDate projectEndDate;
    int pointPerImage;
    int taskUnit;
    List<MultipartFile> files;
}
