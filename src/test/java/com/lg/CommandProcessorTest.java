package com.lg;

import com.google.inject.Injector;
import com.lg.commandExecutors.RequestNewGameCommandExecutor;
import com.lg.cqrs.CommandProcessor;
import com.lg.cqrs.ICommand;
import com.lg.cqrs.IExecuteCommand;
import com.lg.cqrs.IFindCommand;
import com.lg.messages.commands.RequestNewGame;
import com.lg.utils.IJsonSerializer;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.UUID;

import static org.mockito.Mockito.*;

/**
 * Unit test for simple App.
 */
public class CommandProcessorTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public CommandProcessorTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( CommandProcessorTest.class );
    }

    public void test_When_command_request_Should_process() throws Exception {
        // Arrange
        String commandName = UUID.randomUUID().toString();
        String commandBody = UUID.randomUUID().toString();

        IJsonSerializer serializer = mock(IJsonSerializer.class);
        IFindCommand commandFinder = mock(IFindCommand.class);
        Injector injector = mock(Injector.class);

        ICommand command = mock(ICommand.class);
        IExecuteCommand executor = mock(IExecuteCommand.class);
        Class commandClass = RequestNewGame.class;
        Class executorClass = RequestNewGameCommandExecutor.class;

        when(commandFinder.findCommandExecutorClass(commandName)).thenReturn(executorClass);
        when(commandFinder.findCommandClass(commandName)).thenReturn(commandClass);
        when(serializer.deserialize(commandBody, commandClass)).thenReturn(command);
        when(injector.getInstance(executorClass)).thenReturn(executor);

        final CommandProcessor target = new CommandProcessor(commandFinder, serializer, injector);

        // Act
        target.process(commandName, commandBody);

        // Assert
        verify(commandFinder).findCommandClass(commandName);
        verify(commandFinder).findCommandExecutorClass(commandName);
        verify(serializer).deserialize(commandBody, commandClass);
        verify(injector).getInstance(executorClass);
        verify(executor).execute(command);
    }
}
