package com.example.skillbuilder.controller.student;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.skillbuilder.constants.StatusCode;
import com.example.skillbuilder.dao.student.StudentDAO;
import com.example.skillbuilder.model.student.StudentModel;
import com.example.skillbuilder.response.ErrorObject;
import com.example.skillbuilder.response.Response;
import com.example.skillbuilder.service.student.StudentService;
import com.example.skillbuilder.utils.CommonUtils;

@RestController
@RequestMapping("/156")
@CrossOrigin(origins="http://localhost:4200",allowedHeaders="*")
public class StudentController {
	
	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	StudentDAO studentDAO;

	
/*--------------------------------------------Add Student-----------------------------------------*/
	
	
	@RequestMapping(value = "/student", method = RequestMethod.POST, produces = "application/json")
	public Response addStudent(@RequestBody StudentModel studentModel, HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
		logger.info("addParent: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("addUser: Received request: "+ CommonUtils.getJson(studentModel));
		
		return studentService.addStudent(studentModel);
		
	}
	
	/*--------------------------------------------update Student -----------------------------------------*/

	@RequestMapping(value = "/updateStudent", method = RequestMethod.PUT, produces = "application/json")
	public Response updateStudent(@RequestBody StudentModel studentModel, HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
		logger.info("updateStudent: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("updateStudent: Received request:" + CommonUtils.getJson(studentModel));
		return studentService.updateStudent(studentModel);
	}
	
	/*--------------------------------------------Student login -----------------------------------------*/
	@RequestMapping(value="/login",method = RequestMethod.GET, produces ="application/json")
	
	public @ResponseBody String authenticate(@RequestBody StudentModel studentModel, HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
		logger.info("authenticate: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("authenticate :Received request: " + CommonUtils.getJson(studentModel));
		
		studentModel =studentService.authenticate(studentModel);
		
		Response res=CommonUtils.getResponseObject("authenticate Student");
		if(studentModel == null)
		{
			ErrorObject err=CommonUtils.getErrorResponse("Invalid Email or Password", "Invalid Email or Password");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		}
		{
			res.setData(studentModel);
			
		}
		logger.info("authenticate:sent response");
		return CommonUtils.getJson(res);
	}
	
	/*--------------------------------------------Forgot Password -----------------------------------------*/
	@RequestMapping(value="/forgotPassword",method = RequestMethod.PUT, produces ="application/json")
	public @ResponseBody String resetPassword(@RequestBody StudentModel studentModel, HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
		logger.info("forgotPassword: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?1" + request.getQueryString().toString()));
		logger.info("forgotPassword: Received request: "+CommonUtils.getJson(studentModel));
		
		String status = studentService.forgotPassword(studentModel);
		Response res = CommonUtils.getResponseObject("forgot password");
		if (status.equalsIgnoreCase(StatusCode.ERROR.name())) {
			ErrorObject err = CommonUtils.getErrorResponse("forgot password failed", "forgot password failed");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
	}
		logger.info("forgotPassword: Sent response");
		return CommonUtils.getJson(res);
	}
		
	/*--------------------------------------------student status -----------------------------------------*/

	@RequestMapping(value = "/StudentStatus", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")

	public Response updateParentStatus(@RequestBody StudentModel studentModel, HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
		logger.info("updateStudentStatus: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("updateStudentStatus: Received request: " + CommonUtils.getJson(studentModel));
		return studentService.updateStudent(studentModel);
	}
	}
	

