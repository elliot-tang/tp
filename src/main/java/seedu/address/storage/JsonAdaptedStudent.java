package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Student;
import seedu.address.model.tag.Tag;

public class JsonAdaptedStudent extends JsonAdaptedPerson {

    public static final String MISSING_PARENT_FIELD_MESSAGE_FORMAT = "Person's parent %s field is missing!";

    private final String parentName;
    private final String parentPhone;
    private final String parentEmail;

    public JsonAdaptedStudent(String name, String phone, String email, String address, String parentName,
            String parentPhone, String parentEmail, List<JsonAdaptedTag> tags) {
        super(name, phone, email, address, tags);
        this.parentName = parentName;
        this.parentEmail = parentEmail;
        this.parentPhone = parentPhone;
    }

    public JsonAdaptedStudent(Student source) {
        super(source);
        parentName = source.getParentName() == null ? null : source.getParentName().fullName;
        parentPhone = source.getParentPhone() == null ? null : source.getParentPhone().value;
        parentEmail = source.getParentEmail() == null ? null : source.getParentEmail().value;
    }

    @Override
    public Person toModelType() throws IllegalValueException {

        String name = this.getName();
        String phone = this.getPhone();
        String email = this.getEmail();
        String address = this.getAddress();
        List<JsonAdaptedTag> tags = this.getTags();

        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        Name modelParentName = null;
        if (parentName != null) {
            if (!Name.isValidName(parentName)) {
                throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
            }
            modelParentName = new Name(parentName);
        }

        Phone modelParentPhone = null;
        if (parentPhone != null) {
            if (!Phone.isValidPhone(parentPhone)) {
                throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
            }
            modelParentPhone = new Phone(parentPhone);
        }

        Email modelParentEmail = null;
        if (parentEmail != null) {
            if (!Email.isValidEmail(parentEmail)) {
                throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
            }
            modelParentEmail = new Email(parentEmail);
        }

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Student(modelName, modelPhone, modelEmail, modelAddress, modelParentName, modelParentPhone,
                modelParentEmail, modelTags);
    }
}
