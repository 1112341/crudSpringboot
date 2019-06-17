package com.xample.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.Student;

import net.sf.jasperreports.engine.JRException;

public interface StudentService {
	List<Student> getAllStudents();
	public void addStudent(Student student);
	public void updateSutdent(Student student);
	public void removeStudent(int id);
	public void createReport() throws JRException, SQLException;
}
