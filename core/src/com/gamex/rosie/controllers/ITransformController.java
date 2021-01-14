package com.gamex.rosie.controllers;

import com.gamex.rosie.common.Transformation;
import com.gamex.rosie.common.TransformationResult;

public interface ITransformController {

    TransformationResult applyTransform(Transformation transformation, Transformation.Consideration[] considerations);
}