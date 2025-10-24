package javaFaker;

import com.github.javafaker.Faker;

public class FakerExample {
    public static void main(String[] args) {
        Faker faker = new Faker();

        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String phone = faker.phoneNumber().cellPhone(); 

        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
        
        
        
        String city = faker.address().city();
        String country = faker.address().country();
        String street = faker.address().streetAddress();
        String company = faker.company().name();

        System.out.println("Company: " + company);
        System.out.println("Street: " + street);
        System.out.println("City: " + city);
        System.out.println("Country: " + country);
        
        
        
        String accountNumber = faker.finance().iban(); // fake IBAN
        String creditCard = faker.finance().creditCard(); // fake credit card number

        System.out.println("Account Number: " + accountNumber);
        System.out.println("Credit Card: " + creditCard);
    }
}

