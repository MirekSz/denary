package pl.altkom;

import java.util.Calendar;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;

import pl.altkom.model.Classes;
import pl.altkom.model.Mark;
import pl.altkom.model.Student;
import pl.altkom.model.Subject;
import pl.altkom.model.SubjectType;
import pl.altkom.model.TeacheTitle;
import pl.altkom.model.Teacher;

public class DataImporter {

	public static void main(String[] args) {
		Configuration configuration = new Configuration();
		// uzupelnic nazwe pliku
		configuration.configure("hibernate.cfg-postgres.xml");
		configuration.setProperty("hibernate.hbm2ddl.auto", "create");
		ServiceRegistryBuilder ssrb = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties());
		SessionFactory sessionFactory = configuration.buildSessionFactory(ssrb
				.buildServiceRegistry());

		Random random = new Random();
		for (int i = 0; i < 200; i++) {
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			Teacher teacher = new Teacher();
			teacher.setFirstName("Jan " + i);
			teacher.setLastName("Kowalski " + i);
			teacher.setEmail("jan.kowalski" + i + "gmail.com");
			teacher.setTitle(i % 3 == 0 ? TeacheTitle.Dr
					: i % 3 == 1 ? TeacheTitle.Mgr : TeacheTitle.Prof);
			Calendar instance = Calendar.getInstance();
			instance.add(Calendar.YEAR, -1 * i);
			teacher.setBirthDate(instance.getTime());
			session.persist(teacher);

			for (int k = 0; k < 10; k++) {
				Student student = new Student();
				student.setFirstName("FirstName " + k);
				student.setLastName("LastName " + k);
				student.setIndex("Index " + i + "" + k);
				session.persist(student);

				for (int j = 0; j < 50; j++) {
					Subject subject = new Subject();
					subject.setTitle("Subject " + i + "" + k + "" + j + "");
					subject.setKind(j % 3 == 0 ? SubjectType.Cw
							: j % 3 == 1 ? SubjectType.Lab : SubjectType.Wyklad);
					session.persist(subject);

					for (int g = 0; g < 5; g++) {
						Calendar calendar = Calendar.getInstance();
						calendar.add(Calendar.DAY_OF_MONTH, j);
						Classes classes = new Classes();
						classes.setRoom("Room " + g);
						classes.setSemester(1);
						classes.setSubject(subject);
						classes.setTeacher(teacher);
						classes.getStudentSet().add(student);
						classes.setDateFrom(calendar.getTime());
						classes.setDateTo(calendar.getTime());
						session.persist(classes);

						for (int v = 0; v < 2; v++) {
							Mark mark = new Mark();
							mark.setSubject(subject);
							mark.setStudent(student);
							mark.setValue(random.nextInt(6) + 1);
							session.persist(mark);
						}
					}
				}

			}
			transaction.commit();
			session.close();
		}

		sessionFactory.close();
	}
}
