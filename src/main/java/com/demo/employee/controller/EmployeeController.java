package com.demo.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.employee.model.Employee;
import com.demo.employee.service.EmployeeService;
import com.demo.employee.vo.ResponseTemplate;

import ch.qos.logback.classic.Logger;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {
	
	public static final String SERVICE_NAME="CircuitBreaker";
	@Autowired
	EmployeeService employeeService;

	@PostMapping("/")
	public Employee saveEmployee(Employee emp) {
		return employeeService.saveEmployee(emp);
	}

	@GetMapping("/{id}")
	@CircuitBreaker(name =SERVICE_NAME,fallbackMethod = "fallback")
	public ResponseTemplate getEmployeeById(@PathVariable("id") int employeeId) {
		log.info("calling employee getEmployeeById()");
		return employeeService.getEmployeeById(employeeId);
	}
	
	public ResponseTemplate fallback(Exception exception) {
		
		return new ResponseTemplate();
	}
	
	
}
