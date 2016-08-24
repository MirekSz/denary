package pl.altkom.lab06_hql;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import org.hibernate.Query;
import org.junit.Test;

import pl.altkom.model.TeacheTitle;
import pl.altkom.util.BaseTest;

public class HQLTest extends BaseTest {

	@Test
	public void should_find_teacher_by_email() {
		// given
		String email = "jan.kowalski0gmail.com";
		Query query = session.createQuery("FROM Teacher t WHERE t.email = '"
				+ email + "'");

		// when
		Object result = query.uniqueResult();

		// then
		assertThat(result).isNotNull();

	}

	@Test
	public void should_find_teacher_by_firstName_or_lastName_ignoring_case() {
		String firstName = "jan 3";
		Query query = session.createQuery("FROM Teacher t "
				+ "WHERE upper(t.firstName) = upper('" + firstName
				+ "') OR upper(t.lastName) = upper('" + firstName + "')");

		// when
		Object result = query.uniqueResult();

		// then
		assertThat(result).isNotNull();
	}

	@Test
	public void should_find_teachers_with_title() {
		Query query = session.createQuery("FROM Teacher t " + "WHERE t.title='"
				+ TeacheTitle.Dr + "'");

		// when
		List result = query.list();

		// then
		assertThat(result.size()).isGreaterThan(5);

	}

	@Test
	public void should_find_teachers_with_title_and_birth_date_after_selected_date() {
		Query query = session.createQuery("FROM Teacher t " + "WHERE t.title='"
				+ TeacheTitle.Dr + "' AND t.birthDate> '2000-01-01'");

		// when
		List result = query.list();

		// then
		assertThat(result.size()).isGreaterThan(5);

	}
}
