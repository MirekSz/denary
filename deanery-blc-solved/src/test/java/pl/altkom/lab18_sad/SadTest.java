package pl.altkom.lab18_sad;

import java.util.List;

import org.hibernate.Criteria;
import org.junit.Test;

import pl.altkom.model.Student;
import pl.altkom.util.BaseTest;

public class SadTest extends BaseTest {

	@Test
	public void should_change_index() {
		// given
		Criteria criteria = session.createCriteria(Student.class, "s");
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Student> list = criteria.list();

		// when
		Student student1 = list.get(0);
		Student student2 = list.get(1);
		String index1 = student1.getIndex();
		String index2 = student2.getIndex();

		student1.setIndex("tymczasowe");
		student2.setIndex(index1);
		session.flush();
		student1.setIndex(index2);

	}
}
