package com.hibernate.onetomany.EagerLazyVersion2HQLJoinFetch;

import com.hibernate.onetomany.EagerLazyVersion2HQLJoinFetch.entity.InstructorDetail;
import com.hibernate.onetomany.EagerLazyVersion2HQLJoinFetch.entity.Course;
import com.hibernate.onetomany.EagerLazyVersion2HQLJoinFetch.entity.Instructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class FetchJoinDemo {

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
			
			// option 2: Hibernate query with HQL
			
			// get the instructor from db
			int theId = 1;


//			The FETCH keyword of the JOIN FETCH statement is JPA-specific.
//			It tells the persistence provider
//			to not only join the 2 database tables within the query but to also
//			initialize the association on the returned entity.
			Query<Instructor> query =
					session.createQuery("select i from Instructor i "
									+ "JOIN FETCH i.courses "
									+ "where i.id=:theInstructorId",
							Instructor.class);

			// set parameter on query
			query.setParameter("theInstructorId", theId);

//			>>-- Why did we use getSingleResult instead of getResultList?
//			To load the instructor and courses at all once (single query).
			// execute query and get instructor
			Instructor tempInstructor = query.getSingleResult();
			
			System.out.println("luv2code: Instructor: " + tempInstructor);	
			
			// commit transaction
			session.getTransaction().commit();
			
			// close the session
			session.close();
			
			System.out.println("\nluv2code: The session is now closed!\n");
			
			// get courses for the instructor
			System.out.println("luv2code: Courses: " + tempInstructor.getCourses());
			
			System.out.println("luv2code: Done!");
		}
		finally {
			
			// add clean up code
			session.close();
			
			factory.close();
		}
	}

}





