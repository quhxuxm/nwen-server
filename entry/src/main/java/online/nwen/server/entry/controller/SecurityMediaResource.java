package online.nwen.server.entry.controller;

import online.nwen.server.common.constant.IConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.*;

@Controller
@RequestMapping(IConstant.RequestPath.SECURITY_MEDIA_RESOURCE)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SecurityMediaResource {
}
