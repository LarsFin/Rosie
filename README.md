# Rosie

A 2D block pushing game with a mechanic to add complexity and a new approach to the genre. While the game is two dimensional, it is based within a three dimensional field; bearing the x, y & z coordinates. The added mechanic is the ability to revert an action which the player had made. This action only occurs from blocks pushed by the player. The design of the first level has been laid out below.

![1st level design](img/level_design_00.png)

In the level above, you can push the block forward, then sitting in either of the sides you can retract your move. This will pull the block back allowing you to push it into the other slot. This gives you access to the goal which is marked as the red arrow on the map. This mechanic in turn with new gimmicks should offer an easy mobile game experience or one which players can do on their computer too.

## Architecture

### Event Cycle

The game will follow a strict event cycle which **will not** be broken. This is to ensure simplicity in design and development. The event cycle is as follows;

1. **Player Input** - an event cannot be played without the input of the player. Should the player not input an action; the game will continue to render but no logic is pressed.
2. **Player Action** - Based on the input given; the player character will react accordingly. An example of this could be the player moving in a given direction.
3. **Level Reaction** - This step can be missed if the player character did not interact with a level element that wasn't themselves. An example of a level action would be a block being pushed.
4. **Physics Update** - Rosie holds a very simple physics environment. This would extend to the conerns of heavy gravity and weight comparisons when a binary force is applied. This physics update would be applied to all elements within the level; including the player's character which may have been pushed off a ledge.
5. **Status Check** - Once the physics update has been performed; the game checks any logical flags. This could include; the player having died via being crushed by a block. The player having completed a level by reaching the goal tile.

## Design

### Gimmicks

The mechanism of this game is straightforward, combining it with gimmicks in levels or elements wtihin them; a more interesting environment can be created. Below, some gimmicks which have been pre-thought are listed.

#### Standard Block

While being the ordinary of the game, it still has some features which are worth noting in this series.
- Can be pushed by the player's character.
- Two standard blocks cannot be pushed in their connected direction.
- When a standard block's push has been reverted; it will push the character.
- A standard block being reverted against the player's character into a wall will kill them.
- A standard block being reverted against another standard block will push it but not when towards a wall.

#### Paper Block

A light version of the standard block.
- Can be pushed by teh player's character.
- Any number of paper blocks can be pushed together.
- A reverted paper block will not push another paper block, standard block or the player's character.
- Will be crushed when a standard block reverts against it into a wall.