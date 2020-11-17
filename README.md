# Rosie

A 2D block pushing game with a mechanic to add complexity and a new approach to the genre. While the game is two dimensional, it is based within a three dimensional field; bearing the x, y & z coordinates. The added mechanic is the ability to revert an action which the player had made. This action only occurs from blocks pushed by the player. The design of the first level has been laid out below.

<div align="center">

![1st level design](img/examples/first_level/00.jpg)

*Figure a.1: First Level Design*

</div>

In the level above, you can push the block forward, then sitting in either of the sides you can retract your move. This will pull the block back allowing you to push it into the other slot. This gives you access to the goal which is marked as the red arrow on the map. This mechanic in turn with new gimmicks should offer an easy mobile game experience or one which players can do on their computer too.

## Architecture

### Event Cycle

The game will follow a strict event cycle which **will not** be broken. This is to ensure simplicity in design and development. The event cycle is as follows;

1. **Player Input** - an event cannot be played without the input of the player. Should the player not input an action; the game will continue to render but no logic is pressed.
2. **Player Action** - Based on the input given; the player character will react accordingly. An example of this could be the player moving in a given direction.
3. **Level Reaction** - This step can be missed if the player character did not interact with a level element that wasn't themselves. An example of a level action would be a block being pushed.
4. **Physics Update** - Rosie holds a very simple physics environment. This would extend to the conerns of heavy gravity and weight comparisons when a binary force is applied. This physics update would be applied to all elements within the level; including the player's character which may have been pushed off a ledge.
5. **Status Check** - Once the physics update has been performed; the game checks any logical flags. This could include; the player having died via being crushed by a block. The player having completed a level by reaching the goal tile.

### Block Element

A block's size should be varied, allowing for greater flexibility in design. For this reason; a block cannot have its location pointed to one coordinate on the map. Instead, each space of the 3D map which is taken by the block should be considered as its coordinates. When a block moves, each of its coordinates should be updated as well as asserted to ensure it can be updated. Look at the example diagram below;

<div align="center">

![Block Layout ex1](img/examples/block_sample_00.jpg)

*Figure b.1: Initial Map Position*

</div>

In this space; two axes have been labelled, X and Y. For the sake of simplicity we'll be ignoring the third dimension for the time being. Our Standard Block which is coloured orange occupies two coordinates on the map `2,2` & `3,2`. A wall also exists within our example, on the tile `3,4`.

Let's define our cardinal directions as the following;
- **North:** `0,1`
- **East:** `1,0`
- **South:** `0,-1`
- **West:** `-1,0`

If we apply a north directed force to our Standard Block it should assert whether each of its occupied coordinates can perform the transform successfully.

```
pA[ 2, 2 ] + d[ 0, 1 ] = p2[ 2, 3 ]
Check whether p2 is valid
pB[ 3, 2 ] + d[ 0, 1 ] = p2[ 3, 3 ]
Again, check whether p2 is valid
```

Looking at the map we know the `p2` positions to be transformed to are valid as they are empty and within the bounds of the map. Therefore, the Standard Block can move north within our map. Now, we have the map below;

<div align="center">

![Block Layout ex1](img/examples/block_sample_01.jpg)

*Figure b.2: New Map Position*

</div>

## Design

### Gimmicks

The mechanism of this game is straightforward, combining it with gimmicks in levels or elements wtihin them; a more interesting environment can be created. Below, some gimmicks which have been pre-thought are listed.

#### **Standard Block**

While being the ordinary of the game, it still has some features which are worth noting in this series.

1. Can be pushed by the player's character.

<div align="center">

![Standard Block Movement](img/examples/standard_block/00.jpg)

*Figure c.1: Standard Block Movement*

</div>

2. Two standard blocks cannot be pushed in their connected direction.

<div align="center">

![Standard Block Invalid Movement Another Block](img/examples/standard_block/02.jpg)

*Figure c.2: Standard Block Invalid Connected Block Movement*

</div>

3. When a standard block's push has been reverted; it will push the character.

<div align="center">

![Standard Block Reverted Against Player](img/examples/standard_block/03.jpg)

*Figure c.3: Standard Block Reverted Against Player*

</div>

4. A standard block being reverted against the player's character into a wall will kill them.

<div align="center">

![Standard Block Reverted To Kill Player](img/examples/standard_block/04.jpg)

*Figure c.4: Standard Block Reverted Onto Player Against Wall*

</div>

5. A standard block being reverted against another standard block will push it but not when towards a wall.

<div align="center">

![Standard Block Reverted Against Standard Block](img/examples/standard_block/05.jpg)

*Figure c.5: Standard Block Reverted Against Another Standard Block*

