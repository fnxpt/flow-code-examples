package com.example.flow.interaction;

import com.backbase.flow.casedata.CaseDataService;
import com.backbase.flow.interaction.engine.action.ActionHandler;
import com.backbase.flow.interaction.engine.action.ActionResult;
import com.backbase.flow.interaction.engine.action.InteractionContext;
import com.example.flow.casedata.CaseDefinition;
import com.example.flow.interaction.SimpleStepHandler.SimpleStepHandlerResponse;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component("simple-step-handler")
@RequiredArgsConstructor
public class SimpleStepHandler implements ActionHandler<CaseDefinition, SimpleStepHandlerResponse> {

    private final CaseDataService caseDataService;

    @Override
    public ActionResult<SimpleStepHandlerResponse> handle(CaseDefinition simpleCaseDefinition,
        InteractionContext interactionContext) {

        interactionContext.saveCaseData(simpleCaseDefinition);

        String processId = interactionContext.startProcess(interactionContext.getCaseKey(), "simple-process");

        simpleCaseDefinition.setStartedProcess(true);

        interactionContext.saveCaseData(simpleCaseDefinition);

        return new ActionResult<>(new SimpleStepHandlerResponse(interactionContext.getCaseKey(), processId));
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SimpleStepHandlerResponse {

        private UUID caseKey;
        private String processId;
    }
}