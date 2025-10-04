package com.sinan.hegsHaber.mapper;

import com.sinan.hegsHaber.dto.TaskDto;
import com.sinan.hegsHaber.entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDto toTaskDto(Task task);

    Task toTask(TaskDto taskDto);
}
