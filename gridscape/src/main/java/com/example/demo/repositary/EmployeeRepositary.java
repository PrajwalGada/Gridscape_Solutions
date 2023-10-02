package com.example.demo.repositary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Employee;

@Repository
public interface EmployeeRepositary extends JpaRepository<Employee, Long>
{

	Employee findByUsername(String username);

}
