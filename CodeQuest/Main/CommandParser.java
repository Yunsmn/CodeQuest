package CodeQuest.Main;

import java.util.*;
import java.util.regex.*;

/**
 * CommandParser - Parses Python-style commands and delegates to CommandAdapter
 * Maintains the Adapter design pattern while adding full Python syntax support
 */
public class CommandParser {
    public GamePanel gamePanel;
    public CommandAdapter adapter;
    private Map<String, Integer> variables;
    
    public CommandParser(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.adapter = new CommandAdapter(gamePanel);
        this.variables = new HashMap<>();
    }
    
    /**
     * Main parsing method - handles multiple command types
     */
    public boolean parseCommand(String command) {
        try {
            command = command.trim();
            if (command.isEmpty()) return true;
            
            // Handle special control commands
            if (command.equals("clear") || command.equals("stop")) {
                adapter.clearQueue();
                return true;
            }
            
            if (command.equals("help")) {
                showHelp();
                return true;
            }
            
            // Handle Python for loops
            if (command.startsWith("for ")) {
                return parseForLoop(command);
            }
            
            // Handle Python if statements
            if (command.startsWith("if ")) {
                return parseIfStatement(command);
            }
            
            // Handle multiple commands separated by newlines or semicolons
            if (command.contains("\n") || command.contains(";")) {
                String[] lines = command.split("[;\n]");
                for (String line : lines) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        parseCommand(line); // Recursive call for each line
                    }
                }
                return true;
            }
            
