package rewards.internal.aspects;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import rewards.internal.exception.RewardDataAccessException;

@Aspect
@Component
public class DBExceptionHandlingAspect {

	public static final String EMAIL_FAILURE_MSG = "Failed sending an email to Mister Smith : ";
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@AfterThrowing(value = "execution(* *..*Repository.*(..))", throwing = "e")
	void implExceptionHandling(RewardDataAccessException e) {
		logger.warn("%s%s%n".formatted(EMAIL_FAILURE_MSG, e));
	}
}
