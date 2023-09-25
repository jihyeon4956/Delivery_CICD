package com.example.miniprojectdelivery.service;

import com.example.miniprojectdelivery.dto.MessageResponseDto;
import com.example.miniprojectdelivery.dto.user.SignupRequestDto;
import com.example.miniprojectdelivery.enums.UserRoleEnum;
import com.example.miniprojectdelivery.model.*;
import com.example.miniprojectdelivery.model.Address;
import com.example.miniprojectdelivery.repository.AuthRepository;
import com.example.miniprojectdelivery.repository.CustomerRepository;
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
    private final AuthRepository authRepository;
    private final CustomerRepository customerRepository;
    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public ResponseEntity<MessageResponseDto> signup(SignupRequestDto requestDto) {

        MessageResponseDto responseDto = new MessageResponseDto( "회원가입 성공");

        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();
        Address address = new Address(requestDto.getAddress(), requestDto.getZipcode());

        if(!Objects.equals(requestDto.getCheckpassword(), requestDto.getPassword())){
            throw new IllegalArgumentException("패스워드와 패스워드 확인이 다릅니다.");
        }
        // username 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if(checkUsername.isPresent()){
            throw new IllegalArgumentException("중복된 username 입니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if(requestDto.isAdmin()){
            if(!ADMIN_TOKEN.equals(requestDto.getAdminToken())){
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능 합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        Auth auth = authRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일 인증이 진행되지 않은 메일입니다."));
        if(requestDto.getCheckemail() != auth.getPaswword()){
            authRepository.delete(auth);
            throw new IllegalArgumentException("이메일 인증이 틀렸습니다. 다시 진행해주세요");
        }
        authRepository.delete(auth);
        // 사용자 등록
        if(requestDto.isCustomer()){            // isCustomer 가 True면 사용자로 생성
            customerRepository.save(new Customer(username, password, role, email));
        }else {                                 // 아니면 User로 생성
            userRepository.save(new User(username, password, role, email,address));
        }
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
    public void Mailing(Mail email) {
        int rand = (int) (Math.random()*10000);
        log.info(Integer.toString(rand));
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
            receiverList[0] = new InternetAddress(email.getEmail());
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
            mimeMessage.setText(Integer.toString(rand)+"를 입력해주세요");
            // Mail 발송
            Transport.send(mimeMessage);

        } catch(Exception e) {
            log.info(e.getMessage());
            log.info("메일 발송 오류!!");
        }
        try {
            if(!authRepository.findByEmail(email.getEmail()).isEmpty()){
                authRepository.delete(authRepository.findByEmail(email.getEmail()).orElseThrow());
            }
            authRepository.save(new Auth(email.getEmail(), rand));
        }catch(Exception e){
            log.info(e.getMessage());
            log.info("DB 저장 오류");
        }
    }
}
