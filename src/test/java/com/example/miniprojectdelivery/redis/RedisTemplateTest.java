package com.example.miniprojectdelivery.redis;

import static org.assertj.core.api.Assertions.*;

import com.example.miniprojectdelivery.enums.UserRoleEnum;
import com.example.miniprojectdelivery.model.RefreshToken;
import com.example.miniprojectdelivery.repository.RedisRepository;
import com.example.miniprojectdelivery.utill.jwt.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
public class RedisTemplateTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    JwtUtil jwtUtil;


    @Autowired
    private RedisRepository redisRepository;

    private final String REFRESH_PREFIX = "refresh:";
    private final String MAIL_PREFIX = "mail:";


    @Test
    void mailAuthSaveTest() {

        /**
         * mailauth:lionalmessi@naver.com 1234
         *
         * auth 저장에 이메일 주소 필요, 인증번호 필요
         */
        StringBuilder sb = new StringBuilder();

        String key = sb.append(MAIL_PREFIX).append("lionalmessi@naver.com").toString();
        System.out.println("key = " + key);
        // given
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        // when
        valueOperations.set(key, "1234");
        redisTemplate.expire(key, 180L, TimeUnit.SECONDS);

        // then
        String value = valueOperations.get(key);
        assertThat(value).isEqualTo("1234");


    }

    @Test
    void refreshTokenSaveTest() {

        /**
         * refresh:랜덤 UUID username
         *
         * auth 저장에 이메일 주소 필요, 인증번호 필요
         */
        StringBuilder sb = new StringBuilder();
        UUID uuid = UUID.randomUUID();
        String key = sb.append(REFRESH_PREFIX).append(uuid).toString();
        System.out.println("key = " + key);
//        // given
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        // 엑세스토큰 만료 리프레시 확인 리프레시 없으면 로그인하라고 빠구
        // 음식점 생성 엑세스토큰 만료 리프레시 있으면 엑세스토큰 다시 발급 해주고 바로 로직 실행
//        // when
        valueOperations.set(key, "dlwnsdud1228");
        redisTemplate.expire(key, 7 * 24 * 60 * 60L, TimeUnit.SECONDS);

//        // then
        String value = valueOperations.get(key);
        assertThat(value).isEqualTo("dlwnsdud1228");
    }


    @Test
    @DisplayName("유효하지 않은 값을 조회하면 null 반환 테스트")
    void emptyValueGetTest() {
        // given
        String key = String.valueOf(Math.random());
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        //when
        String s = valueOperations.get(key);

        // then
        assertThat(s).isNull();
    }

    @Test
    @DisplayName("RedisRepository 작동 테스트")
    void repositoryTest() {
        // given
        String key = "hello";

        //when
        redisRepository.save(key, "1234");
        System.out.println(redisRepository.getValue(key));

        //then
        assertThat("1234").isEqualTo(redisRepository.getValue(key));
    }

    @Test
    @DisplayName("Redis TTL 만료 메소드 테스트")
    void expireTest() {

        //given
        String key = "hello";
        redisRepository.save("hello", "1234");

        //when
        redisRepository.setExpire("hello", 100L);
        System.out.println(redisTemplate.getExpire("hello"));
        redisRepository.setExpire("hello", 0L);
        System.out.println(redisTemplate.getExpire("hello"));
        //then
        assertThat(redisRepository.getValue("hello")).isNull();
        assertThat(redisTemplate.getExpire("hello")).isEqualTo(-2L);
    }

    @Test
    @DisplayName("객체 JSON 변환, JSON 객체 변환 테스트")
    void name() throws JsonProcessingException {
        RefreshToken refreshToken = new RefreshToken("hello", UserRoleEnum.CUSTOMER);
        ObjectMapper objectMapper = new ObjectMapper();

        String s = objectMapper.writeValueAsString(refreshToken);
        RefreshToken refreshToken1 = objectMapper.readValue(s, RefreshToken.class);
        assertThat(refreshToken1.getUsername()).isEqualTo("hello");
        assertThat(refreshToken1.getRole()).isEqualTo(UserRoleEnum.CUSTOMER);

    }



}
