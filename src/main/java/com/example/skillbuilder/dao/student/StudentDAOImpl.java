package com.example.skillbuilder.dao.student;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import com.example.skillbuilder.constants.StatusCode;
import com.example.skillbuilder.domain.student.Student;
import com.example.skillbuilder.response.Response;
import com.example.skillbuilder.utils.CommonUtils;

@Repository
@Transactional
public class StudentDAOImpl implements StudentDAO {

	
	private static final Logger logger = LoggerFactory.getLogger(StudentDAOImpl.class);
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public Response addStudent(Student Student) throws Exception{
		
		Response response = CommonUtils.getResponseObject("Add Student data");
		try 
		{
			entityManager.persist(Student);
			response.setStatus(StatusCode.SUCCESS.name());
		} 
		catch (Exception e) 
		 
		{
			logger.error("Exception in addUser", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return response;
		
	}
	
//	@Override
//	public Response deleteStudent (String StudentId) throws Exception{
//		
//		Response response = CommonUtils.getResponseObject("Delete Student data");
//		try {
//			
//
//			Student student=getStudent(StudentId);
//			student.setActive(false);
//		
//			entityManager.flush();
//
//			response.setStatus(StatusCode.SUCCESS.name());	
//		} 
//		catch(Exception ex)
//		{
//			logger.error("Exception in deleteStudent", ex);
//			response.setStatus(StatusCode.ERROR.name());
//			response.setErrors(ex.getMessage());
//		}
//		return response;	
//	}
	
//	@Override
//	public boolean isStudentNameExist(String studentName)throws Exception {
//		
//		try {
//			String hql = "FROM User WHERE firstName=?1 and isActive=true";
//			int count = entityManager.createQuery(hql).setParameter(0, studentName).getResultList().size();
//			return count > 0 ? true : false;
//		} catch (Exception e) {
//			logger.error("Exception in isstudentNameExist: ", e);
//		}
//		return false;
//	}
	
	@Override
	public Student getStudent(String studentfirstName) throws Exception {
		
		try 
		{
			String hql = "From Student where studentId=?1 and isActive=true";
			return (Student) entityManager.createQuery(hql).setParameter(1, studentfirstName).getSingleResult();
		} 
		catch (EmptyResultDataAccessException e) 
		{
			return null;
		} 
		catch (Exception e) 
		{
			logger.error("Exception in getStudent"+ e.getMessage());
			return null;
		}
	}
	@Override
	public List<Student>getStudents() throws Exception {
		
		try 
		{
			String hql = "FROM Student where isActive=true";
			
			return (List<Student>) entityManager.createQuery(hql).getResultList();
		} 
		catch (Exception e)
		{
			logger.error("Exception in getStudent", e);
		}
		return null;
	}
	
	@Override
	public String forgotPassword(String studentId, String encriptString) throws Exception {
		try {
			Student fran = getStudent(studentId);
			fran.setPassword(encriptString);
			entityManager.flush();
			return StatusCode.SUCCESS.name();
	} catch (Exception e) {
		logger.error("Exception in forgotPassword", e);
		return StatusCode.ERROR.name();
	}
	}
	
//	@Override
//	public boolean isfirstNameExist(String firstName) throws Exception {
//		try {
//			String hql = "FROM Student WHERE firstName=?1 and  isActive=true ";
//			int count = entityManager.createQuery(hql).setParameter(1, firstName).getResultList().size();
//			System.out.println(count);
//			return count > 0 ? true : false;
//		} catch (Exception e) {
//			logger.error("Exception in isfirstNameExist: ", e);
//		}
//		return false;
//	}

	
	@Override
	public Response updateStudentStatus(Student student) throws Exception{
		
		Response response = CommonUtils.getResponseObject("Update Student data");
		try {
			Student student1 = getStudent(student.getStdId());
			
			
			entityManager.flush();
			response.setStatus(StatusCode.SUCCESS.name());
		} catch (Exception e) {
			logger.error("Exception in deleteStudent", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return response;
	}
	
	@Override
	public Student isStudentExist(Student student) throws Exception {
		try {
			String hql = "FROM Student where Email=?1 and isActive=true";
			return (Student) entityManager.createQuery(hql).setParameter(1, student.getEmail()).getSingleResult();
		} catch (Exception e) {
			logger.error("Exception in isStudentExist", e);
		}
	
	
		return student;
	}

	@Override
	public Student authenticate(Student student) throws Exception {
		try 
		{
		String hql = "FROM Student where Email=:Email  and Password=:Password  and isActive=true";
		return (Student) entityManager.createQuery(hql).setParameter("Email", student.getEmail()).setParameter("Password", student.getPassword()).getSingleResult();
		} 
		catch (Exception e)
		{
		logger.error("Exception in auteneticate", e);
	}
		return null;
	}

}



