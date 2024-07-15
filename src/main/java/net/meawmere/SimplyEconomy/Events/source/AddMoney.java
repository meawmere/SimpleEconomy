package net.meawmere.SimplyEconomy.Events.source;

public class AddMoney {
    public Float amount;
    public String user;

    public AddMoney(String user, Float amount) {
        this.amount = amount;
        this.user = user;
    }
}
