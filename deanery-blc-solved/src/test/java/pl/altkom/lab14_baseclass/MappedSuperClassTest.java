package pl.altkom.lab14_baseclass;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Date;

import org.hibernate.Session;
import org.junit.Test;

import pl.altkom.model.Teacher;
import pl.altkom.teacher.TeacherDao;
import pl.altkom.util.BaseTest;

public class MappedSuperClassTest extends BaseTest {

	private TeacherDao dao;

	@Override
	protected void beforeTest(Session session) {
		this.dao = new TeacherDao(session);
	}

	@Test
	public void should_add_teacher_with_last_modified_date() {
		// given
		Date date = new Date();

		Teacher teacher = preapareTeacher();
		teacher.setLastModified(date);
		dao.save(teacher);

		// when
		Teacher load = dao.load(teacher.getId());

		// then
		assertThat(load.getLastModified()).isNotNull();
	}
}
