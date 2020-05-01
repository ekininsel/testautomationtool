// AUTO_GENERATED BY TestGenerationTool 
// Test class for GoldGame Class
package junit.generate.test;

import static org.junit.Assert.*;

import CSseniorProject.GoldGame;
import java.lang.String;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class GoldGameTest {
  private final int int0;

  private final int int1;

  private final int int2;

  private final int int3;

  private final String StringExpectedForGameStarter;

  private final int intExpectedForGetInt;

  public GoldGame goldgame;

  public GoldGameTest(int int0, int int1, int int2, int int3, String StringExpectedForGameStarter,
      int intExpectedForGetInt) {
    this.int0 = int0;
        this.int1 = int1;
        this.int2 = int2;
        this.int3 = int3;
        this.StringExpectedForGameStarter = StringExpectedForGameStarter;
        this.intExpectedForGetInt = intExpectedForGetInt;
        ;
  }

  @Before
  public void initialize() {
    goldgame = new GoldGame();
  }

  @Parameterized.Parameters
  public static List dataForParameterized() {
    return Arrays.asList( new Object[][] {{26867, 26867, 26867, 26867, "100 altın kazandın.", 2} , {96282, 96282, 96282, 96282, "100 altın kazandın.", 0} , {58198, 58198, 58198, 58198, "100 altın kazandın.", 1} , {71283, 71283, 71283, 71283, "100 altın kazandın.", 0} , {33624, 33624, 33624, 33624, "100 altın kazandın.", 0} , {79874, 79874, 79874, 79874, "100 altın kazandın.", 2} , {12894, 12894, 12894, 12894, "100 altın kazandın.", 0} , {99251, 99251, 99251, 99251, "100 altın kazandın.", 2} , {36519, 36519, 36519, 36519, "100 altın kazandın.", 0} , {35628, 35628, 35628, 35628, "100 altın kazandın.", 0} , {28957, 28957, 28957, 28957, "100 altın kazandın.", 1} , {0, 0, 0, 0, "100 den büyük bir sayı girmelisin.", 0} , {68042, 68042, 68042, 68042, "100 altın kazandın.", 2} , {48446, 48446, 48446, 48446, "100 altın kazandın.", 2} , {83548, 83548, 83548, 83548, "100 altın kazandın.", 1} , {6150, 6150, 6150, 6150, "100 altın kazandın.", 0} , {1, 1, 1, 1, "100 den büyük bir sayı girmelisin.", 2} , {37919, 37919, 37919, 37919, "100 altın kazandın.", 2} , {23134, 23134, 23134, 23134, "100 altın kazandın.", 1} , {78339, 78339, 78339, 78339, "100 altın kazandın.", 0} , {98975, 98975, 98975, 98975, "100 altın kazandın.", 2} , {68638, 68638, 68638, 68638, "100 altın kazandın.", 1} , {55883, 55883, 55883, 55883, "100 altın kazandın.", 2} , {283, 283, 283, 283, "100 altın kazandın.", 1} , {11853, 11853, 11853, 11853, "100 altın kazandın.", 0} , {27270, 27270, 27270, 27270, "100 altın kazandın.", 0} , {8218, 8218, 8218, 8218, "100 altın kazandın.", 1} , {4154, 4154, 4154, 4154, "100 altın kazandın.", 2} , {24114, 24114, 24114, 24114, "100 altın kazandın.", 0} , {23812, 23812, 23812, 23812, "100 altın kazandın.", 1} , {76024, 76024, 76024, 76024, "100 altın kazandın.", 1} , {80901, 80901, 80901, 80901, "100 altın kazandın.", 0} , {3739, 3739, 3739, 3739, "100 altın kazandın.", 1} , {9637, 9637, 9637, 9637, "100 altın kazandın.", 1} , {89425, 89425, 89425, 89425, "100 altın kazandın.", 1} , {35128, 35128, 35128, 35128, "100 altın kazandın.", 1} , {32678, 32678, 32678, 32678, "100 altın kazandın.", 2} , {78979, 78979, 78979, 78979, "100 altın kazandın.", 1} , {3971, 3971, 3971, 3971, "100 altın kazandın.", 2} , {61768, 61768, 61768, 61768, "100 altın kazandın.", 1} , {3102, 3102, 3102, 3102, "100 altın kazandın.", 0} , {54323, 54323, 54323, 54323, "100 altın kazandın.", 2} , {75464, 75464, 75464, 75464, "100 altın kazandın.", 2} , {31838, 31838, 31838, 31838, "100 altın kazandın.", 2} , {64649, 64649, 64649, 64649, "100 altın kazandın.", 2} , {38009, 38009, 38009, 38009, "100 altın kazandın.", 2} , {68874, 68874, 68874, 68874, "100 altın kazandın.", 0} , {10271, 10271, 10271, 10271, "100 altın kazandın.", 2} , {876, 876, 876, 876, "100 altın kazandın.", 0} , {53412, 53412, 53412, 53412, "100 altın kazandın.", 0}});
  }

  @Test
  public final void testGameStarter() {
    assertEquals(StringExpectedForGameStarter, goldgame.gameStarter(int0, int1, int2, int3));
  }

  @Test
  public final void testGetInt() {
    assertEquals(intExpectedForGetInt, goldgame.getInt(int0));
  }
}
