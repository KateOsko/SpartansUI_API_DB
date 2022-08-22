Feature: Create a spartan and verify E2E
  Scenario: User should be able to create a Spartan on API and verify created spartans information between UI -API-DB

  Given user hits and POST a spartan on api "/spartans"
    | name   | Michael    |
    | gender | Male       |
    | phone  | 3124737289 |
    When user hit and GETs already created users info from Spartans api "/spartans"
    And User verifies api information equals created spartans information
    When User connects to DB
    And   User gets created spartans info from DB
    And User verifies DB information equals created spartans information
    When User is on the Spartans UI page
    And User enters spartans name in the name search input box
    And User clicks on the search button
    And User gets created spartans information from UI
    Then User verifies already UI information equal created spartans information