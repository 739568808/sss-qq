package top.itning.qq.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class DBSelect {
	
	/**
	 * 判断用户是否存在
	 * @param userName
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public static User selectUser(String userName,String password) throws SQLException {
		 String sql = null;  
		 DBUtil db1 = null;  
		 ResultSet rs = null;
		 User user = new User();
		 sql = "select * from wb_user where userName='"+userName +"' and password='"+password+"'";//SQL语句  
	     db1 = new DBUtil(sql);//创建DBHelper对象  
	  
        try {  
        	rs = db1.pst.executeQuery();//执行语句，得到结果集  
        	while (rs.next()) {  
        		int id = rs.getInt(1);  
                String userName1 = rs.getString(2);  
//                String password1 = rs.getString(3);  
                int permission = rs.getInt(4);  
                String wbUserName = rs.getString(5);  
                String wbPassword = rs.getString(6);  
                String wbContent = rs.getString(7);  
                Date endDate = rs.getDate(8);  
                String wbUserId = rs.getString(9);  
                user.setId(id);
                user.setUserName(userName1);
//                user.setPassword(password1);
                user.setPermission(permission);
                user.setWbUserName(wbUserName);
                user.setWbPassword(wbPassword);
                user.setWbContent(wbContent);
                user.setEndDate(endDate);
                user.setWbUserId(wbUserId);
                System.out.println(id + "\t" + userName1 + "\t"  + "\t" + permission + "\t" + wbUserName + "\t" + wbPassword );  
            }//显示数据  
        	rs.close();  
            db1.close();//关闭连接 
        } catch (SQLException e) {  
            e.printStackTrace();  
        }finally {
        	rs.close();  
            db1.close();//关闭连接  
		}
		return user;
	}
	
	
	public static User selectByPrimary (int id) throws SQLException {
		 String sql = null;  
		 DBUtil db1 = null;  
		 ResultSet rs = null;
		 User user = new User();
		 sql = "select * from wb_user where id="+id;//SQL语句  
	     db1 = new DBUtil(sql);//创建DBHelper对象  
	  
       try {  
       	rs = db1.pst.executeQuery();//执行语句，得到结果集  
       	while (rs.next()) {  
               String userName1 = rs.getString(2);  
//               String password1 = rs.getString(3);  
               int permission = rs.getInt(4);  
               String wbUserName = rs.getString(5);  
               String wbPassword = rs.getString(6);  
               String wbContent = rs.getString(7);  
               Date endDate = rs.getDate(8);  
               user.setId(id);
               user.setUserName(userName1);
//               user.setPassword(password1);
               user.setPermission(permission);
               user.setWbUserName(wbUserName);
               user.setWbPassword(wbPassword);
               user.setWbContent(wbContent);
               user.setEndDate(endDate);
           }//显示数据  
       		rs.close();  
           db1.close();//关闭连接 
       } catch (SQLException e) {  
           e.printStackTrace();  
       }finally {
       	rs.close();  
           db1.close();//关闭连接  
		}
		return user;
	}
	
	
	/**
	 * 插入微博用户名密码
	 * @param userName
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public static boolean insertWbUserNameAndPassword(User user) throws SQLException {
		 String sql = null;  
		 DBUtil db1 = null;  
		 int rs = 0;
		 
		 sql = "update  wb_user set wbUserName= '"+user.getWbUserName()+"' , wbPassword= '"+user.getWbPassword()+"', wbUserId='"+user.getWbUserId()+"' where id ="+user.getId();//SQL语句  
	     db1 = new DBUtil(sql);//创建DBHelper对象  
	  
       try {  
       	rs = db1.pst.executeUpdate(sql);//执行语句，得到结果集  
       } catch (SQLException e) {  
           e.printStackTrace();  
       }finally {
           db1.close();//关闭连接  
		}
		return false;
	}
}
