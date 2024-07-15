package net.meawmere.SimplyEconomy;

import net.meawmere.SimplyEconomy.Events.IEventListener;
import net.meawmere.SimplyEconomy.Events.source.AddMoney;
import net.meawmere.SimplyEconomy.Events.source.RemoveMoney;
import net.meawmere.SimplyEconomy.Events.source.SetMoney;
import net.meawmere.SimplyEconomy.Events.source.Transfer;

import java.sql.*;
import java.util.List;

public class SQLSimplyEconomy {
    protected String executeSet;
    protected String executeSetColumn;
    protected String executeSetColumnUser;
    protected String executeRemove;;
    protected String executeRemoveColumn;
    protected String executeRemoveColumnUser;
    protected String path;
    protected List<IEventListener> listeners;

    @Deprecated
    public SQLSimplyEconomy(String executeSet, String executeSetColumn, String executeSetColumnUser,
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

    public SQLSimplyEconomy add(String user, Float amount) throws Exception {
        try (Connection connection = DriverManager.getConnection(path);
             Statement statement = connection.createStatement()){
            statement.executeUpdate(String.format(
                    "UPDATE %s SET %s = %s + %s WHERE %s = %s",
                    executeSet, executeSetColumn, executeSetColumn, amount, executeSetColumnUser, user
            ));

            for (IEventListener listener:listeners) {
                listener.onAddMoney(new AddMoney(user, amount));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return (new SQLSimplyEconomy(executeSet, executeSetColumn, executeSetColumnUser,
                executeRemove, executeRemoveColumn, executeRemoveColumnUser,
                path, listeners));
    }

    public SQLSimplyEconomy remove(String user, Float amount) throws Exception {
        try (Connection connection = DriverManager.getConnection(path);
             Statement statement = connection.createStatement()){
            statement.executeUpdate(String.format(
                    "UPDATE %s SET %s = %s - %s WHERE %s = %s",
                    executeSet, executeSetColumn, executeSetColumn, amount, executeSetColumnUser, user
            ));

            for (IEventListener listener:listeners) {
                listener.onRemoveMoney(new RemoveMoney(user, amount));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return (new SQLSimplyEconomy(executeSet, executeSetColumn, executeSetColumnUser,
                executeRemove, executeRemoveColumn, executeRemoveColumnUser,
                path, listeners));
    }

    public SQLSimplyEconomy set(String user, Float amount) throws Exception {
        try (Connection connection = DriverManager.getConnection(path);
             Statement statement = connection.createStatement()){
            statement.executeUpdate(String.format(
                    "UPDATE %s SET %s = %s WHERE %s = %s",
                    executeSet, executeSetColumn, amount, executeSetColumnUser, user
            ));

            for (IEventListener listener:listeners) {
                listener.onSetMoney(new SetMoney(user, amount));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return (new SQLSimplyEconomy(executeSet, executeSetColumn, executeSetColumnUser,
                executeRemove, executeRemoveColumn, executeRemoveColumnUser,
                path, listeners));
    }

    public SQLSimplyEconomy transfer(String userFrom, String userTo, Float amount) throws Exception {
        try (Connection connection = DriverManager.getConnection(path);
             Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery(String.format("SELECT %s FROM %s WHERE %s = %s",
                    executeRemoveColumn, executeRemove, executeRemoveColumnUser, userFrom));
            while (rs.next()) {
                if (rs.getFloat(executeRemoveColumn) < amount) {
                    throw new RuntimeException("The user's balance is less than necessary");
                } else {
                    statement.executeUpdate(String.format(
                            "UPDATE %S SET %s = %s + %s WHERE %s = %s",
                            executeSet, executeSetColumn, executeSetColumn, amount, executeSetColumnUser, userTo
                    ));
                    statement.executeUpdate(String.format(
                            "UPDATE %S SET %s = %s - %s WHERE %s = %s",
                            executeRemove, executeRemoveColumn, executeRemoveColumn, amount, executeRemoveColumnUser, userFrom
                    ));
                    for (IEventListener listener : listeners) {
                        listener.onTransfer(new Transfer(userFrom, userTo, amount));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return (new SQLSimplyEconomy(executeSet, executeSetColumn, executeSetColumnUser,
                executeRemove, executeRemoveColumn, executeRemoveColumnUser,
                path, listeners));
    }
}
