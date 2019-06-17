package com.xample.service;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.StudentDao;
import com.example.entity.Student;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
@Service
@Transactional
public class StudentServiceImpl implements StudentService {
	@Autowired
	DataSource datasource;
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
	@Override
	public void createReport() throws JRException, SQLException {
		
		InputStream employeeReportStream
		  = getClass().getResourceAsStream("/student.jrxml");
		JasperReport jasperReport
		  = JasperCompileManager.compileReport(employeeReportStream);
		JRSaver.saveObject(jasperReport, "student.jasper");
	
		JasperPrint jasperPrint = JasperFillManager.fillReport(
				  jasperReport, null, datasource.getConnection());
		
		JRPdfExporter exporter = new JRPdfExporter();
		 System.out.println(datasource.getConnection());
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(
		  new SimpleOutputStreamExporterOutput("student.pdf"));
		 
		SimplePdfReportConfiguration reportConfig
		  = new SimplePdfReportConfiguration();
		reportConfig.setSizePageToContent(true);
		reportConfig.setForceLineBreakPolicy(false);
		 
		SimplePdfExporterConfiguration exportConfig
		  = new SimplePdfExporterConfiguration();
		exportConfig.setMetadataAuthor("Author");
		exportConfig.setEncrypted(true);
		exportConfig.setAllowedPermissionsHint("PRINTING");
		 
		exporter.setConfiguration(reportConfig);
		exporter.setConfiguration(exportConfig);
		 
		exporter.exportReport();
	}

}
