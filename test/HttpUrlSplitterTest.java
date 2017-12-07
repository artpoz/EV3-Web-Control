import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class HttpUrlSplitterTest {

	private HttpUrlSplitter splitter;
	
	@Before
	public void setUp() {
		splitter =new HttpUrlSplitter();
	}
	
	@Test
	public void shouldSplitHttpLine() {
		String line ="GET /forward?speed=100 HTTP/1.1";
		
		Command command = splitter.split(line);
		
		Assert.assertEquals("GET", command.getHttpMethod());
		Assert.assertEquals("forward", command.getMove());
		Assert.assertEquals(new Integer(100), command.getSpeed());

	}

}
