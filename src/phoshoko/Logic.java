package phoshoko;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class Logic
{

    static Connect db = new Connect();
    //user details
    static String username, password;
    static String userGuess = "", state = "else", randomWord = "", wordsToAdd = "";
    static int time = 60;
    static boolean canContinue = true, timerAlowed = false, timerOn = false;
    static String[] wordsFromFile = new String[2315];
    static int letter = 0, code, attempt = 0, score = 0, avgGuess = 0, preScore = 0;
    static int currentStreak = 0, userID = 0, games = 0, loses = 0, wins = 0, addWord = 5;

    static String randomWord()
    {
        int i = (int) (Math.random() * 2135);
        return wordsFromFile[i];
    }

    public static double avgGuesses()
    {
        if (games > 0)
        {
            double calc = avgGuess, calc1 = games;
            return Math.round((float) ((calc / calc1) * 100.0)) / 100.0;
        } else
        {
            return 0;
        }

    }

    public static double winRate()
    {
        if (games > 0)
        {
            double calc = wins, calc1 = games;
            return Math.round(((float) calc / calc1) * 1000.0) / 10.0;
        } else
        {
            return 0;
        }

    }

    public static void getDetails()
    {
        String sql = "SELECT ID, TotalGuess, Loses, Wins, Score FROM tblUsers WHERE Username = '" + username + "' "
                + "AND Password = '" + password + "'";
        System.out.println(sql);
        ResultSet rs = db.query(sql);

        try
        {
            while (rs.next())
            {
                //getting user details from database
                userID = Integer.parseInt(rs.getString("ID"));

                loses = Integer.parseInt(rs.getString("Loses"));

                wins = Integer.parseInt(rs.getString("Wins"));

                avgGuess = Integer.parseInt(rs.getString("TotalGuess"));

                preScore = Integer.parseInt(rs.getString("Score"));

            }
        } catch (SQLException ex)
        {
            Logger.getLogger(Logic.class.getName()).log(Level.SEVERE, null, ex);
        }
        games = wins + loses;
    }

    public static void deleteUser()
    {
        String sql = "DELETE FROM tblUsers WHERE id = " + userID;
        int rs = db.update(sql);
        varibleReset();
    }

    public static void varibleReset()
    {
        letter = 0;
        code = 0;
        attempt = 0;
        score = 0;
        avgGuess = 0;
        preScore = 0;
        currentStreak = 0;
        userID = 0;
        games = 0;
        loses = 0;
        wins = 0;
    }

    public static void topFiveUsers(JTable highscore) 
{
    String sql = "SELECT TOP 5 Username, Score FROM (SELECT DISTINCT Username, Score FROM tblUsers) ORDER BY Score DESC";
    System.out.println(sql);
    ResultSet rs = db.query(sql);
    int i = 0; // Row index
    try
    {
        while (rs != null && rs.next())
        {
            System.out.println("hi");

            // Set Username in column 0
            highscore.setValueAt(rs.getString("Username"), i, 0);
            System.out.println(i + ".Name:\t" + rs.getString("Username"));

            // Set Score in column 1
            highscore.setValueAt(rs.getString("Score"), i, 1);
            System.out.println(i + ".Score:\t" + rs.getString("Score"));

            i++; // Move to the next row
        }
    } 
    catch (SQLException ex)
    {
        Logger.getLogger(Logic.class.getName()).log(Level.SEVERE, null, ex);
    }
}


    public void GuessedWords()
    {

    }

    public static void readFromFile()
    {
        int i = 0;
        try
        {

            Scanner scFile = new Scanner(new File("answers.txt"));

            while (scFile.hasNextLine())
            {

                String line = scFile.nextLine();

                Scanner scLine = new Scanner(line);

                wordsFromFile[i] = scLine.next();
                i++;
            }
            Logic.randomWord = Logic.randomWord();
            scFile.close();
        } catch (FileNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null, "Error while loading file!");
        }

    }

    public static void Highlight(JLabel letter, boolean highlight)
    {
        if (highlight)
        {
            Color boarder = new Color(135, 138, 140);
            letter.setBorder(new LineBorder(boarder, 3));
        } else
        {
            Color boarder = new Color(211, 214, 218);
            letter.setBorder(new LineBorder(boarder, 3));
            letter.setForeground(Color.black);
            letter.setBackground(Color.white);
        }
    }

    public static void highlightYellow(JLabel letter)
    {
        Color boarder = new Color(201, 180, 88);
        letter.setBackground(boarder);
        letter.setForeground(Color.white);
        letter.setBorder(new LineBorder(boarder, 3));

    }

    public static void highlightGreen(JLabel letter)
    {

        Color boarder = new Color(106, 170, 100);
        letter.setBackground(boarder);
        letter.setForeground(Color.white);
        letter.setBorder(new LineBorder(boarder, 3));

    }

    public static void highlightGrey(JLabel letter)
    {
        Color boarder = new Color(120, 124, 126);
        letter.setBackground(boarder);
        letter.setForeground(Color.white);
        letter.setBorder(new LineBorder(boarder, 3));

    }

    public static void reset(JLabel board)
    {
        board.setText("");
        Highlight(board, false);
    }

    public static boolean Login(JTextField username, JPasswordField password, JLabel check)
    {

        String sql = "SELECT Password FROM tblUsers WHERE Username = '" + username.getText() + "'";
        ResultSet rs = db.query(sql);
        try
        {
            while (rs.next())
            {
                if (rs.getString("Password").equals(password.getText()))
                {
                    return true;
                } else
                {
                    check.setText("password or username is incorect.");
                    return false;
                }

            }

        } catch (SQLException ex)
        {
            Logger.getLogger(Logic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean UsernameCheck(JTextField username, JLabel usernameExits)
    {

        String sql = "select Username from tblUsers";
        System.out.println(sql);
        ResultSet rs = db.query(sql);
        boolean temp = true;
        if (username.getText().length() >= 5 && !username.getText().isEmpty()
                && !username.getText().contains(" "))
        {
            try
            {
                while (rs.next() && temp)
                {
                    String user = rs.getString("Username");

                    if (user.equalsIgnoreCase(username.getText()))
                    {
                        usernameExits.setText("username already exits");
                        Color colour = new Color(255, 51, 51);
                        usernameExits.setForeground(colour);
                        temp = false;
                    } else
                    {
                        usernameExits.setText("");
                    }
                }

            } catch (SQLException ex)
            {
                Logger.getLogger(Logic.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        } else
        {
            return false;
        }

    }

    public static boolean PasswordValidation(JPasswordField password)
    {
        String temp = password.getText();
        char help;
        boolean letterUpperCase = false, letter = false, digit = false, character = false;

        if (temp.length() >= 8 && !temp.contains(" "))
        {
            for (int i = 0; i < temp.length(); i++)
            {
                help = temp.charAt(i);
                if (Character.isUpperCase(help))
                {
                    letterUpperCase = true;
                }
                if (Character.isDigit(help))
                {
                    digit = true;
                }
                if (Character.isLetter(help))
                {
                    letter = true;
                }
                if (!Character.isLetter(help) && !Character.isDigit(help))
                {
                    character = true;
                }
            }
        }

        return letterUpperCase && letter && digit && character;
    }

    public static boolean SignUp(JTextField username, JLabel usernameExits, JPasswordField password, JPasswordField password1)
    {
        //check if passwords are the same
        if (!password.getText().equals(password1.getText()))
        {
            usernameExits.setText("passwords don't match");
            state = "password";
            return false;
        }
        //adding the user into the database
        if (UsernameCheck(username, usernameExits) && PasswordValidation(password))
        {
            String user = username.getText(), pass = password.getText();
            String sql = "INSERT INTO tblUsers (Username, Password, TotalGuess, Loses, Wins, Score)"
                    + "Values (\"" + user + "\", \"" + pass + "\", 0, 0, 0, 0)";
            System.out.println(sql);
            db.update(sql);
            Logic.password = pass;
            Logic.username = user;
            return true;
        } else
        {
            //user didnt meet the requirements
            return false;
        }

    }

    public static void updateDetails(String username, String password)
    {
        preScore += score;
        String sql = "UPDATE tblUsers "
                + "SET TotalGuess = " + avgGuess + ", Loses = " + loses + ", Wins = " + wins + ", Score = " + preScore
                + " WHERE Username = '" + username + "' "
                + "AND Password = '" + password + "'";
        System.out.println(sql);
        db.update(sql);
    }

    public static boolean winCheck()
    {
        if (userGuess.equalsIgnoreCase(randomWord))
        {
            System.out.println("win");
            attempt = 0;
            games++;
            wins++;
            currentStreak++;
            if (currentStreak == 3)
            {
                JOptionPane.showMessageDialog(null, "Timer is now avilabile, press timmer start\n when you on row 1 before you type");
                timerAlowed = true;
            }
            if (timerOn)
            {
                switch (attempt)
                {
                    case 0:
                        score *= 5;
                        break;
                    case 1:
                        score *= 4;
                        break;
                    case 2:
                        score *= 3;
                        break;
                    case 3:
                        score *= 2;
                        break;
                }
            }

            //update details into database
            updateDetails(username, password);
            JOptionPane.showMessageDialog(null, "Congratulations on guessing the word: " + randomWord);
            Logic.randomWord = randomWord();

            return true;
        } else if (attempt > 5 || time <= 0)
        {
            //set varibles to 0
            currentStreak = 0;
            attempt = 0;
            games++;
            loses++;
            JOptionPane.showMessageDialog(null, "You lose!\nThe word was: " + randomWord);
            randomWord = randomWord();
            return true;
        }
        return false;
    }

    public static boolean wordValidation()
    {
        for (int i = 0; i < wordsFromFile.length; i++)
        {
            if (userGuess.equalsIgnoreCase(wordsFromFile[i]))
            {
                avgGuess++;
                return true;
            }
        }
        return false;
    }
}
