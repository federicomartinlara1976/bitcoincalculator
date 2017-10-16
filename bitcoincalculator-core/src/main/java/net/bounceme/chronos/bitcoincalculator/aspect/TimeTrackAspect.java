package net.bounceme.chronos.bitcoincalculator.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import net.bounceme.chronos.utils.log.Log;
import net.bounceme.chronos.utils.log.Log.LogLevels;
import net.bounceme.chronos.utils.log.LogFactory;

@Aspect
public class TimeTrackAspect {
	private static final Log LOGGER = LogFactory.getInstance().getLog(TimeTrackAspect.class);
	
	private Long start;
	
	@Before("execution(* net.bounceme.chronos.bitcoincalculator.services.impl.*.*(..))")
	public void before(JoinPoint joinPoint) {
		start = System.currentTimeMillis();
	}
	
	@After("execution(* net.bounceme.chronos.bitcoincalculator.services.impl.*.*(..))")
	public void after(JoinPoint joinPoint) {
		Long elapsedTime = System.currentTimeMillis() - start;
		LOGGER.log(LogLevels.DEBUG, joinPoint.getSignature().getName() + " execution time: " + elapsedTime + " milliseconds");
	}
}
