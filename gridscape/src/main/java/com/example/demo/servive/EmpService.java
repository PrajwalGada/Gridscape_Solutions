	package com.example.demo.servive;

	import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Employee;
import com.example.demo.repositary.EmployeeRepositary;

	@Service
	public class EmpService
	{
		@Autowired
		EmployeeRepositary employeeRepositary;

		public Employee findByUsername(String username)
		{
	        return employeeRepositary.findByUsername(username);
	    }
		public Employee saveEmployee(Employee employee) throws IOException {

	        return employeeRepositary.save(employee);
	    }



	    public List<Employee> getEmployees()
	    {
	        return employeeRepositary.findAll();
	    }
	    public Employee viewById(long id) {
	        return employeeRepositary.findById(id).get();
	    }
	    public void updateEmployee(Employee UpdateEmp)
	    {
	      Optional<Employee> existingEmployee = employeeRepositary.findById(UpdateEmp.getId());
	      Employee employeeToUpdate = existingEmployee.get();
	      employeeToUpdate.setName(UpdateEmp.getName());
	      employeeToUpdate.setEmail(UpdateEmp.getEmail());
	      employeeToUpdate.setCity(UpdateEmp.getCity());
	      employeeToUpdate.setUsername(UpdateEmp.getUsername());
	      employeeToUpdate.setDateofbirth(UpdateEmp.getDateofbirth());
	      employeeToUpdate.setCountry(UpdateEmp.getCountry());
	      employeeToUpdate.setDesignation(UpdateEmp.getDesignation());
	      employeeToUpdate.setPassword(UpdateEmp.getPassword());
	      employeeToUpdate.setMobileno(UpdateEmp.getMobileno());
	      employeeToUpdate.setDepartment(UpdateEmp.getDepartment());
	      employeeRepositary.save(employeeToUpdate);

	    }

	}
