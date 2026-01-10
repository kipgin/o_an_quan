# Mini-Project Report  
**Project:** Traditional game - Ô ăn quan
**Group:** 9

---

## 1. Assignment Members

| Name                 | Student ID | Contribution                            |
| -------------------- | ---------- | --------------------------------------- |
| Vũ Đức Tâm           | 20230064   |                                         |
| Nguyễn Tuấn Long     | 202416269  |                                         |
| Nguyễn Đăng Cao Tuấn | 202400119  |                                         |
| Nguyễn Gia Khánh     | 202416803  |                                         |

---

## 2. Source Usage Declaration


---

## 3. Mini-Project Description

### 3.1 Overview

This mini-project implements the traditional Vietnamese board game **Ô ăn quan** using Object-Oriented Programming principles.

The software allows two players to:
- Select squares
- Choose distribution direction
- Distribute stones according to rules
- Capture stones
- Track scores and detect game end

The project emphasizes:
- Separation of concerns
- Maintainable object-oriented design
- Clean domain modeling

---

### 3.2 Functional Requirements

- Display the game board.
- Allow player to select a square.
- Allow player to select direction (left or right).
- Distribute stones following the rules.
- Capture stones when applicable.
- Switch turns automatically.
- Detect game end and winner.

---

### 3.3 Use Case Diagram (Textual)

**Actors:**
- Player

**Use Cases:**
- Start Game
- Select Square
- Choose Direction
- Make Move
- View Board
- View Score
- End Game

**Explanation:**

1. Player starts the game.
2. Player selects a square on their side.
3. Player selects a direction.
4. System distributes stones and applies rules.
5. System updates board and score.
6. System switches turn.
7. Repeat until game ends.

---

## 4. Design

### 4.1 General Architecture

The system follows a layered MVC-style design:

- **Model:** Game state and rules
- **Controller:** Game flow and coordination
- **View:** User interface

---
### 4.2 General Class Diagram 

---
### 4.3 Detailed Class Diagram

---
## 5. Explanation of Design

### 5.1 Relationships Between Classes

- `Board` contains multiple `Square`.
- `Square` is an abstract concept extended by `CitizenSquare` and `MandarinSquare`.
- `OAnQuanGame` contains a `Board`, `Players`, and a `GameRule`.
- `GameRule` defines how a move is applied (Strategy Pattern).
- `GameController` coordinates between user input, game logic, and view.
- `GameEvent` objects describe what happened during a move.

---

### 5.2 Important OOP Methods

- `GameRule.applyMove()` — contains the core rule logic.
- `Board.getSquare()` — provides controlled access to board state.
- `Square.addStone()` / `removeStone()` — encapsulate state changes.
- `GameController.handleMove()` — connects UI input to game logic.

---

### 5.3 Design Highlights

- **Strategy Pattern:** Used for game rules (`GameRule`).
- **Event Pattern:** Game logic produces events instead of directly modifying UI.
- **MVC Separation:** Model does not depend on view.
- **Polymorphism:** Used in Square, SquareView, and GameEvent hierarchies.

---

## 6. Conclusion

This project demonstrates:
- Proper object-oriented decomposition
- Use of inheritance, interfaces, and polymorphism
- Clean separation of concerns
- Extendable and maintainable architecture




