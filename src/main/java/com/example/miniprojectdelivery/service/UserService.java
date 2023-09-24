package com.example.miniprojectdelivery.service;

import com.example.miniprojectdelivery.dto.MessageResponseDto;
import com.example.miniprojectdelivery.dto.user.MailRequestDto;
import com.example.miniprojectdelivery.dto.user.SignupRequestDto;
import com.example.miniprojectdelivery.enums.UserRoleEnum;
import com.example.miniprojectdelivery.model.*;
import com.example.miniprojectdelivery.model.Address;
import com.example.miniprojectdelivery.repository.RedisRepository;
import com.example.miniprojectdelivery.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisRepository redisRepository;
    // ADMIN_TOKEN
    private final String MAIL_PREFIX = "mail:";

    public ResponseEntity<MessageResponseDto> signup(SignupRequestDto requestDto) {

        MessageResponseDto responseDto = new MessageResponseDto( "회원가입 성공");

        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();
        String checkemail = requestDto.getCheckemail();
        Address address = new Address(requestDto.getAddress(), requestDto.getStreet());

        if(!Objects.equals(requestDto.getCheckpassword(), requestDto.getPassword())){
            throw new IllegalArgumentException("패스워드와 패스워드 확인이 다릅니다.");
        }
        // username 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if(checkUsername.isPresent()){
            throw new IllegalArgumentException("중복된 username 입니다.");
        }

        //이메일번호 인증
        String emailAuthNum = redisRepository.getValue(MAIL_PREFIX + email);

        //인증번호 유무 체크
        if (emailAuthNum == null) {
            throw new IllegalArgumentException("이메일 인증번호를 발급 받아야 합니다.");
        }

        //인증번호 일치여부 체크
        if (!checkemail.equals(emailAuthNum)) {
            throw new IllegalArgumentException("인증번호가 일치하지 않습니다.");
        }

        //인증 성공시 인증번호 만료
        redisRepository.setExpire(MAIL_PREFIX + email,0L);

        //todo: 주소 requestDto에 validation 추가

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.CUSTOMER;
        if(requestDto.isOwner()){
            role = UserRoleEnum.OWNER;
        }

        userRepository.save(new User(username, password, role, email,address));

        return new ResponseEntity<>(responseDto, null, HttpStatus.OK);
    }

    public ResponseEntity<MessageResponseDto> deleteById(Long id) {
        User entity = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원은 존재하지 않습니다."));

        userRepository.delete(entity);

        MessageResponseDto responseDto = new MessageResponseDto("회원 삭제 성공");
        return new ResponseEntity<>(responseDto, null, HttpStatus.OK);
    }

    //@Transactional
    public void Mailing(MailRequestDto requestDto) {
        int rand = (int) (Math.random()*10000);
        String randStr = String.format("%04d", rand);
        log.info(randStr);
        /** 메일 HOST **/
        String HOST = "smtp.naver.com";
        /** 메일 PORT **/
        String PORT = "587";
        /** 메일 ID **/
        String MAIL_ID = "dlwlgnsaaa@naver.com";
        /** 메일 PW **/
        String MAIL_PW = "1QKNC22EHLE3";

        try {
            InternetAddress[] receiverList = new InternetAddress[1];
            receiverList[0] = new InternetAddress(requestDto.getEmail());
            // SMTP 발송 Properties 설정
            Properties props = new Properties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.host", HOST);
            props.put("mail.smtp.port", PORT);
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.ssl.trust", HOST);
            props.put("mail.smtp.auth", "true");
            // SMTP Session 생성
            Session mailSession = Session.getDefaultInstance(props, new javax.mail.Authenticator(){
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new javax.mail.PasswordAuthentication(MAIL_ID, MAIL_PW);
                }
            });

            // Mail 조립
            Message mimeMessage = new MimeMessage(mailSession);
            mimeMessage.setFrom(new InternetAddress(MAIL_ID));
            mimeMessage.setRecipients(Message.RecipientType.TO, receiverList);
            // 메일 제목
            mimeMessage.setSubject("인증 메일입니다.");
            // 메일 본문 (.setText를 사용하면 단순 텍스트 전달 가능)
            mimeMessage.setText(randStr+"를 입력해주세요");
            // Mail 발송
            Transport.send(mimeMessage);

        } catch(Exception e) {
            log.info(e.getMessage());
            log.info("메일 발송 오류!!");
        }
        try {
            //todo: 레디스로 변경 필요
            String email = requestDto.getEmail();
            String key = MAIL_PREFIX + email;
            redisRepository.save(key,randStr);
            redisRepository.setExpire(key, 180L); // 만료 기간 지정 (3분)
        }catch(Exception e){
            log.info(e.getMessage());
            log.info("Redis 저장 오류");
        }
    }
}
