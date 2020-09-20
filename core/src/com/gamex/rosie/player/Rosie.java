package com.gamex.rosie.player;

import com.gamex.rosie.common.IWorldBody;
import com.gamex.rosie.common.WorldConstants;

import java.util.HashMap;

// Playable (main) character
public class Rosie {
    private final IWorldBody body;
    private final HashMap<Integer, Memory> memories;

    public Rosie(IWorldBody body, HashMap<Integer, Memory> memories) {

        this.body = body;
        this.memories = memories;
    }

    public void attemptMovement(WorldConstants._2dDirection direction) {


    }
}