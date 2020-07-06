package com.hibernate.one.onetoooneuniRemovingInstructorDetail;

import com.hibernate.one.onetoooneuniRemovingInstructorDetail.entity.Instructor;
import com.hibernate.one.onetoooneuniRemovingInstructorDetail.entity.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DeletingOnlyInstructorDetails {

    public static void main(String[] args) {

        // create session factory
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .buildSessionFactory();

        // create session
        Session session = factory.getCurrentSession();

        try {

            // start a transaction
            session.beginTransaction();

            // get the instructor detail object
            int theId = 8;
            InstructorDetail tempInstructorDetail =
                    session.get(InstructorDetail.class, theId);

            // print the instructor detail
            System.out.println("tempInstructorDetail: " + tempInstructorDetail);

            // print  the associated instructor
            System.out.println("the associated instructor: " +
                    tempInstructorDetail.getInstructor());

            // now let's delete the instructor detail
            System.out.println("Deleting tempInstructorDetail: "
                    + tempInstructorDetail);

            // remove the associated object reference
            // break bi-directional link

            tempInstructorDetail.getInstructor().setInstructorDetail(null);
//            if we do comment the above line
//             (remove deleted object from associations): [com.hibernate.one.onetoooneuniRemovingInstructorDetail.entity.InstructorDetail#8]

//            which means remove the instructorDetails class from the association.
//            this class has association with instructor.
//            so set null instructor



//            With the first method we are creating less queries running
//wihtout flash and and refresh
//    org.hibernate.SQL - update instructor set email=?, first_name=?, instructordetail_id=?, last_name=? where id=?
//    org.hibernate.SQL - delete from instructordetail where id=?
//            WITH ------>

//            	@OneToOne(mappedBy="instructorDetail",
//			cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
//					CascadeType.REFRESH})

//WITH
//            calling flush() we force hibernate to execute the SQL commands on Database. But do understand that changes are not "committed" yet.
//            	@OneToOne(mappedBy="instructorDetail",
//			cascade={CascadeType.ALL})
            session.flush();
            session.refresh(tempInstructorDetail);

            session.delete(tempInstructorDetail);

            // commit transaction
            session.getTransaction().commit();

            System.out.println("Done!");
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        finally {
            // handle connection leak issue
            session.close();

            factory.close();
        }
    }

}
