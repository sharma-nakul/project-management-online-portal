package edu.sjsu.cmpe275.project.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Naks
 *         Aspect class to enforce restrictions on User related methods.
 */
@Aspect
@Configuration
@EnableAspectJAutoProxy
public class UserAspect {

    /**
     * Variable of type logger to print on console
     */
    private static final Logger logger = LoggerFactory.getLogger(UserAspect.class);

    @Before("within(edu.sjsu.cmpe275.project.service.*)")
    public void printMessageWheneverUserIsCalled() {
        logger.info("Aspect is executing before advice");
    }

    @After("execution(* edu.sjsu.cmpe275.project.service.IUserService.verifyCredentials(..))")
    public void printEmailAfterLogin(JoinPoint joinPoint) {
        Object args[] = joinPoint.getArgs();
        String email = args[0].toString();
        logger.info("Aspect: Email Id -" + email);
    }

    /* a. Once project created, Project state should be Planning */
    /*@Around("execution(long createProject(..))")
    public void checkStatusAfterCreated(org.aspectj.lang.JoinPoint joinPoint)
    {
        System.out.println("**************Before Execution*******");

        Object Args[] = joinPoint.getArgs();
        Project.ProjectState getProjectStatus = (Project.ProjectState)Args[2];
        System.out.println("*********Before Print*****");
        System.out.println(getProjectStatus);
        System.out.println("Before condition");
        if(getProjectStatus.compareTo(Project.ProjectState.PLANNING) != 0 ) {
            System.out.println("Inside Check");
            System.out.println("Check Project Status");
        }
    }*/





}
