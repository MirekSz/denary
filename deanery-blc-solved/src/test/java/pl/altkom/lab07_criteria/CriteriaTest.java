package pl.altkom.lab07_criteria;

import static org.fest.assertions.Assertions.assertThat;
import static org.hibernate.criterion.Restrictions.and;
import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.ge;
import static org.hibernate.criterion.Restrictions.or;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.junit.Test;

import pl.altkom.model.TeacheTitle;
import pl.altkom.model.Teacher;
import pl.altkom.util.BaseTest;

public class CriteriaTest extends BaseTest {
	@Test
	public void should_find_teacher_by_email() {
		// given
		String email = "jan.kowalski0gmail.com";

		// when
		Criteria criteria = session.createCriteria(Teacher.class);
		Object result = criteria.add(eq("email", email)).uniqueResult();

		// then
		assertThat(result).isNotNull();

	}

	@Test
	public void should_find_teacher_by_firstName_or_lastName_ignoring_case() {
		String firstName = "jan 3";

		// when
		Criteria criteria = session.createCriteria(Teacher.class);
		Object result = criteria.add(
				or(eq("firstName", firstName).ignoreCase(),
						eq("lastName", firstName).ignoreCase())).uniqueResult();

		// then
		assertThat(result).isNotNull();
	}

	@Test
	public void should_find_teachers_with_title() {
		// when
		Criteria criteria = session.createCriteria(Teacher.class);
		List result = criteria.add(eq("title", TeacheTitle.Dr)).list();

		// then
		assertThat(result.size()).isGreaterThan(5);

	}

	@Test
	public void should_find_teachers_with_title_and_birth_date_after_selected_date() {
		// given
		Calendar calendar = Calendar.getInstance();
		calendar.set(2000, 1, 1);

		// when
		Criteria criteria = session.createCriteria(Teacher.class);
		List result = criteria.add(
				and(eq("title", TeacheTitle.Dr),
						ge("birthDate", calendar.getTime()))).list();

		// then
		assertThat(result.size()).isGreaterThan(5);

	}

}
