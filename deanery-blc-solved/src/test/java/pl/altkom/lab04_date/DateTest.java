package pl.altkom.lab04_date;

import org.hibernate.Session;
import org.junit.Test;

import pl.altkom.model.Teacher;
import pl.altkom.teacher.TeacherDao;
import pl.altkom.util.BaseTest;

public class DateTest extends BaseTest {

	TeacherDao dao;

	@Override
	protected void beforeTest(Session session) {
		dao = new TeacherDao(session);
	}

	@Test
	public void should_create_teacher_with_birth_date() {
		// given
		Teacher teacher = preapareTeacher();

		// when
		dao.save(teacher);

		// then
		flush();

	}
}
