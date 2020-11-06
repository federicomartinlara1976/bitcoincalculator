package net.bounceme.chronos.bitcoincalculator.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import lombok.extern.log4j.Log4j;
import net.bounceme.chronos.bitcoincalculator.utils.LogWrapper;

@Aspect
@Log4j
public class TimeTrackAspect {
	
	private Long start;
	
	@Before("execution(* net.bounceme.chronos.bitcoincalculator.services.impl.*.*(..))")
	public void before(JoinPoint joinPoint) {
		start = System.currentTimeMillis();
	}
	
	@After("execution(* net.bounceme.chronos.bitcoincalculator.services.impl.*.*(..))")
	public void after(JoinPoint joinPoint) {
		Long elapsedTime = System.currentTimeMillis() - start;
		LogWrapper.debug(log, "%s execution time: %d milliseconds", joinPoint.getSignature().getName(), elapsedTime);
	}
}
