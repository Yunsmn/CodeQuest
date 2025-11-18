# CodeQuest

A 2D educational game where players learn programming by typing commands to control their character and solve puzzles through an interactive Java-based adventure.

## Game Concept

CodeQuest is an educational puzzle game that teaches programming fundamentals through engaging gameplay. Players control their character by writing Python-like commands in a text parser, seamlessly blending authentic programming education with adventure-style gameplay.

**Key Features:**
- Learn by doing: Type actual code commands to move and interact
- Progressive difficulty: Start with simple commands, advance to loops and conditionals
- Visual feedback: See your code execute in real-time

## Current Status

### Completed Features

**Game Engine Core**
- 60 FPS game loop with delta time calculations
- Smooth rendering with double buffering
- Efficient viewport culling (only visible tiles rendered)

**Player System**
- 8-directional sprite animation with walk cycles
- Smooth camera-following movement
- Rectangle-based collision detection
- Sprite animation system with frame switching

**Map System**
- 50x50 tile world map with three tile types
- Tile-based collision system
- Map loading from text files
- World camera system (player centered)

**Collision Detection**
- Predictive collision checking
- Solid area rectangles for entities
- Tile-based collision grid
- Direction-aware collision response

### In Development

**Command Parser** (Primary Focus)
- Text input field for typing commands
- Python-style command execution system
- Command validation and error handling

### Planned Features

**Phase 1: Command System**
- Basic movement commands: `player.move()`, `player.moveTo(x, y)`

**Phase 2: Programming Concepts**
- Variables: 
- Loops: 

**Phase 3: Level Design**
- Progressive tutorial levels
- Puzzle-based challenges
- Multiple solution approaches
- Achievement system

**Phase 4: Content Expansion**
- NPCs and dialogue
- Quest system
- Interactive objects

## Graphics Upgrade Plan

The team is planning to enhance visual quality while maintaining the game's educational focus.

### Current Graphics
- Tile Size: 16x16 pixels
- Scale Factor: 3x
- Display Size: 48x48 pixels per tile
- Style: Simple pixel art

### Planned Upgrades
- New Tile Sizes: 32x32, 48x48, or 64x64 pixels
- Higher detail sprites and textures
- Improved character animations
- Enhanced environmental details

### Implementation Process

**Step 1: Update Configuration** (GamePanel.java)
```java
final int GameTiles = 32;  // Change from 16 to new sprite size
final int scale = 2;       // Adjust scale factor as needed
// Result: 32 × 2 = 64 pixels per tile on screen
```

**Step 2: Replace Assets**
- Maintain existing file structure and naming
- Place new sprites in `/res/player/`
- Place new tiles in `/res/tiles/`
- Support for PNG format

**Step 3: Verify Compatibility**
- Maps stored as tile indices (resolution-independent)
- No map file modifications required

### Design Considerations
- Maintain readability at all resolutions
- Ensure consistent art style across all assets
- Keep file sizes reasonable for distribution
- Consider accessibility (color contrast, clarity)

## Map System

### File Format

Maps are stored as text files with space-separated tile indices:

```
1 1 1 1 1
1 0 0 0 1
1 0 2 0 1
1 0 0 0 1
1 1 1 1 1
```

### Tile Types

**Current Tiles:**
- `0` - Grass (walkable terrain)
- `1` - Wall (solid, blocks movement)
- `2` - Water (solid, blocks movement)

### Creating Custom Maps

1. Create new text file in `/res/Maps/`
2. Format: 50 columns × 50 rows
3. Use space-separated integers (0-9)
4. Load in TileManager: `loadMap("/CodeQuest/res/Maps/YourMap.txt")`

Example map structure:
```
Border: All 1s (walls)
Interior: Mix of 0s (grass) and 2s (water)
Structures: Enclosed areas with 1s
```

### Future Enhancements
- Multi-layer rendering (ground, objects, overlay)
- Animated tiles (water ripples, grass sway)
- Procedural generation for practice levels
- Tile metadata (properties, events, triggers)

## Technical Architecture

### Project Structure

```
CodeQuest/
├── Main/
│   ├── Main.java              # Application entry point
│   ├── GamePanel.java         # Game loop, rendering pipeline
│   ├── KeyHandler.java        # Input event handling
│   └── CollisionChecker.java  # Collision detection system
├── Entity/
│   ├── entity.java            # Base entity class
│   └── Player.java            # Player character logic
├── Tiles/
│   ├── Tile.java              # Tile data structure
│   └── TileManager.java       # Map loading and rendering
└── res/
    ├── player/                # Player sprite sheets
    ├── tiles/                 # Tile textures
    └── Maps/                  # Level data files
```

### Core Systems

**Camera System**
- Player remains at fixed screen position
- World translates relative to player movement
- Viewport culling for performance optimization
- Smooth scrolling with pixel-perfect alignment

**Collision Detection**
- Entity solid areas defined as rectangles
- Predictive collision (checks before movement)
- Tile-grid based collision lookup
- Direction-aware collision response
- Prevents overlap with solid tiles

**Animation System**
- Frame-based sprite animation
- 2 frames per direction (8 sprites total)
- Timer-based frame switching
- Direction changes update sprite immediately

**Rendering Pipeline**
1. Clear screen
2. Render visible tiles (with culling)
3. Render entities (player, NPCs)
4. Render UI elements (future: parser)
5. Swap buffers (double buffering)

## Development Roadmap

### Phase 1: Core Mechanics (COMPLETED)
- [x] Game loop implementation
- [x] Player movement system
- [x] Tile-based rendering
- [x] Collision detection
- [x] Camera/viewport system
- [x] World map loading

### Phase 2: Command System (IN PROGRESS)
- [ ] Text input field UI
- [ ] Command parser implementation
- [ ] String-based command matching
- [ ] Basic command set (move, interact)
- [ ] Error handling

### Phase 3: Graphics Enhancement (PLANNED)
- [ ] Source or create 32x32+ sprites
- [ ] Design detailed tile textures
- [ ] Implement sprite scaling system
- [ ] Polish animations

### Phase 4: Educational Content (PLANNED)
- [ ] Tutorial level sequence
- [ ] Progressive command introduction
- [ ] Puzzle design and implementation
- [ ] Achievement tracking
- [ ] Progress saving system

## Running the Game

### Development Setup

1. **Clone Repository**
   ```bash
   git clone https://github.com/yourusername/codequest.git
   cd codequest
   ```

2. **Open in IDE**


3. **Configure Resources**
   - Mark `/res/` folder as resources root
   - Ensure all image files are accessible
   - Verify map files are in correct format

4. **Run Application**
   - Execute `Main.java`
   - Game window should appear
   - Use arrow keys for temporary controls

### Current Controls (Temporary)
- Arrow Up: Move player up
- Arrow Down: Move player down
- Arrow Left: Move player left
- Arrow Right: Move player right

Note: Keyboard controls are temporary and will be replaced by the command parser system.

## Project Team

**Developers:**
- Mohamed Amine El Bacha - UM6P Cyber Security
- Younes Menfalouti - UM6P Cyber Security
