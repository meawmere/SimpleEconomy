# SimplyEconomy
```java
public class Tests implements IEventListener {
    public static void main(String[] args) throws Exception {
        SQLSimplyEconomy simplyEconomy = SQLSimplyEconomyBuilder
                .setPath("jdbc:sqlite:db.db")
                .setSettingsAdd("users", "money", "userid")
                .setSettingsRemove("users", "money", "userid")
                .addEventsListener(new Tests())
                .build();
        
        simplyEconomy.transfer("1", "2", 1f);
        simplyEconomy.set("1", 1f);
        simplyEconomy.add("1", 1f);
        simplyEconomy.remove("2", 0.5f);
    }

    @Override
    public void onTransfer(Transfer event) {
        System.out.println("New transfer!");
        System.out.println("From: " + event.userFrom);
        System.out.println("To: " + event.userTo);
        System.out.println("Amount: " + event.amount);
    }

    @Override
    public void onReady() {
        System.out.println("ready");
    }

    @Override
    public void onAddMoney(AddMoney event) {
        System.out.println("Add money!");
        System.out.println("User: " + event.user);
        System.out.println("Amount: " + event.amount);
    }

    @Override
    public void onSetMoney(SetMoney event) {
        System.out.println("Set money!");
        System.out.println("User: " + event.user);
        System.out.println("Amount: " + event.amount);
    }

    @Override
    public void onRemoveMoney(RemoveMoney event) {
        System.out.println("Remove money!");
        System.out.println("User: " + event.user);
        System.out.println("Amount: " + event.amount);
    }
}
```
