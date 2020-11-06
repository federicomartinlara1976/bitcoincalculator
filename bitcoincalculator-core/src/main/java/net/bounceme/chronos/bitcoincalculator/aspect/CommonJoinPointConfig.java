package net.bounceme.chronos.bitcoincalculator.aspect;

import org.aspectj.lang.annotation.Pointcut;

/**
 * @author federico
 *
 */
public class CommonJoinPointConfig {
	
	/**
	 * 
	 */
	@Pointcut("@annotation(net.bounceme.chronos.bitcoincalculator.aspect.TrackTime)")
	public void trackTimeAnnotation(){}

}
