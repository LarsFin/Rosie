package com.gamex.rosie.controllers;

public class GameController implements IGameController {

    private final ICameraController cameraController;
    private final IInputController inputController;
    private final IPhysicsController physicsController;
    private final IPlayerController playerController;
    private final ITransformController transformController;

    public GameController(ICameraController cameraController,
                          IInputController inputController,
                          IPhysicsController physicsController,
                          IPlayerController playerController,
                          ITransformController transformController) {

        this.cameraController = cameraController;
        this.inputController = inputController;
        this.physicsController = physicsController;
        this.playerController = playerController;
        this.transformController = transformController;
    }

    public void update() {

        // Player Movement
        // == Apply Movement transform
        // == Store into Memory Channel
        // == Update Physics
    }
}