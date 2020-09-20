package com.gamex.rosie.player;

import com.gamex.rosie.common.IWorldBody;
import org.junit.jupiter.api.*;

import java.util.HashMap;

import static org.mockito.Mockito.mock;

public class RosieTests {

    private Rosie subject;

    private IWorldBody mockBody;
    private HashMap<Integer, Memory> memories;

    @BeforeEach
    public void beforeEach() {

        mockBody = mock(IWorldBody.class);
        memories = new HashMap<>();

        subject = new Rosie(mockBody, memories);
    }

    @Nested
    @DisplayName("Tests for moving rosie in two dimensional directions")
    public class MoveTests {

        
    }
}
