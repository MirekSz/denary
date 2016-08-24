package pl.altkom.lab08_example;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import org.hibernate.criterion.Example;
import org.junit.Test;

import pl.altkom.model.TeacheTitle;
import pl.altkom.model.Teacher;
import pl.altkom.util.BaseTest;

public class QueryByExampleTest extends BaseTest {

	@Test
	public void should_find_teacher_by_email() {
		// given
		String email = "jan.kowalski0gmail.com";
		Teacher teacher = new Teacher();
		teacher.setEmail(email);
		Example example = Example.create(teacher).ignoreCase();

		// when
		Object result = session.createCriteria(Teacher.class).add(example)
				.uniqueResult();

		// then
		assertThat(result).isNotNull();

	}

	@Test
	public void should_find_teacher_by_firstName_or_lastName_ignoring_case() {
		// BRAK
	}

	@Test
	public void should_find_teachers_with_title() {
		// given
		Teacher teacher = new Teacher();
		teacher.setTitle(TeacheTitle.Dr);
		Example example = Example.create(teacher);

		// when
		List result = session.createCriteria(Teacher.class).add(example).list();

		// then
		assertThat(result.size()).isGreaterThan(5);

	}

	@Test
	public void should_find_teachers_with_title_and_birth_date_after_selected_date() {
		// BRAK

	}
}
