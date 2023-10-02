package com.example.demo.servive;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Department;
import com.example.demo.repositary.DepartmentRepo;

@Service
public class DepartmentService
{
	@Autowired
	DepartmentRepo departmentRepo;

	public List<Department> getAllDepartnment()
	{
		return departmentRepo.findAll();
	}

}
