package com.sparta.trellocopy.domain.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Aspect
@Component
public class AlarmAspect {

    // 알림 보낼 url
    @Value("${WEBHOOKS_URL}")
    String url;

    // 일단 알림 요청 보내는 메서드
    @Before("@annotation(Alarm)")
    public void sendAlarm(JoinPoint joinPoint){

        RestTemplate restTemplate = new RestTemplate();

        // 실행될 메서드의 이름을 가져옴
        String text = joinPoint.getSignature().getName();

        // 가져온 이름을 알림 메시지로 보냄
        String requestBody = String.format("{\"text\" : \"%s\"}", text);

        HttpEntity<String> request = new HttpEntity<>(requestBody);

        ResponseEntity<String> response =
                restTemplate.exchange(url, HttpMethod.POST, request, String.class);
    }
}
