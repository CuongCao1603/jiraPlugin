package it.com.atlassian.tutorial.ao.todo.rest;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.*;

import com.atlassian.tutorial.ao.todo.model.MyRestResourceModel;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;

public class MyRestResourceFuncTest {

    @Before
    public void setup() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void messageIsValid() {

        String baseUrl = System.getProperty("baseurl");
        String resourceUrl = baseUrl + "/rest/myrestresource/1.0/message";

        RestClient client = new RestClient();
        Resource resource = client.resource(resourceUrl);

        MyRestResourceModel message = resource.get(MyRestResourceModel.class);

        assertEquals("wrong message","Hello World",message.getMessage());
    }
}
