package com.example.skillbuilder.service.student;

import com.example.skillbuilder.domain.student.Student;
import com.example.skillbuilder.model.student.StudentModel;
import com.example.skillbuilder.response.Response;

public interface StudentService {

	Response addStudent(StudentModel studentModel) throws Exception;

	Response updateStudent(StudentModel studentModel) throws Exception;

	String forgotPassword(StudentModel studentModel) throws Exception;

	StudentModel authenticate(StudentModel studentModel)throws Exception;

}
