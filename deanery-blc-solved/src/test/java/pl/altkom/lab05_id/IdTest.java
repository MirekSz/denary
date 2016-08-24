package pl.altkom.lab05_id;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import pl.altkom.model.Teacher;
import pl.altkom.util.BaseTest;

public class IdTest extends BaseTest {

	private static final int COUNT = 10000;

	@Test
	public void should_create_teacher() {
		// given
		long table = countRowsInTable(Teacher.class);
		for (int i = 0; i < COUNT; i++) {
			Teacher teacher = preapareTeacher();
			teacher.setEmail("Jan " + i);
			// when
			session.persist(teacher);
		}
		// then
		assertThat(countRowsInTable(Teacher.class)).isEqualTo(table + COUNT);

	}
}
