# Counter-Strike-With-Java

## Project Description:
🎮 In this game, the objective for each player is to eliminate other players in a rectangular playground of adjustable size (n*m). The playground contains randomly placed obstacles that change with each round of the game. In the offline mode, three computer-controlled players act as enemies, spawning in random locations on the ground.
## Players and Weapons:
👥 Each player (including enemy players) starts the game or respawns with three lives. They have a gun that requires a one-second cooldown after each shot before firing again. The gun has infinite arrows with a range that can hit a player, causing 1 life to be deducted. Arrows move instantaneously and shoot in a straight path in the direction the player is facing. Arrows stop when they hit the first obstacle or player in their path.
## Game Mechanics:
🎯 Players can fire arrows by pressing the fire button in their current facing direction. There is a one-second delay between shots to prevent rapid firing. Players cannot enter houses occupied by obstacles, and arrows do not pass through obstacles.
## Enemy Players:
🤖 In the single-player mode, enemy players take an action every second. They shoot if another player is in their line of fire. Otherwise, they move one house in a random direction.
## Extra Life:
💖 Every 20 seconds, an extra life randomly appears on the playing field where no obstacles are present. If a player or robot moves to the square with an extra life, they gain a life. A player can have a maximum of 4 lives. If the player already has 4 lives, the extra life is still taken but doesn't increase their total lives.
## Death and Respawn:
☠️ If a player or robot runs out of lives, they die. Dead players cannot move, shoot, or perform any actions until the start of a new game round. After a 5-second delay, the dead player respawns in a random location on the screen with three lives, and their previous corpse disappears.
## End of the Game:
🏁 In the offline mode, if the player fails to eliminate all the monsters, the game ends, displaying the game duration to the player. After a 5-second delay, the game returns to the main menu. The score of each game round doesn't need to be recorded.
## Online Gameplay:
🌐 In the online mode, two players (up to 8 players can be implemented positively) can engage in combat in the same field as the offline mode. The online gameplay can be implemented with a frame rate of 20 frames per second. Death in the online mode differs from the offline mode, and the game doesn't end. After dying, each player respawns after 5 seconds at a random location on the screen with three lives, and their previous corpse vanishes.
## Program Environment:
🖥️ Upon launching the program, the player is provided with options for offline, online client, and online server. Choosing the offline option directly enters the game. Selecting the online server option waits for the connection of the second player. If the player chooses the online option, it will connect with the player who selected the server. If no server is available, an error message stating "No server" is displayed. After connecting, both players enter the game and can perform all actions from the offline section.
## Visual and Interface Features:
👀 The remaining lives of all players are displayed during the game. The direction each player is facing is visible. In the offline section, the player is visually distinguishable from enemy players, and in the online section, each player is distinguishable from others. A border surrounds the ground, fully filling the outer area and making it inaccessible.






