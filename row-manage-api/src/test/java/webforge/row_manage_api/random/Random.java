package webforge.row_manage_api.random;
import fabricator.Contact;
import fabricator.Fabricator;

import static java.lang.String.format;

public class Random {
    private static final Contact contact = Fabricator.contact();

    public static String firstName() {
        return contact.firstName();
    }

    public static String lastName() {
        return contact.lastName();
    }

    public static String fullName() {
        return format("%s %s", firstName(), lastName());
    }

    public Random() {
    }

    public static String generateNumber() {
        return contact.phoneNumber();
    }

    public static String generateEmail() {
        return contact.eMail();
    }
}
