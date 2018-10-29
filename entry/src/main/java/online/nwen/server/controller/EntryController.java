package online.nwen.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import online.nwen.server.executor.api.IExecutorInvoker;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
class EntryController {
    private ObjectMapper objectMapper;
    private IExecutorInvoker executorInvoker;

    EntryController(IExecutorInvoker executorInvoker) {
        this.executorInvoker = executorInvoker;
        this.objectMapper=new ObjectMapper();
    }

    @RequestMapping("/insecure")
    EntryResponse execInsecureApi(EntryRequest request) {
        String payload=request.getPayload();
        this.executorInvoker.invoke(this.objectMapper.readValue(payload, ));
        return null;
    }

    @RequestMapping("/secure")
    EntryResponse execSecureApi(EntryRequest request) {
        return null;
    }
}