            // Handle single command
            return executeSingleCommand(command);
            
        } catch (Exception e) {
            System.out.println("❌ Parse error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Alias for parseCommand - maintains compatibility
     */
    public void parse(String command) {
        parseCommand(command);
    }
    
    /**
     * Parse Python for loop: for i in range(5): player.moveup()
     */
    private boolean parseForLoop(String line) {
        // Pattern: for <var> in range(<number>): <command>
        Pattern pattern = Pattern.compile("for\\s+(\\w+)\\s+in\\s+range\\s*\\(\\s*(\\d+)\\s*\\)\\s*:\\s*(.+)");
        Matcher matcher = pattern.matcher(line);
        
        if (matcher.matches()) {
            String varName = matcher.group(1);
            int count = Integer.parseInt(matcher.group(2));
            String body = matcher.group(3).trim();
            
            System.out.println("✓ Python for loop: executing '" + body + "' " + count + " times");
            
            for (int i = 0; i < count; i++) {
                variables.put(varName, i);
                executeSingleCommand(body);
            }
            return true;
        }
        
        System.out.println("❌ Invalid for loop syntax. Use: for i in range(5): player.moveup()");
        return false;
    }
    
    /**
     * Parse Python if statement: if x < 200: player.moveright()
     */
    private boolean parseIfStatement(String line) {
        // Pattern: if <condition>: <command>
        Pattern pattern = Pattern.compile("if\\s+(.+?)\\s*:\\s*(.+)");
        Matcher matcher = pattern.matcher(line);
        
        if (matcher.matches()) {
            String condition = matcher.group(1).trim();
            String command = matcher.group(2).trim();
            
            if (evaluateCondition(condition)) {
                System.out.println("✓ Condition '" + condition + "' is TRUE, executing: " + command);
                executeSingleCommand(command);
            } else {
                System.out.println("✓ Condition '" + condition + "' is FALSE, skipping command");
            }
            return true;
        }
        
        System.out.println("❌ Invalid if syntax. Use: if x < 200: player.moveright()");
        return false;
    }
    
    /**
     * Evaluate conditions for if statements
     */
    private boolean evaluateCondition(String condition) {
        Pattern pattern = Pattern.compile("(\\w+)\\s*([<>!=]+)\\s*(\\d+)");
        Matcher matcher = pattern.matcher(condition);
        
        if (matcher.matches()) {
            String varName = matcher.group(1);
            String operator = matcher.group(2);
            int value = Integer.parseInt(matcher.group(3));
            
            int varValue = getVariableValue(varName);
            
            switch (operator) {
                case "<": return varValue < value;
                case ">": return varValue > value;
                case "==": return varValue == value;
                case "!=": return varValue != value;
                case "<=": return varValue <= value;
                case ">=": return varValue >= value;
            }
        }
        return false;
    }
    
    /**
     * Get variable value (from variables map or player properties)
     */
    private int getVariableValue(String varName) {
        if (variables.containsKey(varName)) {
            return variables.get(varName);
        }
        
        switch (varName) {
            case "x": return gamePanel.player.worldX;
            case "y": return gamePanel.player.worldY;
            case "health": return 100; // Default, add to Player if needed
            default: return 0;
        }
    }
    
    /**
     * Execute a single command
     */
    private boolean executeSingleCommand(String cmd) {
        cmd = cmd.trim();
        
        // Handle print statements
        if (cmd.startsWith("print ")) {
            String message = cmd.substring(6).trim();
            message = message.replaceAll("^['\"]|['\"]$", ""); // Remove quotes
            adapter.executePrint(message);
            return true;
        }
        
        // Handle wait statements
        if (cmd.startsWith("wait")) {
            if (cmd.equals("wait")) {
                adapter.executeWait(500);
            } else {
                try {
                    String[] parts = cmd.split("\\s+");
                    if (parts.length > 1) {
                        int ms = Integer.parseInt(parts[1]);
                        adapter.executeWait(ms);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("❌ Invalid wait time");
                }
            }
            return true;
        }
        
        // Handle turn statements: turn left, turn right, etc.
        if (cmd.startsWith("turn ")) {
            String direction = cmd.substring(5).trim();
            adapter.executeTurn(direction);
            return true;
        }
        
        // Handle walk statements: walk 5 up
        if (cmd.startsWith("walk ")) {
            String[] parts = cmd.split("\\s+");
            if (parts.length == 3) {
                try {
                    int steps = Integer.parseInt(parts[1]);
                    String direction = parts[2];
                    for (int i = 0; i < steps; i++) {
                        executeMovement(direction);
                    }
                    System.out.println("✓ Walking " + steps + " steps " + direction);
                    return true;
                } catch (NumberFormatException e) {
                    System.out.println("❌ Invalid walk syntax. Use: walk 5 up");
                }
            }
        }
        
        // Handle movement commands
        if (cmd.startsWith("move ")) {
            String direction = cmd.substring(5).trim();
            return executeMovement(direction);
        }
        
        // Handle player.method() style commands
        switch (cmd) {
            case "player.moveup()":
                adapter.executeMoveUp();
                return true;
            case "player.movedown()":
                adapter.executeMoveDown();
                return true;
            case "player.moveleft()":
                adapter.executeMoveLeft();
                return true;
            case "player.moveright()":
                adapter.executeMoveRight();
                return true;
            default:
                System.out.println("❌ Unknown command: " + cmd);
                return false;
        }
    }
    
    /**
     * Execute movement in a direction
     */
    private boolean executeMovement(String direction) {
        direction = direction.toLowerCase().replaceAll("['\"]", "");
        
        switch (direction) {
            case "up":
            case "north":
            case "w":
                adapter.executeMoveUp();
                return true;
            case "down":
            case "south":
            case "s":
                adapter.executeMoveDown();
                return true;
            case "left":
            case "west":
            case "a":
                adapter.executeMoveLeft();
                return true;
            case "right":
            case "east":
            case "d":
                adapter.executeMoveRight();
                return true;
            default:
                System.out.println("❌ Invalid direction: " + direction);
                return false;
        }
    }
    
    /**
     * Display help information
     */
    public void showHelp() {
        System.out.println("\n╔════════════════ PYTHON COMMANDS ════════════════╗");
        System.out.println("│                                                 │");
        System.out.println("│ MOVEMENT (Python style):                        │");
        System.out.println("│   move up / down / left / right                 │");
        System.out.println("│   walk 5 up                                     │");
        System.out.println("│   turn left                                     │");
        System.out.println("│                                                 │");
        System.out.println("│ MOVEMENT (Method style):                        │");
        System.out.println("│   player.moveup()                               │");
        System.out.println("│   player.movedown()                             │");
        System.out.println("│   player.moveleft()                             │");
        System.out.println("│   player.moveright()                            │");
        System.out.println("│                                                 │");
        System.out.println("│ PYTHON FOR LOOPS:                               │");
        System.out.println("│   for i in range(5): player.moveup()            │");
        System.out.println("│   for i in range(3): move right                 │");
        System.out.println("│                                                 │");
        System.out.println("│ PYTHON IF STATEMENTS:                           │");
        System.out.println("│   if x < 200: player.moveright()                │");
        System.out.println("│   if y > 100: move up                           │");
        System.out.println("│                                                 │");
        System.out.println("│ UTILITIES:                                      │");
        System.out.println("│   print \"message\"                               │");
        System.out.println("│   wait                                          │");
        System.out.println("│   wait 1000                                     │");
        System.out.println("│                                                 │");
        System.out.println("│ CONTROL:                                        │");
        System.out.println("│   clear / stop                                  │");
        System.out.println("│                                                 │");
        System.out.println("╠═════════════════════════════════════════════════╣");
        System.out.println("│ EXAMPLES:                                       │");
        System.out.println("│                                                 │");
        System.out.println("│ # Square pattern                                │");
        System.out.println("│ for i in range(4): walk 5 up; turn right        │");
        System.out.println("│                                                 │");
        System.out.println("│ # Conditional movement                          │");
        System.out.println("│ if x < 300: player.moveright()                  │");
        System.out.println("│                                                 │");
        System.out.println("│ # Multiple commands                             │");
        System.out.println("│ move up; move right; move down                  │");
        System.out.println("│                                                 │");
        System.out.println("╚═════════════════════════════════════════════════╝\n");
    }
    
    /**
     * Get queue size - delegates to adapter
     */
    public int getQueueSize() {
        return adapter.getQueueSize();
    }
    
    /**
     * Check if executing - delegates to adapter
     */
    public boolean isExecuting() {
        return adapter.isExecuting();
    }
    
    /**
     * Set command delay - delegates to adapter
     */
    public void setCommandDelay(int delayMs) {
        adapter.setActionDelay(delayMs);
    }
}
