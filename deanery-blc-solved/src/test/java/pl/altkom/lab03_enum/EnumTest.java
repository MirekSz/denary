package pl.altkom.lab03_enum;

import org.hibernate.Session;
import org.junit.Test;

import pl.altkom.model.Teacher;
import pl.altkom.teacher.TeacherDao;
import pl.altkom.util.BaseTest;

public class EnumTest extends BaseTest {

	TeacherDao dao;

	@Override
	protected void beforeTest(Session session) {
		dao = new TeacherDao(session);
	}

	@Test
	public void should_create_teacher_with_title() {
		// given
		Teacher teacher = preapareTeacher();

		// when
		dao.save(teacher);

		// then
	}
}
