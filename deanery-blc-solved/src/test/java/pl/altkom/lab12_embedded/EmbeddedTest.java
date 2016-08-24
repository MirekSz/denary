package pl.altkom.lab12_embedded;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Date;

import org.hibernate.Session;
import org.junit.Test;

import pl.altkom.model.SalaryInfo;
import pl.altkom.model.Teacher;
import pl.altkom.teacher.TeacherDao;
import pl.altkom.util.BaseTest;

public class EmbeddedTest extends BaseTest {

	private TeacherDao dao;

	@Test
	public void should_create_teacher_with_salary_info() {
		// given
		Teacher teacher = preapareTeacher();

		Date lastSalaryRise = new Date();
		double salary = 1000.0;
		double credit = 20000.0;
		teacher.setSalaryInfo(new SalaryInfo(lastSalaryRise, salary, credit));
		// when

		dao.save(teacher);

		// then
		assertThat(dao.load(teacher.getId()).getSalaryInfo()).isNotNull();

	}

	@Override
	protected void beforeTest(Session session) {
		dao = new TeacherDao(session);
	}
}