![Standard Block Invalid Reversion Into Wall](img/examples/standard_block/06.jpg)

*Figure c.6: Standard Block Invalid Inversion Against Wall*

</div>

6. If a Standard Block is to fall on the player's character, it will kill them.

<div align="center">

![Standard Block Falling Onto Player](img/examples/standard_block/07.jpg)

*Figure c.7: Standard Block Falling Onto Player*

</div>

7. A Standard Block cannot be pushed into a wall

<div align="center">

![Standard Block Invalid Movement Into Wall](img/examples/standard_block/01.jpg)

*Figure c.8: Standard Block Invalid Wall Movement*

</div>

#### **Paper Block**

A light version of the standard block.

1. Can be pushed by the player's character.

<div align="center">

![Paper Block Pushed](img/examples/paper_block/00.jpg)

*Figure d.1: Paper Block pushed by Player's Character*

</div>

2. Any number of paper blocks can be pushed together.

<div align="center">

![Many Paper Blocks Pushed](img/examples/paper_block/01.jpg)

*Figure d.2: Many Paper Blocks pushed by Player's Character*

</div>

3. A reverted paper block will push one or more other paper blocks but not a standard block or the player's character.

<div align="center">

![Multiple Paper Blocks Reverted](img/examples/paper_block/02.jpg)

*Figure d.3.i: Paper Block reverting onto another Paper Block*

</div>

<div align="center">

![Paper Block Invalid Reversion on Standard Block](img/examples/paper_block/03.jpg)

*Figure d.3.ii: Paper Block Invalid Reversion onto Standard Block*

</div>

<div align="center">

