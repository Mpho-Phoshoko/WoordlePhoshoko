# Woordle - Java Wordle Clone

A professional Java Swing implementation of the popular word-guessing game Wordle. Guess the hidden five-letter word within six attempts using intelligent color-coded feedback.

## 🎮 Features

### Core Gameplay
- **Six Attempts**: Guess the hidden five-letter word within limited attempts
- **Color-Coded Feedback**: 
  - Green: Correct letter, correct position
  - Yellow: Correct letter, wrong position
  - Gray: Letter not in word
- **Input Validation**: Ensures valid five-letter words
- **Multiple Game Modes**: Standard and timed challenges

### Data Management
- **Word Database**: Text-based storage of valid five-letter words
- **High Score Tracking**: Persistent leaderboard with player statistics
- **User Profiles**: Name-based scoring and progress tracking

### User Interface
- **Java Swing GUI**: Clean, responsive interface
- **Virtual Keyboard**: Visual feedback for letter status
- **Tabbed Navigation**: Game, scores, settings, and help panels
- **Animated Transitions**: Smooth tile animations

## 🚀 Quick Start

### Prerequisites
- Java JDK 8 or higher
- NetBeans IDE (recommended)

### Installation
```bash
git clone https://github.com/Mpho-Phoshoko/WoordlePhoshoko.git
cd WoordlePhoshoko
```

### Using NetBeans
1. Open NetBeans IDE
2. Select **File → Open Project**
3. Navigate to the cloned folder
4. Click **Open Project**
5. Run: **Run → Run Project** (F6)

### Command Line
```bash
javac -d bin src/main/*.java
java -cp bin WoordleMain
```

## ⚙️ Configuration

### Word Database
- Edit `resources/words.txt` to add custom five-letter words
- One word per line, uppercase recommended

### Game Settings
- Modify `config.properties` for application preferences
- Adjust timer durations and scoring parameters

## 🏆 High Scores System

### Scoring Algorithm
- Base score: 100 points for correct guess
- Attempt bonus: +20 points per remaining attempt
- Time bonus (Timer Mode): Up to 50 additional points
- Streak multiplier for consecutive wins

### Leaderboard Features
- Top 10 scores per game mode
- Player statistics tracking
- Score persistence across sessions

## 🔧 Technical Implementation

### Key Components
- **Word Selection**: Random selection from validated database
- **Feedback Algorithm**: Position-based color determination
- **State Management**: Game progression and validation
- **Data Persistence**: File-based storage for scores and words

### Design Patterns
- Model-View-Controller architecture
- Singleton pattern for database access
- Observer pattern for UI updates

## 📄 License

MIT License - See LICENSE file for details.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/improvement`)
3. Commit changes (`git commit -m 'Add feature'`)
4. Push to branch (`git push origin feature/improvement`)
5. Open a Pull Request

## 📞 Support

For issues or questions, please open an issue on the GitHub repository with:
- Java version and OS details
- Steps to reproduce the issue
- Expected vs actual behavior

---

**Enjoy the challenge of word deduction!**
