# Clash Royale 2D

A 2D Java/JavaFX clone of Clash Royale: pick a deck, queue an AI opponent
(simple or smart), and battle for the towers.

> **About this version.** The repository began as a single-developer
> learning project — feature-complete but architecturally immature
> (1,500-line god class, string-based identity, hand-rolled SQL,
> magic numbers, no tests). This branch introduces the foundations
> of a senior-grade rebuild: build system, security fixes, design
> patterns, type safety, and a test harness. See
> [Refactor highlights](#refactor-highlights) for the full list.

---

## Tech stack

| Concern   | Choice                                |
| --------- | ------------------------------------- |
| Language  | Java 17                               |
| UI        | JavaFX 17 (FXML scenes)               |
| Build     | **Maven** (was: ad-hoc `.iml`)        |
| Database  | MySQL 8 (JDBC + BCrypt)               |
| Testing   | JUnit 5 + AssertJ                     |
| Logging   | `java.util.logging`                   |

---

## Build & run

```bash
# compile, run unit tests, package
mvn clean verify

# launch the app (uses the JavaFX Maven plugin)
mvn javafx:run
```

### Database connection

The connection target is no longer hard-coded. Set the following
environment variables (or fall back to the local defaults):

| Variable           | Default                                                          |
| ------------------ | ---------------------------------------------------------------- |
| `CR2D_DB_URL`      | `jdbc:mysql://localhost:3306/clashroyale2d?useSSL=false&serverTimezone=UTC` |
| `CR2D_DB_USERNAME` | `root`                                                           |
| `CR2D_DB_PASSWORD` | *(empty)*                                                        |

---

## Architecture overview

```
clashroyale
├── Main                         # JavaFX entry point
├── controllers/                 # FXML controllers (one per scene)
├── views/                       # FXML + GameView
│
├── constants/                   # ArenaConstants, GameConstants
├── models/
│   ├── enums/                   # CardType, BotKind, Range, Speed, Target
│   ├── Owner                    # value object replacing String relatedUser
│   ├── factory/CardFactory      # central card construction
│   ├── cardsmodels/             # Card / TroopsCard / Building / Spells …
│   ├── towersmodels/            # Tower / KingTower / QueenTower
│   ├── game/                    # Robot family, GameHistory, LeftTime
│   └── GameModel                # match orchestrator (legacy, big)
│
├── bots/                        # NEW — Strategy-pattern bot framework
│   ├── BotPlayer                # composes selection + deployment strategies
│   ├── BotPlayerFactory
│   ├── BotContext               # read-only view of arena state
│   ├── CardSelectionStrategy    # functional interface
│   ├── DeploymentStrategy       # functional interface
│   └── strategies/              # Random + ZoneAware + QueueCycle
│
├── persistence/
│   ├── DatabaseConfig           # env-driven settings
│   └── ConnectionFactory        # short-lived JDBC connections
│
└── security/
    └── PasswordHasher           # BCrypt with legacy-row upgrade path
```

---

## Refactor highlights

| Theme                | Before                                                                             | After                                                                                                                |
| -------------------- | ---------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------- |
| **Build**            | IntelliJ `.iml` only — no CLI build                                                | `pom.xml` with JavaFX, MySQL, BCrypt, JUnit 5, AssertJ; runs anywhere                                                 |
| **Security**         | `SELECT * FROM users WHERE username='" + raw + "'"`; plaintext password compare     | `PreparedStatement`s everywhere; BCrypt hashing with transparent migration of legacy rows                             |
| **Resource leaks**   | `DriverManager.getConnection()` never closed                                       | `ConnectionFactory` + try-with-resources at every call site                                                           |
| **Magic numbers**    | Arena bounds (`25`, `340`, `230`, `260`, `133`…), `1.4` rage buff, `30`-frame elixir | `ArenaConstants` + `GameConstants`                                                                                    |
| **String identity**  | `card.getRelatedUser().equals("simpleBot")`, `card.getTitle().equals("babyDragon")` | `Owner` value object, `BotKind` enum, `CardType` enum                                                                 |
| **Card construction**| Hand-written lists in `UserModel` and `Robot`                                      | Single `CardFactory` driven by the `CardType` enum                                                                    |
| **AI design**        | `Robot` ←─ `SimpleRobot` / `SmartRobot` (inheritance for behaviour)                | `BotPlayer` composing `CardSelectionStrategy` + `DeploymentStrategy` — Strategy pattern, plain composition           |
| **Logging**          | `System.out.println` and `e.printStackTrace()`                                     | `java.util.logging` with leveled messages and proper exception attachment                                             |
| **Tests**            | None                                                                               | JUnit 5 + AssertJ covering enums, factory, value objects, strategies, hashing, arena geometry                          |
| **Project layout**   | One implicit `src/` mixing code & resources                                        | Maven-canonical `src/` (sources) + `src/clashroyale/resources/` (resources) + `test/` (tests)                          |

### Design patterns applied

| Pattern             | Where                                                                  |
| ------------------- | ---------------------------------------------------------------------- |
| **Factory**         | `CardFactory`, `BotPlayerFactory`                                      |
| **Strategy**        | `CardSelectionStrategy`, `DeploymentStrategy` and the four implementations under `bots.strategies` |
| **Value Object**    | `Owner`                                                                |
| **Closed-set Enum** | `CardType`, `BotKind`                                                  |
| **Configuration Object** | `DatabaseConfig`, `ArenaConstants`, `GameConstants`               |

### Known follow-ups (future PRs)

- Decompose `GameModel` (1,500 lines) into `CombatSystem`, `MovementSystem`,
  `SpellResolver`, `ElixirSystem`, `ArenaState` — the new infrastructure
  (constants, factories, value objects, strategies) is in place to make
  this incremental and safe.
- Replace `instanceof` chains in spell-effect dispatch with a Visitor.
- Introduce a domain `GameEventBus` so the view can react to card-damaged
  / tower-destroyed events instead of polling.
- Connection pooling (HikariCP) once load justifies it.

---

## Testing

```bash
mvn test
```

Test packages mirror the production layout under `test/`. The current
suite focuses on the new infrastructure — in particular the units that
the legacy code couldn't easily express:

- `CardTypeTest`, `OwnerTest` — type taxonomy round-trips
- `CardFactoryTest` — every (type, level) combination produces a card
- `BotPlayerTest` — Strategy composition, elixir invariants, deployment bounds
- `PasswordHasherTest` — BCrypt round-trip, legacy plaintext migration path
- `ArenaConstantsTest` — geometric invariants of the arena

---

## License

MIT.
