package com.mercadolibre.quasar.currego;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Pointcut("@annotation(com.mercadolibre.quasar.currego.TrackExecutionTimeTarget)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object logMethod(ProceedingJoinPoint joinPoint) {

        try {
            long startTime = System.currentTimeMillis();
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Object object = joinPoint.proceed();
            long endtime = System.currentTimeMillis();
            log.info("==> class: {}, method(s): {}, elapsedTime: {}  ",
                    signature.getMethod().getDeclaringClass(), signature.getMethod().getName(),
                    (endtime-startTime) +"ms" );
            return object;
        } catch (Throwable e) {
            log.error("Failed to log elapsed method time");
            throw new RuntimeException(e);
        }
    }

}
