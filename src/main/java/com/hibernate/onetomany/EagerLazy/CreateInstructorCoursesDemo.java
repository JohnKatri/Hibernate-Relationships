package com.hibernate.onetomany.EagerLazy;

import com.hibernate.onetomany.EagerLazy.entity.InstructorDetail;
import com.hibernate.onetomany.EagerLazy.entity.Course;
import com.hibernate.onetomany.EagerLazy.entity.Instructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CreateInstructorCoursesDemo {

	public static void main(String[] args) {

		// create session factory
		Configuration configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");
		configuration.addAnnotatedClass(Instructor.class);
		configuration.addAnnotatedClass(Course.class);
		configuration.addAnnotatedClass(InstructorDetail.class);
		SessionFactory factory = configuration
								.buildSessionFactory();
		
		// create session
		Session session = factory.getCurrentSession();
		
		try {			
			
			// start a transaction
			session.beginTransaction();
			
			// create instructor
			Instructor tempInstructor = new Instructor("Daffy", "Duck", "daffy.duck@luv2code.com");
			
			session.save(tempInstructor);
			
			// create some courses
			Course tempCourse1 = new Course("Duck training - volume 1");
			Course tempCourse2 = new Course("Duck training - volume 2");
			
			// add courses to instructor
			tempInstructor.add(tempCourse1);
			tempInstructor.add(tempCourse2);
			
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





