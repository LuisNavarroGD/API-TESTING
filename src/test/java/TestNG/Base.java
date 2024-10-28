/*
package TestNG;

import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Base extends Base {

    @BeforeClass
    public void init(){
        setupGB();
    }

    @Test
    public void testApikeyLoaded(){
        String apiKey = getApikey();
        Assert.assertNotNull(apiKey, "API key should not be null");
        Assert.assertFalse(apiKey.isEmpty(), "API key should not be empty");
    }

    @Test
    public void testTokenLoaded(){
        String token = getToken();
        Assert.assertNotNull(token, "Token should not be null");
        Assert.assertFalse(token.isEmpty(), "Token should not be empty");
    }

    @Test
    public void testBaseUrlLoaded(){
        String baseUrl = getBaseURL();
        Assert.assertNotNull(baseUrl, "Base URL should not be null");
        Assert.assertFalse(baseUrl.isEmpty(), "Base URL should not be empty");

        Assert.assertTrue(baseUrl.startsWith("https://"), "Base URL should start with 'https://'");
        Assert.assertEquals(RestAssured.baseURI, baseUrl, "RestAssured base URI should match the loaded base URL");
    }

}
*/