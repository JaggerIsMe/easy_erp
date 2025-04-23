package com.easyerp.aspect;

import com.easyerp.annotation.GlobalInterceptor;
import com.easyerp.entity.enums.ResponseCodeEnum;
import com.easyerp.entity.po.UserInfo;
import com.easyerp.exception.BusinessException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 * @author Jagger
 * * @date 2025/4/23
 */
@Component("operationAspect")
@Aspect
public class GlobalOperationAspect {

    @Pointcut("@annotation(com.easyerp.annotation.GlobalInterceptor)")
    private void requestInterceptor() {
    }

    @Before("requestInterceptor()")
    public void interceptorDo(JoinPoint point) {
        try {
            Object target = point.getTarget();
            Object[] arguments = point.getArgs();
            String methodName = point.getSignature().getName();
            Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();
            Method method = target.getClass().getMethod(methodName, parameterTypes);
            GlobalInterceptor interceptor = method.getAnnotation(GlobalInterceptor.class);
            if (null == interceptor) {
                return;
            }

            /**
             * 校验登录 和 管理员权限
             */
            if (interceptor.checkLogin()) {
                checkLogin();
            }


        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(ResponseCodeEnum.CODE_500);
        }
    }

    private void checkLogin() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        UserInfo loginUserInfo = (UserInfo) session.getAttribute("loginUserInfo");
        if (null == loginUserInfo) {
            throw new BusinessException(ResponseCodeEnum.CODE_901);
        }
    }

}
