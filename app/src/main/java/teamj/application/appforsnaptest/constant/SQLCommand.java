package teamj.application.appforsnaptest.constant;

import android.util.Log;

import teamj.application.appforsnaptest.LoginActivity;

/**
 * Created by Rakesh on 27-09-2016.
 */

public class SQLCommand {
public static String user;

    SQLCommand()
    {
        Log.d("Email now is",user);
    }
    public static String PasswordFetch = "Select password from User where user_id = '"+ user + "'";
    public static String RoleFetch = "Select User_Role from User where user_id ='"+ user + "'";
    //List the call numbers of books with the title ‘Database Management’
    public static String QUERY_2 = "select lbcallnum from libbook where lbtitle like '%Database Management%'";
    // Display the list of student ID who have paid the fine for returning the book late
    public static String QUERY_3 = "select checkout.stid,student.stname from student,checkout where cofine >0 AND student.stid = checkout.stid group by checkout.stid;";
    // Display the list of the Call num and the due date of the books that where not returned
    public static String QUERY_4 = "Select checkout.lbcallnum, checkout.coduedate from checkout, Student where checkout.coreturned = \"N\" group by checkout.lbcallnum;";
    // List of the students and the details of the student who haven't returned the books
    public static String QUERY_5 = "Select checkout.lbcallnum, checkout.coduedate, checkout.stid, Student.stname from checkout, Student where checkout.coreturned = \"N\" AND checkout.stid = Student.stid group by checkout.lbcallnum;\n";
    // Display the total fine paid by the Students
    public static String QUERY_6 = "select sum(cofine) AS Total_Fine_Paid_By_Students from checkout;";
    // Display the details of students who borrowed the book B@ which is Fifth Discipline
    public static String QUERY_7 = "select checkout.stid, checkout.lbcallnum, Student.stname, libbook.lbtitle from checkout, Student, Libbook where checkout.lbcallnum = \"b2\" AND checkout.lbcallnum = libbook.lbcallnum AND checkout.stid = Student.stid group by checkout.stid;";
    public static String RETURN_BOOK = "update checkout set coreturned=? where stid=? and lbcallnum=?";
    public static String CHECK_BOOK = "insert into checkout(stid,lbcallnum,coduedate,coreturned) values(?,?,?,?)";
    public static String CHECKOUT_SUMMARY = "select strftime('%m',coduedate) as month,count(*) as total from checkout where strftime('%Y',coduedate)='2011' group by month order by total desc";
}
