package gr.aueb.mscis.theater.Resource;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.spi.TestContainerFactory;

import gr.aueb.mscis.theater.persistence.Initializer;

public abstract class TheaterResourceTest extends JerseyTest {

	Initializer dataHelper;

	public TheaterResourceTest() {
		super();
	}

	public TheaterResourceTest(TestContainerFactory testContainerFactory) {
		super(testContainerFactory);
	}

	public TheaterResourceTest(Application jaxrsApplication) {
		super(jaxrsApplication);
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		dataHelper = new Initializer();
		dataHelper.prepareData();
	}
}
