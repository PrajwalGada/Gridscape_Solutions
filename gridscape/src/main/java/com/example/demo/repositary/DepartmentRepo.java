package com.example.demo.repositary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Department;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Long>{

}
