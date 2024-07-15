package net.meawmere.SimplyEconomy.Events;

import net.meawmere.SimplyEconomy.Events.source.AddMoney;
import net.meawmere.SimplyEconomy.Events.source.RemoveMoney;
import net.meawmere.SimplyEconomy.Events.source.SetMoney;
import net.meawmere.SimplyEconomy.Events.source.Transfer;

public interface IEventListener {
    void onTransfer(Transfer event);
    void onReady();
    void onAddMoney(AddMoney event);
    void onSetMoney(SetMoney event);
    void onRemoveMoney(RemoveMoney event);
}
