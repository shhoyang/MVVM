package com.mvvm.aop

import com.mvvm.MVVMLibrary
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import kotlin.jvm.Throws

/**
 * @author Yang Shihao
 *
 * 判断登录
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class CheckLogin

@Aspect
class CheckLoginAspect {

    @Pointcut("execution(@com.mvvm.aop.CheckLogin * *(..))")
    fun pointcutLogin() {

    }

    @Around("pointcutLogin()")
    @Throws(Throwable::class)
    fun aroundLogin(joinPoint: ProceedingJoinPoint) {
        val signature = joinPoint.signature as MethodSignature
        if (null != signature.method.getAnnotation(CheckLogin::class.java)) {
            if (MVVMLibrary.CONFIG.loginHandler.isLogin()) {
                joinPoint.proceed()
            } else {
                MVVMLibrary.CONFIG.loginHandler.login()
            }
        }
    }
}