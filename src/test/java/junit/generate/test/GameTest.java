// AUTO_GENERATED BY TestGenerationTool 
// Test class for Game Class
package junit.generate.test;

import static org.junit.Assert.*;

import CSseniorProject.Game;
import java.lang.Object;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class GameTest {
  private final int int0;

  private final int int1;

  private final Object ObjectExpectedForframeEnded;

  private final int intExpectedForgetFrame;

  private final int intExpectedForgetScore;

  private final Object ObjectExpectedForisOver;

  public Game game;

  public GameTest(int int0, int int1, Object ObjectExpectedForframeEnded,
      int intExpectedForgetFrame, int intExpectedForgetScore, Object ObjectExpectedForisOver) {
    this.int0 = int0;
        this.int1 = int1;
        this.ObjectExpectedForframeEnded = ObjectExpectedForframeEnded;
        this.intExpectedForgetFrame = intExpectedForgetFrame;
        this.intExpectedForgetScore = intExpectedForgetScore;
        this.ObjectExpectedForisOver = ObjectExpectedForisOver;
        ;
  }

  @Before
  public void initialize() {
    game = new Game();
  }

  @Parameterized.Parameters
  public static List dataForParameterized() {
    return Arrays.asList( new Object[][] {{26867, 26867, true, 0, 0, false} , {96282, 96282, true, 0, 0, false} , {58198, 58198, true, 0, 0, false} , {71283, 71283, true, 0, 0, false} , {33624, 33624, true, 0, 0, false} , {79874, 79874, true, 0, 0, false} , {12894, 12894, true, 0, 0, false} , {99251, 99251, true, 0, 0, false} , {36519, 36519, true, 0, 0, false} , {35628, 35628, true, 0, 0, false} , {28957, 28957, true, 0, 0, false} , {0, 0, true, 0, 0, false} , {68042, 68042, true, 0, 0, false} , {48446, 48446, true, 0, 0, false} , {83548, 83548, true, 0, 0, false} , {6150, 6150, true, 0, 0, false} , {1, 1, true, 0, 0, false} , {37919, 37919, true, 0, 0, false} , {23134, 23134, true, 0, 0, false} , {78339, 78339, true, 0, 0, false} , {98975, 98975, true, 0, 0, false} , {68638, 68638, true, 0, 0, false} , {55883, 55883, true, 0, 0, false} , {283, 283, true, 0, 0, false} , {11853, 11853, true, 0, 0, false} , {27270, 27270, true, 0, 0, false} , {8218, 8218, true, 0, 0, false} , {4154, 4154, true, 0, 0, false} , {24114, 24114, true, 0, 0, false} , {23812, 23812, true, 0, 0, false} , {76024, 76024, true, 0, 0, false} , {80901, 80901, true, 0, 0, false} , {3739, 3739, true, 0, 0, false} , {9637, 9637, true, 0, 0, false} , {89425, 89425, true, 0, 0, false} , {35128, 35128, true, 0, 0, false} , {32678, 32678, true, 0, 0, false} , {78979, 78979, true, 0, 0, false} , {3971, 3971, true, 0, 0, false} , {61768, 61768, true, 0, 0, false} , {3102, 3102, true, 0, 0, false} , {54323, 54323, true, 0, 0, false} , {75464, 75464, true, 0, 0, false} , {31838, 31838, true, 0, 0, false} , {64649, 64649, true, 0, 0, false} , {38009, 38009, true, 0, 0, false} , {68874, 68874, true, 0, 0, false} , {10271, 10271, true, 0, 0, false} , {876, 876, true, 0, 0, false} , {53412, 53412, true, 0, 0, false}});
  }

  @Test
  public final void testFrameEnded() {
    assertEquals(ObjectExpectedForframeEnded, game.frameEnded(int0, int1));
  }

  @Test
  public final void testGetFrame() {
    assertEquals(intExpectedForgetFrame, game.getFrame());
  }

  @Test
  public final void testGetScore() {
    assertEquals(intExpectedForgetScore, game.getScore());
  }

  @Test
  public final void testİsOver() {
    assertEquals(ObjectExpectedForisOver, game.isOver());
  }
}
