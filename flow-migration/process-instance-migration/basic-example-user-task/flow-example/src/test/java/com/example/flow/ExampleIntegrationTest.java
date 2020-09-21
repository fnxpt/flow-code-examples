package com.example.flow;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.backbase.flow.casedata.cases.Case;
import com.backbase.flow.casedata.cases.CaseRepository;
import com.backbase.flow.iam.util.WithMockUserPermissions;
import com.backbase.flow.interaction.api.model.InteractionRequestDto;
import com.example.flow.casedata.CaseDefinition;
import com.example.flow.interaction.SimpleStepHandler.SimpleStepHandlerResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.camunda.bpm.engine.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ExampleIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CaseRepository caseRepository;

    @Autowired
    private TaskService taskService;

    @Test
    @WithMockUserPermissions(username = "admin")
    public void shouldCreateCaseHandleInteractionAndHandleTask() throws Exception {

        // Start a case, interaction and process

        SimpleStepHandlerResponseBody response = startInteraction();

        // Verify case, interaction and process have started successfully

        verifyCaseAndProcessHaveStarted(response);

        // Find the task spawned by the process and complete it

        completeTask(response.getBody().getProcessId());
    }

    private void verifyCaseAndProcessHaveStarted(SimpleStepHandlerResponseBody response) {
        Optional<Case> caseInstance = caseRepository.findByKey(response.getBody().getCaseKey());

        assertThat(caseInstance).isPresent();

        CaseDefinition caseData = caseInstance.get().getCaseData(CaseDefinition.class);

        assertThat(caseData.getStartedCase()).isTrue();
        assertThat(caseData.getStartedProcess()).isTrue();
    }

    private SimpleStepHandlerResponseBody startInteraction() throws Exception {

        InteractionRequestDto interactionRequestData = new InteractionRequestDto();
        CaseDefinition payload = new CaseDefinition();
        payload.setStartedCase(true);
        interactionRequestData.setPayload(payload);

        String responseBody = mockMvc
            .perform(post("/api/interaction/v1/interactions/interaction/actions/simple-step-handler")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(interactionRequestData)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.interactionId").exists())
            .andExpect(jsonPath("$.step.name").value("done"))
            .andExpect(jsonPath("$.body.caseKey").exists())
            .andReturn()
            .getResponse()
            .getContentAsString();

        return objectMapper.readValue(responseBody, SimpleStepHandlerResponseBody.class);
    }

    private void completeTask(String processId) throws Exception {

        String taskId = taskService.createTaskQuery()
            .processInstanceId(processId)
            .list()
            .get(0)
            .getId();

        mockMvc
            .perform(post("/api/process/v1/task/{taskId}/complete", taskId)
                .contentType(APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isAccepted());
    }

    public static class SimpleStepHandlerResponseBody {

        private SimpleStepHandlerResponse body;

        public SimpleStepHandlerResponse getBody() {
            return body;
        }

        public void setBody(SimpleStepHandlerResponse body) {
            this.body = body;
        }
    }
}
