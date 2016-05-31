package DBAdapters;

import android.os.StrictMode;
import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLAdapter
{
    String ip = "172.24.20.67";
    String dbName = "controlventas";
    String userName = "sa";
    String password = "Ssc123456";

    public SQLAdapter()
    {
    }

    public ResultSet connect(String query)
    {
        ResultSet result = null;

        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();

            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Connection dbConn = DriverManager.getConnection("jdbc:jtds:sqlserver://"+ip+"/"+dbName+";user=" + userName + ";password=" + password);

            Statement statement = dbConn.createStatement();
            result = statement.executeQuery(query);

            //dbConn.close();

        }
        catch (Exception e)
        {
            Log.w(" \n\n\n\n\nError: ",e.getMessage()+"\n\n\n");
        }
        return result;
    }
}