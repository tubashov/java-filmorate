package ru.yandex.practicum.filmorate.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

//    // Логируем все методы в пакетах controller, service, storage
//    @Around("execution(* ru.yandex.practicum.filmorate.controller..*(..)) || " +
//            "execution(* ru.yandex.practicum.filmorate.service..*(..)) || " +
//            "execution(* ru.yandex.practicum.filmorate.storage..*(..))")
//    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
//        String methodName = joinPoint.getSignature().toShortString();
//        Object[] args = joinPoint.getArgs();
//        log.info("➡️ Вызов метода: {} с аргументами: {}", methodName, args);
//
//        try {
//            Object result = joinPoint.proceed();
//            log.info("⬅️ Метод {} вернул: {}", methodName, result);
//            return result;
//        } catch (Exception ex) {
//            log.error("❌ Ошибка в методе {}: {}", methodName, ex.getMessage(), ex);
//            throw ex; // обязательно пробрасываем дальше
//        }
//    }
///
/// Отдельный advice только для ошибок
//    @AfterThrowing(pointcut = "execution(* ru.yandex.practicum.filmorate..*(..))", throwing = "ex")
//    public void logException(JoinPoint joinPoint, Throwable ex) {
//        String methodName = joinPoint.getSignature().toShortString();
//        log.error("💥 Исключение в методе {}: {}", methodName, ex.getMessage(), ex);
//    }
}
