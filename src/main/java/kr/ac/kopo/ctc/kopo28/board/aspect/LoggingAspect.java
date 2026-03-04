package kr.ac.kopo.ctc.kopo28.board.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component


public class LoggingAspect {

    @Before("execution(* kr.ac.kopo.ctc.kopo28.board.service.*.*(..))")
    public void onBefore(JoinPoint joinPoint) {
        System.out.println("Method called: " + joinPoint.getSignature());
    }

    @After("execution(* kr.ac.kopo.ctc.kopo28.board.service.*.*(..))")
    public void onAfter(JoinPoint joinPoint) {
        System.out.println("Method finished: " + joinPoint.getSignature());
    }


    @AfterReturning(pointcut ="execution(* kr.ac.kopo.ctc.kopo28.board.service.*.*(..))", returning = "result")
    public void onAfterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("Method returned: " + joinPoint.getSignature());
        System.out.println("Result: " + result);
    }
    @AfterThrowing(pointcut = "execution(* kr.ac.kopo.ctc.kopo28.board.service.*.*(..))", throwing = "exception")
    public void onAfterThrowing(JoinPoint joinPoint, Exception exception) {
        System.out.println("Method threw: " + joinPoint.getSignature());
        System.out.println("Exception: " + exception.getMessage());

    }

    @Around("execution(* kr.ac.kopo.ctc.kopo28.board.service.*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Before method: " + joinPoint.getSignature());
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();

        System.out.println("After method: " + joinPoint.getSignature());
        System.out.println("Execution time: " + (end - start) + "ms");
        return result;
    }
}
