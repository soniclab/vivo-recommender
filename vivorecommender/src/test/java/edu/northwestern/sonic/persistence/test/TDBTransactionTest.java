package edu.northwestern.sonic.persistence.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

import edu.northwestern.sonic.model.User;
import edu.northwestern.sonic.persistence.JavatoRDF;
import edu.northwestern.sonic.persistence.TDBAccessObject;
import edu.northwestern.sonic.persistence.TDBTransaction;
import edu.northwestern.sonic.util.StringDigesterUtil;

public class TDBTransactionTest {
	private static User ego;
	private static String directory;
	private static Model m;
	private static TDBTransaction tdbTransaction;
	
	@Before
	public void setUp() throws Exception {
		ego = new User();
		ego.setEmail("mconlon@ufl.edu");
		ego.setUri(new URI("http://vivo.ufl.edu/individual/n25562"));
		ego.setPassword(StringDigesterUtil.digest("wadapav"));
		Properties properties = new Properties();
		properties.load(getClass().getResourceAsStream("/vivorecommender.properties"));
		directory = properties.getProperty("directoryTDB");
		TDBAccessObject.setDataset(directory);
		m = JavatoRDF.write(ego);
		tdbTransaction = new TDBTransaction();
	}

	@After
	public void tearDown() throws Exception {
		m.close();
	}

	@Test
	public void testUpdateState() throws IOException, URISyntaxException {
		tdbTransaction.updateState(m);
		m.close();
		m = tdbTransaction.getDefaultModel();
		Resource res = m.getResource("http://sonic.northwestern.edu/User/" +
				"mconlon@ufl.edu");
		Property prop = m.getProperty("http://sonic.northwestern.edu/Password");
		assertEquals("Check for property value : ", true, res.hasLiteral(prop,
				"EF92AA94C6FE50BD710D6377B771EB119F89D368"));
		
	}
}
