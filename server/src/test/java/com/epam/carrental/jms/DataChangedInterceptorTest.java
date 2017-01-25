package com.epam.carrental.jms;

import com.epam.carrental.config.ServicesConfig;
import com.epam.carrental.dto.CustomerDTO;
import com.epam.carrental.services.CustomerService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ServicesConfig.class})
public class DataChangedInterceptorTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    CustomerService customerService;
    @Autowired
    DataChangedInterceptor dataChangedInterceptor;

    private MessageSender messageSenderMock ;

    @BeforeMethod
    public void setUp() {
        messageSenderMock = Mockito.mock(MessageSender.class);
        dataChangedInterceptor.messageSender = messageSenderMock;
    }

    @Test
    public void testMessageSenderCommit() throws Exception {
        // arrange
        //act
        customerService.create(new CustomerDTO("uniqueName", "uniqueEmail"));

        //assert
        verify(messageSenderMock, times(1)).sendNotification(any());
    }

    @Test
    public void testMessageSenderRollback() throws Exception {
        // arrange

        //act
        customerService.create(new CustomerDTO("uniqueName", "uniqueEmail"));

        //assert
        verify(messageSenderMock, times(1)).sendNotification(any());
        reset(messageSenderMock);
        try {
            customerService.create(new CustomerDTO("uniqueName", "uniqueEmail"));
        } catch (IllegalArgumentException ex) {
        }
        verify(messageSenderMock, times(0)).sendNotification(any());
    }
}