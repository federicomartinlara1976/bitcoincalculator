package net.bounceme.chronos.bitcoincalculator.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

import lombok.extern.log4j.Log4j;
import net.bounceme.chronos.bitcoincalculator.utils.LogWrapper;

@Configuration
@Aspect
@Log4j
public class LogTrace {

	@Around("net.bounceme.chronos.bitcoincalculator.aspect.CommonJoinPointConfig.trackTimeAnnotation()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();

		Object returnProceed = joinPoint.proceed();

		long timeTaken = System.currentTimeMillis() - startTime;
		LogWrapper.debug(log, "%s execution time: %d milliseconds", joinPoint.getSignature(), timeTaken);

		return returnProceed;
	}
}
