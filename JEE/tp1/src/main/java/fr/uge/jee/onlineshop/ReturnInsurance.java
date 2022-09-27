package fr.uge.jee.onlineshop;

public class ReturnInsurance implements Insurance {

    private boolean membersOnly;

    @Override
    public String getDescription() {
        return "Return insurance" + (membersOnly ? " for members only" : "");
    }

    public void setMembersOnly(boolean membersOnly) {
        this.membersOnly = membersOnly;
    }
}
