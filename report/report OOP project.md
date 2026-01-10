# Object-Oriented Programming Mini-Project Report

**Project title:** Traditional Game – Ô ăn quan  
**Topic:** Topic 4 — Traditional game: Ô ăn quan  

---

## 1. Assignment of Members

| Member name | Responsibilities | Contribution |
|------------|------------------|--------------|
| Member 1 | Core game logic (`OAnQuanGame`, `Board`, `Square`, rules, move processing) | 25% |
| Member 2 | Player system (`Player`, `HumanPlayer`, `AIPlayer`), turn management | 25% |
| Member 3 | GUI & controllers (JavaFX screens, controllers, navigation, input handling) | 25% |
| Member 4 | Utilities (animation, sounds, timers), testing, integration, bug fixing | 25% |

> Replace "Member X" with actual names.

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

**Packages:**

- `controller` — GUI controllers  
- `model.entity` — core entities  
- `model.game` — game engine  
- `model.rules` — rule implementations  
- `util` — utilities  
- `config` — constants  

**Relationships:**

- `OAnQuanGame` aggregates `Board`, `Player`, and `GameRule`.
- `Square` is the superclass of `CitizenSquare` and `MandarinSquare`.
- `Player` is the superclass of `HumanPlayer` and `AIPlayer`.
- Controllers depend on the game engine.

---

## 5. Detail for Classes / Methods

### `OAnQuanGame`

- `makeMove(squareIndex, direction)` — Executes a player's move.
- `checkEndCondition()` — Checks if the game should end.
- `switchTurn()` — Changes active player.

### `Board`

- `getNextSquare(index, direction)`
- `getSquareAt(index)`

### `StandardRule`

- Applies capturing and spreading logic.
- Calculates chain captures.

---

## 6. Source Usage Declaration

- The project was implemented by the team members.
- No external game engines were used.
- Only Java standard libraries and JavaFX were used.
- Game rules were referenced from public descriptions of Ô ăn quan.

**Declaration:**  
> We declare that this project is our original work. No source code was copied from external repositories.
