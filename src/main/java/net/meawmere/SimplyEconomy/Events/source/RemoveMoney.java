package net.meawmere.SimplyEconomy.Events.source;

public class RemoveMoney {
    public Float amount;
    public String user;

    public RemoveMoney(String user, Float amount) {
        this.amount = amount;
        this.user = user;
    }
}
