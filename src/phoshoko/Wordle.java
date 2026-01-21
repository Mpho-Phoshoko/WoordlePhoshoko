/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package phoshoko;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_ENTER;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author Naume
 */
public class Wordle extends javax.swing.JFrame
{

    /**
     * Creates new form Wordle
     */
    public Wordle()
    {
        initComponents();
        //Get words
        Logic.readFromFile();

        //displaying forms in the middle of the screen
        frmSignUp.pack();
        frmSignUp.setLocationRelativeTo(null);
        frmSignUp.setVisible(true);
        frmLogin.pack();
        frmLogin.setLocationRelativeTo(null);
        frmHowToPlay.pack();
        frmHowToPlay.setLocationRelativeTo(null);

        //disabling buttons
        btnRegister.setEnabled(false);
        btnLogin.setEnabled(false);

        //setting random word
        Logic.randomWord = Logic.randomWord();

    }

    ChangeLetter action = new ChangeLetter();
    Timer timer = new Timer(0, action);
    JLabel[][] board = new JLabel[6][5];

    public void wordle()
    {
        Logic.userGuess = "";
        for (int i = 0; i < 5; i++)
        {
            Logic.userGuess += arrayFill(Logic.attempt, i).getText();
        }

        if (wordCheck())
        {
            Logic.attempt++;
            //changing guess count so user can know weher to add word

            Logic.letter = 0;
        }
        if (Logic.winCheck())
        {
            for (int i = 0; i < 6; i++)
            {
                for (int j = 0; j < 5; j++)
                {
                    Logic.reset(arrayFill(i, j));
                }

            }
            
        }
        txaWords.append(Logic.userGuess + "\t");
        Logic.topFiveUsers(tblHighScores);
        lblScoreResult.setText("" + Logic.score);
        lblcurrentStreakResult.setText("" + Logic.currentStreak);
        lblAverageGuessesResult.setText("" + Logic.avgGuesses());
        lblWinRateResult.setText("" + Logic.winRate() + " %");
        tblStats.setValueAt(Logic.loses, 0, 0);
        tblStats.setValueAt(Logic.games, 0, 1);
        tblStats.setValueAt(Logic.wins, 0, 2);

    }


    //making the blocks were the letters are acsible
    public JLabel arrayFill(int row, int col)
    {
        board[0][0] = lbl1;
        board[0][1] = lbl2;
        board[0][2] = lbl3;
        board[0][3] = lbl4;
        board[0][4] = lbl5;

        board[1][0] = lbl6;
        board[1][1] = lbl7;
        board[1][2] = lbl8;
        board[1][3] = lbl9;
        board[1][4] = lbl10;

        board[2][0] = lbl11;
        board[2][1] = lbl12;
        board[2][2] = lbl13;
        board[2][3] = lbl14;
        board[2][4] = lbl15;

        board[3][0] = lbl16;
        board[3][1] = lbl17;
        board[3][2] = lbl18;
        board[3][3] = lbl19;
        board[3][4] = lbl20;

        board[4][0] = lbl21;
        board[4][1] = lbl22;
        board[4][2] = lbl23;
        board[4][3] = lbl24;
        board[4][4] = lbl25;

        board[5][0] = lbl26;
        board[5][1] = lbl27;
        board[5][2] = lbl28;
        board[5][3] = lbl29;
        board[5][4] = lbl30;
        return board[row][col];
    }

