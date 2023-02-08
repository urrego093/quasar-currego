package com.mercadolibre.quasar.currego;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Pointcut("@annotation(com.mercadolibre.quasar.currego.TrackExecutionTimeTarget)")
    public void pointcut() {
    }

    /**
     * Logs how much the method execution lasted in order to create som metrics
     *
     * @param joinPoint The annotation that methods to be tracked will have, ist a custom one!
     * @return The returned object response, all mapped methods should have an Object response
     */
    @Around("pointcut()")
    public Object logMethod(ProceedingJoinPoint joinPoint) {

        try {
            long startTime = System.currentTimeMillis();
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Object object = joinPoint.proceed();
            long endtime = System.currentTimeMillis();
            log.info("==> class: {}, method(s): {}, elapsedTime: {}  ",
                    signature.getMethod().getDeclaringClass(), signature.getMethod().getName(),
                    (endtime - startTime) + "ms");
            return object;
        } catch (Throwable e) {
            log.error("Failed to log elapsed method time");
            throw new RuntimeException(e);
        }
    }

}
