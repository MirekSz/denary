package pl.altkom.lab02_teacher;

import static org.fest.assertions.Assertions.assertThat;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.junit.Test;

import pl.altkom.model.Teacher;
import pl.altkom.teacher.TeacherDao;
import pl.altkom.util.BaseTest;

import com.google.common.base.Optional;

public class TeacherTest extends BaseTest {

	TeacherDao dao;

	@Override
	protected void beforeTest(Session session) {
		dao = new TeacherDao(session);
	}

	@Test
	public void should_create_teacher() {
		// given
		long rowsInTable = countRowsInTable(Teacher.class);
		Teacher teacher = preapareTeacher();

		// when
		dao.save(teacher);

		// then
		assertThat(countRowsInTable(Teacher.class)).isEqualTo(rowsInTable + 1);

	}

	@Test
	public void should_update_teacher() {
		// given
		Teacher teacher = preapareTeacher();
		dao.save(teacher);

		// when
		Teacher load = (Teacher) session.load(Teacher.class, teacher.getId());
		load.setFirstName("Marek");
		dao.save(load);

		// then
		Teacher result = dao.load(teacher.getId());

		assertThat(result.getFirstName()).isEqualTo("Marek");

	}

	@Test
	public void should_delete_teacher() {
		// given
		long rowsInTable = countRowsInTable(Teacher.class);
		Teacher teacher = preapareTeacher();
		dao.save(teacher);
		assertThat(countRowsInTable(Teacher.class)).isEqualTo(rowsInTable + 1);

		// when
		dao.delete(teacher);

		// then
		assertThat(countRowsInTable(Teacher.class)).isEqualTo(rowsInTable);

	}

	@Test
	public void should_load_existing_teacher() {
		// given
		Teacher teacher = preapareTeacher();
		dao.save(teacher);
		flushClear();

		// when
		Teacher load = dao.load(teacher.getId());

		// then
		assertThat(load).isNotNull();

	}

	@Test(expected = ObjectNotFoundException.class)
	public void should_throw_exception_when_load__not_existing_teacher() {
		// when
		Object load = dao.load(500L);

		// then
		load.toString();
	}

	@Test
	public void should_get_null_when_teacher_not_exist() {
		// when
		Optional<Teacher> optional = dao.get(500L);

		// then
		assertThat(optional.isPresent()).isFalse();

	}

}
