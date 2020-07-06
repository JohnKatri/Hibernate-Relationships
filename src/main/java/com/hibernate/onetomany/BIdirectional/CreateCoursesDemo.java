package com.hibernate.onetomany.BIdirectional;

import com.hibernate.onetomany.BIdirectional.entity.Course;
import com.hibernate.onetomany.BIdirectional.entity.Instructor;
import com.hibernate.onetomany.BIdirectional.entity.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CreateCoursesDemo {

	public static void main(String[] args) {

		// create session factory
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Instructor.class)
								.addAnnotatedClass(InstructorDetail.class)
								.addAnnotatedClass(Course.class)
								.buildSessionFactory();
		
		// create session
		Session session = factory.getCurrentSession();
		
		try {			
			
			// start a transaction
			session.beginTransaction();
			
			// get the instructor from db
			int theId = 1;
			Instructor tempInstructor = session.get(Instructor.class, theId);		
			
			// create some courses
			Course tempCourse1 = new Course("2Air Guitar - The Ultimate Guide");
			Course tempCourse2 = new Course("2The Pinball Masterclass");

//			It is not compulsory to have both either 1 or 2

//			This is 1
			tempCourse1.setInstructor(tempInstructor);
			tempCourse2.setInstructor(tempInstructor);

//			This is 2
			// add courses to instructor
//			tempInstructor.add(tempCourse1);
//			tempInstructor.add(tempCourse2);
			
			// save the courses
			session.save(tempCourse1);
			session.save(tempCourse2);
			
			// commit transaction
			session.getTransaction().commit();
			
			System.out.println("Done!");
		}
		finally {
			
			// add clean up code
			session.close();
			
			factory.close();
		}
	}

}





