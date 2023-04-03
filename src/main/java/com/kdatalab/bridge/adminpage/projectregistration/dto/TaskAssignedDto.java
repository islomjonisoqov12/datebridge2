package com.kdatalab.bridge.adminpage.projectregistration.dto;

import com.kdatalab.bridge.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskAssignedDto {
    List<TaskDto> tasks;
    List<UserDto> users;
    List<UserDto> admins;
    Boolean edited;
}
