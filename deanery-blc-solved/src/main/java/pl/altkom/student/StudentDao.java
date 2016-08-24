package pl.altkom.student;

import org.hibernate.Session;

import pl.altkom.lib.BaseDao;
import pl.altkom.model.Student;

public class StudentDao extends BaseDao<Student> {
	public StudentDao(Session session) {
		super(session);
	}

}
