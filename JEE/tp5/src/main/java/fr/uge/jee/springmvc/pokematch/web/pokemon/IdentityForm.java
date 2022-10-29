package fr.uge.jee.springmvc.pokematch.web.pokemon;

import java.util.Objects;
import javax.validation.constraints.Pattern;

public class IdentityForm {

    @Pattern(regexp = "[a-zA-Z]+")
    private String firstname;

    @Pattern(regexp = "[a-zA-Z]+")
    private String lastname;

    public IdentityForm() {
    }

    public IdentityForm(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
