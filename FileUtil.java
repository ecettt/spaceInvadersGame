import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class FileUtil {

    static String filePath = "./scores.txt";

    public static void createFile() {
        try {
            File file = new File(filePath);
            if (file.createNewFile()) {
                System.out.println("Score file created: " + file.getName());
            } else {
                System.out.println("Score file already exists.");
            }
        } catch (IOException ex) {
            System.out.println("An error occured while creating the file: " + ex.getMessage());
        }
    }
    public static String readUsernameAndScore(String filePath) {
        BufferedReader reader = null;
        int offset = 0;
        int length = 45;
        List<UsernameAndScore> userScores = new ArrayList<>();

        try {
            char[] buffer = new char[length];
            reader = new BufferedReader(new FileReader(filePath));
            while ((reader.read(buffer, 0, length)) > 0) {
                String line = new String(buffer, 0, length);
                String username = line.substring(0, 20).trim();
                int score = Integer.parseInt(line.substring(40, 45).trim());
                userScores.add(new UsernameAndScore(username, score));
                offset += length;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Collections.sort(userScores, Comparator.comparingInt(UsernameAndScore::getScore).reversed());

        StringBuilder stringBuilder = new StringBuilder();
        for (UsernameAndScore userScore : userScores) {
            stringBuilder.append(userScore.getUsername())
                    .append("     ")
                    .append(userScore.getScore())
                    .append("\n");
        }

        return stringBuilder.toString();
    }


    public static String addOrUpdate(String username, String password, String score) {
        BufferedReader reader = null;
        int offset = 0;
        int length = 45;
        try {
            char[] buffer = new char[length];
            reader = new BufferedReader(new FileReader(filePath));
            while ((reader.read(buffer, 0, length)) > 0) {
                String line = new String(buffer, 0, length);
                if (line.substring(0, 20).equals(username)) {
                    return "Username already exists.";
                }
                offset += length;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
            }
        }
        RandomAccessFile writer = null;
        try {
            writer = new RandomAccessFile(filePath, "rw");
            String data = username + password + score;
            writer.seek(offset);
            writer.writeBytes(data);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        return null;
    }

    public static void updateScore(String activeUser, String score) {
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile(filePath, "rw");
            int offset = 0, length = 45;
            byte[] buffer = new byte[length];
            while(file.read(buffer, 0, length) > 0) {
                String line = new String(buffer);
                if(line.substring(0,20).equals(StringUtil.padRight(activeUser, 20, ' '))){
                    file.seek(offset + 40);
                    file.writeBytes(score);
                    break;
                }
                offset += length;
            }
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            if(file != null) {
                try {
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
