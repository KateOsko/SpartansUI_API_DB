package com.sparta.step_defs;

import com.sparta.pages.SpartaPage;
import com.sparta.pojo.Spartan;
import com.sparta.utilities.ConfigurationReader;
import com.sparta.utilities.DB_Util;
import com.sparta.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;


import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;


public class SpartaSteps {


    int spartanId;
    SpartaPage spartaPage= new SpartaPage();
    String expectedName;
    String expectedGender;
    String expectedPhone;

    String idAPI;
    String nameAPI;
    String genderAPI;
    String phoneAPI;

    String nameDB;
    String phoneDB;
    String genderDB;
    String idDB;
    String nameUI;
    String phoneUI;
    String genderUI;


    @Given("user hits and POST a spartan on api {string}")
    public void user_hits_and_post_a_spartan_on_api_endpoint( String endpoint,Map<String, String> jasonBody) {
        expectedName =jasonBody.get("name");
        expectedPhone = jasonBody.get("phone");
        expectedGender= jasonBody.get("gender");


        RestAssured.baseURI = ConfigurationReader.getProperty("spartan.api.url");

        Response response = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(jasonBody)
                .post(endpoint);

        response.prettyPrint();
        assertEquals(HttpStatus.SC_CREATED,response.statusCode());

        JsonPath jsonPath = response.jsonPath();

        assertEquals(expectedName, jsonPath.getString("data.name"));
        assertEquals(expectedGender,jsonPath.getString("data.gender"));
        assertEquals(expectedPhone, jsonPath.getString("data.phone"));

        spartanId = jsonPath.getInt("data.id");

    }
    @When("user hit and GETs already created users info from Spartans api {string}")
    public void user_hit_and_ge_ts_already_created_users_info_from_spartans_api_endpoint(String endpoint) {

        RestAssured.baseURI = ConfigurationReader.getProperty("spartan.api.url");

        Response response = given().accept(ContentType.JSON)
                .pathParam("id",spartanId)
                .get(endpoint+"/{id}");

        response.prettyPrint();

        assertEquals(HttpStatus.SC_OK,response.statusCode());

        Spartan spartan = response.as(Spartan.class);
        idAPI= String.valueOf(spartan.getId());
        nameAPI=spartan.getName();
        genderAPI=spartan.getGender();
        phoneAPI= String.valueOf(spartan.getPhone());


    }
    @And("User verifies api information equals created spartans information")
    public void userVerifiesApiInformationEqualsCreatedSpartansInformation() {

        assertEquals(spartanId+"",idAPI);
        assertEquals(expectedName,nameAPI);
        assertEquals(expectedGender,genderAPI);
        assertEquals(expectedPhone,phoneAPI);

    }
    //========================ANA======================

    @When("User connects to DB")
    public void user_connects_to_db() {
        String url=ConfigurationReader.getProperty("spartan.db.url");
        String username = ConfigurationReader.getProperty("spartan.db.username");
        String password = ConfigurationReader.getProperty("spartan.db.password");
        DB_Util.createConnection(url, username, password);
    }

    @And("User gets created spartans info from DB")
    public void userGetsCreatedSpartanSInfoFromDB()  {
        String query="SELECT name, gender, phone, spartan_id FROM spartans WHERE spartan_id = " + spartanId;
        DB_Util.runQuery(query);
        idDB=DB_Util.getCellValue(1, "SPARTAN_ID");
        nameDB=DB_Util.getCellValue(1,"NAME");
        genderDB=DB_Util.getCellValue(1,"GENDER");
        phoneDB=DB_Util.getCellValue(1,"PHONE");

        DB_Util.destroy();


    }

    @And("User verifies DB information equals created spartans information")
    public void userVerifiesDBInformationEqualsCreatedSpartansInformation() {
        assertEquals(spartanId+"",idDB);
        assertEquals(expectedName, nameDB);
        assertEquals(expectedGender, genderDB);
        assertEquals(expectedPhone, phoneDB);
    }

    //==========================UI===========================


    @When("User is on the Spartans UI page")
    public void user_is_on_the_spartan_s_ui_page() {
        Driver.getDriver().get(ConfigurationReader.getProperty("spartan.ui.url"));
    }
    @And("User enters spartans name in the name search input box")
    public void userEntersSpartanSNameInTheNameSearchInputBox() {
        spartaPage.nameInputBox.sendKeys(expectedName);
    }

    @And("User clicks on the search button")
    public void userClicksOnTheSearchButton() {
        spartaPage.btnSearch.click();
    }

    @When("User gets created spartans information from UI")
    public void user_gets_created_spartan_s_information_from_ui() {
        nameUI= spartaPage.getSpartanName(spartanId);
        phoneUI= spartaPage.getSpartanPhone(spartanId);
        genderUI= spartaPage.getSpartanGender(spartanId);

    }


    @Then("User verifies already UI information equal created spartans information")
    public void user_verifies_already_created_spartans_information_between_ui_db_api() {

        assertEquals(expectedName, nameUI);
        assertEquals(expectedPhone, phoneUI);
        assertEquals(expectedGender, genderUI);

        Driver.closeDriver();

    }


}
