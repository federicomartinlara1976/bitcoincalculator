package net.bounceme.chronos.bitcoincalculator.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class TimeTrackAspect {
	private static final Logger LOGGER = Logger.getLogger(TimeTrackAspect.class);
	
	private long startTime;
	private long endTime;
	
	@Before("execution(* net.bounceme.chronos.buscoloteria.services.impl.*.*(..))")
	public void trackBefore(JoinPoint joinPoint) {
		startTime = System.currentTimeMillis();
		LOGGER.debug(joinPoint.getSignature().getName() + " start: " + startTime);
	}
	
	@After("execution(* net.bounceme.chronos.buscoloteria.services.impl.*.*(..))")
	public void trackAfter(JoinPoint joinPoint) {
		endTime = System.currentTimeMillis();
		LOGGER.debug(joinPoint.getSignature().getName() + " end: " + endTime);
		LOGGER.debug(joinPoint.getSignature().getName() + " execution time: " + (endTime - startTime));
	}
}
