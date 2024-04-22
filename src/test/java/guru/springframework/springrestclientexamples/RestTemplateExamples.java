package guru.springframework.springrestclientexamples;

import com.fasterxml.jackson.databind.JsonNode;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Created by jt on 9/22/17.
 */
/**
 * Created by jt on 9/22/17.
 */
public class RestTemplateExamples {

    public static final String API_ROOT = "https://fruitshop2-predic8.azurewebsites.net/shop/v2";

    @Test
    public void getVendors() throws Exception {
        String apiUrl = API_ROOT + "/vendors";

        RestTemplate restTemplate = new RestTemplate();

        JsonNode jsonNode = restTemplate.getForObject(apiUrl, JsonNode.class);

        System.out.println("Response");
        System.out.println(jsonNode.toString());
    }


    @Test
    public void getCustomers() throws Exception {
        String apiUrl = API_ROOT + "/customers";

        RestTemplate restTemplate = new RestTemplate();

        JsonNode jsonNode = restTemplate.getForObject(apiUrl, JsonNode.class);

        System.out.println("Response");
        System.out.println(jsonNode.toString());
    }

    @Test
    public void createProduct() throws Exception {
        String apiUrl = API_ROOT + "/products";

        RestTemplate restTemplate = new RestTemplate();


        //Java object to parse to JSON
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("name", "Oranges");
        postMap.put("price",5.65);

        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);


        System.out.println("Response");
        System.out.println(jsonNode.toString());
    }

    @Test
    public void updateProduct() throws Exception {

        //create customer to update
        String apiUrl = API_ROOT + "/products";

        RestTemplate restTemplate = new RestTemplate();

        //Java object to parse to JSON
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("name", "Oranges");
        postMap.put("price",5.65);

        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);

        System.out.println("Response");
        System.out.println(jsonNode.toString());

        String productUrl = jsonNode.get("self_link").textValue();

        String id = productUrl.split("/")[4];

        System.out.println("Created product id: " + id);

        postMap.put("name", "Bananas");
        postMap.put("price", "7.56");

        restTemplate.put(apiUrl + "/" + id, postMap);

        JsonNode updatedNode = restTemplate.getForObject(apiUrl + "/" + id, JsonNode.class);

        System.out.println(updatedNode.toString());

    }

    @Test(expected = ResourceAccessException.class)
    public void updateProductUsingPatchSunHttp() throws Exception {

        //create customer to update
        String apiUrl = API_ROOT + "/products";

        RestTemplate restTemplate = new RestTemplate();

        //Java object to parse to JSON
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("name", "Oranges");
        postMap.put("price", "4.44");

        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);

        System.out.println("Response");
        System.out.println(jsonNode.toString());

        String productUrl = jsonNode.get("self_link").textValue();

        String id = productUrl.split("/")[4];

        System.out.println("Created product id: " + id);

        postMap.put("name", "Grapes");
        postMap.put("price", "6.65");

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(postMap, headers);

        //fails due to sun.net.www.protocol.http.HttpURLConnection not supporting patch
        JsonNode updatedNode = restTemplate.patchForObject(apiUrl + "/" + id, entity, JsonNode.class);

        System.out.println(updatedNode.toString());

    }

    @Test
    public void updateProductUsingPatch() throws Exception {

        //create customer to update
        String apiUrl = API_ROOT + "/products";

        // Use Apache HTTP client factory
        //see: https://github.com/spring-cloud/spring-cloud-netflix/issues/1777
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        //Java object to parse to JSON
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("name", "Bananas");
        postMap.put("price", "4.56");

        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);

        System.out.println("Response");
        System.out.println(jsonNode.toString());

        String productUrl = jsonNode.get("self_link").textValue();

        String id = productUrl.split("/")[4];

        System.out.println("Created product id: " + id);

        postMap.put("name", "Pinnaple");
        postMap.put("price", "11.11");

        //example of setting headers
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(postMap, headers);

        JsonNode updatedNode = restTemplate.patchForObject(apiUrl + "/" + id, entity, JsonNode.class);

        System.out.println(updatedNode.toString());
    }

    @Test(expected = HttpClientErrorException.class)
    public void deleteProduct() throws Exception {

        String apiUrl = API_ROOT + "/products";

        RestTemplate restTemplate = new RestTemplate();


        //Java object to parse to JSON
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("name", "Oranges");
        postMap.put("price",5.65);

        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);

        System.out.println("Response");
        System.out.println(jsonNode.toString());

        String productUrl = jsonNode.get("self_link").textValue();

        String id = productUrl.split("/")[4];

        System.out.println("Created product id: " + id);

        restTemplate.delete(apiUrl + "/" + id); //expects 200 status

        System.out.println("product deleted");

        //should go boom on 404
        restTemplate.getForObject(apiUrl + "+" + id, JsonNode.class);

    }


}