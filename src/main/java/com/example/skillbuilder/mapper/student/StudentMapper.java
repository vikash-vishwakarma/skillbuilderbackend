package com.example.skillbuilder.mapper.student;

import org.springframework.stereotype.Component;

import com.example.skillbuilder.domain.student.Student;
import com.example.skillbuilder.mapper.AbstractModelMapper;
import com.example.skillbuilder.model.student.StudentModel;

@Component
public class StudentMapper extends AbstractModelMapper<StudentModel, Student>{

	@Override
	public Class<StudentModel> entityType() {
		// TODO Auto-generated method stub
		return StudentModel.class;
	}

	@Override
	public Class<Student> modelType() {
		// TODO Auto-generated method stub
		return Student.class;
	}

}
