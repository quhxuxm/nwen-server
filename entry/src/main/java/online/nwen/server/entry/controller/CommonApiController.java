package online.nwen.server.entry.controller;

import online.nwen.server.common.constant.IConstant;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

@RestController
@RequestMapping(path = IConstant.RequestPath.COMMON_API)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CommonApiController {
}
