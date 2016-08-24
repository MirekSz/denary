package pl.altkom.lab13_cascade;

import static org.fest.assertions.Assertions.assertThat;
import static org.hibernate.criterion.Restrictions.eq;

import org.hibernate.Session;
import org.junit.Test;

import pl.altkom.model.Mark;
import pl.altkom.model.Student;
import pl.altkom.model.Subject;
import pl.altkom.student.StudentDao;
import pl.altkom.util.BaseTest;

public class CascadeTest extends BaseTest {
	private StudentDao studentDao;

	private Long STUDENT_ID;
	private Subject subject;

	@Override
	protected void beforeTest(Session session) {
		studentDao = new StudentDao(session);
		Student student = (Student) session.createCriteria(Student.class)
				.add(eq("index", "Index 00")).uniqueResult();
		this.STUDENT_ID = student.getId();

		subject = (Subject) session.createCriteria(Subject.class)
				.add(eq("title", "Subject 000")).uniqueResult();
	}

	@Test
	public void should_add_mark_to_student_marks_collection_and_save() {
		// given
		long countRowsInTable = countRowsInTable(Mark.class);
		Mark mark = new Mark();
		mark.setValue(5);
		mark.setSubject(subject);

		// when
		Student student = studentDao.load(STUDENT_ID);
		student.getMarkSet().add(mark);

		// then
		assertThat(countRowsInTable(Mark.class))
				.isEqualTo(countRowsInTable + 1);

	}

	@Test
	public void should_delete_student_with_all_his_marks() {
		// given
		long countRowsInTable = countRowsInTable(Mark.class);

		// when

		studentDao.delete(STUDENT_ID);

		// then
		assertThat(countRowsInTable(Mark.class)).isLessThan(countRowsInTable);

	}
}
