package com.xample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.StudentDao;
import com.example.entity.Student;
@Service
@Transactional
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentDao studentDao;
	@Override
	public List<Student> getAllStudents() {
		return studentDao.getAllStudents();
		
	}
	@Override
	public void addStudent(Student student) {
		studentDao.addStudent(student);
		
	}
	@Override
	public void updateSutdent(Student student) {
		// TODO Auto-generated method stub
		studentDao.updateStudent(student);
	}
	@Override
	public void removeStudent(int id) {
		// TODO Auto-generated method stub
		studentDao.removeStudent(id);
	}

}
