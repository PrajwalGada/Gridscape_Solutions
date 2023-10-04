package com.example.demo.Controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Department;
import com.example.demo.model.Employee;
import com.example.demo.servive.DepartmentService;
import com.example.demo.servive.EmpService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class EmployeeController {

	@Autowired
	private EmpService service;
	@Autowired
	private DepartmentService departmentService;

	@GetMapping("/")
	public String home() {
		return "index";
	}

	@GetMapping("/registrationts")
	public String registration(Employee employee) {
		return "registrationts";
	}

	@PostMapping("/add")
	public String addEmployee(HttpServletRequest request, @RequestParam("image") MultipartFile file)
			throws IOException, SerialException, SQLException {
		byte[] bytes = file.getBytes();
		Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
		Employee employee = new Employee();
		employee.setPhoto(blob);
		employee.setName(request.getParameter("name"));
		employee.setCity(request.getParameter("city"));
		employee.setCountry(request.getParameter("country"));
		employee.setDateofbirth(request.getParameter("dateofbirth"));
		employee.setEmail(request.getParameter("email"));
		employee.setUsername(request.getParameter("username"));
		employee.setPassword(request.getParameter("password"));
		employee.setDesignation(request.getParameter("designation"));
		employee.setMobileno(request.getParameter("mobileno"));
		service.saveEmployee(employee);
		return "redirect:/registrationts";
	}

	@PostMapping("/login")
	public String login(String username, String password, Model model) {
		Employee user = service.findByUsername(username);
		if (user != null && user.getPassword().equals(password))
		{
			return "redirect:/view/"+ user.getId();
		} else {
			model.addAttribute("error", "Invalid username or password");
			return "index";
		}
	}
	@GetMapping("/view/{id}")
	public String viewEmployee(@PathVariable Long id, Model model) {
	    Employee employee = service.viewById(id);

	    if (employee != null) {
	        model.addAttribute("employee", employee);
	        return "view";
	    } else {
	        model.addAttribute("error", "Employee not found");
	        return "redirect:/list";
	    }
	}


	@GetMapping("/employee/{id}")
	public String editEmployee(@PathVariable Long id, Model model) {
	    Employee employee = service.viewById(id);

	    if (employee != null) {
	        List<Department> departments = departmentService.getAllDepartnment();
	        model.addAttribute("employee", employee);
	        model.addAttribute("departments", departments);
	        return "edit"; // Return the "edit.html" template
	    } else {
	        model.addAttribute("error", "Employee not found");
	        return "redirect:/list";
	    }
	}

	@PostMapping("/update")
	public String updateEmployee(@ModelAttribute Employee updatedEmployee) {

		service.updateEmployee(updatedEmployee);
		return "redirect:/list"; 
	}

	@GetMapping("/list")
	public ModelAndView homeview() {
		ModelAndView mv = new ModelAndView("list");
		List<Department> department=departmentService.getAllDepartnment();
		List<Employee> imageList = service.getEmployees();
		mv.addObject("imageList", imageList);
		mv.addObject("department",department);
		return mv;
	}

	@GetMapping("/display")
	public ResponseEntity<byte[]> displayImage(@RequestParam("id") long id) throws IOException, SQLException {
		Employee image = service.viewById(id);
		byte[] imageBytes = null;
		imageBytes = image.getPhoto().getBytes(1, (int) image.getPhoto().length());
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
	}

	

}
