package rewards.internal.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import rewards.internal.monitor.Monitor;
import rewards.internal.monitor.MonitorFactory;

@Aspect
@Component
public class LoggingAspect {
	public static final String BEFORE = "'Before'";
	public static final String AROUND = "'Around'";

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final MonitorFactory monitorFactory;

	public LoggingAspect(MonitorFactory monitorFactory) {
		this.monitorFactory = monitorFactory;
	}

	@Before("execution(* *..*Repository.find*(..))")
	void implLogging(JoinPoint joinPoint) {
		// Do not modify this log message or the test will fail
		logger.info("{} advice implementation - {}; Executing before {}() method", BEFORE,
				joinPoint.getTarget().getClass(), joinPoint.getSignature().getName());
	}

	@Around("execution(* *..*Repository.update*(..))")
	Object monitor(ProceedingJoinPoint repositoryMethod) throws Throwable {
		String name = createJoinPointTraceName(repositoryMethod);
		Monitor monitor = monitorFactory.start(name);
		try {
			// Invoke repository method ...
			return repositoryMethod.proceed();
		} finally {
			monitor.stop();
			// Do not modify this log message or the test will fail
			logger.info("{} advice implementation - {}", AROUND, monitor);
		}
	}
		
	private String createJoinPointTraceName(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
        return signature.getDeclaringType().getSimpleName() +
               '.' + signature.getName();
	} 
}