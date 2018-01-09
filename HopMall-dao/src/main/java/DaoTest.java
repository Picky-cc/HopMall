import java.sql.*;
public class DaoTest {
        private static final String COM_MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
        private static final String JDBC_MYSQL_LOCALHOST_JASMINE = "jdbc:mysql://192.168.1.171:3306/jasmine?useUnicode=true&characterEncoding=utf-8 ";

        private static final byte[] USER = {114, 111, 111, 116};
        private static final byte[] PASS = {114, 111, 111, 116};

        public static void main(String[] args){
            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try{
                Class.forName(COM_MYSQL_JDBC_DRIVER);
                System.out.println("正在连接数据库...");
                conn = DriverManager.getConnection(JDBC_MYSQL_LOCALHOST_JASMINE, new String(USER), new String(PASS));
                String sql = "select * from user";
                stmt = conn.prepareStatement(sql);
                rs = stmt.executeQuery(sql);

                while(rs.next()){
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    System.out.println("["+id+","+name+"] ");
                }
            }catch(SQLException e){
                System.out.println("sql连接错误:"+e);
            }catch(Exception e){
                System.out.println("其他错误呀:"+e);
            }finally{
                closeConnection(conn,stmt,rs);
            }
        }

    /**
     * 关闭数据库链接
     *
     * 这里做个笔记:
     * conn.close();是为了关闭与数据库链接的资源
     * conn = null;是为了释放这个对象本身的资源
     *
     * @param conn {@link Connection}的实例
     * @param stmt {@link PreparedStatement}的实例
     * @param rs {@link ResultSet}的实例
     * @return 全部关闭返回true,否则返回false
     */
    public static boolean closeConnection(Connection conn, PreparedStatement stmt, ResultSet rs) {
        boolean returnBool = true;
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            returnBool = false;
            System.out.println("rs关闭失败");
        } finally {
            rs = null;
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                returnBool = false;
                System.out.println("stmt关闭失败");
            } finally {
                stmt = null;
                try {
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    returnBool = false;
                    System.out.println("conn关闭失败");
                } finally {
                    conn = null;
                }
            }
        }
        return returnBool;
    }
}
