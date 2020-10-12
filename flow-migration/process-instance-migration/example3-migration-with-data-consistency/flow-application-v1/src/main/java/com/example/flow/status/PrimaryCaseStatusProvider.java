package com.example.flow.status;

import com.backbase.flow.casedata.CaseDefinitionsProperties;
import com.backbase.flow.casedata.cases.CaseRepository;
import com.backbase.flow.casedata.status.CaseEvent;
import com.backbase.flow.casedata.status.StatusProvider;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PrimaryCaseStatusProvider extends StatusProvider {

    public PrimaryCaseStatusProvider(CaseDefinitionsProperties caseDefinitionProperties,
        CaseRepository caseRepository) {

        super(caseDefinitionProperties, caseRepository);
    }

    @Override
    protected List<Class<? extends CaseEvent>> getProcessableEventClasses() {
        return new ArrayList<>();
    }

    @Override
    protected String determineCaseStatusValue(CaseEvent caseEvent) {
        return "open";
    }
}
