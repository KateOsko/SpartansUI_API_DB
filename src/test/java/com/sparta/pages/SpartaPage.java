package com.sparta.pages;

import com.sparta.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SpartaPage {

    public SpartaPage(){
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(id = "name")
    public WebElement nameInputBox;

    @FindBy(id = "search")
    public WebElement btnSearch;


    //tbody/tr/td[.=16]
    public String getSpartanName(int spartanID){
       return Driver.getDriver().findElement(By.xpath("//tbody/tr/td[.="+spartanID+"]/following-sibling::*")).getText();

    }

    public String getSpartanPhone(int spartanID){
        return Driver.getDriver().findElement(By.xpath("//tbody/tr/td[.="+spartanID+"]/following-sibling::*/following-sibling::*")).getText();

    }

    public String getSpartanGender(int spartanID){
        return Driver.getDriver().findElement(By.xpath("//tbody/tr/td[.="+spartanID+"]/..//td/span")).getText();

    }




}
