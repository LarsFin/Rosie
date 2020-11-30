package com.gamex.rosie.controllers;

import com.badlogic.gdx.Input;

public class InputController implements IInputController {

    Input inputApi;

    public InputController(Input input) {

        inputApi = input;
    }

    public int getHorizontal() {

        int result = 0;

        if (inputApi.isKeyPressed(Input.Keys.RIGHT)) {

            result++;
        }

        if (inputApi.isKeyPressed(Input.Keys.LEFT)) {

            result--;
        }

        return result;
    }

    public int getVertical() {

        int result = 0;

        if (inputApi.isKeyPressed(Input.Keys.UP)) {

            result++;
        }

        if (inputApi.isKeyPressed(Input.Keys.DOWN)) {

            result--;
        }

        return result;
    }
}