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

    public boolean getActionControl() {

        return inputApi.isKeyJustPressed(keyMappings.get(Inputs.ACTION));
    }

    public int getCameraControl() {

        int result = 0;

        if (inputApi.isKeyPressed(keyMappings.get(Inputs.TURN_CAMERA_RIGHT))) {

            result++;
        }

        if (inputApi.isKeyPressed(keyMappings.get(Inputs.TURN_CAMERA_LEFT))) {

            result--;
        }

        return result;
    }

    public int getHorizontalControl() {

        int result = 0;

        if (inputApi.isKeyPressed(keyMappings.get(Inputs.RIGHT))) {

            result++;
        }

        if (inputApi.isKeyPressed(keyMappings.get(Inputs.LEFT))) {

            result--;
        }

        return result;
    }

    public int getVerticalControl() {

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

        keyMappings.put(Inputs.TURN_CAMERA_RIGHT, Input.Keys.E);
        keyMappings.put(Inputs.TURN_CAMERA_LEFT, Input.Keys.Q);

        keyMappings.put(Inputs.ACTION, Input.Keys.SPACE);
    }
}