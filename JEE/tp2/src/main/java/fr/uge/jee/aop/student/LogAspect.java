package fr.uge.jee.aop.student;

import java.util.Arrays;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public final class LogAspect {

    @Around("execution(* fr.uge.jee.aop.student.RegistrationService.create*(..))")
    public Object logCreate(ProceedingJoinPoint joinPoint) throws Throwable {
        var methodName = joinPoint.getSignature().getName();
        System.out.println("Calling " + methodName + " with arguments " + Arrays.toString(joinPoint.getArgs()));
        var res = joinPoint.proceed();
        System.out.println("Return id " + res + " by " + methodName);
        return res;
    }
}
