package com.example.skillbuilder.service.student;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.skillbuilder.constants.StatusCode;
import com.example.skillbuilder.dao.student.StudentDAO;
import com.example.skillbuilder.domain.student.Student;
import com.example.skillbuilder.mapper.student.StudentMapper;
import com.example.skillbuilder.model.student.StudentModel;
import com.example.skillbuilder.response.Response;
import com.example.skillbuilder.utils.CommonUtils;
import com.example.skillbuilder.utils.SmtpMailSender;

@Service
public class StudentServiceImpl implements StudentService{

	@Autowired
	StudentDAO studentDAO;
	
	@Autowired
	StudentMapper studentMapper;
	
	@Autowired
	SmtpMailSender smtpMailSender;
	
	private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
	
	@Override
	public Response addStudent(StudentModel studentModel) throws Exception{
		try
		{
			Student student=new Student();
			student.setStdId(CommonUtils.generateRandomId());
			student.setFirstName(studentModel.getFirstName());
			student.setLastName(studentModel.getLastName());
			student.setEmail(studentModel.getEmail());
			student.setContactNo(studentModel.getContactNo());
            //student.setPassword(CommonUtils.encriptString(studentModel.getPassword()));
            //student.setConfirmPassword(CommonUtils.encriptString(studentModel.getConfirmPassword()));
			student.setPassword(studentModel.getPassword());
			student.setConfirmPassword(studentModel.getConfirmPassword());
			student.setGender(studentModel.getGender());
			student.setActive(true);
			
			Response response = studentDAO.addStudent(student);
			return response;
		
		}
		catch(Exception e)
		{
			logger.info("Exception Service:" + e.getMessage());
			Response response=new Response();
			response.setStatus(StatusCode.ERROR.name());
			response.setMessage(e.getMessage());
			return response;
		}
	}
	
	@Override
	public Response updateStudent(StudentModel studentModel) throws Exception {
		
		{
			Student student=new Student();
			BeanUtils.copyProperties(studentModel, student);
			Response res=studentDAO.updateStudentStatus(student);
			return res;
		}
			
		}

	@Override
	public String forgotPassword(StudentModel studentModel) throws Exception {
		try {
			Student student = new Student();
			BeanUtils.copyProperties(studentModel, student);
			student = studentDAO.isStudentExist(student);
			System.out.println(student.getFirstName());
			if (student != null) {
				String password = CommonUtils.generateRandomId();
				//String password = "kalal";
				
				String status = studentDAO.forgotPassword(student.getStdId(), CommonUtils.encriptString(password));
				if (status.equals(StatusCode.SUCCESS.name())) {
					String email=student.getEmail();
					String pass=password;
					smtpMailSender.send(email,"Snipe It tech pvt ltd reset password","forgot password ="+pass);
				}
				return status;
			} else
				return StatusCode.ERROR.name(); 
		} catch (Exception e) {
			logger.error("Exception in forgotPassword:", e);
			return StatusCode.ERROR.name();
		}
	}

//	@Override
//	public StudentModel authenticate(StudentModel studentModel) throws Exception {
//		studentModel.setPassword(CommonUtils.encriptString(studentModel.getPassword()));
//		Student student = new Student();
//		BeanUtils.copyProperties(studentModel, student);
//		System.out.println(studentModel.getEmail());
//		System.out.println(studentModel.getContactNo());
//
//		student = studentDAO.authenticate(student);
//		if (student == null)
//			return null;
//		BeanUtils.copyProperties(student, studentModel);
//		return studentModel;
//	}
	@Override
	public StudentModel authenticate(StudentModel studentModel) throws Exception {
        //studentModel.setPassword(CommonUtils.encriptString(studentModel.getPassword()));
		studentModel.setPassword(studentModel.getPassword());
		Student student = new Student();
		BeanUtils.copyProperties(studentModel, student);
		System.out.println(studentModel.getEmail());
		System.out.println(studentModel.getContactNo());

		student = studentDAO.authenticate(student);
		if (student == null)
			return null;
		BeanUtils.copyProperties(student, studentModel);
		return studentModel;
	}
}
