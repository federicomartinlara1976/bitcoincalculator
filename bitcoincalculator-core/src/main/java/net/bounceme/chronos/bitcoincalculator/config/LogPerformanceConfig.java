package net.bounceme.chronos.bitcoincalculator.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"net.bounceme.chronos.bitcoincalculator.aspect"})
public class LogPerformanceConfig {

}
