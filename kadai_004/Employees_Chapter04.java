package kadai_004;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Employees_Chapter04 {
    public static void main(String[] args) {
        // データベース接続に必要な情報
        String jdbcUrl = "jdbc:mysql://localhost:3306/challenge_java"; // データベースURL
        String dbUser = "root"; // MySQLユーザー名
        String dbPassword = "Shiomix2528@"; // MySQLパスワード

        Connection connection = null;
        Statement statement = null;

        try {
            // JDBCドライバーをロード
            Class.forName("com.mysql.cj.jdbc.Driver");

            // データベースに接続
            connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
            System.out.println("データベース接続成功：" + connection);

            // テーブル作成SQL
            String createTableSQL = """
                    CREATE TABLE IF NOT EXISTS employees (
                        id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(60) NOT NULL,
                        email VARCHAR(255) NOT NULL,
                        age INT(11),
                        address VARCHAR(255)
                    );
                    """;

            // SQLを実行
            statement = connection.createStatement();
            int result = statement.executeUpdate(createTableSQL);
            System.out.println("社員テーブルを作成しました: 更新レコード数=" + result);

        } catch (Exception e) {
            e.printStackTrace(); // エラー内容を出力
        } finally {
            try {
                // リソースを解放
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}