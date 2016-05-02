package org.synyx.syscontrol.execution;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.synyx.syscontrol.TestData;
import org.synyx.syscontrol.action.Action;
import org.synyx.syscontrol.system.System;

/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class ActionExecutorUnitTest {

    private RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
    
    private ActionExecutor actionExecutor = new ActionExecutor(restTemplate);

    @Test
    public void testSuccessfulExecute() throws Exception {

        Action action = TestData.completeAction();
        System system = TestData.completeSystem();
        ExecutionResult expectedResult = TestData.completeExecutionResult();
        
        String expectedUrl = String.format("%s%s", system.getHost(), action.getTemplate());
        Mockito.when(restTemplate.execute(Mockito.eq(expectedUrl),
                Mockito.eq(action.getMethod()),
                Mockito.any(RequestCallback.class),
                Mockito.any(ResponseExtractor.class))).thenReturn(expectedResult);
        
        
        ExecutionResult result = actionExecutor.execute(action, system);

        Assert.assertThat(result, Matchers.is(expectedResult));
    }

    @Test
    public void testErrorHandling() throws Exception {

        Action action = TestData.completeAction();
        System system = TestData.completeSystem();

        HttpStatus statusCode = HttpStatus.ALREADY_REPORTED;
        String statusText = "Something Failed";
        Mockito.when(restTemplate.execute(Mockito.anyString(),
                Mockito.eq(action.getMethod()),
                Mockito.any(RequestCallback.class),
                Mockito.any(ResponseExtractor.class))).thenThrow(new HttpClientErrorException(statusCode, statusText));


        ExecutionResult result = actionExecutor.execute(action, system);

        Assert.assertThat(result.getStatus(), Matchers.is(statusCode));
        Assert.assertThat((String)result.getData().get("message"), Matchers.containsString(statusText));

    }

    @Test
    public void testUnexpectedErrorHandling() throws Exception {

        Action action = TestData.completeAction();
        System system = TestData.completeSystem();
        
        Mockito.when(restTemplate.execute(Mockito.anyString(),
                Mockito.eq(action.getMethod()),
                Mockito.any(RequestCallback.class),
                Mockito.any(ResponseExtractor.class))).thenThrow(new NullPointerException("NPE"));


        ExecutionResult result = actionExecutor.execute(action, system);

        Assert.assertThat(result.getStatus(), Matchers.nullValue());
        Assert.assertThat((String)result.getData().get("message"), Matchers.containsString("NPE"));

    }
}