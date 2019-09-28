package com.testathon.automation.keywords;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.path.json.JsonPath.from;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.jayway.restassured.response.Response;
import com.testathon.automation.core.Config;
import com.testathon.automation.logging.LoggingManager;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class RestKeywords {

	static String header;
	static String param;
	private String message;
	public static String token;

	/** converts Response to string 
	 */
	public static String convertResponseToString(Response response){
		String stringResponse=response.asString();
		stringResponse = stringResponse.replaceAll("\\s+","");
		return stringResponse;
	}

	public static Response getJSONResponse1(String apiUri ,String method ,Map<String,String> headers, Map<String,String> params , String message) throws Exception{

		switch (method.toLowerCase())
		{
		case "post":
			if (header.equals("NA") && param.equals("NA")){
				return given().when().body(message).post(apiUri);
			}
			else if (header.equals("NA") && !param.equals("NA") ){
				return given().queryParams(params).when().body(message).post(apiUri);
			}
			else if (param.equals("NA")){
				return given().headers(headers).when().body(message).post(apiUri);
			}
			else {
				return given().headers(headers).queryParams(params).when().body(message).post(apiUri);
			}

		case "get":
			if (header.equals("NA") && param.equals("NA")){
				return given().when().get(apiUri);
			}
			else if (header.equals("NA") && !param.equals("NA") ){
				return given().queryParams(params).when().get(apiUri);
			}
			else if (param.equals("NA")){
				return given().headers(headers).when().get(apiUri);
			}
			else {
				return given().headers(headers).queryParams(params).when().get(apiUri);
			}

		case "put":
			if (header == "NA" && param == "NA"){
				return given().when().body(message).put(apiUri);
			}
			else if (header.equals("NA") && !param.equals("NA")){
				return given().queryParams(params).when().body(message).put(apiUri);
			}
			else if (param.equals("NA")){
				return given().headers(headers).when().body(message).put(apiUri);
			}
			else {
				return given().headers(headers).queryParams(params).when().body(message).put(apiUri);
			}


		case "delete":
			if (header == "NA" && param == "NA"){
				return given().when().delete(apiUri);
			}
			else if (header.equals("NA") && !param.equals("NA") ){
				return given().queryParams(params).when().delete(apiUri);
			}
			else if (param.equals("NA")){
				return given().headers(headers).when().delete(apiUri);
			}
			else {
				return given().headers(headers).queryParams(params).when().delete(apiUri);
			}

		default:
			throw new Exception("Method " + method + " is not suppported");
		}
	}


	/**getting JSON object value from String converted Response
	 * @author mohitmaliwal
	 */
	public static String getJSONObjectValue(String stringResponse, String objectPath){
		String apiResponse =String.valueOf(from(stringResponse).get(objectPath));
		return apiResponse;
	}


	/**Asserting each JSON Object value with expected value
	 * @author mohitmaliwal
	 */
	public static void assertJSONResponseObjects(String stringResponse, String[] object, String[] objectPath, String[] objectValue){
		for(int i=0;i<objectPath.length;i++)
		{
			String actualValue=getJSONObjectValue(stringResponse, objectPath[i]);
			actualValue = actualValue.replaceAll("\\s+","");
			String expectedValue = objectValue[i];
			expectedValue = expectedValue.replaceAll("\\s+","");
			Assert.assertEquals(actualValue, expectedValue);
			LoggingManager.getConsoleLogger().info(" ----Asserted---- " + objectPath[i] + ": " + actualValue);
			LoggingManager.getReportLogger().log(Status.PASS, " Object value Matched. ||  Expected value:" + expectedValue + " ||  Actual Code : " + actualValue);
		}
		LoggingManager.getConsoleLogger().info("-------------------API Tested Successfully---------------------");
	}

	public static void getToken(){
		String umMessage = "grant_type=client_credentials&client_id=b2e28e62-91d3-4972-aacb-8439249dafa7&client_secret=c9fa5df7-b590-4c72-a6b1-3de221b818d8&scope=all_um_claims";
		//String UMmessage = "grant_type=password" + "&" + "client_id=" + Config.ClientId + "&" + "client_secret=" + Config.ClientSecret +"&" + "redirect_uri=" + Config.RedirectUri + "&" + "username=" + Config.Username + "&" +"password=" + Config.Password;
		Response response = given().when().header("Content-Type", "application/x-www-form-urlencoded").body(umMessage).post(Config.UMUri);
		String stringResponse = convertResponseToString(response);
		if(response.getStatusCode()==500)
			LoggingManager.getConsoleLogger().error("Internal Server Error - Token is not generated");
		else if(response.getStatusCode()==415)
			LoggingManager.getConsoleLogger().error("Unsupported Media Type - Token is not generated");
		else if(response.getStatusCode()==400)
			LoggingManager.getConsoleLogger().error("Bad Request - Token is not generated");
		else if(response.getStatusCode()==404)
			LoggingManager.getConsoleLogger().error("API doesnt exist - Token is not generated");
		else
		token = getJSONObjectValue(stringResponse,"access_token");
	}

	public static Map<String,String> createParamsMap(String[] paramKeys,String[] paramValues){
		Map<String,String> params=new HashMap<String,String>();
		for(int i=0;i<paramKeys.length;i++)
			params.put(paramKeys[i], paramValues[i]);
		return params;
	}

	public static Map<String,String> createHeadersMap(String[] paramKeys,String[] paramValues){
		Map<String,String> params=new HashMap<String,String>();
		for(int i=0;i<paramKeys.length;i++){
			params.put(paramKeys[i], paramValues[i]);
		}
		params.put("Authorization",  "bearer " + token);
		return params;
	}

	public static String[][] webReadExcel(int startRow, String sheetPath, String sheetName) throws BiffException, IOException {

		File inputWorkbook = new File(sheetPath);
		Workbook w;
		w = Workbook.getWorkbook(inputWorkbook);
		Sheet sheet = w.getSheet(sheetName);

		int column = sheet.getColumns();
		int row = sheet.getRows();

		String[][] strArray = null;
		strArray = new String[row-startRow][column];

		for (int j = startRow; j < row; j++) 
		{
			for (int i = 0; i < column; i++) 
			{
				Cell cell = sheet.getCell(i,j);
				String s =cell.getContents();
				strArray[j-startRow][i] = s;
			}

		} 
		return strArray;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public static void testAPICall(String testName,String	serverURL, String apiPath, String method, String requestHeaderKey, 
			String requestHeaderValue, String apiParameterKey, String apiParameterValues,String requestPayload, String responseJSONObjectName, 
			String responseJSONObjectPath,String expectedJSONObjectValue, String expectedResponseCode) throws Exception{
		Map<String,String> params;
		Map<String, String> headers;
		Response response;
		String[] paramKeys;
		String[] paramValues;

		String methodName = testName;
		String apiUri = serverURL + apiPath;
		String method1 = method;
		String[] headerKeys = requestHeaderKey.split(";");
		String[] headerValues = requestHeaderValue.split(";");
		paramKeys=apiParameterKey.split(";");
		paramValues=apiParameterValues.split(";");
		String message = requestPayload;
		String[] object=responseJSONObjectName.split(";");
		String[] objectPath=responseJSONObjectPath.split(";");
		String[] objectValue=expectedJSONObjectValue.split(";");
		int responseCode = Integer.parseInt(expectedResponseCode);
		header = requestHeaderKey;
		param = apiParameterKey;
		
		//Get token
		getToken();
		
		//Create Headers Map and Params Map
		headers = createHeadersMap(headerKeys, headerValues);
		params=createParamsMap(paramKeys, paramValues );

		LoggingManager.getReportLogger().log(Status.PASS, " Test Method Name :" + methodName );
		LoggingManager.getReportLogger().log(Status.PASS, " API URL Used :" + apiUri );
		LoggingManager.getReportLogger().log(Status.PASS, " Method Used : " + method1 );
		LoggingManager.getReportLogger().log(Status.PASS, " Payload Used : " + message );

		//getting response from request
		response = getJSONResponse1(apiUri , method1 , headers, params ,message );

		//calling method to convert Response to string
		if(response.statusCode() == responseCode){
			String stringResponse=convertResponseToString(response);
			LoggingManager.getReportLogger().log(Status.PASS, " Status code Matched.  ||   Expected Code : " + responseCode + "||  Actual Code : " + response.statusCode());
			LoggingManager.getConsoleLogger().info("Response Code is : " + response.statusCode());
			LoggingManager.getConsoleLogger().info("Response String is --------");
			LoggingManager.getConsoleLogger().info(stringResponse);
			assertJSONResponseObjects(stringResponse, object, objectPath, objectValue);
		}
		else 
		{	
			LoggingManager.getReportLogger().log(Status.FAIL, " Status code doesnt Matched. ||   Expected Code : " + responseCode + " || Actual Code : " + response.statusCode());
			String stringResponse= convertResponseToString(response);
			LoggingManager.getConsoleLogger().info("Response Code is : " + response.statusCode());
			LoggingManager.getConsoleLogger().info("Response String is --------");
			LoggingManager.getConsoleLogger().info(stringResponse);
			Assert.assertEquals(response.statusCode(), responseCode);
		}
	}
}
