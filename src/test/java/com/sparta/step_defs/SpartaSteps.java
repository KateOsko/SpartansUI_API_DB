package com.sparta.step_defs;

import com.sparta.pojo.Spartan;
import com.sparta.utilities.BrowserUtil;
import com.sparta.utilities.ConfigurationReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class SpartaSteps extends BrowserUtil  {


    @Given("user hits and POST a spartan on api “endpoint”")
    public void user_hits_and_post_a_spartan_on_api_endpoint(Map<String, Object> jasonBody) {


        RestAssured.baseURI = ConfigurationReader.getProperty("spartan.api.url");

        Response response = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(jasonBody)
                .post("/spartans");

        response.prettyPrint();
        assertEquals(HttpStatus.SC_CREATED, response.statusCode());

        JsonPath jsonPath = response.jsonPath();

        assertEquals(jsonPath.getString("data.name"), jasonBody.get("name"));
        assertEquals(jsonPath.getString("data.gender"), jasonBody.get("gender"));
        assertEquals(jsonPath.getString("data.phone"), jasonBody.get("phone") + "");

        BrowserUtil.spartanId = jsonPath.getInt("data.id");

    }

    @When("user hit and GETs “already created” users’ info from Spartans api “endpoint”")
    public void user_hit_and_ge_ts_already_created_users_info_from_spartans_api_endpoint() {

        RestAssured.baseURI = ConfigurationReader.getProperty("spartan.api.url");

        Response response = given().accept(ContentType.JSON)
                .pathParam("id", spartanId)
                .get("/spartans/{id}");

        response.prettyPrint();

        assertEquals(HttpStatus.SC_OK, response.statusCode());

        Spartan spartan = response.as(Spartan.class);
        assertEquals(spartan.getId(), spartanId);
        assertEquals(spartan.getName(), "Michael");
        assertEquals(spartan.getGender(), "Male");
        assertEquals(spartan.getPhone(), 3124737289L);

    }
    //========================ANA======================

    @When("User connects to DB")
    public void user_connects_to_db() {
        BrowserUtil.createConnection();
    }

    @And("User gets created spartan’s info from DB")
    public void userGetsCreatedSpartanSInfoFromDB() throws SQLException {
        BrowserUtil.getSpartanInfo();

    }

    //==========================UI===========================


    @When("User is on the Spartan’s UI page")
    public void user_is_on_the_spartan_s_ui_page() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("User clicks on Web Data button")
    public void user_clicks_on_web_data_button() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("User search spartan by “name”")
    public void user_search_spartan_by_name() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("User gets created spartan’s information from UI")
    public void user_gets_created_spartan_s_information_from_ui() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    ///=========================================
    @Then("User verifies already created spartans information between UI-DB-API")
    public void user_verifies_already_created_spartans_information_between_ui_db_api() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }



}
