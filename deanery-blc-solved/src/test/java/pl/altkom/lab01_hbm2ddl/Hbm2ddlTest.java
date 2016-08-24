package pl.altkom.lab01_hbm2ddl;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import pl.altkom.util.BaseTest;

public class Hbm2ddlTest extends BaseTest {
	@Test
	public void should_create_session() {
		assertThat(session).isNotNull();
	}
}
