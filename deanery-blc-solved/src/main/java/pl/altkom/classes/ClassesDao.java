package pl.altkom.classes;

import org.hibernate.Session;

import pl.altkom.lib.BaseDao;
import pl.altkom.model.Classes;
import pl.altkom.model.Student;

public class ClassesDao extends BaseDao<Classes> {
	public ClassesDao(Session session) {
		super(session);
	}

	public void addStudentToClasses(Classes classes, Student student) {
		// classes.getStudentSet().add(student);
		classes.add(student);
	}

}
