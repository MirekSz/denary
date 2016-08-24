package pl.altkom.lab16_finders;

import static org.fest.assertions.Assertions.assertThat;
import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.ge;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.junit.Test;

import pl.altkom.model.Student;
import pl.altkom.model.Subject;
import pl.altkom.util.BaseTest;

public class CriteriaProTest extends BaseTest {
	private Long SUBJECT_ID;

	@Override
	protected void beforeTest(Session session) {
		Subject subject = (Subject) session.createCriteria(Subject.class)
				.add(eq("title", "Subject 000")).uniqueResult();
		this.SUBJECT_ID = subject.getId();
	}

	@Test
	public void should_find_students_5_from_selected_subject() {
		// when
		Criteria criteria = session.createCriteria(Student.class, "s");
		criteria.createAlias("s.markSet", "m");
		criteria.createAlias("m.subject", "sub");
		criteria.add(ge("m.value", 5));
		criteria.add(eq("sub.id", SUBJECT_ID));
		criteria.addOrder(Order.asc("s.firstName"));

		criteria.setMaxResults(10);
		List list = criteria.list();

		// then
		assertThat(list).isNotEmpty();
	}
}
