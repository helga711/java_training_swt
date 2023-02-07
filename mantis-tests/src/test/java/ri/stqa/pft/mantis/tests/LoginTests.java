package ri.stqa.pft.mantis.tests;

import org.apache.hc.core5.http.ParseException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ri.stqa.pft.mantis.appmanager.HttpSession;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class LoginTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {

  }

  @Test
  public void testLogin() throws IOException, ParseException {
    HttpSession session = app.newSession();
    assertTrue(session.login("administrator", "root"));
    assertTrue(session.isLoggedInAs("administrator"));
  }
}
