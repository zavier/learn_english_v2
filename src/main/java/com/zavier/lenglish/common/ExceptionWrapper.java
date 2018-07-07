package com.zavier.lenglish.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
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

    @Pointcut("execution(public ResultBean com.zavier.lenglish.web..*.*(..))")
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
        }  catch (UnknownAccountException ae) {
            log.info("用户名不存在");
            return ResultBean.createByErrorMessage("用户名不存在");
        } catch (AuthenticationException e) {
            log.info("认证失败");
            return ResultBean.createByErrorMessage("用户名或密码错误");
        } catch (BusinessProcessException e) {
            log.error("业务处理异常", e);
            return ResultBean.createByErrorMessage(e.getMessage());
        } catch (Throwable e) {
            log.error("系统异常", e);
            return ResultBean.createByErrorMessage("系统内部错误，请稍后重试");
        }
        return resultBean;
    }
}
