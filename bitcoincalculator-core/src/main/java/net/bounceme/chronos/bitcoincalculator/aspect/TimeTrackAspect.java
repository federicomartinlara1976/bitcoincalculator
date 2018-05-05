package net.bounceme.chronos.bitcoincalculator.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.jboss.logging.Logger;

@Aspect
public class TimeTrackAspect {
	private static Logger log = Logger.getLogger(TimeTrackAspect.class.getName());
	
	private Long start;
	
	@Before("execution(* net.bounceme.chronos.bitcoincalculator.services.impl.*.*(..))")
	public void before(JoinPoint joinPoint) {
		start = System.currentTimeMillis();
	}
	
	@After("execution(* net.bounceme.chronos.bitcoincalculator.services.impl.*.*(..))")
	public void after(JoinPoint joinPoint) {
		Long elapsedTime = System.currentTimeMillis() - start;
		log.info(joinPoint.getSignature().getName() + " execution time: " + elapsedTime + " milliseconds");
	}
}
