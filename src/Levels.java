/**
 * Created by alxye on 19-Jul-18.
 */
public class Levels {
        public static byte[][] structure1 = new byte[][]{ //30*100
                {1, 1, 1, 1, 2, 2, 1, 1, 1, 1},
                {1, 0, 0, 1, 2, 2, 1, 0, 0, 1},
                {1, 0, 0, 1, 2, 2, 1, 0, 0, 1},
                {1, 0, 0, 1, 2, 2, 1, 0, 0, 1},
                {1, 0, 0, 1, 2, 2, 1, 0, 0, 1},
                {1, 0, 0, 1, 2, 2, 1, 0, 0, 1},
                {1, 0, 0, 1, 2, 2, 1, 0, 0, 1},
                {1, 0, 0, 1, 2, 2, 1, 0, 0, 1},
                {1, 0, 0, 1, 2, 2, 1, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };
//    public static byte[][] level1 = new byte[100][100];
    public static Level levelOne = new Level(new byte[200][800], 0);
}