package com.springboot.HREMS.controller;

import com.springboot.HREMS.payloads.ApiResponse;
import com.springboot.HREMS.payloads.EmployersDtoRequest;
import com.springboot.HREMS.services.IEmployerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/employer/")
@AllArgsConstructor
public class employerController {
    private final IEmployerService usersService;
    @PostMapping("save")
    public ResponseEntity<?> register(@RequestBody EmployersDtoRequest request){
        try{
            ApiResponse response =usersService.createUsers(request);
            if(response.getStatus().equals(HttpStatus.CREATED)) {
                return new ResponseEntity<>(HttpStatus.CREATED);
            }else {
                return ResponseEntity.badRequest().body(response);
            }
        }
        catch (Exception exception){
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }
    @PostMapping("login")
    public ResponseEntity<?> Login(@RequestBody EmployersDtoRequest request){
        try{
            ApiResponse response =usersService.loginUsers(request.getEmailId(), request.getPassword());
            if(response.getStatus().equals(HttpStatus.OK)) {
                return ResponseEntity.ok(response);
            }else {
                return ResponseEntity.badRequest().body(response);
            }
        }
        catch (Exception exception){
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }
    @GetMapping("forget/{email}")
    public ResponseEntity<?> forgetEmail(@PathVariable("email") String email){
        try{
            ApiResponse response =usersService.forgetPassword(email);
            if(response.getStatus().equals(HttpStatus.OK)) {
                return ResponseEntity.ok(response);
            }else {
                return ResponseEntity.badRequest().body(response);
            }
        }
        catch (Exception exception){
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }
    @PostMapping("reset")
    public ResponseEntity<?> resetPassword(@RequestBody EmployersDtoRequest request){
        try{
            ApiResponse response =usersService.resetPassword(request.getNewPassword(), request.getId());
            if(response.getStatus().equals(HttpStatus.OK)) {
                return ResponseEntity.ok(response);
            }else {
                return ResponseEntity.badRequest().body(response);
            }
        }
        catch (Exception exception){
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }
}
