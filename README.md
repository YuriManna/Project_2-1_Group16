
# Project 2.1 - Quarto Game (Group 16)

**Version:** 1.0

## Overview
Welcome to Project 2.1, a Java version of the game Quarto developed by Group 16. In this project, we aim to create an exciting implementation of the popular board game Quarto. This version is designed to be played between human players. We have plans to implement AI opponents in the near future, which you can already try out (currently without the GUI).

## Table of Contents
1. [Prerequisites](#prerequisites)
2. [Running the Game](#running-the-game)
3. [How to Play Quarto](#how-to-play-quarto)
4. [Contributors](#contributors)
5. [License](#license)

## Prerequisites

Before you can run the Quarto game, make sure you have the following prerequisites installed on your system:

- **Java Runtime Environment (JRE):** You need to have Java Runtime Environment (JRE) installed on your computer. You can download it from [Oracle's official website](https://www.oracle.com/java/technologies/javase-downloads.html).

## Running the Game

To run the Quarto game, follow these simple steps:

### Phase 1: 
1. **Download the Quarto-Phase1.jar:** You can find the Quarto-Phase1.jar file in the project's repository.

2. **Open a Terminal (Linux/macOS) or Command Prompt (Windows):** Navigate to the directory where you downloaded the Quarto-Phase1.jar file.

3. **Run the JAR File:** Use the following command to run the Quarto game:
```bash
java -jar Quarto-Phase1.jar
```

Make sure to replace `Quarto-Phase1.jar` with the actual filename if it's different. This command will start the Quarto game, and you can begin playing!


### Phase 2:
You can simply run the application from the App.java file. (src>main>java>com.quarto>)

If you would like to test the AI agents, you can run them directly from these files:
- PlayMinimax.java for Human vs Minimax model
- PlayBaseline.java for Human vs baseline (random) model


## How to Play Quarto

Quarto is a two-player strategy board game. The objective of the game is to be the first player to form a line of four pieces with at least one common characteristic, whether it be size, color, shape, or hollowness. Players take turns selecting a piece for their opponent to place on the board. The twist is that you choose the piece for your opponent, making it a game of strategy and deduction.

For detailed game rules and strategies, you can refer to the official [Quarto rules](https://en.wikipedia.org/wiki/Quarto_(board_game)).

## Contributors

This project was developed by Group 16. The team members who contributed to this project are:

- [Yuri Manna]
- [Nigel Wens]
- [Dorina Sîli]
- [Mateusz Zboś]
- [Mārtiņš Pūķis]
- [Yinchu Wang]
- [Bochen Qiao]

## License

This project is open-source and released under the [MIT License](LICENSE.md). You are free to use, modify, and distribute this software under the terms of the license.

Enjoy playing Quarto, and we look forward to implementing AI opponents in future versions! If you have any questions or encounter any issues, please don't hesitate to reach out to us.

For updates and the latest version of the game, check our project repository on GitHub.

Happy gaming!