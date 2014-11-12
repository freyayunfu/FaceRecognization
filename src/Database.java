
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fuyun
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import static java.util.Calendar.*;
import java.util.GregorianCalendar;
import java.util.Properties;




public class Database {
    public static Connection conn = null;
    PreparedStatement psInsert;
    PreparedStatement psUpdate;
    Statement s;
    Statement s1;
    Statement s2;
    Statement s3;
    Statement s4;
    ResultSet rs = null;
    String[] data1 = new String[4];
    Properties props = new Properties();
    String dbName = "FaceDB";
    

    
    
    public Database(){     
            /*
             * This connection specifies create=true in the connection URL to
             * cause the database to be created when connecting for the first
             * time. 
             */
            
            props.put("user", "");
            props.put("password", "");
            try{
            
            conn = DriverManager.getConnection("jdbc:derby:" + dbName
                    + ";create=true", props);
            
            conn.setAutoCommit(false);
            s = conn.createStatement();
           
            System.out.println("0");

            // We create a table... table cruise
            s.executeUpdate("create table student(StudentID int, Name varchar(40), "
                    + "Gender varchar(40), Program varchar(40), Age int, "
                    + "Nationalities varchar(40), primary key(StudentID))");
            System.out.println("1.0");
            psInsert = conn.prepareStatement("insert into student values (?, ?, ?, ?, ?, ?)");
            System.out.println("1.1");
            
            String[] FirstNames = {"Bob", "Jill", "Tom", "Brandon", "Joan", "Ethel", "Albert", "Hpward", "Roy", "Annie", "Alice", "Ruby", "Donald", "Carl", "Bonnie", "Lisa", "Scott", "Sean", "Morgan", "Oliva"};
            String[] LastNames = {"Matthew", "Nathan", "Aaron", "Zachary", "Jadon", "Matteo", "Harrison", "Titus", "Magnus", "Jax", "Jude", "Dexter", "Sawyer", "Beckett", "Miles", "Land", "Letitia", "Leopold", "Louise", "Lucretia"};
            String[] Gender = {"Male", "Female"};
            String[] Programs = {"MISM", "MSPPM", "MSIT"};
            String[] Nationalities = {"China", "Japan", "USA", "Korea", "Australia", "India"};
   
System.out.println("1");
            for (int i = 1; i < 85; i++) { 
                psInsert.setInt(1, i);
                int index1 = (int) (Math.random() * FirstNames.length);
                int index2 = (int) (Math.random() * LastNames.length);
                psInsert.setString(2, FirstNames[index1] + " " + LastNames[index2]);
                int index3 = (int) (Math.random() * Gender.length);
                psInsert.setString(3, Gender[index3]);
                int index4 = (int) (Math.random() * Programs.length);
                psInsert.setString(4, Programs[index4]);
                int age = (int) (20 + Math.random() * 10);
                psInsert.setInt(5, age);
                int index6 = (int) (Math.random() * Nationalities.length);
                psInsert.setString(6, Nationalities[index6]);
                
                psInsert.executeUpdate();
            }
            System.out.println("2");
            //table passenger
            s.executeUpdate("create table visit(VisitID int, StudentID int, "
                    + "date double, category varchar(40), primary key(VisitID))");
            System.out.println("2.0");
            psInsert = conn.prepareStatement("insert into visit values (?, ?, ?, ?)");
            
            
            String[] Categories = {"stapler","tuition fee", "complaints", "collect assignments","meet people"};
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy, MMM, dd");
            System.out.println("2.1");
	    
            
            for(int i=1; i<1001; i++){
                psInsert.setInt(1, i);
                int index2 = (int) (1 + Math.random() * 85);
                psInsert.setInt(2, index2);
                Calendar calendar = randomDateBetweenMinAndMax();
                psInsert.setLong(3, calendar.getTime().getTime());
                int index4 = (int) (Math.random() * Categories.length);
                psInsert.setString(4, Categories[index4]);
                psInsert.executeUpdate();
                
            }
            
            System.out.println("3");
            
            conn.commit();
            
            System.out.println("Generate Report");
            rs = s.executeQuery("SELECT * FROM student");
           
            while (rs.next()) {
                System.out.println(rs.getInt("StudentID")+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getInt(5)+" "+rs.getString(6));
            }
            System.out.println("");

            }catch(SQLException ex){
                
            }

    }
    
    public static Calendar randomDateBetweenMinAndMax(){  
       Calendar calendar = Calendar.getInstance();  
       //注意月份要减去1  
       calendar.set(2000,11,31);  
       calendar.getTime().getTime();  
       //根据需求，这里要将时分秒设置为0  
       calendar.set(Calendar.HOUR_OF_DAY, 0);  
       calendar.set(Calendar.MINUTE, 0);  
       calendar.set(Calendar.SECOND,0);  
       long min = calendar.getTime().getTime();  
       calendar.set(2014,11,12);  
       calendar.set(Calendar.HOUR_OF_DAY,0);  
       calendar.set(Calendar.MINUTE,0);  
       calendar.set(Calendar.SECOND,0);  
       calendar.getTime().getTime();  
       long max = calendar.getTime().getTime();  
       //得到大于等于min小于max的double值  
       double randomDate = Math.random()*(max-min)+min;  
       //将double值舍入为整数，转化成long类型  
       calendar.setTimeInMillis(Math.round(randomDate));  
       return calendar;    
    }  
    
    public int getFrequency(Calendar date1, Calendar date2, String category, String gender){
        int frequency = 0;
        
        try{
            conn = DriverManager.getConnection("jdbc:derby"+dbName+"create = false"+props);
            s1 = conn.createStatement();
            rs = s1.executeQuery("SELECT COUNT(visitID) AS frequency FROM visit where date>"+date1.getTime().getTime()+
                    "AND date<"+date2.getTime().getTime());
            
            conn.commit();
            
            frequency = rs.getInt("frequency");
            System.out.println("frequency");
            
        }catch(SQLException ex){
            
        }
        
        return frequency;
        
    }
       
       public void close() throws SQLException {
           conn.close();
       }
    
}


