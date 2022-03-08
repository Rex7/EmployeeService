package com.demo.employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demo.employee.model.Employee;
import com.demo.employee.repository.EmployeeRepository;
import com.demo.employee.vo.Department;
import com.demo.employee.vo.ResponseTemplate;

@Service
public class EmployeeService {
	
	@Autowired 
	EmployeeRepository employeeRepository;
	@Autowired
	RestTemplate restTemplate;
	
	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}
	public Employee findEmployeeById(int employeeId) {
		return employeeRepository.findByEmployeeId(employeeId);
	}
	public ResponseTemplate getEmployeeById(int employeeId) {
		ResponseTemplate responseTemplate=new ResponseTemplate();
		Employee employee=employeeRepository.findByEmployeeId(employeeId);
		Department department =restTemplate.getForObject("http://DEPARTMENTSERVICE/departments/"+employee.getDepartmentId(), 
				 Department.class);
	responseTemplate.setEmployee(employee);
	responseTemplate.setDepartment(department);
	return responseTemplate;
	}
	

}
