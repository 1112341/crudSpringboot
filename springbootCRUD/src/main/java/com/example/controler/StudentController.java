package com.example.controler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Utils.MediaTypeUtils;
import com.example.entity.Student;
import com.xample.service.StudentService;

import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping(value={"/api"})
public class StudentController {
	@Autowired
    private ServletContext servletContext;
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
	
	@GetMapping(value= {"/student/export"} )
	public ResponseEntity<ByteArrayResource>  getReportStudents() throws JRException, SQLException, IOException {
		String fileName= "student.pdf";
		System.out.println("show report");
		studentService.createReport();
		MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
        System.out.println("fileName: " + fileName);
        System.out.println("mediaType: " + mediaType);
 
        Path path = Paths.get(fileName);
        byte[] data = Files.readAllBytes(path);
        ByteArrayResource resource = new ByteArrayResource(data);
		ResponseEntity<Student> rs = new ResponseEntity<Student>(HttpStatus.OK);
		
		return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
                // Content-Type
                .contentType(mediaType) //
                // Content-Lengh
                .contentLength(data.length) //
                .body(resource);
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