    public void Login()
    {
        if (Logic.Login(txfUsername, pasLogin, lblCheck))
        {
            frmLogin.setVisible(false);
            this.setVisible(true);
            Logic.username = txfUsername.getText();
            Logic.password = pasLogin.getText();

            Logic.getDetails();

            //setting user details
            lblAverageGuessesResult.setText("" + Logic.avgGuesses());
            lblWinRateResult.setText("" + Logic.winRate() + " %");
            tblStats.setValueAt(Logic.loses, 0, 0);
            tblStats.setValueAt(Logic.games, 0, 1);
            tblStats.setValueAt(Logic.wins, 0, 2);

            //option to show instructions
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this,
                    "Would you like to see the game instructions?"
                    + "\n(can also be found in help)", "Welcome!",
                    dialogButton);
            if (dialogResult == 0)
            {
                frmHowToPlay.setVisible(true);
            }
        }
    }

    public void SignUp()
    {
        if (Logic.SignUp(txfUsernameReg, lblUsernameCheck, pasRegister, pasRegisterConfirm))
        {
            //changing to login screen and displayingthe signup details for them
            frmSignUp.dispose();
            frmLogin.setVisible(true);
            btnLogin.setEnabled(true);
            txfUsername.setText(Logic.username);
            pasLogin.setText(Logic.password);

        } else
        {
            //Displaying the requirements 
            if (Logic.state.equals("else"))
            {
                JOptionPane.showMessageDialog(this, """
                                                Make sur to meet the following requirements:
                                                Username:
                                                *Minimum of 5 characters
                                                *No symbols
                                                *No Spaces!
                                                
                                                Password:
                                                *Minimum of 8 characters
                                                *At least 1 upcase letter
                                                *At least 1 lowercase letter
                                                *At least 1 number
                                                *At least 1 symbol
                                                *No Spaces!""");
            }
        }
    }

    public boolean wordCheck()
    {

        if (Logic.wordValidation())
        {
            Logic.randomWord = Logic.randomWord.toUpperCase();
            for (int i = 0; i < 5; i++)
            {
                if (!Character.toString(Logic.randomWord.charAt(i)).equalsIgnoreCase(Character.toString(Logic.userGuess.charAt(i))))
                {
                    Logic.highlightGrey(arrayFill(Logic.attempt, i));
                }
                if (Logic.randomWord.contains(Character.toString(Logic.userGuess.charAt(i))))
                {
                    Logic.highlightYellow(arrayFill(Logic.attempt, i));
                    Logic.score++;
                }
                if (Character.toString(Logic.randomWord.charAt(i)).equalsIgnoreCase(Character.toString(Logic.userGuess.charAt(i))))
                {
                    Logic.highlightGreen(arrayFill(Logic.attempt, i));
                    Logic.score += 2;
                }
            }
            //ADD word to geussed words
            if (Logic.addWord >= 0)
            {
                Logic.wordsToAdd += Logic.userGuess.toLowerCase() + " ";
                Logic.addWord--;
            } else
            {
                Logic.wordsToAdd += Logic.userGuess.toLowerCase() + "\n";
                Logic.addWord = 4;
            }
            return true;
        } else
        {
            JOptionPane.showMessageDialog(null, "Word does not exist");
            txfGame.requestFocusInWindow();
            return false;
        }
    }

  

    private class ChangeLetter implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent evt)
        {
            //check (and fill) if the main text box is empty so no errors occur
            if (txfGame.getText().isEmpty())
            {
                txfGame.setText(" ");
            }
            //get the last character wich is the users input
            char text = txfGame.getText().toUpperCase().charAt(txfGame.getText().length() - 1);

            //cast it as a string so i can compare if its a valid letter
            Logic.code = (int) (text);

            //check if its a valid letter
            if (Logic.code >= KeyEvent.VK_A && Logic.code <= KeyEvent.VK_Z)
            {
                //canContinue detects if its at the last column to restrict typing
                if (Logic.canContinue)
                {
                    //if the letter count is less than 5 the user can type
                    if (Logic.letter < 5)
                    {
                        //give the typed block a highlight
                        Logic.Highlight(arrayFill(Logic.attempt, Logic.letter), true);

                        //fill the block
                        arrayFill(Logic.attempt, Logic.letter).setText(Character.toString(text));
                        Logic.letter++;
                    } else
                    {
                        Logic.canContinue = false;
                    }
                }
            }

            //to make sure txfGame is not empty
            txfGame.setText("     ");

        }
    }

   

    public void backspace()
    {

        Logic.canContinue = true;
        Logic.letter--;
        if (Logic.letter != -1)
        {
            arrayFill(Logic.attempt, Logic.letter).setText("");
            Logic.Highlight(arrayFill(Logic.attempt, Logic.letter), false);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        frmLogin = new javax.swing.JFrame();
        pnlBackground1 = new javax.swing.JPanel();
        pnlBackkground2 = new javax.swing.JPanel();
        lblLogin = new javax.swing.JLabel();
        lblLoginPng = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        lblLine = new javax.swing.JLabel();
        lblLine1 = new javax.swing.JLabel();
        txfUsername = new javax.swing.JTextField();
        pasLogin = new javax.swing.JPasswordField();
        btnToRegister = new javax.swing.JButton();
        btnLogin = new javax.swing.JButton();
        lblCheck = new javax.swing.JLabel();
        frmSignUp = new javax.swing.JFrame();
        pnlBackground2 = new javax.swing.JPanel();
        pnlBackkground3 = new javax.swing.JPanel();
        lblRegister = new javax.swing.JLabel();
        lblLoginPng1 = new javax.swing.JLabel();
        lblPassword1 = new javax.swing.JLabel();
        lblUsername1 = new javax.swing.JLabel();
        lblLine2 = new javax.swing.JLabel();
        lblLine3 = new javax.swing.JLabel();
        txfUsernameReg = new javax.swing.JTextField();
        pasRegister = new javax.swing.JPasswordField();
        pasRegisterConfirm = new javax.swing.JPasswordField();
        lblLine4 = new javax.swing.JLabel();
        lblPasswordConfirm = new javax.swing.JLabel();
        btnRegister = new javax.swing.JButton();
        btnBckLogin = new javax.swing.JButton();
        lblUsernameCheck = new javax.swing.JLabel();
        frmHowToPlay = new javax.swing.JFrame();
        pnlHowToPlay = new javax.swing.JPanel();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        lblHowToPlay = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txaTimer = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        pnlBckground = new javax.swing.JPanel();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        pnlBoard = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        lbl1 = new javax.swing.JLabel();
        lbl2 = new javax.swing.JLabel();
        lbl3 = new javax.swing.JLabel();
        lbl4 = new javax.swing.JLabel();
        lbl5 = new javax.swing.JLabel();
        lbl6 = new javax.swing.JLabel();
        lbl7 = new javax.swing.JLabel();
        lbl8 = new javax.swing.JLabel();
        lbl9 = new javax.swing.JLabel();
        lbl10 = new javax.swing.JLabel();
        lbl11 = new javax.swing.JLabel();
        lbl12 = new javax.swing.JLabel();
        lbl13 = new javax.swing.JLabel();
        lbl14 = new javax.swing.JLabel();
        lbl15 = new javax.swing.JLabel();
        lbl16 = new javax.swing.JLabel();
        lbl17 = new javax.swing.JLabel();
        lbl18 = new javax.swing.JLabel();
        lbl19 = new javax.swing.JLabel();
        lbl20 = new javax.swing.JLabel();
        lbl21 = new javax.swing.JLabel();
        lbl22 = new javax.swing.JLabel();
        lbl23 = new javax.swing.JLabel();
        lbl24 = new javax.swing.JLabel();
        lbl25 = new javax.swing.JLabel();
        lbl26 = new javax.swing.JLabel();
        lbl27 = new javax.swing.JLabel();
        lbl28 = new javax.swing.JLabel();
        lbl29 = new javax.swing.JLabel();
        lbl30 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnlScore = new javax.swing.JPanel();
        lblWinRate = new javax.swing.JLabel();
        lblWinRateResult = new javax.swing.JLabel();
        lblScore = new javax.swing.JLabel();
        lblScoreResult = new javax.swing.JLabel();
        lblSCurrentStreak = new javax.swing.JLabel();
        lblcurrentStreakResult = new javax.swing.JLabel();
        lblAverageGuesses = new javax.swing.JLabel();
        lblAverageGuessesResult = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblStats = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHighScores = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        txaWords = new javax.swing.JTextArea();
        jLabel36 = new javax.swing.JLabel();
        lblWordle = new javax.swing.JLabel();
        txfGame = new javax.swing.JTextField();
        mnbOptions = new javax.swing.JMenuBar();
        mnuProfile = new javax.swing.JMenu();
        mitSwitchUser = new javax.swing.JMenuItem();
        mitDeleteAccount = new javax.swing.JMenuItem();
        mnuHelp = new javax.swing.JMenu();

        frmLogin.setResizable(false);

        pnlBackground1.setBackground(new java.awt.Color(3, 25, 30));
        pnlBackground1.setPreferredSize(new java.awt.Dimension(650, 500));
        pnlBackground1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlBackkground2.setBackground(new java.awt.Color(255, 153, 51));
        pnlBackkground2.setPreferredSize(new java.awt.Dimension(300, 500));

        lblLogin.setFont(new java.awt.Font("Tahoma", 1, 50)); // NOI18N
        lblLogin.setForeground(new java.awt.Color(3, 25, 30));
        lblLogin.setText("Login");

        lblLoginPng.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/LoginUser.png"))); // NOI18N
        lblLoginPng.setMaximumSize(new java.awt.Dimension(130, 130));
        lblLoginPng.setMinimumSize(new java.awt.Dimension(100, 100));
        lblLoginPng.setPreferredSize(new java.awt.Dimension(130, 130));

        javax.swing.GroupLayout pnlBackkground2Layout = new javax.swing.GroupLayout(pnlBackkground2);
        pnlBackkground2.setLayout(pnlBackkground2Layout);
        pnlBackkground2Layout.setHorizontalGroup(
            pnlBackkground2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBackkground2Layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addGroup(pnlBackkground2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblLogin)
                    .addComponent(lblLoginPng, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(81, 81, 81))
        );
        pnlBackkground2Layout.setVerticalGroup(
            pnlBackkground2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBackkground2Layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(lblLoginPng, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(lblLogin)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlBackground1.add(pnlBackkground2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        lblPassword.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblPassword.setForeground(new java.awt.Color(255, 255, 255));
        lblPassword.setText("Password");
        pnlBackground1.add(lblPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 240, 190, -1));

        lblUsername.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblUsername.setForeground(new java.awt.Color(255, 255, 255));
        lblUsername.setText("Username");
        pnlBackground1.add(lblUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 110, 190, -1));

        lblLine.setToolTipText("");
        lblLine.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        lblLine.setMaximumSize(new java.awt.Dimension(45, 1));
        lblLine.setPreferredSize(new java.awt.Dimension(45, 1));
        pnlBackground1.add(lblLine, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 190, 260, 3));

        lblLine1.setToolTipText("");
        lblLine1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        lblLine1.setMaximumSize(new java.awt.Dimension(50, 1));
        lblLine1.setMinimumSize(new java.awt.Dimension(0, 0));
        lblLine1.setPreferredSize(new java.awt.Dimension(47, 1));
        pnlBackground1.add(lblLine1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 320, 260, 3));

        txfUsername.setBackground(new java.awt.Color(3, 25, 30));
        txfUsername.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txfUsername.setForeground(new java.awt.Color(255, 255, 255));
        txfUsername.setBorder(null);
        txfUsername.setCaretColor(new java.awt.Color(255, 255, 255));
        txfUsername.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txfUsername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txfUsernameKeyPressed(evt);
            }
        });
        pnlBackground1.add(txfUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 150, 260, 40));

        pasLogin.setBackground(pnlBackground1.getBackground());
        pasLogin.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        pasLogin.setForeground(new java.awt.Color(255, 255, 255));
        pasLogin.setBorder(null);
        pasLogin.setCaretColor(new java.awt.Color(255, 255, 255));
        pasLogin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pasLoginKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                pasLoginKeyTyped(evt);
            }
        });
        pnlBackground1.add(pasLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 280, 260, 40));

        btnToRegister.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnToRegister.setForeground(new java.awt.Color(255, 255, 255));
        btnToRegister.setText("Don't have an account?");
        btnToRegister.setBorder(null);
        btnToRegister.setContentAreaFilled(false);
        btnToRegister.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnToRegisterMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnToRegisterMouseExited(evt);
            }
        });
        pnlBackground1.add(btnToRegister, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 440, 260, -1));

        btnLogin.setBackground(pnlBackkground2.getBackground());
        btnLogin.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        pnlBackground1.add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 380, 260, 50));

        lblCheck.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCheck.setForeground(new java.awt.Color(255, 51, 51));
        pnlBackground1.add(lblCheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 330, 260, 20));

        javax.swing.GroupLayout frmLoginLayout = new javax.swing.GroupLayout(frmLogin.getContentPane());
        frmLogin.getContentPane().setLayout(frmLoginLayout);
        frmLoginLayout.setHorizontalGroup(
            frmLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBackground1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        frmLoginLayout.setVerticalGroup(
            frmLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBackground1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        frmSignUp.setResizable(false);

        pnlBackground2.setBackground(new java.awt.Color(3, 25, 30));
        pnlBackground2.setMinimumSize(new java.awt.Dimension(650, 500));
        pnlBackground2.setPreferredSize(new java.awt.Dimension(650, 500));
        pnlBackground2.setLayout(null);

        pnlBackkground3.setBackground(new java.awt.Color(255, 153, 51));
        pnlBackkground3.setPreferredSize(new java.awt.Dimension(300, 500));

        lblRegister.setFont(new java.awt.Font("Tahoma", 1, 50)); // NOI18N
        lblRegister.setForeground(new java.awt.Color(3, 25, 30));
        lblRegister.setText("SignUp");

        lblLoginPng1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLoginPng1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/SignUp.png"))); // NOI18N
        lblLoginPng1.setMaximumSize(new java.awt.Dimension(130, 130));
        lblLoginPng1.setMinimumSize(new java.awt.Dimension(100, 100));
        lblLoginPng1.setPreferredSize(new java.awt.Dimension(130, 130));

        javax.swing.GroupLayout pnlBackkground3Layout = new javax.swing.GroupLayout(pnlBackkground3);
        pnlBackkground3.setLayout(pnlBackkground3Layout);
        pnlBackkground3Layout.setHorizontalGroup(
            pnlBackkground3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBackkground3Layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addGroup(pnlBackkground3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblRegister, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblLoginPng1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(81, 81, 81))
        );
        pnlBackkground3Layout.setVerticalGroup(
            pnlBackkground3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBackkground3Layout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addComponent(lblLoginPng1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRegister)
                .addContainerGap(192, Short.MAX_VALUE))
        );

        pnlBackground2.add(pnlBackkground3);
        pnlBackkground3.setBounds(0, 0, 300, 500);

        lblPassword1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblPassword1.setForeground(new java.awt.Color(255, 255, 255));
        lblPassword1.setText("Password");
        pnlBackground2.add(lblPassword1);
        lblPassword1.setBounds(330, 150, 190, 29);

        lblUsername1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblUsername1.setForeground(new java.awt.Color(255, 255, 255));
        lblUsername1.setText("Username");
        pnlBackground2.add(lblUsername1);
        lblUsername1.setBounds(330, 40, 190, 29);

        lblLine2.setToolTipText("");
        lblLine2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        lblLine2.setMaximumSize(new java.awt.Dimension(45, 1));
        lblLine2.setPreferredSize(new java.awt.Dimension(45, 1));
        pnlBackground2.add(lblLine2);
        lblLine2.setBounds(330, 120, 260, 3);

        lblLine3.setToolTipText("");
        lblLine3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        lblLine3.setMaximumSize(new java.awt.Dimension(50, 1));
        lblLine3.setMinimumSize(new java.awt.Dimension(0, 0));
        lblLine3.setPreferredSize(new java.awt.Dimension(47, 1));
        pnlBackground2.add(lblLine3);
        lblLine3.setBounds(330, 230, 260, 3);

        txfUsernameReg.setBackground(new java.awt.Color(3, 25, 30));
        txfUsernameReg.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txfUsernameReg.setForeground(new java.awt.Color(255, 255, 255));
        txfUsernameReg.setToolTipText("minimuin of 5 characters, no symbols allowed");
        txfUsernameReg.setBorder(null);
        txfUsernameReg.setCaretColor(new java.awt.Color(255, 255, 255));
        txfUsernameReg.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txfUsernameReg.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txfUsernameRegFocusLost(evt);
            }
        });
        txfUsernameReg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txfUsernameRegKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txfUsernameRegKeyTyped(evt);
            }
        });
        pnlBackground2.add(txfUsernameReg);
        txfUsernameReg.setBounds(330, 80, 260, 40);

        pasRegister.setBackground(pnlBackground1.getBackground());
        pasRegister.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        pasRegister.setForeground(new java.awt.Color(255, 255, 255));
        pasRegister.setBorder(null);
        pasRegister.setCaretColor(new java.awt.Color(255, 255, 255));
        pasRegister.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                pasRegisterFocusLost(evt);
            }
        });
        pasRegister.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pasRegisterMouseClicked(evt);
            }
        });
        pnlBackground2.add(pasRegister);
        pasRegister.setBounds(330, 190, 260, 40);

        pasRegisterConfirm.setBackground(pnlBackground1.getBackground());
        pasRegisterConfirm.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        pasRegisterConfirm.setForeground(new java.awt.Color(255, 255, 255));
        pasRegisterConfirm.setBorder(null);
        pasRegisterConfirm.setCaretColor(new java.awt.Color(255, 255, 255));
        pasRegisterConfirm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pasRegisterConfirmMouseClicked(evt);
            }
        });
        pasRegisterConfirm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pasRegisterConfirmKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pasRegisterConfirmKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                pasRegisterConfirmKeyTyped(evt);
            }
        });
        pnlBackground2.add(pasRegisterConfirm);
        pasRegisterConfirm.setBounds(330, 300, 260, 40);

        lblLine4.setToolTipText("");
        lblLine4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        lblLine4.setMaximumSize(new java.awt.Dimension(50, 1));
        lblLine4.setMinimumSize(new java.awt.Dimension(0, 0));
        lblLine4.setPreferredSize(new java.awt.Dimension(47, 1));
        pnlBackground2.add(lblLine4);
        lblLine4.setBounds(330, 340, 260, 3);

        lblPasswordConfirm.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblPasswordConfirm.setForeground(new java.awt.Color(255, 255, 255));
        lblPasswordConfirm.setText("Re-Enter Password");
        pnlBackground2.add(lblPasswordConfirm);
        lblPasswordConfirm.setBounds(330, 260, 250, 29);

        btnRegister.setBackground(pnlBackkground2.getBackground());
        btnRegister.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnRegister.setForeground(new java.awt.Color(255, 255, 255));
        btnRegister.setText("Register");
        btnRegister.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRegisterMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRegisterMouseExited(evt);
            }
        });
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
            }
        });
        pnlBackground2.add(btnRegister);
        btnRegister.setBounds(330, 390, 260, 50);

        btnBckLogin.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnBckLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnBckLogin.setText("Already a member?");
        btnBckLogin.setBorder(null);
        btnBckLogin.setContentAreaFilled(false);
        btnBckLogin.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btnBckLoginMouseMoved(evt);
            }
        });
        btnBckLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBckLoginMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBckLoginMouseExited(evt);
            }
        });
        btnBckLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBckLoginActionPerformed(evt);
            }
        });
        pnlBackground2.add(btnBckLogin);
        btnBckLogin.setBounds(330, 450, 260, 22);

        lblUsernameCheck.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblUsernameCheck.setForeground(new java.awt.Color(255, 51, 51));
        pnlBackground2.add(lblUsernameCheck);
        lblUsernameCheck.setBounds(330, 350, 260, 20);

        javax.swing.GroupLayout frmSignUpLayout = new javax.swing.GroupLayout(frmSignUp.getContentPane());
        frmSignUp.getContentPane().setLayout(frmSignUpLayout);
        frmSignUpLayout.setHorizontalGroup(
            frmSignUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBackground2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        frmSignUpLayout.setVerticalGroup(
            frmSignUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBackground2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        frmHowToPlay.setTitle("How to play");
        frmHowToPlay.setAlwaysOnTop(true);
        frmHowToPlay.setResizable(false);

        pnlHowToPlay.setBackground(new java.awt.Color(255, 255, 255));

        lblHowToPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Woordle-Play-scrnshot.png"))); // NOI18N
        jLayeredPane3.add(lblHowToPlay);
        lblHowToPlay.setBounds(0, 0, 510, 470);

        jScrollPane4.setBorder(null);

        txaTimer.setColumns(20);
        txaTimer.setFont(new java.awt.Font("Calibri Light", 0, 24)); // NOI18N
        txaTimer.setRows(5);
        txaTimer.setText("If you guess the correct answer 3 times \nin a row. you can choose to play with \nthe timer in the menu bar.");
        txaTimer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        txaTimer.setFocusable(false);
        txaTimer.setRequestFocusEnabled(false);
        jScrollPane4.setViewportView(txaTimer);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setText("Point System");

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setText("2 Points for green\n1 Point for yellow\n0 Points for grey");
        jTextArea1.setFocusable(false);
        jScrollPane5.setViewportView(jTextArea1);

        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextArea2.setRows(5);
        jTextArea2.setText("If timer is on\nx5 of your pionts for row 1\nx4 of your pionts for row 2\nx3 of your pionts for row 3\nx2 of your pionts for row 4\nx1 of your pionts for row 5\nx1 of your pionts for row 6");
        jTextArea2.setFocusable(false);
        jScrollPane6.setViewportView(jTextArea2);

        javax.swing.GroupLayout pnlHowToPlayLayout = new javax.swing.GroupLayout(pnlHowToPlay);
        pnlHowToPlay.setLayout(pnlHowToPlayLayout);
        pnlHowToPlayLayout.setHorizontalGroup(
            pnlHowToPlayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHowToPlayLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHowToPlayLayout.createSequentialGroup()
                .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnlHowToPlayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(pnlHowToPlayLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0))
        );
        pnlHowToPlayLayout.setVerticalGroup(
            pnlHowToPlayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHowToPlayLayout.createSequentialGroup()
                .addGroup(pnlHowToPlayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 473, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlHowToPlayLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout frmHowToPlayLayout = new javax.swing.GroupLayout(frmHowToPlay.getContentPane());
        frmHowToPlay.getContentPane().setLayout(frmHowToPlayLayout);
        frmHowToPlayLayout.setHorizontalGroup(
            frmHowToPlayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHowToPlay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        frmHowToPlayLayout.setVerticalGroup(
            frmHowToPlayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmHowToPlayLayout.createSequentialGroup()
                .addComponent(pnlHowToPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Wordle");
        setMinimumSize(new java.awt.Dimension(650, 500));
        setResizable(false);

        pnlBckground.setBackground(new java.awt.Color(255, 255, 255));
        pnlBckground.setMinimumSize(new java.awt.Dimension(650, 500));
        pnlBckground.setPreferredSize(new java.awt.Dimension(650, 500));

        jLayeredPane2.setBackground(new java.awt.Color(153, 51, 255));

        pnlBoard.setBackground(pnlBckground.getBackground());
        pnlBoard.setPreferredSize(new java.awt.Dimension(284, 295));

        jLayeredPane1.setBackground(new java.awt.Color(102, 0, 255));

        lbl1.setBackground(new java.awt.Color(255, 255, 255));
        lbl1.setFont(new java.awt.Font("Nirmala UI", 1, 34)); // NOI18N
        lbl1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(211, 214, 218), 3, true));
        lbl1.setOpaque(true);
        lbl1.setPreferredSize(new java.awt.Dimension(60, 60));

        lbl2.setBackground(new java.awt.Color(255, 255, 255));
        lbl2.setFont(new java.awt.Font("Nirmala UI", 1, 34)); // NOI18N
        lbl2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(211, 214, 218), 3, true));
        lbl2.setOpaque(true);
        lbl2.setPreferredSize(new java.awt.Dimension(60, 60));

        lbl3.setBackground(new java.awt.Color(255, 255, 255));
        lbl3.setFont(new java.awt.Font("Nirmala UI", 1, 34)); // NOI18N
        lbl3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(211, 214, 218), 3, true));
        lbl3.setOpaque(true);
        lbl3.setPreferredSize(new java.awt.Dimension(60, 60));

        lbl4.setBackground(new java.awt.Color(255, 255, 255));
        lbl4.setFont(new java.awt.Font("Nirmala UI", 1, 34)); // NOI18N
        lbl4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(211, 214, 218), 3, true));
        lbl4.setOpaque(true);
        lbl4.setPreferredSize(new java.awt.Dimension(60, 60));

        lbl5.setBackground(new java.awt.Color(255, 255, 255));
        lbl5.setFont(new java.awt.Font("Nirmala UI", 1, 34)); // NOI18N
        lbl5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(211, 214, 218), 3, true));
        lbl5.setMaximumSize(new java.awt.Dimension(60, 60));
        lbl5.setMinimumSize(new java.awt.Dimension(60, 60));
        lbl5.setOpaque(true);
        lbl5.setPreferredSize(new java.awt.Dimension(60, 60));
        lbl5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lbl5KeyPressed(evt);
            }
        });

        lbl6.setBackground(new java.awt.Color(255, 255, 255));
        lbl6.setFont(new java.awt.Font("Nirmala UI", 1, 34)); // NOI18N
        lbl6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(211, 214, 218), 3, true));
        lbl6.setOpaque(true);
        lbl6.setPreferredSize(new java.awt.Dimension(60, 60));

        lbl7.setBackground(new java.awt.Color(255, 255, 255));
        lbl7.setFont(new java.awt.Font("Nirmala UI", 1, 34)); // NOI18N
        lbl7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(211, 214, 218), 3, true));
        lbl7.setOpaque(true);
        lbl7.setPreferredSize(new java.awt.Dimension(60, 60));

        lbl8.setBackground(new java.awt.Color(255, 255, 255));
        lbl8.setFont(new java.awt.Font("Nirmala UI", 1, 34)); // NOI18N
        lbl8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(211, 214, 218), 3, true));
        lbl8.setOpaque(true);
        lbl8.setPreferredSize(new java.awt.Dimension(60, 60));

        lbl9.setBackground(new java.awt.Color(255, 255, 255));
        lbl9.setFont(new java.awt.Font("Nirmala UI", 1, 34)); // NOI18N
        lbl9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(211, 214, 218), 3, true));
        lbl9.setOpaque(true);
        lbl9.setPreferredSize(new java.awt.Dimension(60, 60));

        lbl10.setBackground(new java.awt.Color(255, 255, 255));
        lbl10.setFont(new java.awt.Font("Nirmala UI", 1, 34)); // NOI18N
        lbl10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl10.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(211, 214, 218), 3, true));
        lbl10.setMaximumSize(new java.awt.Dimension(60, 60));
        lbl10.setMinimumSize(new java.awt.Dimension(60, 60));
        lbl10.setOpaque(true);
        lbl10.setPreferredSize(new java.awt.Dimension(60, 60));

        lbl11.setBackground(new java.awt.Color(255, 255, 255));
        lbl11.setFont(new java.awt.Font("Nirmala UI", 1, 34)); // NOI18N
        lbl11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl11.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(211, 214, 218), 3, true));
        lbl11.setOpaque(true);
        lbl11.setPreferredSize(new java.awt.Dimension(60, 60));

        lbl12.setBackground(new java.awt.Color(255, 255, 255));
        lbl12.setFont(new java.awt.Font("Nirmala UI", 1, 34)); // NOI18N
        lbl12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl12.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(211, 214, 218), 3, true));
        lbl12.setOpaque(true);
        lbl12.setPreferredSize(new java.awt.Dimension(60, 60));

        lbl13.setBackground(new java.awt.Color(255, 255, 255));
        lbl13.setFont(new java.awt.Font("Nirmala UI", 1, 34)); // NOI18N
        lbl13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl13.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(211, 214, 218), 3, true));
        lbl13.setOpaque(true);
        lbl13.setPreferredSize(new java.awt.Dimension(60, 60));

        lbl14.setBackground(new java.awt.Color(255, 255, 255));
        lbl14.setFont(new java.awt.Font("Nirmala UI", 1, 34)); // NOI18N
        lbl14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl14.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(211, 214, 218), 3, true));
        lbl14.setOpaque(true);
        lbl14.setPreferredSize(new java.awt.Dimension(60, 60));

        lbl15.setBackground(new java.awt.Color(255, 255, 255));
        lbl15.setFont(new java.awt.Font("Nirmala UI", 1, 34)); // NOI18N
        lbl15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl15.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(211, 214, 218), 3, true));
        lbl15.setMaximumSize(new java.awt.Dimension(60, 60));
        lbl15.setMinimumSize(new java.awt.Dimension(60, 60));
        lbl15.setOpaque(true);
        lbl15.setPreferredSize(new java.awt.Dimension(60, 60));

        lbl16.setBackground(new java.awt.Color(255, 255, 255));
        lbl16.setFont(new java.awt.Font("Nirmala UI", 1, 34)); // NOI18N
        lbl16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl16.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(211, 214, 218), 3, true));
        lbl16.setOpaque(true);
        lbl16.setPreferredSize(new java.awt.Dimension(60, 60));

        lbl17.setBackground(new java.awt.Color(255, 255, 255));
        lbl17.setFont(new java.awt.Font("Nirmala UI", 1, 34)); // NOI18N
        lbl17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl17.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(211, 214, 218), 3, true));
        lbl17.setOpaque(true);
        lbl17.setPreferredSize(new java.awt.Dimension(60, 60));

        lbl18.setBackground(new java.awt.Color(255, 255, 255));
        lbl18.setFont(new java.awt.Font("Nirmala UI", 1, 34)); // NOI18N
        lbl18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl18.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(211, 214, 218), 3, true));
        lbl18.setOpaque(true);
        lbl18.setPreferredSize(new java.awt.Dimension(60, 60));

        lbl19.setBackground(new java.awt.Color(255, 255, 255));
        lbl19.setFont(new java.awt.Font("Nirmala UI", 1, 34)); // NOI18N
        lbl19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl19.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(211, 214, 218), 3, true));
        lbl19.setOpaque(true);
        lbl19.setPreferredSize(new java.awt.Dimension(60, 60));

        lbl20.setBackground(new java.awt.Color(255, 255, 255));
        lbl20.setFont(new java.awt.Font("Nirmala UI", 1, 34)); // NOI18N
        lbl20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl20.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(211, 214, 218), 3, true));
        lbl20.setMaximumSize(new java.awt.Dimension(60, 60));
        lbl20.setMinimumSize(new java.awt.Dimension(60, 60));
        lbl20.setOpaque(true);
        lbl20.setPreferredSize(new java.awt.Dimension(60, 60));

        lbl21.setBackground(new java.awt.Color(255, 255, 255));
        lbl21.setFont(new java.awt.Font("Nirmala UI", 1, 34)); // NOI18N
        lbl21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl21.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(211, 214, 218), 3, true));
        lbl21.setOpaque(true);
        lbl21.setPreferredSize(new java.awt.Dimension(60, 60));

        lbl22.setBackground(new java.awt.Color(255, 255, 255));
        lbl22.setFont(new java.awt.Font("Nirmala UI", 1, 34)); // NOI18N
        lbl22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl22.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(211, 214, 218), 3, true));
        lbl22.setOpaque(true);
        lbl22.setPreferredSize(new java.awt.Dimension(60, 60));

        lbl23.setBackground(new java.awt.Color(255, 255, 255));
        lbl23.setFont(new java.awt.Font("Nirmala UI", 1, 34)); // NOI18N
        lbl23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl23.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(211, 214, 218), 3, true));
        lbl23.setOpaque(true);
        lbl23.setPreferredSize(new java.awt.Dimension(60, 60));

        lbl24.setBackground(new java.awt.Color(255, 255, 255));
        lbl24.setFont(new java.awt.Font("Nirmala UI", 1, 34)); // NOI18N
        lbl24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl24.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(211, 214, 218), 3, true));
        lbl24.setOpaque(true);
        lbl24.setPreferredSize(new java.awt.Dimension(60, 60));

        lbl25.setBackground(new java.awt.Color(255, 255, 255));
        lbl25.setFont(new java.awt.Font("Nirmala UI", 1, 34)); // NOI18N
        lbl25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl25.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(211, 214, 218), 3, true));
        lbl25.setMaximumSize(new java.awt.Dimension(60, 60));
        lbl25.setMinimumSize(new java.awt.Dimension(60, 60));
        lbl25.setOpaque(true);
        lbl25.setPreferredSize(new java.awt.Dimension(60, 60));

        lbl26.setBackground(new java.awt.Color(255, 255, 255));
        lbl26.setFont(new java.awt.Font("Nirmala UI", 1, 34)); // NOI18N
        lbl26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl26.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(211, 214, 218), 3, true));
        lbl26.setOpaque(true);
        lbl26.setPreferredSize(new java.awt.Dimension(60, 60));

        lbl27.setBackground(new java.awt.Color(255, 255, 255));
        lbl27.setFont(new java.awt.Font("Nirmala UI", 1, 34)); // NOI18N
        lbl27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl27.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(211, 214, 218), 3, true));
        lbl27.setOpaque(true);
        lbl27.setPreferredSize(new java.awt.Dimension(60, 60));

        lbl28.setBackground(new java.awt.Color(255, 255, 255));
        lbl28.setFont(new java.awt.Font("Nirmala UI", 1, 34)); // NOI18N
        lbl28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl28.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(211, 214, 218), 3, true));
        lbl28.setOpaque(true);
        lbl28.setPreferredSize(new java.awt.Dimension(60, 60));

        lbl29.setBackground(new java.awt.Color(255, 255, 255));
        lbl29.setFont(new java.awt.Font("Nirmala UI", 1, 34)); // NOI18N
        lbl29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl29.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(211, 214, 218), 3, true));
        lbl29.setOpaque(true);
        lbl29.setPreferredSize(new java.awt.Dimension(60, 60));

        lbl30.setBackground(new java.awt.Color(255, 255, 255));
        lbl30.setFont(new java.awt.Font("Nirmala UI", 1, 34)); // NOI18N
        lbl30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl30.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(211, 214, 218), 3, true));
        lbl30.setMaximumSize(new java.awt.Dimension(60, 60));
        lbl30.setMinimumSize(new java.awt.Dimension(60, 60));
        lbl30.setOpaque(true);
        lbl30.setPreferredSize(new java.awt.Dimension(60, 60));

        jLayeredPane1.setLayer(lbl1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbl2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbl3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbl4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbl5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbl6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbl7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbl8, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbl9, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbl10, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbl11, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbl12, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbl13, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbl14, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbl15, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbl16, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbl17, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbl18, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbl19, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbl20, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbl21, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbl22, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbl23, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbl24, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbl25, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbl26, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbl27, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbl28, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbl29, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbl30, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(lbl26, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl27, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl28, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl29, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl30, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(lbl21, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl22, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl23, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl24, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl25, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(lbl16, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl17, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl18, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl19, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl20, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(lbl11, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl12, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl13, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl14, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl15, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jLayeredPane1Layout.createSequentialGroup()
                            .addComponent(lbl6, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lbl7, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lbl8, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lbl9, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lbl10, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jLayeredPane1Layout.createSequentialGroup()
                            .addComponent(lbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lbl2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lbl3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lbl4, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lbl5, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlBoardLayout = new javax.swing.GroupLayout(pnlBoard);
        pnlBoard.setLayout(pnlBoardLayout);
        pnlBoardLayout.setHorizontalGroup(
            pnlBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
        );
        pnlBoardLayout.setVerticalGroup(
            pnlBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
        );

        jLayeredPane2.add(pnlBoard);
        pnlBoard.setBounds(12, 72, 344, 428);

        jTabbedPane1.setBackground(pnlBckground.getBackground());
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jTabbedPane1.setOpaque(true);

        pnlScore.setBackground(pnlBoard.getBackground());
        pnlScore.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        pnlScore.setOpaque(false);

        lblWinRate.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        lblWinRate.setText("WinRate:");

        lblWinRateResult.setFont(new java.awt.Font("Modern No. 20", 1, 24)); // NOI18N
        lblWinRateResult.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblWinRateResult.setText("0");

        lblScore.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        lblScore.setText("Score:");

        lblScoreResult.setFont(new java.awt.Font("Modern No. 20", 1, 24)); // NOI18N
        lblScoreResult.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblScoreResult.setText("0");

        lblSCurrentStreak.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        lblSCurrentStreak.setText("Current Streak:");

        lblcurrentStreakResult.setFont(new java.awt.Font("Modern No. 20", 1, 24)); // NOI18N
        lblcurrentStreakResult.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblcurrentStreakResult.setText("0");

        lblAverageGuesses.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        lblAverageGuesses.setText("Average Guesses:");

        lblAverageGuessesResult.setFont(new java.awt.Font("Modern No. 20", 1, 24)); // NOI18N
        lblAverageGuessesResult.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAverageGuessesResult.setText("0");

        tblStats.setBackground(new java.awt.Color(255, 249, 79));
        tblStats.setFont(lblcurrentStreakResult.getFont());
        tblStats.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                { new Integer(0),  new Integer(0),  new Integer(0)}
            },
            new String [] {
                "Loses", "Games", "Wins"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblStats.setGridColor(new java.awt.Color(255, 255, 255));
        tblStats.setOpaque(false);
        tblStats.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tblStats);
        if (tblStats.getColumnModel().getColumnCount() > 0) {
            tblStats.getColumnModel().getColumn(0).setResizable(false);
            tblStats.getColumnModel().getColumn(1).setResizable(false);
            tblStats.getColumnModel().getColumn(2).setResizable(false);
        }

        javax.swing.GroupLayout pnlScoreLayout = new javax.swing.GroupLayout(pnlScore);
        pnlScore.setLayout(pnlScoreLayout);
        pnlScoreLayout.setHorizontalGroup(
            pnlScoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlScoreLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlScoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(pnlScoreLayout.createSequentialGroup()
                        .addGroup(pnlScoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblWinRate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblScore, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblSCurrentStreak, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblAverageGuesses, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(14, 14, 14)
                        .addGroup(pnlScoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblWinRateResult, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblcurrentStreakResult, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblAverageGuessesResult, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblScoreResult, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(48, 48, 48))
        );
        pnlScoreLayout.setVerticalGroup(
            pnlScoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlScoreLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlScoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblScore, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblScoreResult, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlScoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSCurrentStreak, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblcurrentStreakResult, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(pnlScoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAverageGuesses, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAverageGuessesResult, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlScoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblWinRate, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblWinRateResult, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Stats", new javax.swing.ImageIcon(getClass().getResource("/images/ScoreIcon.png")), pnlScore); // NOI18N

        jPanel2.setBackground(pnlBckground.getBackground());

        tblHighScores.setBackground(new java.awt.Color(255, 243, 240));
        tblHighScores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Name", "Score"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHighScores.setFocusable(false);
        tblHighScores.setGridColor(new java.awt.Color(0, 0, 0));
        tblHighScores.setOpaque(false);
        tblHighScores.setShowGrid(true);
        tblHighScores.getTableHeader().setResizingAllowed(false);
        tblHighScores.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblHighScores);
        if (tblHighScores.getColumnModel().getColumnCount() > 0) {
            tblHighScores.getColumnModel().getColumn(0).setResizable(false);
            tblHighScores.getColumnModel().getColumn(1).setResizable(false);
        }

        txaWords.setEditable(false);
        txaWords.setBackground(new java.awt.Color(255, 243, 240));
        txaWords.setColumns(20);
        txaWords.setLineWrap(true);
        txaWords.setRows(5);
        txaWords.setTabSize(3);
        txaWords.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        txaWords.setFocusable(false);
        jScrollPane3.setViewportView(txaWords);

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("Guessed Words");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel36)
                .addGap(4, 4, 4)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );

        jTabbedPane1.addTab("High Scores", new javax.swing.ImageIcon(getClass().getResource("/images/HighScore.png")), jPanel2); // NOI18N

        jLayeredPane2.add(jTabbedPane1);
        jTabbedPane1.setBounds(362, 72, 280, 390);

        lblWordle.setFont(new java.awt.Font("Lilita One", 0, 52)); // NOI18N
        lblWordle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblWordle.setText("Wordle");
        jLayeredPane2.add(lblWordle);
        lblWordle.setBounds(20, 6, 610, 60);

        txfGame.setForeground(new java.awt.Color(255, 255, 255));
        txfGame.setText("  ");
        txfGame.setBorder(null);
        txfGame.setCaretColor(new java.awt.Color(255, 255, 255));
        txfGame.setSelectionColor(new java.awt.Color(255, 255, 255));
        txfGame.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txfGameFocusGained(evt);
            }
        });
        txfGame.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txfGameKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txfGameKeyTyped(evt);
            }
        });
        jLayeredPane2.add(txfGame);
        txfGame.setBounds(0, 0, 64, 16);

        javax.swing.GroupLayout pnlBckgroundLayout = new javax.swing.GroupLayout(pnlBckground);
        pnlBckground.setLayout(pnlBckgroundLayout);
        pnlBckgroundLayout.setHorizontalGroup(
            pnlBckgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
        );
        pnlBckgroundLayout.setVerticalGroup(
            pnlBckgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );

        mnbOptions.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N

        mnuProfile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/userForMenuTab.png"))); // NOI18N
        mnuProfile.setText("Profile");

        mitSwitchUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/switch.png"))); // NOI18N
        mitSwitchUser.setText("Switch user");
        mitSwitchUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitSwitchUserActionPerformed(evt);
            }
        });
        mnuProfile.add(mitSwitchUser);

        mitDeleteAccount.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ProfileDelete.png"))); // NOI18N
        mitDeleteAccount.setText("Delete Account");
        mitDeleteAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitDeleteAccountActionPerformed(evt);
            }
        });
        mnuProfile.add(mitDeleteAccount);

        mnbOptions.add(mnuProfile);

        mnuHelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/question.png"))); // NOI18N
        mnuHelp.setText("Help");
        mnuHelp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mnuHelpMouseClicked(evt);
            }
        });
        mnuHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuHelpActionPerformed(evt);
            }
        });
        mnbOptions.add(mnuHelp);

        setJMenuBar(mnbOptions);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(pnlBckground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlBckground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txfUsernameRegKeyTyped(java.awt.event.KeyEvent evt)//GEN-FIRST:event_txfUsernameRegKeyTyped
    {//GEN-HEADEREND:event_txfUsernameRegKeyTyped

    }//GEN-LAST:event_txfUsernameRegKeyTyped

    private void pasRegisterMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_pasRegisterMouseClicked
    {//GEN-HEADEREND:event_pasRegisterMouseClicked


    }//GEN-LAST:event_pasRegisterMouseClicked

    private void pasRegisterConfirmMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_pasRegisterConfirmMouseClicked
    {//GEN-HEADEREND:event_pasRegisterConfirmMouseClicked

    }//GEN-LAST:event_pasRegisterConfirmMouseClicked

    private void pasRegisterConfirmKeyTyped(java.awt.event.KeyEvent evt)//GEN-FIRST:event_pasRegisterConfirmKeyTyped
    {//GEN-HEADEREND:event_pasRegisterConfirmKeyTyped
        //if signup form not blank signup button enabled
        if (!txfUsernameReg.getText().isEmpty() && !pasRegister.getText().isEmpty() && !pasRegisterConfirm.getText().isEmpty())
        {
            btnRegister.setEnabled(true);
            if (evt.getKeyCode() == VK_ENTER)
            {
                SignUp();
            }
        }

    }//GEN-LAST:event_pasRegisterConfirmKeyTyped

    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnRegisterActionPerformed
    {//GEN-HEADEREND:event_btnRegisterActionPerformed
        SignUp();
    }//GEN-LAST:event_btnRegisterActionPerformed

    private void btnBckLoginMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_btnBckLoginMouseMoved
    {//GEN-HEADEREND:event_btnBckLoginMouseMoved


    }//GEN-LAST:event_btnBckLoginMouseMoved

    private void btnRegisterMouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event_btnRegisterMouseEntered
    {//GEN-HEADEREND:event_btnRegisterMouseEntered
        //setting color on hover
        Color colour = new Color(255, 111, 89);
        btnRegister.setBackground(colour);
    }//GEN-LAST:event_btnRegisterMouseEntered

    private void btnRegisterMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_btnRegisterMouseExited
    {//GEN-HEADEREND:event_btnRegisterMouseExited
        //setting color on hover
        Color colour = new Color(255, 153, 51);
        btnRegister.setBackground(colour);
    }//GEN-LAST:event_btnRegisterMouseExited

    private void btnBckLoginMouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event_btnBckLoginMouseEntered
    {//GEN-HEADEREND:event_btnBckLoginMouseEntered
        btnBckLogin.setForeground(java.awt.SystemColor.textHighlight);
    }//GEN-LAST:event_btnBckLoginMouseEntered

    private void btnBckLoginMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_btnBckLoginMouseExited
    {//GEN-HEADEREND:event_btnBckLoginMouseExited
        //setting color on hover
        btnBckLogin.setForeground(Color.white);
    }//GEN-LAST:event_btnBckLoginMouseExited

    private void pasRegisterConfirmKeyPressed(java.awt.event.KeyEvent evt)//GEN-FIRST:event_pasRegisterConfirmKeyPressed
    {//GEN-HEADEREND:event_pasRegisterConfirmKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_pasRegisterConfirmKeyPressed

    private void pasRegisterConfirmKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_pasRegisterConfirmKeyReleased
    {//GEN-HEADEREND:event_pasRegisterConfirmKeyReleased


    }//GEN-LAST:event_pasRegisterConfirmKeyReleased

    private void pasLoginKeyTyped(java.awt.event.KeyEvent evt)//GEN-FIRST:event_pasLoginKeyTyped
    {//GEN-HEADEREND:event_pasLoginKeyTyped
        if (!txfUsername.getText().isEmpty() && !pasLogin.getText().isEmpty())
        {
            btnLogin.setEnabled(true);
        }
    }//GEN-LAST:event_pasLoginKeyTyped

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnLoginActionPerformed
    {//GEN-HEADEREND:event_btnLoginActionPerformed
        Login();
    }//GEN-LAST:event_btnLoginActionPerformed

    private void txfUsernameRegKeyPressed(java.awt.event.KeyEvent evt)//GEN-FIRST:event_txfUsernameRegKeyPressed
    {//GEN-HEADEREND:event_txfUsernameRegKeyPressed
        if (evt.getKeyCode() == VK_ENTER)
        {
            pasRegister.requestFocus();
        }
    }//GEN-LAST:event_txfUsernameRegKeyPressed

    private void txfUsernameRegFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_txfUsernameRegFocusLost
    {//GEN-HEADEREND:event_txfUsernameRegFocusLost
        if (!btnBckLogin.getModel().isPressed())
        {
            if (!Logic.UsernameCheck(txfUsernameReg, lblUsernameCheck))
            {
                //Displaying the requirements 
                JOptionPane.showMessageDialog(this, """
                                                Username does not meet the following requirements:
                                                *Minimum of 5 characters
                                                *No symbols
                                                *No Spaces!""");
            }
        }

    }//GEN-LAST:event_txfUsernameRegFocusLost

    private void pasRegisterFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_pasRegisterFocusLost
    {//GEN-HEADEREND:event_pasRegisterFocusLost
        if (!Logic.PasswordValidation(pasRegister))
        {
            //Displaying the requirements 
            JOptionPane.showMessageDialog(this, """
                                                Password does not meet the following requirements:
                                                *Minimum of 8 characters
                                                *At least 1 upcase letter
                                                *At least 1 lowercase letter
                                                *At least 1 number
                                                *At least 1 symbol
                                                *No Spaces!""");
        }
    }//GEN-LAST:event_pasRegisterFocusLost

    private void btnBckLoginActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnBckLoginActionPerformed
    {//GEN-HEADEREND:event_btnBckLoginActionPerformed
        frmSignUp.dispose();
        frmLogin.setVisible(true);
    }//GEN-LAST:event_btnBckLoginActionPerformed

    private void btnToRegisterMouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event_btnToRegisterMouseEntered
    {//GEN-HEADEREND:event_btnToRegisterMouseEntered
        btnBckLogin.setForeground(java.awt.SystemColor.textHighlight);
    }//GEN-LAST:event_btnToRegisterMouseEntered

    private void btnToRegisterMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_btnToRegisterMouseExited
    {//GEN-HEADEREND:event_btnToRegisterMouseExited
        btnBckLogin.setForeground(Color.white);
    }//GEN-LAST:event_btnToRegisterMouseExited

    private void txfUsernameKeyPressed(java.awt.event.KeyEvent evt)//GEN-FIRST:event_txfUsernameKeyPressed
    {//GEN-HEADEREND:event_txfUsernameKeyPressed

        if (evt.getKeyCode() == VK_ENTER)
        {
            pasLogin.requestFocus();
        }
    }//GEN-LAST:event_txfUsernameKeyPressed

    private void pasLoginKeyPressed(java.awt.event.KeyEvent evt)//GEN-FIRST:event_pasLoginKeyPressed
    {//GEN-HEADEREND:event_pasLoginKeyPressed
        if (evt.getKeyCode() == VK_ENTER)
        {
            if (!txfUsername.getText().isEmpty() && !pasLogin.getText().isEmpty())
            {
                Login();
            } else
            {
                JOptionPane.showMessageDialog(null, "Please fill in the required feilds");
            }
        }
    }//GEN-LAST:event_pasLoginKeyPressed

    private void txfGameKeyPressed(java.awt.event.KeyEvent evt)//GEN-FIRST:event_txfGameKeyPressed
    {//GEN-HEADEREND:event_txfGameKeyPressed
        if (Logic.letter > -1)
        {
            if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE)
            {
                backspace();
            }
        } else
        {
            Logic.letter = 0;
        }
        if (Logic.letter == 5)
        {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER)
            {
                wordle();
            }
        }

    }//GEN-LAST:event_txfGameKeyPressed


    private void txfGameKeyTyped(java.awt.event.KeyEvent evt)//GEN-FIRST:event_txfGameKeyTyped
    {//GEN-HEADEREND:event_txfGameKeyTyped
        //start timer
        if (!txfGame.getText().isEmpty())
        {
            timer.start();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txfGameKeyTyped

    private void lbl5KeyPressed(java.awt.event.KeyEvent evt)//GEN-FIRST:event_lbl5KeyPressed
    {//GEN-HEADEREND:event_lbl5KeyPressed

    }//GEN-LAST:event_lbl5KeyPressed

    private void txfGameFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event_txfGameFocusGained
    {//GEN-HEADEREND:event_txfGameFocusGained

    }//GEN-LAST:event_txfGameFocusGained

    private void mnuHelpActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mnuHelpActionPerformed
    {//GEN-HEADEREND:event_mnuHelpActionPerformed
    }//GEN-LAST:event_mnuHelpActionPerformed

    private void mitDeleteAccountActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mitDeleteAccountActionPerformed
    {//GEN-HEADEREND:event_mitDeleteAccountActionPerformed
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this,
                "Please confirm that want to delete your account?", "Warning Message!",
                dialogButton);
        if (dialogResult == 0)
        {
            Logic.deleteUser();
        } else
        {
            txfGame.requestFocusInWindow();
        }
    }//GEN-LAST:event_mitDeleteAccountActionPerformed

    private void mitSwitchUserActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mitSwitchUserActionPerformed
    {//GEN-HEADEREND:event_mitSwitchUserActionPerformed
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this,
                "Please confirm that you want to switch accounts?", "Warning Message!",
                dialogButton);
        if (dialogResult == 0)
        {
            Logic.varibleReset();
            this.dispose();
            new Wordle().setVisible(false);
            frmSignUp.setVisible(true);
        } else
        {
            txfGame.requestFocusInWindow();
        }
    }//GEN-LAST:event_mitSwitchUserActionPerformed

    private void mnuHelpMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_mnuHelpMouseClicked
    {//GEN-HEADEREND:event_mnuHelpMouseClicked
        frmHowToPlay.setVisible(true);

    }//GEN-LAST:event_mnuHelpMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(Wordle.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(Wordle.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(Wordle.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(Wordle.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new Wordle().setVisible(false);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBckLogin;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnRegister;
    private javax.swing.JButton btnToRegister;
    private javax.swing.JFrame frmHowToPlay;
    private javax.swing.JFrame frmLogin;
    private javax.swing.JFrame frmSignUp;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JLabel lbl1;
    private javax.swing.JLabel lbl10;
    private javax.swing.JLabel lbl11;
    private javax.swing.JLabel lbl12;
    private javax.swing.JLabel lbl13;
    private javax.swing.JLabel lbl14;
    private javax.swing.JLabel lbl15;
    private javax.swing.JLabel lbl16;
    private javax.swing.JLabel lbl17;
    private javax.swing.JLabel lbl18;
    private javax.swing.JLabel lbl19;
    private javax.swing.JLabel lbl2;
    private javax.swing.JLabel lbl20;
    private javax.swing.JLabel lbl21;
    private javax.swing.JLabel lbl22;
    private javax.swing.JLabel lbl23;
    private javax.swing.JLabel lbl24;
    private javax.swing.JLabel lbl25;
    private javax.swing.JLabel lbl26;
    private javax.swing.JLabel lbl27;
    private javax.swing.JLabel lbl28;
    private javax.swing.JLabel lbl29;
    private javax.swing.JLabel lbl3;
    private javax.swing.JLabel lbl30;
    private javax.swing.JLabel lbl4;
    private javax.swing.JLabel lbl5;
    private javax.swing.JLabel lbl6;
    private javax.swing.JLabel lbl7;
    private javax.swing.JLabel lbl8;
    private javax.swing.JLabel lbl9;
    private javax.swing.JLabel lblAverageGuesses;
    private javax.swing.JLabel lblAverageGuessesResult;
    private javax.swing.JLabel lblCheck;
    private javax.swing.JLabel lblHowToPlay;
    private javax.swing.JLabel lblLine;
    private javax.swing.JLabel lblLine1;
    private javax.swing.JLabel lblLine2;
    private javax.swing.JLabel lblLine3;
    private javax.swing.JLabel lblLine4;
    private javax.swing.JLabel lblLogin;
    private javax.swing.JLabel lblLoginPng;
    private javax.swing.JLabel lblLoginPng1;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblPassword1;
    private javax.swing.JLabel lblPasswordConfirm;
    private javax.swing.JLabel lblRegister;
    private javax.swing.JLabel lblSCurrentStreak;
    private javax.swing.JLabel lblScore;
    private javax.swing.JLabel lblScoreResult;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JLabel lblUsername1;
    private javax.swing.JLabel lblUsernameCheck;
    private javax.swing.JLabel lblWinRate;
    private javax.swing.JLabel lblWinRateResult;
    private javax.swing.JLabel lblWordle;
    private javax.swing.JLabel lblcurrentStreakResult;
    private javax.swing.JMenuItem mitDeleteAccount;
    private javax.swing.JMenuItem mitSwitchUser;
    private javax.swing.JMenuBar mnbOptions;
    private javax.swing.JMenu mnuHelp;
    private javax.swing.JMenu mnuProfile;
    private javax.swing.JPasswordField pasLogin;
    private javax.swing.JPasswordField pasRegister;
    private javax.swing.JPasswordField pasRegisterConfirm;
    private javax.swing.JPanel pnlBackground1;
    private javax.swing.JPanel pnlBackground2;
    private javax.swing.JPanel pnlBackkground2;
    private javax.swing.JPanel pnlBackkground3;
    private javax.swing.JPanel pnlBckground;
    private javax.swing.JPanel pnlBoard;
    private javax.swing.JPanel pnlHowToPlay;
    private javax.swing.JPanel pnlScore;
    private javax.swing.JTable tblHighScores;
    private javax.swing.JTable tblStats;
    private javax.swing.JTextArea txaTimer;
    private javax.swing.JTextArea txaWords;
    private javax.swing.JTextField txfGame;
    private javax.swing.JTextField txfUsername;
    private javax.swing.JTextField txfUsernameReg;
    // End of variables declaration//GEN-END:variables
}
