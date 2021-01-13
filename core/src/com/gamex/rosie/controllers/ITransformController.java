package com.gamex.rosie.controllers;

import com.gamex.rosie.common.Transformation;

public interface ITransformController {

    void applyTransform(Transformation transformation, Transformation.Consideration[] considerations);
}