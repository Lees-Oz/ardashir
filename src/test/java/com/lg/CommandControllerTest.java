package com.lg;

import com.lg.controllers.CommandController;
import com.lg.cqrs.IProcessCommand;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import spark.Request;
import spark.Response;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class CommandControllerTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public CommandControllerTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( CommandControllerTest.class );
    }

    public void test_When_command_request_Should_process() throws Exception {
        // Arrange
        Request req = mock(Request.class);
        Response res = mock(Response.class);

        IProcessCommand commandProcessor = mock(IProcessCommand.class);

        String commandName = UUID.randomUUID().toString();
        String requestBody = UUID.randomUUID().toString();

        when(req.params(":name")).thenReturn(commandName);
        when(req.body()).thenReturn(requestBody);

        final CommandController target = new CommandController(commandProcessor);

        // Act
        target.handle(req, res);

        // Assert
        verify(commandProcessor).process(commandName, requestBody);
        verify(res).status(200);
    }
}
