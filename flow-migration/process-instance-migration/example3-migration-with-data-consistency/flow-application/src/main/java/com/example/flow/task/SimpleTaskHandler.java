package com.example.flow.task;

import com.backbase.flow.process.task.DefaultUserTaskHandler;
import com.backbase.flow.process.task.TaskContext;
import com.backbase.flow.process.task.TaskResult;
import com.example.flow.casedata.CaseDefinition;
import org.springframework.stereotype.Component;

@Component
public class SimpleTaskHandler extends DefaultUserTaskHandler<CaseDefinition, String, Void> {

    @Override
    public TaskResult<String> complete(TaskContext<CaseDefinition> taskContext) {
        CaseDefinition caseDataAsDto = taskContext.getCaseDataAsDTO();
        caseDataAsDto.setCompletedTask(true);
        return new TaskResult<>("completedTask");
    }
}
