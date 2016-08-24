package pl.altkom.teacher;

import org.hibernate.Session;

import pl.altkom.lib.BaseDao;
import pl.altkom.model.Teacher;

public class TeacherDao extends BaseDao<Teacher> {
	public TeacherDao(Session session) {
		super(session);
	}

}
