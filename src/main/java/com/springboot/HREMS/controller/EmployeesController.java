package com.springboot.HREMS.controller;

import com.springboot.HREMS.payloads.ApiResponse;
import com.springboot.HREMS.payloads.EmployeeDtoRequest;
import com.springboot.HREMS.payloads.EmployersDtoRequest;
import com.springboot.HREMS.payloads.OfferDtoRequest;
import com.springboot.HREMS.services.IEmployeeService;
import com.springboot.HREMS.services.IEmployerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/employees/")
@AllArgsConstructor
public class EmployeesController {
    private final IEmployeeService employeeService;
    @PostMapping("save")
    public ResponseEntity<?> register(@RequestBody EmployeeDtoRequest request){
        try{
            ApiResponse response =employeeService.createUsers(request);
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
    public ResponseEntity<?> Login(@RequestBody EmployeeDtoRequest request){
        try{
            ApiResponse response =employeeService.loginUsers(request.getEmail(), request.getPassword());
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
            ApiResponse response =employeeService.forgetPassword(email);
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
            ApiResponse response =employeeService.resetPassword(request.getNewPassword(), request.getId());
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
    @GetMapping("searchByPan/{PanNumber}")
    public ResponseEntity<?> searchByPan(@PathVariable("PanNumber") String PanNumber){
        try{
            return ResponseEntity.ok(employeeService.SearchByPan(PanNumber));
        }
        catch (Exception exception){
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }
    @PostMapping("offer")
    public ResponseEntity<?> PostOffer(@RequestBody OfferDtoRequest request){
        try{
            ApiResponse response =employeeService.createEmployeesOffer(request);
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
}
