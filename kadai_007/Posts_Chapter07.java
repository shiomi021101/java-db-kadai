package kadai_007;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Posts_Chapter07 {
    public static void main(String[] args) {
        // データベース接続情報
        String url = "jdbc:mysql://localhost:3306/challenge_java";
        String user = "root"; // データベースユーザー名
        String password = "Shiomix2528@"; // データベースパスワード

        // データベース接続
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("データベース接続成功：" + conn);

            // データ追加用SQLクエリ
            String insertSQL = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES " +
                    "(1003, '2023-02-08', '昨日の夜は徹夜でした・・', 13), " +
                    "(1002, '2023-02-08', 'お疲れ様です！', 12), " +
                    "(1003, '2023-02-09', '今日も頑張ります！', 18), " +
                    "(1001, '2023-02-09', '無理は禁物ですよ！', 17), " +
                    "(1002, '2023-02-10', '明日から連休ですね！', 20);";

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {
                System.out.println("レコード追加を実行します");
                int rowsInserted = insertStmt.executeUpdate();
                System.out.println(rowsInserted + "件のレコードが追加されました");
            }

            // データ検索用SQLクエリ
            String selectSQL = "SELECT posted_at, post_content, likes FROM posts WHERE user_id = ?";
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSQL)) {
                selectStmt.setInt(1, 1002); // ユーザーID1002を検索
                try (ResultSet result = selectStmt.executeQuery()) {
                    System.out.println("ユーザーIDが1002のレコードを検索しました");
                    int count = 1;
                    while (result.next()) {
                        // データを取得
                        String postedAt = result.getDate("posted_at").toString();
                        String postContent = result.getString("post_content");
                        int likes = result.getInt("likes");

                        // データを出力
                        System.out.printf("%d件目：投稿日時=%s／投稿内容=%s／いいね数=%d%n",
                                count, postedAt, postContent, likes);
                        count++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

