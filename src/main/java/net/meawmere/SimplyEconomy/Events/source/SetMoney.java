package net.meawmere.SimplyEconomy.Events.source;

public class SetMoney {
    public Float amount;
    public String user;

    public SetMoney(String user, Float amount) {
        this.amount = amount;
        this.user = user;
    }
}
