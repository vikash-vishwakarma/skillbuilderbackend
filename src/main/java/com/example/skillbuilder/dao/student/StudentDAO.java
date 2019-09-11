package com.example.skillbuilder.dao.student;

import java.util.List;

import com.example.skillbuilder.domain.student.Student;
import com.example.skillbuilder.response.Response;

public interface StudentDAO {

	Response updateStudentStatus(Student student) throws Exception;

	Student isStudentExist(Student student) throws Exception;

	String forgotPassword(String studentId, String encriptString) throws Exception;

	List<Student> getStudents() throws Exception;

	Student getStudent(String studentfirstName) throws Exception;

	Response addStudent(Student Student) throws Exception;

	Student authenticate(Student student)throws Exception;

//	boolean isfirstNameExist(String firstName) throws Exception;

//	boolean isStudentNameExist(String studentName) throws Exception;

//	Response deleteStudent(String StudentId) throws Exception;
}