![Paper Block Invalid Reversion on Player's Character](img/examples/paper_block/04.jpg)

*Figure d.3.iii: Paper Block Invalid Reversion onto Player's Character*

</div>

4. Will be crushed when a standard block is pushed against it into a wall.

<div align="center">

![Paper Block Crushed by Pushed Standard Block](img/examples/paper_block/05.jpg)

*Figure d.4: Paper Block crushed by pushed Standard Block*

</div>

5. Will be crushed when a standard block reverts against it into a wall.

<div align="center">

![Paper Block Crushed by Reverted Standard Block](img/examples/paper_block/06.jpg)

*Figure d.5: Paper Block crushed by reverted Standard Block*

</div>

6. If a Paper Block is to fall on the player's character, it will remain above them until the character's player moves away and a 'physics update' is performed.

<div align="center">

![Paper Block held by Player's Character](img/examples/paper_block/07.jpg)

*Figure d.6: Paper Block above Player's Character and falling once Player's Character has Moved*

</div>

#### **Magnet Block**

Acts identically to the Standard Block but has extended behaviour.

1. When a Magnet Block comes in contact with another Magnet Block; it joins to form one element within the world.

<div align="center">

![Magnetic Block Connecting](img/examples/magnetic_block/00.jpg)

*Figure e.1: Magnetic Block Connects when pushed into Contact*

</div>

2. When magnetizing, memory which related to each separate entity is overlapped.

<div align="center">

![Magnetic Blocks](img/examples/magnetic_block/01.jpg)

*Figure e.2.i: Magnetic Blocks*

![First Magnetic Block pushed](img/examples/magnetic_block/02.jpg)

*Figure e.2.ii: First Magnetic Block pushed*

![Second Magnetic Block pushed](img/examples/magnetic_block/03.jpg)

*Figure e.2.iii: Second Magnetic Block pushed and Connects with First*

![Connected Magnetic Block Reverts Second](img/examples/magnetic_block/04.jpg)

*Figure e.2.iv: Connected Magnetic Block reverts Second push*

![Connected Magnetic Block Reverts First](img/examples/magnetic_block/05.jpg)

*Figure e.2.v: Connected Magnetic Block reverts First push afterwards*

</div>

3. Even when magnetized to form a larger entity, it still will act the same as the standard block in core behaviour.

<div align="center">

![Connected Magnetic Block Invalid Motion](img/examples/magnetic_block/06.jpg)

*Figure e.3: Connected Magnetic Block cannot move due to connected portion*

</div>

#### **Goo Block**

A unique block which shares some similarish behaviours with the Magnet and Paper Block.

1. Can be pushed by the player's character.

<div align="center">

![Goo Block Pushed](img/examples/goo_block/00.jpg)

*Figure f.1: Goo Block pushed by Player's Character*

</div>

2. Two Magnetic Blocks combine after making contact. Two Goo Blocks must be pushed towards contact to form a larger Block (the resulting larger Goo Block will not move in this Action).

<div align="center">

![Goo Block Not Combining on Contact](img/examples/goo_block/01.jpg)

*Figure f.2.i: Goo Block not combining on Contact*

![Goo Block Combining on Push](img/examples/goo_block/02.jpg)

*Figure f.2.ii: Goo Block combining on Push into Contact*

</div>

3. A large goo block will adjust its shape when only a portion of it can perform a transformation due to a map edge, wall or Standard Block (see images for clarity). However, the mutation is only made depending on where the Player's Character pushes from.

<div align="center">

![Large Goo Block Scenario](img/examples/goo_block/03.jpg)

*Figure f.3.i: Goo Block which has been combined*

![Large Goo Block not Updated when Pushed at Wall Side](img/examples/goo_block/04.jpg)

*Figure f.3.ii: Combined Goo Block not mutating when pushed on side of Wall*

![Large Goo Block Updated when Pushed at Free Side](img/examples/goo_block/05.jpg)

*Figure f.3.iii: Combined Goo Block mutates shape when pushed on opposite side of Wall*

</div>

4. If a Goo Block falls on the player's character, they won't be hurt, but instead; will emerge from the top of the Goo Block.

<div align="center">

![Goo Block Reverted above Player's Character](img/examples/goo_block/06.jpg)

*Figure f.4.i: Goo Block reverted above Player's Character*

![Goo Block Falling onto Player's Character](img/examples/goo_block/07.jpg)

*Figure f.4.ii: Goo Block falling onto Player's Character*

![Player's Character emerging from Fallen Goo Block](img/examples/goo_block/08.jpg)

*Figure f.4.iii: Player's Character emerges from top of fallen Goo Block*

</div>

5. A Goo Block being reverted against the player's character will cause the block to engulf the player's character and emit them at the top.

<div align="center">

![Goo Block Reverted onto Player's Character](img/examples/goo_block/09.jpg)

*Figure f.5.i: Goo Block reverted onto Player's Character*

![Player's Character emerging from Fallen Goo Block](img/examples/goo_block/10.jpg)

*Figure f.5.ii: Players' Character emerges from top of fallen Goo Block*

</div>

6. Goo Blocks can be combined vertically by them falling at least one block's height onto another Goo Block. Alternatively, if two Goo Blocks are stacked, the Player's Character can walk atop them to force their combination.

<div align="center">

![Goo Block Pushed into Stack](img/examples/goo_block/11.jpg)

*Figure f.6.i: Goo Block pushed onto Stack does not Combine*

![Goo Block Falling onto Stack](img/examples/goo_block/12.jpg)

*Figure f.6.ii: Goo Block falling onto Stack Combines*

![Goo Block Stepped into Stack](img/examples/goo_block/13.jpg)

*Figure f.6.iii: Walking atop Goo Block stack results in Combination*

</div>

7. Emerging works on a set of priorities. If the space above the Player's Character is occupied when a Goo Block is reverted over them; another direction to emit the Player's Character must be decided. The priorities are **up**, **backward**, **forward**, **left**, **right**. Where directions are from the perspective of the Player's Character towards the reverted Goo Block.

<div align="center">

![Player's Character Forced Backward](img/examples/goo_block/14.jpg)

*Figure f.7.i: Player's Character forced Backward*

![Player's Character Forced Forward on Large Block](img/examples/goo_block/15.jpg)

*Figure f.7.ii: Player's Character forced Forward on longer Goo Block*

</div>

8. When a Goo Block is squashed with a Standard Block, it squeezes to the surface of the Standard Block. This is similar to how the Player's Character emerges from a Goo Block which has engulfed them. However, it is possible there is no direction the Goo Block could emerge, in this case; it is destroyed.

<div align="center">

![Goo Block Squashed to Surface](img/examples/goo_block/16.jpg)

*Figure f.8.i: Crushed Goo Block is pushed to Surface*

![Goo Block Forced Right when Squashed](img/examples/goo_block/17.jpg)

*Figure f.8.ii: Crushed Goo Block is forced Right*

![Goo Block Destroyed when Squashed with no Available Option](img/examples/goo_block/18.jpg)

*Figure f.8.iii: Crushed Goo Block destroyed when no available Options exist*

</div>

#### **Temple Block (Better Name TBD)**

Very different in behaviour to Standard Blocks.

1. Cannot be pushed by the player's character.

2. Gravity is not applied to these blocks and they can remain in the air.

3. When the player presses the action button while facing a Temple Block, the character is locked but has the ability to move the block in a 3-dimensional direction. That being UP, DOWN, LEFT or RIGHT. Left and Right being from the perspective of the player's character.

4. In terms of pushing, these blocks act the same as Standard Blocks. Meaning, they can't be pushed towards another Temple Block they are against or towards a wall.