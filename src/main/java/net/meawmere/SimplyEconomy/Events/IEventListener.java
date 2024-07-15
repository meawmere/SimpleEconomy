package net.meawmere.SimplyEconomy.Events;

import net.meawmere.SimplyEconomy.Events.source.Transfer;

public interface IEventListener {
    void onTransfer(Transfer event);
    void onReady();
}
