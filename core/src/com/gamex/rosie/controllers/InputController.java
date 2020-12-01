package com.gamex.rosie.controllers;

import com.badlogic.gdx.Input;

import java.util.HashMap;

public class InputController implements IInputController {

    private HashMap<Inputs, Integer> keyMappings;
    private final Input inputApi;

    public InputController(Input input) {

        inputApi = input;
        configureKeyMappings();
    }

    public int getHorizontal() {

        int result = 0;

        if (inputApi.isKeyPressed(keyMappings.get(Inputs.RIGHT))) {

            result++;
        }

        if (inputApi.isKeyPressed(keyMappings.get(Inputs.LEFT))) {

            result--;
        }

        return result;
    }

    public int getVertical() {

        int result = 0;

        if (inputApi.isKeyPressed(keyMappings.get(Inputs.UP))) {

            result++;
        }

        if (inputApi.isKeyPressed(keyMappings.get(Inputs.DOWN))) {

            result--;
        }

        return result;
    }

    private void configureKeyMappings() {

        keyMappings = new HashMap<>();
        keyMappings.put(Inputs.RIGHT, Input.Keys.RIGHT);
        keyMappings.put(Inputs.LEFT, Input.Keys.LEFT);
        keyMappings.put(Inputs.DOWN, Input.Keys.DOWN);
        keyMappings.put(Inputs.UP, Input.Keys.UP);
    }
}