package net.meawmere.SimplyEconomy.Builder;

import net.meawmere.SimplyEconomy.Events.IEventListener;
import net.meawmere.SimplyEconomy.SQLSimplyEconomy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class SQLSimplyEconomyBuilder {
    protected String executeSet;
    protected String executeSetColumn;
    protected String executeSetColumnUser;
    protected String executeRemove;;
    protected String executeRemoveColumn;
    protected String executeRemoveColumnUser;
    protected String path;
    protected List<IEventListener> listeners;

    protected SQLSimplyEconomyBuilder(String executeSet, String executeSetColumn, String executeSetColumnUser,
                                      String executeRemove, String executeRemoveColumn, String executeRemoveColumnUser,
                                      String path, List<IEventListener> listeners) {
        this.executeRemove = executeRemove;
        this.executeRemoveColumn = executeRemoveColumn;
        this.executeRemoveColumnUser = executeRemoveColumnUser;
        this.executeSet = executeSet;
        this.executeSetColumn = executeSetColumn;
        this.executeSetColumnUser = executeSetColumnUser;
        this.path = path;
        this.listeners = listeners;
    }

    public static SQLSimplyEconomyBuilder setPath(String path) {
        return (new SQLSimplyEconomyBuilder("", "", "",
                "", "", "", path, new ArrayList<>()));
    }

    /**
     * Enter the name of the table, the cells with the money, the cells with the user ID that will be used when performing the operation.
     * Example: {@code setTableAdd("users", "money", "userid")}
     * @param table the table to be edited.
     * @param cellMoney the cell where information about the user's amount is stored.
     * @param cellUser the cell where the user ID or other method of user identification is stored.
     */
    public SQLSimplyEconomyBuilder setSettingsAdd(String table, String cellMoney, String cellUser) {
        return (new SQLSimplyEconomyBuilder(table, cellMoney, cellUser,
                this.executeRemove, this.executeRemoveColumn, this.executeRemoveColumnUser,
                this.path, this.listeners));
    }

    /**
     * Enter the name of the table, the cells with the money, the cells with the user ID that will be used when performing the operation.
     * Example: {@code setSettingsRemove("users", "money", "userid")}
     * @param table the table to be edited.
     * @param cellMoney the cell where information about the user's amount is stored.
     * @param cellUser the cell where the user ID or other method of user identification is stored.
     */
    public SQLSimplyEconomyBuilder setSettingsRemove(String table, String cellMoney, String cellUser) {
        return (new SQLSimplyEconomyBuilder(this.executeSet, this.executeSetColumn, this.executeSetColumnUser,
                table, cellMoney, cellUser,
                this.path, this.listeners));
    }

    public SQLSimplyEconomyBuilder addEventsListener(IEventListener listener) {
        listeners.add(listener);
        return (new SQLSimplyEconomyBuilder(executeSet, executeSetColumn, executeSetColumnUser,
                executeRemove, executeRemoveColumn, executeRemoveColumnUser,
                path, listeners));
    }

    public SQLSimplyEconomy build() {
        if (Objects.equals(executeSet, "") || Objects.equals(executeRemove, "")) {
            throw new RuntimeException("The table to add or remove cannot be empty!");
        }
        return (new SQLSimplyEconomy(executeSet, executeSetColumn, executeSetColumnUser,
                executeRemove, executeRemoveColumn, executeRemoveColumnUser,
                path, listeners));
    }

}
