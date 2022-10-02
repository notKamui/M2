package fr.uge.jee.aop.student;

import java.util.ArrayList;
import java.util.List;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public final class DBTimerAspect {

    private final List<Long> saveTimes = new ArrayList<>();
    private final List<Long> loadTimes = new ArrayList<>();

    public void printReport() {
        System.out.println("DB timing report:");
        System.out.println("\tsaveToDB");
        System.out.println("\t\tMeasured access times: " + saveTimes);
        System.out.println("\t\tAverage time: " + saveTimes.stream().mapToLong(Long::longValue).average().orElse(0));
        System.out.println("\tloadFromDB");
        System.out.println("\t\tMeasured access times: " + loadTimes);
        System.out.println("\t\tAverage time: " + loadTimes.stream().mapToLong(Long::longValue).average().orElse(0));
    }

    @Around("execution(* fr.uge.jee.aop.student.RegistrationService.saveToDB(..))")
    public Object timeSaveToDB(ProceedingJoinPoint joinPoint) throws Throwable {
        return time(joinPoint, saveTimes);
    }

    @Around("execution(* fr.uge.jee.aop.student.RegistrationService.loadFromDB(..))")
    public Object timeLoadFromDB(ProceedingJoinPoint joinPoint) throws Throwable {
        return time(joinPoint, loadTimes);
    }

    private Object time(ProceedingJoinPoint joinPoint, List<Long> listToAdd) throws Throwable {
        var start = System.currentTimeMillis();
        var res = joinPoint.proceed();
        var end = System.currentTimeMillis();
        listToAdd.add(end - start);
        return res;
    }
}
