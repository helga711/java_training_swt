package ru.stqa.pft.addressbook.appmanager;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import java.util.List;

public class ContactHelper extends HelperBase{

    public ContactHelper(WebDriver driver) {
        super(driver);
    }

    public void create(@NotNull ContactData contactData) {
        fillContactForm(contactData);
        submitContactCreation();
        contactCache = null;
        returnToHomePage();
    }

    public void delete(@NotNull ContactData contact) {
        selectContact(contact.getId());
        deleteSelectedContact();
        contactCache = null;
    }

    public void modify(@NotNull ContactData contact) {
        initModification(contact.getId());
        fillContactForm(contact);
        submitContactModification();
        contactCache = null;
        returnToHomePage();
    }

    public void fillContactForm(@NotNull ContactData contactData) {
        type(By.name("firstname"), contactData.getFirstName());
        type(By.name("lastname"), contactData.getLastName());
        type(By.name("address"), contactData.getAddress());
        type(By.name("home"), contactData.getPhoneHome());
        type(By.name("email"), contactData.getEmail());
    }

    public void submitContactCreation() {
        click(By.name("submit"));
    }

    public void returnToHomePage() {
        click(By.linkText("home page"));
    }

    public void deleteSelectedContact() {
        click(By.xpath("//input[@value='Delete']"));
        acceptAlert();
    }

    public void selectContact(int id) {
        driver.findElement(By.xpath(String.format("//input[@id='%s']", id))).click();
    }

    public void initModification(int id) {
        driver.findElement(By.xpath(String.format("//a[@href='edit.php?id=%s']", id))).click();
    }

    public void submitContactModification() {
        click(By.name("update"));
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public int count() {
        return driver.findElements(By.name("selected[]")).size();
    }

    private Contacts contactCache = null;

    public Contacts all() {
        if (contactCache != null) {
            return new Contacts(contactCache);
        }

        Contacts contacts = new Contacts();
        List<WebElement> rows = driver.findElements(By.name("entry"));
        for (WebElement row : rows) {
            List<WebElement> columns = row.findElements(By.tagName("td"));
            String id = columns.get(0).findElement(By.tagName("input")).getAttribute("id");
            String lastName = columns.get(1).getText();
            String firstName = columns.get(2).getText();
            contacts.add(new ContactData()
                    .withId(Integer.parseInt(id))
                    .withFirstName(firstName)
                    .withLastName(lastName));
        }
        return contacts;
    }
}
