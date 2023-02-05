package ru.stqa.pft.addressbook.appmanager;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.List;

public class ContactHelper extends HelperBase{

    public ContactHelper(WebDriver driver) {
        super(driver);
    }

    public void create(@NotNull ContactData contactData) throws Exception {
        fillContactForm(contactData, true);
        submitContactCreation();
        contactCache = null;
        returnToHomePage();
    }

    public void delete(@NotNull ContactData contact) {
        selectContact(contact.getId());
        deleteSelectedContact();
        contactCache = null;
    }

    public void modify(@NotNull ContactData contact) throws Exception {
        initModification(contact.getId());
        fillContactForm(contact, false);
        submitContactModification();
        contactCache = null;
        returnToHomePage();
    }

    public void fillContactForm(@NotNull ContactData contact, Boolean creation) throws Exception {
        type(By.name("firstname"), contact.getFirstName());
        type(By.name("lastname"), contact.getLastName());
        attach(By.name("photo"), contact.getPhoto());
        type(By.name("address"), contact.getAddress());
        type(By.name("home"), contact.getPhoneHome());
        type(By.name("mobile"), contact.getPhoneMobile());
        type(By.name("work"), contact.getPhoneWork());
        type(By.name("email"), contact.getEmail());
        type(By.name("email2"), contact.getEmail2());
        type(By.name("email3"), contact.getEmail3());
        int groupsSize = contact.getGroups().size();
        if (creation && groupsSize > 0) {
            if (groupsSize > 1){
                throw new Exception(String.format("Contact %s has more than one group for Create operation. Cannot choose.", contact));
            }
            selectByValue(By.name("new_group"), String.valueOf(contact.getGroups().any().getId()));
        }
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

        contactCache = new Contacts();
        List<WebElement> rows = driver.findElements(By.name("entry"));
        for (WebElement row : rows) {
            List<WebElement> columns = row.findElements(By.tagName("td"));
            String id = columns.get(0).findElement(By.tagName("input")).getAttribute("id");
            String lastName = columns.get(1).getText();
            String firstName = columns.get(2).getText();
            String address = columns.get(3).getText();
            String allEmails = columns.get(4).getText();
            String allPhones = columns.get(5).getText();

            contactCache.add(new ContactData()
                    .withId(Integer.parseInt(id))
                    .withFirstName(firstName)
                    .withLastName(lastName)
                    .withAddress(address)
                    .withAllEmails(allEmails)
                    .withAllPhones(allPhones));
        }
        return new Contacts(contactCache);
    }

    public ContactData infoFromEditForm(int id) {
        initModification(id);
        String firstName = driver.findElement(By.name("firstname")).getAttribute("value");
        String lastName = driver.findElement(By.name("lastname")).getAttribute("value");
        String home = driver.findElement(By.name("home")).getAttribute("value");
        String mobile = driver.findElement(By.name("mobile")).getAttribute("value");
        String work = driver.findElement(By.name("work")).getAttribute("value");
        String email = driver.findElement(By.name("email")).getAttribute("value");
        String email2 = driver.findElement(By.name("email2")).getAttribute("value");
        String email3 = driver.findElement(By.name("email3")).getAttribute("value");
        String address = driver.findElement(By.name("address")).getAttribute("value");
        return new ContactData()
                .withId(id).withFirstName(firstName).withLastName(lastName)
                .withPhoneHome(home).withPhoneWork(work).withPhoneMobile(mobile)
                .withEmail(email).withEmail2(email2).withEmail3(email3)
                .withAddress(address);
    }

    public void selectGroup(int id) {
        selectByValue(By.name("to_group"), String.valueOf(id));
    }

    public void submitAddingToGroup() {
        click(By.name("add"));
    }

    public void AddToGroups(ContactData contact, Groups groups) {
        for (GroupData group : groups) {
            selectContact(contact.getId());
            selectGroup(group.getId());
            submitAddingToGroup();
            contactCache = null;
            navigateToContactsInGroup(group);
        }
    }

    private void navigateToContactsInGroup(GroupData group) {
        click(By.linkText(String.format("group page \"%s\"", group.getName())));
    }
}
