package kadai_010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Scores_Chapter10 {

    public static void main(String[] args) {
        // データベース接続情報
        String url = "jdbc:mysql://localhost:3306/java_db_study";
        String user = "root";  // MySQLのユーザー名
        String password = "Shiomix2528@";  // MySQLのパスワード

        Connection conn = null;

        try {
            // データベースに接続
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("データベース接続成功：" + conn);

            // レコード更新
            System.out.println("レコード更新を実行します");
            String updateSQL = "UPDATE scores SET score_math = ?, score_english = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(updateSQL);
            pstmt.setInt(1, 95); // score_math
            pstmt.setInt(2, 80); // score_english
            pstmt.setInt(3, 5);  // id
            int rowsUpdated = pstmt.executeUpdate();
            System.out.println(rowsUpdated + "件のレコードが更新されました");

            // 点数順に並べ替え
            System.out.println("数学・英語の点数が高い順に並べ替えました");
            String querySQL = """
                SELECT id, name, score_math, score_english 
                FROM scores 
                ORDER BY score_math DESC, score_english DESC
            """;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(querySQL);

            // 結果の表示
            int count = 1;
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int scoreMath = rs.getInt("score_math");
                int scoreEnglish = rs.getInt("score_english");

                System.out.printf("%d件目：生徒ID=%d／氏名=%s／数学=%d／英語=%d%n", 
                                  count++, id, name, scoreMath, scoreEnglish);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 接続を閉じる
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
