package net.meawmere.SimplyEconomy.Events.source;

public class Transfer {
    public Float amount;
    public String userFrom;
    public String userTo;

    public Transfer(String userFrom, String userTo, Float amount) {
        this.amount = amount;
        this.userFrom = userFrom;
        this.userTo = userTo;
    }
}
