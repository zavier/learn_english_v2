package com.zavier.scaffold.common;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 异常处理
 */
@Aspect
@Component
@Slf4j
public class ExceptionWrapper {

    @Pointcut("execution(public ResultBean com.zavier.scaffold.web..*.*(..))")
    public void pointCut(){}

    @Around("pointCut()")
    public ResultBean around(ProceedingJoinPoint pjp) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        long startTime = System.currentTimeMillis();
        log.info("URL : " + request.getRequestURL().toString());
        log.info("HTTP_METHOD : " + request.getMethod());
        log.info("IP : " + request.getRemoteAddr());
        String classMethod = pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName();
        log.info("CLASS_METHOD : " + classMethod);
        log.info("ARGS : " + Arrays.toString(pjp.getArgs()));

        ResultBean resultBean;
        try {
            resultBean = (ResultBean) pjp.proceed();
            long endTime = System.currentTimeMillis();
            log.info("{} 用时: {} ms", classMethod, (endTime - startTime));
        } catch (ProcessException e) {
            log.error("业务处理异常", e);
            return ResultBean.createByErrorMessage(e.getMessage());
        } catch (Throwable e) {
            log.error("系统异常", e);
            return ResultBean.createByErrorMessage("系统内部错误，请稍后重试");
        }
        return resultBean;
    }
}
