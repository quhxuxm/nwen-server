package online.nwen.server.controller;

import online.nwen.server.executor.api.IExecutor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
class ExecutorRegistry {
    private Map<String, IExecutor<?,?>> executors;

    ExecutorRegistry() {
        this.executors = new HashMap<>();
    }

    <ResponsePayloadTye, RequestPayloadType> IExecutor<ResponsePayloadTye, RequestPayloadType> getExecutor(
            String name) {
        IExecutor<ResponsePayloadTye, RequestPayloadType> executor=
                (IExecutor<ResponsePayloadTye, RequestPayloadType>) this.executors.get(name);
    }
}
