Feature: Create a spartan and verify E2E
  Scenario: User should be able to create a Spartan on API and verify created spartans information between UI -API-DB

  Given user hits and POST a spartan on api “endpoint”
    | name   | Michael    |
    | gender | Male       |
    | phone  | 3124737289 |
    When user hit and GETs “already created” users’ info from Spartans api “endpoint”
    When User connects to DB
    And   User gets created spartan’s info from DB
    When User is on the Spartan’s UI page
    And User clicks on Web Data button
    And User search spartan by “name”
    And User gets created spartan’s information from UI
    Then User verifies already created spartans information between UI-DB-API