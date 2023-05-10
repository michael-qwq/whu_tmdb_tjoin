package drz.tmdb.Transaction.Transactions;

import net.sf.jsqlparser.statement.Statement;

import drz.tmdb.Transaction.Transactions.Exception.TMDBException;

public interface CreateTJoinDeputyClass {
    boolean createTJoinDeputyClass(Statement stmt) throws TMDBException;
}
