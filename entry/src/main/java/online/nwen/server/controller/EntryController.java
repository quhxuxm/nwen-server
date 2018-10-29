package online.nwen.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import online.nwen.server.executor.api.IExecutorInvoker;
import online.nwen.server.payload.ExceptionPayload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
class EntryController {
    private ObjectMapper objectMapper;
    private IExecutorInvoker executorInvoker;

    EntryController(IExecutorInvoker executorInvoker) {
        this.executorInvoker = executorInvoker;
        this.objectMapper = new ObjectMapper();
    }

    @RequestMapping("/insecure")
    EntryResponse execInsecureApi(EntryRequest request) {
        String executorName = request.getHeader().get(IEntryConstant.EXECUTOR_NAME);
        if (executorName == null) {
            return this.generateExceptionEntryResponse(ExceptionPayload.Code.INPUT_ERROR);
        }
        Object payload = request.getPayload();
        this.executorInvoker.invoke(this.objectMapper.readValue(payload, ));
        return null;
    }

    @RequestMapping("/secure")
    EntryResponse execSecureApi(EntryRequest request) {
        return null;
    }

    private EntryResponse generateExceptionEntryResponse(ExceptionPayload.Code code) {
        EntryResponse response = new EntryResponse();
        ExceptionPayload exceptionPayload = new ExceptionPayload();
        exceptionPayload.setCode(code);
        try {
            response.setPayload(this.objectMapper.writeValueAsString(exceptionPayload));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return response;
    }
}
