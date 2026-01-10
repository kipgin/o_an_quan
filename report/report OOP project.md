# Object-Oriented Programming Mini-Project Report

**Project title:** Traditional Game – Ô ăn quan  
**Topic:** Topic 4 — Traditional game: Ô ăn quan  

---

## 1. Assignment of Members

## 2. Team Members & Assignment

| Name | Student ID | Coding Responsibility | Contribution |
|------|------------|----------------------|--------------|
| Vũ Đức Tâm | 20230064 | Game engine, turn flow, end-game logic | 25% |
| Nguyễn Tuấn Long | 202416269 | Board and square structure, stone distribution | 25% |
| Nguyễn Đăng Cao Tuấn | 202400119 | Player classes, scoring, move validation | 25% |
| Nguyễn Gia Khánh | 202416803 | GUI controllers, event handling, screen navigation | 25% |

---

## 2. Mini-Project Description

### 2.1 Objective

This project implements the traditional Vietnamese board game **Ô ăn quan** as an interactive desktop application using Java and JavaFX. The goal is to apply Object-Oriented Programming principles such as encapsulation, inheritance, abstraction, and polymorphism to model the game board, players, squares, rules, and gameplay mechanics.

### 2.2 Functional Requirements

- Display the game board with 10 citizen squares and 2 mandarin squares.
- Allow two players to take turns selecting a square and a direction.
- Implement stone spreading and capturing rules correctly.
- Update player scores dynamically.
- End the game when both mandarin squares are empty.
- Announce the winner when the game ends.
- Provide a main menu, help screen, and game screen.

### 2.3 Non-Functional Requirements

- Clear separation between logic and UI.
- Extensible design for adding new rules or AI players.
- No use of pre-built game engines.

---

## 3. Use Case Diagram and Explanation

### 3.1 Actor(s) & Use Cases

| Use Case          | Actor  | Description                                                                                 |
| ----------------- | ------ | ------------------------------------------------------------------------------------------- |
| Start Game        | Player | Starts a new Ô ăn quan game session and initializes the board and players.                  |
| View Help / Rules | Player | Displays the instructions and rules of the Ô ăn quan game.                                  |
| Exit Application  | Player | Exits the application after confirmation from the user.                                     |
| Select Square     | Player | Selects a square on the board to pick up and spread stones.                                 |
| Choose Direction  | Player | Chooses the spreading direction (clockwise or counter-clockwise).                           |
| View Board        | Player | Displays the current state of the game board and stones.                                    |
| View Score        | Player | Displays the current scores of both players.                                                |
| End Game          | Player | Ends the current game when the termination condition is reached and shows the final result. |

### 3.2 Explanation

The user interacts with the game via the GUI to select squares and directions. The controller sends these actions to the game engine, which updates the board state and returns the results to the UI for visualization.

---

## 4. Design

### 4.1 General Class Diagram (Package Level)

![](general_diagram.png)

- `controller` — GUI controllers  
- `model.entity` — core entities  
- `model.game` — game engine  
- `model.rules` — rule implementations  
- `util` — utilities  
- `config` — constants  

---

### 4.2 Detailed Class Diagram

#### 4.2.1 Controller

![](controller_diagram.png)

#### 4.2.2 Model

![](model_diagram.png)

#### 4.2.3 Util

![](util_diagram.png)

---
## 5.Explanation of the Design

### 5.1 Overall Architecture

The system is designed using a layered object-oriented architecture. The code is divided into separate packages for game logic, board structure, players, rules, and user interface. This separation ensures that each part of the system has a clear responsibility and can be modified or extended independently.

The core of the system is the game engine, which controls the flow of the game. The board and squares represent the physical state of the game. Players represent the participants and store information such as name and score. Rules define how stones are spread and captured. The user interface handles all interactions with the player.

---

### 5.2 Class Responsibilities

The OAnQuanGame class is responsible for managing the game state, processing turns, validating moves, switching players, and checking for end-game conditions.

The Board class stores and manages all squares on the board. It provides methods to retrieve squares, move to the next square based on direction, and update stone counts.

The Square class represents a single position on the board. It stores the number of stones and its index. It provides basic operations such as adding and removing stones. CitizenSquare and MandarinSquare are specific types of squares that differ in initial stone count and scoring value.

The Player class stores player information such as name, score, and side. It also defines the interface for making a move. HumanPlayer is a concrete implementation that takes input from the user.

The GameRule interface defines the rules of the game. The StandardRule class implements the official rules of O An Quan, including stone spreading, capturing logic, and scoring.

The UI controller classes handle user input, screen navigation, and updating the visual representation of the game state.

---

### 5.3 Object-Oriented Principles

Encapsulation is applied by keeping class fields private and exposing only necessary methods. For example, the stone count inside a Square cannot be modified directly by other classes.

Inheritance is used to model specialization. CitizenSquare and MandarinSquare inherit from Square, and HumanPlayer inherits from Player.

Polymorphism allows the game engine to work with abstract types. The game logic interacts with Square and Player types without needing to know their concrete implementations.

Abstraction is applied through interfaces and abstract classes such as GameRule and Square, which define behavior without exposing implementation details.

---

### 5.4 Relationships Between Classes

OAnQuanGame uses Board, Player, and GameRule to execute the game.

Board contains multiple Square objects, representing a composition relationship.

OAnQuanGame keeps references to Player objects and switches between them during gameplay.

UI controllers depend on OAnQuanGame to retrieve and update the game state.

GameRule is implemented by StandardRule and is used by OAnQuanGame to apply game logic.

---

### 5.5 Key Method Behavior

The makeMove method in OAnQuanGame performs the main game action. It takes the selected square and direction, collects stones, distributes them across the board, applies capturing rules, updates scores, and then switches the turn.

The checkEndCondition method verifies whether both mandarin squares are empty and ends the game if the condition is met.

The getNextSquare method in Board calculates the next square index based on the current index and chosen direction.

---

### 5.6 Design Highlights

The design separates game rules from the game engine, allowing different rule sets to be added easily.

The user interface is decoupled from the game logic, making it possible to change the UI without affecting the core mechanics.

The design supports extensibility and maintainability by clearly dividing responsibilities and following object-oriented principles.

---

