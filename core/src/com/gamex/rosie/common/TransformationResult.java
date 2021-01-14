package com.gamex.rosie.common;

import java.util.ArrayList;
import java.util.List;

public class TransformationResult {

    private final boolean successful;
    private final List<Transformation> attemptedTransformations;

    public TransformationResult(boolean successful, List<Transformation> attemptedTransformations) {

        this.successful = successful;
        this.attemptedTransformations = new ArrayList<>();
    }

    public boolean wasSuccessful() {

        return successful;
    }

    public List<Transformation> getAttemptedTransformations() {

        return attemptedTransformations;
    }
}
