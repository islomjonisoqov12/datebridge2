package com.kdatalab.bridge.adminpage.projectregistration.service;

import com.kdatalab.bridge.adminpage.projectregistration.dto.AttachmentDto;
import com.kdatalab.bridge.adminpage.projectregistration.dto.ProjectRegistrationDto;
import com.kdatalab.bridge.adminpage.projectregistration.dto.TaskDto;
import com.kdatalab.bridge.adminpage.projectregistration.mapper.ProjectRegistrationMapper;
import com.kdatalab.bridge.adminpage.projectregistration.repository.ProjectRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectRegistrationService {

    private final ProjectRegistrationRepository projectRegistrationRepository;

    private final AWSService awsService;
    private final ProjectRegistrationMapper mapper;


    public void registerTask(Integer projectId, String regUser, Integer taskUnit) {
        projectRegistrationRepository.saveTbTaskDtl(projectId, regUser, taskUnit);
    }

    public Integer createProject(ProjectRegistrationDto dto) throws IOException {

        String userId = "yanghee";//TODO userId get from session
        Integer projectId;
        if (dto.getProjectId() == null) {
            projectId = projectRegistrationRepository.saveProject(dto.getProjectType(), dto.getProjectName(), dto.getProjectContent(),
                    dto.getWorkDateInMinutes(), dto.getProjectStartDate(), dto.getProjectEndDate(), dto.getPointPerImage(), dto.getTaskUnit(), userId);
            //generate task
            registerTask(projectId, userId, dto.getTaskUnit());
            //save image to aws s3 and image data to tb_att table
            attachFileToTask(projectId, dto.getProjectName(), dto.getTaskUnit(), userId, dto.getFiles());
        } else {
            //update project
            projectId = dto.getProjectId();
            projectRegistrationRepository.updateProject(projectId, dto.getProjectName(), dto.getProjectContent(),
                    dto.getWorkDateInMinutes(), dto.getProjectStartDate(), dto.getProjectEndDate(), userId);
        }
        return projectId;
    }

    public void attachFileToTask(Integer projectId,String projectName, Integer taskUnit, String regUser, List<MultipartFile> files) throws IOException {
        // list of tasks
        List<Integer> taskIds = projectRegistrationRepository.getTasksByProjectId(projectId);
        int length = files.size();//17
        int taskCount = length / taskUnit;//5
        int remainder = length % taskUnit; //2
        for (int i = 0; i < taskUnit; i++) {
            int taskId = taskIds.get(i);
            for(int j = 0; j < taskCount; j++) {
                int index = i + j;
                AttachmentDto attachmentDto = createAttachment(projectId,
                                                               taskId,
                                                               files.get(index).getOriginalFilename(),
                                                               generateTaskPath(projectName, taskId),
                                                               files.get(index).getContentType(),
                                                               files.get(index).getSize(),regUser);
                //save to db image data
                saveImageData(attachmentDto);
                //save to s3 image
                awsService.uploadImageToAws(files.get(index),attachmentDto.getFilePath());
            }
        }
        for(int i = 0; i < remainder; i++) {
            int taskId = taskIds.get(i);
            int index = taskUnit*taskCount+i;
            AttachmentDto attachmentDto = createAttachment(projectId,
                                                           taskId,
                                                           files.get(index).getOriginalFilename(),
                                                           generateTaskPath(projectName, taskId),
                                                           files.get(index).getContentType(),
                                                           files.get(index).getSize(),regUser);
            //save to db image data
            saveImageData(attachmentDto);
            //save to aws s3 image
            awsService.uploadImageToAws(files.get(index),attachmentDto.getFilePath());
        }
    }

    private AttachmentDto createAttachment(Integer projectId, Integer taskId, String fileName, String filePath,
                                           String fileExt, Long fileSize, String regUser) {
        AttachmentDto attachmentDto = new AttachmentDto();
        attachmentDto.setProjectId(projectId); //EDU_SEQ
        attachmentDto.setTaskId(taskId); //DTL_SEQ
        attachmentDto.setFileName(fileName); //NAME
        attachmentDto.setFilePath(filePath);//PATH
        attachmentDto.setFileExt(fileExt);//EXT
        attachmentDto.setFileSize(fileSize);//SIZE
        attachmentDto.setRegUser(regUser);//REG_USER
        return attachmentDto;
    }

    public void saveImageData(AttachmentDto attachmentDto) {
        //save to db image data
        projectRegistrationRepository.saveTbAtt(attachmentDto.getProjectId(),
                                                attachmentDto.getTaskId(),
                                                attachmentDto.getFileName(),
                                                attachmentDto.getFilePath(),
                                                attachmentDto.getFileSize(),
                                                attachmentDto.getFileExt(),
                                                attachmentDto.getRegUser());
    }

    private String generateTaskPath(String projectName, Integer taskId) {
        return "module/" + projectName + "/" + "task" + taskId;
    }

    public List<TaskDto> getProjectWithTasks(Long projectId) {
        return mapper.selectTasksByProjectId(projectId);
    }

    public boolean existProjectName(String projectName) {
        int cnt = projectRegistrationRepository.existProjectName(projectName.toLowerCase());
        return cnt > 0;
    }
}
