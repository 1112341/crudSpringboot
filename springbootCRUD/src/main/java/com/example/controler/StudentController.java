package com.example.controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Student;
import com.xample.service.StudentService;

@RestController
@RequestMapping(value={"/api"})
public class StudentController {
	@Autowired
	private StudentService studentService;
    @GetMapping("/")
    public String test() {
        return "";
    }

	@GetMapping(value= {"/students"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE )
	public List<Student> getStudents() {
		List<Student> rs = studentService.getAllStudents();
		return rs;
	}
	
	@PostMapping(value = {"/students"})
	public ResponseEntity<Student> addStudent(@RequestBody Student student){
	
		studentService.addStudent(student);
		System.out.println(student);
		return new ResponseEntity<Student>(student,HttpStatus.OK);
	}
	
	@PutMapping(value = {"/students/{id}"})
	public ResponseEntity<Student> updateStudent(@RequestBody Student student, @PathVariable int id) {
		student.setId(id);
		studentService.updateSutdent(student);
		return new ResponseEntity<Student>(student,HttpStatus.OK);
	}
	@DeleteMapping(value = {"/students/{id}"})
	public ResponseEntity<Student> updateStudent(@PathVariable int id) {
		studentService.removeStudent(id);
		return new ResponseEntity<Student>(HttpStatus.OK);
	}
}
