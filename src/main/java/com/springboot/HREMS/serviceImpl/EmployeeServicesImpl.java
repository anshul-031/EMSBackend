package com.springboot.HREMS.serviceImpl;

import com.springboot.HREMS.models.EmployeeOffer;
import com.springboot.HREMS.models.Employees;
import com.springboot.HREMS.payloads.ApiResponse;
import com.springboot.HREMS.payloads.EmployeeDtoRequest;
import com.springboot.HREMS.payloads.EmployersDtoRequest;
import com.springboot.HREMS.payloads.OfferDtoRequest;
import com.springboot.HREMS.repository.IEmployeesRepository;
import com.springboot.HREMS.repository.IPostOfferRepository;
import com.springboot.HREMS.services.IEmployeeService;
import com.springboot.HREMS.utils.EmployeeService;
import com.springboot.HREMS.utils.JwtUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EmployeeServicesImpl implements IEmployeeService {
    private final IEmployeesRepository employeesRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ApiResponse response;
    private final EmployeeService userService;
    private final JwtUtil jwtUtil;
    private final JavaMailSender emailSender;
    private final IPostOfferRepository postOfferRepository;
    @Override
    @Transactional
    public ApiResponse createUsers(EmployeeDtoRequest request) {
        Employees byEmailId = employeesRepository.findByEmail(request.getEmail());
        if(byEmailId==null){
            request.setPassword(passwordEncoder.encode(request.getPassword()));
            request.setId(UUID.randomUUID().toString());
            Employees users=modelMapper.map(request, Employees.class);
            employeesRepository.save(users);
            response.setMessage("users are successfully created");
            response.setStatus(HttpStatus.CREATED);
            return response;
        }else {
            response.setMessage("users are already exist with this email Id"+request.getEmail()+
                    ". Please try again with new Email id");
            response.setStatus(HttpStatus.CREATED);
            return response;
        }
    }

    @Override
    @Transactional
    public ApiResponse loginUsers(String emailId, String password) {
        Employees byEmailId = employeesRepository.findByEmail(emailId);
        boolean matches = passwordEncoder.matches(password, byEmailId.getPassword());
        if(byEmailId==null){
            return null;
        }else if((byEmailId.getEmail().equals(emailId)) && (matches==true)){
            UserDetails userDetails = userService.loadUserByUsername(byEmailId.getEmail());
            String token=jwtUtil.generateToken(userDetails);
            EmployersDtoRequest request=modelMapper.map(byEmailId, EmployersDtoRequest.class);
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
        Employees findByEmail = employeesRepository.findByEmail(emailId);
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
        Optional<Employees> findById=employeesRepository.findById(id);
        if(!findById.isPresent()){
            response.setMessage("Data is null");
            response.setStatus(HttpStatus.NOT_FOUND);
        }else {
            findById.get().setPassword(passwordEncoder.encode(password));
            employeesRepository.save(findById.get());
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

    @Override
    public List<EmployeeOffer> SearchByPan(String PANNumber) {
        return postOfferRepository.findByPanNumber(PANNumber);
    }

    @Override
    public ApiResponse createEmployeesOffer(OfferDtoRequest request) {
        request.setId(UUID.randomUUID().toString());
        EmployeeOffer employeeOffer=modelMapper.map(request, EmployeeOffer.class);
        postOfferRepository.save(employeeOffer);
        response.setStatus(HttpStatus.CREATED);
        response.setMessage("Post offer is submitted successfully");
        return response;
    }
}
