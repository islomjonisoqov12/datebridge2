package com.kdatalab.bridge.adminpage.projectregistration.service;

import com.kdatalab.bridge.adminpage.projectregistration.dto.AttachmentDto;
import com.kdatalab.bridge.adminpage.projectregistration.dto.ProjectRegistrationDto;
import com.kdatalab.bridge.adminpage.projectregistration.dto.TaskAssignedDto;
import com.kdatalab.bridge.adminpage.projectregistration.dto.TaskDto;
import com.kdatalab.bridge.adminpage.projectregistration.mapper.ProjectRegistrationMapper;
import com.kdatalab.bridge.adminpage.projectregistration.repository.ProjectRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

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

    public ProjectRegistrationDto getProjectDetailsById(Integer projectId) {
        Map<String, Object> projectDetails = projectRegistrationRepository.getProjectDetails(projectId);
        ProjectRegistrationDto dto = new ProjectRegistrationDto();
        dto.setProjectId((Integer) projectDetails.get("EDU_SEQ"));
        dto.setProjectType((String) projectDetails.get("EDU_TYPE"));
        dto.setProjectName((String) projectDetails.get("SUBJECT"));
        dto.setProjectContent((String) projectDetails.get("CONTENT"));
        dto.setWorkDateInMinutes((Integer) projectDetails.get("TIME"));
        dto.setProjectStartDate(((java.sql.Timestamp)projectDetails.get("START_DATE")).toLocalDateTime().toLocalDate());
        dto.setProjectEndDate(((java.sql.Timestamp)projectDetails.get("END_DATE")).toLocalDateTime().toLocalDate());
        dto.setPointPerImage((Integer) projectDetails.get("POINT"));
        dto.setTaskUnit(projectDetails.get("TASK_UNIT") == null ? 0 : Integer.parseInt(projectDetails.get("TASK_UNIT").toString()));
        return dto;
    }

    public BiFunction<List<TaskDto>, Integer, String> tasksToJson = (tasks, projectId) -> {
        StringBuilder res = new StringBuilder("[");
        String loginId = "";
        String qcId = "";
        if (tasks.size()==0) {
            return "";
        }
        List<TaskDto> projectWithTasks = getProjectWithTasks((long) projectId);
        for (int i = 0; i < tasks.size(); i++) {
            TaskDto t = tasks.get(i);

            if (t.getDtlSeq() != null) {
                TaskDto taskDto = projectWithTasks.get(i);
                if(!taskDto.getDtlSeq().equals(t.getDtlSeq())){
                    taskDto = projectWithTasks.stream().filter(t1 -> t1.getDtlSeq().equals(t.getDtlSeq())).findFirst().get();
                }
                if (t.getLoginId() == null || taskDto.getTaskDtlProg()>0) loginId = taskDto.getLoginId(); // progress > 0 dan bo'lganda uni bajaruvchisini o'zgartirib bo'lmaydi (ayyor dasturchilar uchun)
                else if (t.getLoginId().isEmpty() && taskDto.getTaskDtlProg()==0) loginId = null;  // progress=0 ga bo'lsa va oldin buni bajaruvchisi tayinlanga bo'lganda. hozir esa o'sha tayinlanganni olib tashlasa null qo'yiladi
                else if(taskDto.getTaskDtlProg()==0) loginId = String.format("\"%s\"", t.getLoginId()); // progress = 0 va yuqoridagi tekshiruvlardan o'tsa login id o'zgartiriladi
                else loginId = taskDto.getLoginId(); // agar yuqoridagi shartlardan o'tmasa demak o'zgartirilmaydi

                if (t.getQcId() == null) qcId = taskDto.getQcId();   // yani select option da disabled bo'lsa null keladi
                else if (t.getQcId().isEmpty() && taskDto.getTaskDtlProg()==0) qcId = null; // yani progres hali 0 ga teng va task ni tekshiruvchisi oldin saqlangan. hozir esa o'shani null qilib qo'yganda
                else if(taskDto.getTaskDtlProg()>0 && taskDto.getQcId() != null) qcId = taskDto.getQcId();  // yani progress 0 dan katta va oldin bu taskni tekshiruvchisi tanlagan bo'lgan holatda o'zgartirmaydi(ayyor dasturchilar uchun check)
                else qcId = String.format("\"%s\"", t.getQcId()); // shartlardan birortasiga tushmasa qcId ni qiymati yangi tanlagangan userga o'zgartiriladi

                res.append("{\"dtlSeq\":").append(t.getDtlSeq()).append(",\"loginId\":").append(loginId).append(", \"qcId\":").append(qcId).append("},");
            }
        }
        res.replace(res.length()-1, res.length(),"]");
        return res.toString();
    };
    public void saveAssignedUsers(TaskAssignedDto dto, String name, int projectId) {
        String tasks = tasksToJson.apply(dto.getTasks(), projectId);
        int i = projectRegistrationRepository.saveAssignedUsers(tasks, name);
        System.out.println("print int: "+i);
    }
}
