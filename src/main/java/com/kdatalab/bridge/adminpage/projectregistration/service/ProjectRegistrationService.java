package com.kdatalab.bridge.adminpage.projectregistration.service;

import com.kdatalab.bridge.adminpage.projectregistration.dto.AttachmentDto;
import com.kdatalab.bridge.adminpage.projectregistration.dto.ProjectRegistrationDto;
import com.kdatalab.bridge.adminpage.projectregistration.repository.ProjectRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectRegistrationService {

    private final ProjectRegistrationRepository projectRegistrationRepository;

    private final AWSService awsService;

    public void registerTask(Integer projectId, String regUser, Integer taskId) {
        projectRegistrationRepository.saveTbTaskDtl(projectId, regUser, taskId);
    }

    public void createProject(ProjectRegistrationDto dto) {
        projectRegistrationRepository.saveProject(dto.getProjectType(), dto.getProjectName(), dto.getProjectContent(), dto.getWorkDateInMinutes(), dto.getProjectStartDate(),
                dto.getProjectEndDate(), dto.getPointPerImage(), dto.getTaskUnit());
    }

    public void attachFileToTask(Integer projectId, String regUser, MultipartFile[] files) throws IOException {
        // list of tasks
        String projectName = "project3";
        List<Integer> taskIds = Arrays.asList(4,5,6);//,7,8);
        int taskUnit = 3;
        int length = files.length;//17
        int taskCount = length / taskUnit;//5
        int remainder = length % taskUnit; //2
        for (int i = 0; i < taskUnit; i++) {
            int taskId = taskIds.get(i);
            for(int j = 0; j < taskCount; j++) {
                AttachmentDto attachmentDto = new AttachmentDto();
                attachmentDto.setProjectId(projectId); //EDU_SEQ
                attachmentDto.setTaskId(taskId); //DTL_SEQ
                attachmentDto.setFileName(files[i+j].getOriginalFilename()); //NAME
                attachmentDto.setFilePath(generateTaskPath(projectName, taskId));//PATH
                attachmentDto.setFileSize(files[i+j].getSize());//SIZE
                attachmentDto.setFileExt(files[i+j].getContentType());//EXT
                attachmentDto.setRegUser(regUser);//REG_USER
                attachmentDto.setRegDate(LocalDateTime.now().toString());//REG_DATE
                attachmentDto.setModUser(regUser);//MOD_USER
                attachmentDto.setModDate(LocalDateTime.now().toString());//MOD_DATE
                System.out.println("attachmentDto = " + attachmentDto);
                //save to db image data
                saveImageData(attachmentDto);
                //save to s3 image
                awsService.uploadImageToAws(files[i+j],attachmentDto.getFilePath());
            }
        }
        for(int i = 0; i < remainder; i++) {
            int taskId = taskIds.get(i);
            AttachmentDto attachmentDto = new AttachmentDto();
            attachmentDto.setProjectId(projectId); //EDU_SEQ
            attachmentDto.setTaskId(taskId); //DTL_SEQ
            attachmentDto.setFileName(files[taskUnit*taskCount+i].getOriginalFilename()); //NAME
            attachmentDto.setFilePath(generateTaskPath(projectName, taskId));//PATH
            attachmentDto.setFileSize(files[taskUnit*taskCount+i].getSize());//SIZE
            attachmentDto.setFileExt(files[taskUnit*taskCount+i].getContentType());//EXT
            attachmentDto.setRegUser(regUser);//REG_USER
            attachmentDto.setRegDate(LocalDateTime.now().toString());//REG_DATE
            attachmentDto.setModUser(regUser);//MOD_USER
            attachmentDto.setModDate(LocalDateTime.now().toString());//MOD_DATE
            System.out.println("attachmentDto = " + attachmentDto);
            //save to db image data
            saveImageData(attachmentDto);
            //save to aws s3 image
            awsService.uploadImageToAws(files[taskUnit*taskCount+i],attachmentDto.getFilePath());
        }

    }

    public void saveImageData(AttachmentDto attachmentDto) {
        //save to db image data
        projectRegistrationRepository.saveTbAtt(attachmentDto.getProjectId(), attachmentDto.getTaskId(), attachmentDto.getFileName(),
                attachmentDto.getFilePath(), attachmentDto.getFileSize(), attachmentDto.getFileExt(), attachmentDto.getRegUser(),
                attachmentDto.getRegDate(), attachmentDto.getModUser(), attachmentDto.getModDate());
    }

    private String generateTaskPath(String projectName, Integer taskId) {
        return "module/" + projectName + "/" + "task" + taskId;
    }

}
