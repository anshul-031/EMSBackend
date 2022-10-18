package com.springboot.HREMS.serviceImpl;

import com.springboot.HREMS.models.Users;
import com.springboot.HREMS.payloads.ApiResponse;
import com.springboot.HREMS.payloads.UsersDtoRequest;
import com.springboot.HREMS.repository.IUsersRepository;
import com.springboot.HREMS.services.IUsersService;
import com.springboot.HREMS.utils.JwtUtil;
import com.springboot.HREMS.utils.UserService;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UsersServicesImpl implements IUsersService {

    private final IUsersRepository usersRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ApiResponse response;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final JavaMailSender emailSender;
    @Override
    @Transactional
    public ApiResponse createUsers(UsersDtoRequest request) {
        Users byEmailId = usersRepository.findByEmailId(request.getEmailId());
        if(byEmailId==null){
            request.setPassword(passwordEncoder.encode(request.getPassword()));
            request.setId(UUID.randomUUID().toString());
            Users users=modelMapper.map(request,Users.class);
            usersRepository.save(users);
            response.setMessage("users are successfully created");
            response.setStatus(HttpStatus.CREATED);
            return response;
        }else {
            response.setMessage("users are already exist with this email Id"+request.getEmailId()+
                    ". Please try again with new Email id");
            response.setStatus(HttpStatus.CREATED);
            return response;
        }
    }

    @Override
    @Transactional
    public ApiResponse loginUsers(String emailId, String password) {
        Users byEmailId = usersRepository.findByEmailId(emailId);
        boolean matches = passwordEncoder.matches(password, byEmailId.getPassword());
        if(byEmailId==null){
            return null;
        }else if((byEmailId.getEmailId().equals(emailId)) && (matches==true)){
            UserDetails userDetails = userService.loadUserByUsername(byEmailId.getEmailId());
            String token=jwtUtil.generateToken(userDetails);
            UsersDtoRequest request=modelMapper.map(byEmailId,UsersDtoRequest.class);
            request.setToken(token);
            response.setMessage("Username/Password is valid.");
            response.setStatus(HttpStatus.OK);
            response.setData(request);
            return response;
        }else {
            response.setMessage("Username/Password is not valid. Please try again");
            response.setStatus(HttpStatus.BAD_REQUEST);
            return response;
        }
    }
    @Override
    @Transactional
    public ApiResponse forgetPassword(String emailId) throws MessagingException {
        Users findByEmail = usersRepository.findByEmailId(emailId);
        if(findByEmail==null){
            response.setMessage("email Id is not exist");
            response.setStatus(HttpStatus.BAD_REQUEST);
            return response;
        }else {
            SendEmail(emailId,"Reset Email","Click on the reset password below link"+
                    "<a href='http://localhost:4200/reset/"+findByEmail.getId()+"'>Click here</a>");
            Random random = new Random();
            long otp = 100000 + random.nextInt(900000);
            response.setMessage("email Id is exist");
            response.setStatus(HttpStatus.OK);
            response.setData(otp);
            return response;
        }
    }

    @Override
    @Transactional
    public ApiResponse resetPassword(String password, String id) {
       Optional<Users> findById=usersRepository.findById(id);
       if(!findById.isPresent()){
           response.setMessage("Data is null");
           response.setStatus(HttpStatus.NOT_FOUND);
       }else {
           findById.get().setPassword(passwordEncoder.encode(password));
           usersRepository.save(findById.get());
           response.setMessage("Password is changed successfully");
           response.setStatus(HttpStatus.OK);
       }
       return response;
    }

    @Override
    public void SendEmail(String to, String subject, String text) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper;
        helper = new MimeMessageHelper(message, true);//true indicates multipart message

        helper.setFrom("chintan.negi@outlook.com"); // <--- THIS IS IMPORTANT

        helper.setSubject(subject);
        helper.setTo(to);
        helper.setText(text, true);//true indicates body is html
        emailSender.send(message);
        System.out.println("message sent");
    }
}
