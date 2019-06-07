package com.example.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Student;

@Repository
@Transactional
public class StudentDao {
	@Autowired
	private SessionFactory sessionFactory;

	public Student getStudent(int id) {
		Session s = sessionFactory.getCurrentSession();
		return s.get(Student.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Student> getAllStudents() {
//	 return sessionFactory.getCurrentSession().createQuery("from student").list();
		return sessionFactory.getCurrentSession().createCriteria(Student.class).list();
	}

	public void updateStudent(Student student) {
		sessionFactory.getCurrentSession().saveOrUpdate(student);
	}
	
	public void addStudent(Student student) {
		sessionFactory.getCurrentSession().save(student);
	}
	
	public void removeStudent(int id) {
		Student s = this.getStudent(id);
		sessionFactory.getCurrentSession().remove(s);
	}
}
