package com.gamex.rosie.common;

import java.util.ArrayList;
import java.util.List;

public class TransformationResult {

    private final boolean successful;
    private final List<Transformation> appliedTransformations;

    public TransformationResult(boolean successful, List<Transformation> appliedTransformations) {

        this.successful = successful;
        this.appliedTransformations = new ArrayList<>();
    }

    public boolean wasSuccessful() {

        return successful;
    }

    public List<Transformation> getAppliedTransformations() {

        return appliedTransformations;
    }
}
