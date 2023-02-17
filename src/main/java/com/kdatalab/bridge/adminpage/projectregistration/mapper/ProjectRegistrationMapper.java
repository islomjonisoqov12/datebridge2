package com.kdatalab.bridge.adminpage.projectregistration.mapper;

import com.kdatalab.bridge.adminpage.projectregistration.dto.TaskDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectRegistrationMapper {

    List<TaskDto> selectTasksByProjectId(Long projectId);
}
