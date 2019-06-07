package com.xample.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.Student;

public interface StudentService {
	List<Student> getAllStudents();
	public void addStudent(Student student);
	public void updateSutdent(Student student);
	public void removeStudent(int id);
}
