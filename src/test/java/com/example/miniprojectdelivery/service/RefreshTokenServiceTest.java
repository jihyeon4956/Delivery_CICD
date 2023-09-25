package com.example.miniprojectdelivery.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.miniprojectdelivery.enums.UserRoleEnum;
import com.example.miniprojectdelivery.repository.RedisRepository;
import com.example.miniprojectdelivery.utill.jwt.JwtUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RefreshTokenServiceTest {

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisRepository redisRepository;

    @Test
    void test() {
        String uuid = refreshTokenService.createRefreshToken("dlwnsdud", UserRoleEnum.CUSTOMER);

        String value = redisRepository.getValue("refresh:" + uuid);

        Assertions.assertThat(value).isEqualTo("dlwnsdud");
    }

//    @Test
//    @DisplayName("access token 만료 테스트")
//    void accessTokenTest(HttpServletResponse res) {
//        // given
//        String username = "hello";
//        String accessToken = jwtUtil.createToken(username, UserRoleEnum.USER);
//        String refreshToken = refreshTokenService.createRefreshToken(username, UserRoleEnum.USER);
//        String s = jwtUtil.substringToken(accessToken);
//
//        //when
//        String s1 = jwtUtil.validateToken(s, refreshToken, res);
//
//        System.out.println("s = " + s);
//        System.out.println("s1 = " + s1);
//
//        //then
//    }
}