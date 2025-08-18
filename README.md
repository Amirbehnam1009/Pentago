# 🎮 Pentago 


## 📖 About  
**Pentago** is a captivating two-player strategy game where players compete to align five marbles in a row while dynamically rotating the game board. This Java implementation was developed under the supervision of [Prof. Hossein Zeinali](https://scholar.google.com/citations?user=KaGpFx8AAAAJ&hl=en) during Spring 2020.

## 🎲 Game Rules  
- **Board**: 6×6 grid divided into four 3×3 rotating blocks  
- **Moves per turn**:  
  1️⃣ Place your marble  
  2️⃣ Rotate any block 90° (clockwise/counter-clockwise)  
- **Winning**: First to make 5-in-a-row (any direction) wins!  
- **Draw**: Full board or simultaneous wins  

📌 *Full rules in [project description](AP-Norooz-Project-8-14.pdf)*  

## ✨ Features  
| Feature | Description |
|---------|-------------|
| 🕹️ **Game Modes** | Player vs Player • Player vs AI |
| 🖥️ **Console UI** | Clean board visualization with `●` (black) and `○` (red) |
| ⚖️ **Rule Enforcement** | Valid moves • Win/draw detection • Rotation logic |
| 📜 **Move Logging** | Step-by-step game progress tracking |

## 🚀 Getting Started  

```bash
git clone https://github.com/Amirbehnam1009/Pentago.git
cd Pentago
javac Pentago.java && java Pentago
 ```

## 🧠 Strategy Tips

- 🎯 **Control the center blocks** - Dominating central blocks increases winning opportunities
- ✋ **Block opponents** - Rotate sections to disrupt their potential rows
- 🔄 **Create multiple threats** - Set up simultaneous winning paths to force favorable rotations

## 🔮 Future Roadmap

| Priority | Feature                          |
|----------|----------------------------------|
| 🔴 High  | Advanced AI (Minimax algorithm)  |
| 🟡 Medium| Graphical interface (JavaFX)     |
| 🟢 Low   | Online multiplayer support       |

## 🤝 Contributing

PRs and issues welcome! Help us improve:
- 🐛 **Bug fixes**
- 💡 **New features**  
- 📊 **Performance optimizations**
