package pl.altkom.lab11_equals_hashCode;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import pl.altkom.model.Student;
import pl.altkom.util.BaseTest;

public class EqualsTest extends BaseTest {

	@Test
	public void should_check_equality_of_the_same_entity_ids() {
		// given
		Student student1 = new Student();
		student1.setId(1L);
		Student student2 = new Student();
		student2.setId(1L);

		// when
		boolean equals = student1.equals(student2);

		// then
		assertThat(equals).isTrue();

	}

	@Test
	public void should_check_equality_of_the_different_entity_ids() {
		// given
		Student student1 = new Student();
		student1.setId(1L);
		Student student2 = new Student();
		student2.setId(2L);

		// when
		boolean equals = student1.equals(student2);

		// then
		assertThat(equals).isFalse();

	}

	@Test
	public void should_check_equality_of_the_different_entity_object() {
		// given
		Student student1 = new Student();
		student1.setId(1L);

		// when
		boolean equals = student1.equals("ala ma kota");

		// then
		assertThat(equals).isFalse();

	}

}
