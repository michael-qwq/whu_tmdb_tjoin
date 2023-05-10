package drz.tmdb.Transaction.Transactions.impl;

import drz.tmdb.Transaction.Transactions.Exception.TMDBException;
import drz.tmdb.Transaction.Transactions.utils.MemConnect;
import drz.tmdb.Transaction.Transactions.utils.SelectResult;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.deputyclass.CreateTJoinDeputyClass;
//import drz.tmdb.Transaction.Transactions.CreateTJoinDeputyClass;
import net.sf.jsqlparser.statement.select.*;

import java.util.ArrayList;
import java.util.List;

public class CreateTJoinDeputyClassImpl extends CreateDeputyClassImpl implements drz.tmdb.Transaction.Transactions.CreateTJoinDeputyClass {
    private MemConnect memConnect;

    public CreateTJoinDeputyClassImpl(MemConnect memConnect) {
        super(memConnect);
        this.memConnect=memConnect;
    }

    public CreateTJoinDeputyClassImpl(){
    }

    public boolean createTJoinDeputyClass(Statement stmt) throws TMDBException {
        return execute((CreateTJoinDeputyClass) stmt);
    }
    private boolean execute(net.sf.jsqlparser.statement.create.deputyclass.CreateTJoinDeputyClass stmt) throws TMDBException {
        Table deputyClass = stmt.getDeputyClass();
        if(memConnect.getClassId(String.valueOf(deputyClass))!=-1){
            throw new TMDBException(deputyClass+" already exists");
        }
        Select select = stmt.getSelect();
        TJoinSelect tJoinSelect=new TJoinSelect(memConnect);
        SelectResult selectResult = tJoinSelect.select(select);
        List<SelectBody> selects = ((SetOperationList) select.getSelectBody()).getSelects();
        String[] strings = new String[0];
        if (!selects.isEmpty()) {
            List<FromItem> list=new ArrayList<>();
            for (int i = 1; i < selects.size(); i++) {
                SelectBody selectBody = selects.get(i);
                list.add(((PlainSelect)selectBody).getFromItem());
            }

            strings = list.stream().map(FromItem::toString).toArray(String[]::new);
        }
        boolean help = super.help(selectResult, 5, String.valueOf(deputyClass));
        if (strings.length!=0) {
            insertElseDeputyTable(strings,5,deputyClass.toString());
        }
        return help;
    }

    private void insertElseDeputyTable(String[] strings, int i, String deputyClass) throws TMDBException {
        int classId = memConnect.getClassId(deputyClass);
        super.insertDeputyTable(strings,i,classId);
    }


}
