import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Load
{
    private Block[][] blockMatrix;
    private Block[] blocks;
    private Color[][] playerMatrix;

    public Load() throws IOException
    {
        blockMatrix = getBlankMatrix();
        playerMatrix = new Color[16][8];
        blocks = new Block[10];

        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int val = jfc.showOpenDialog(null);

        if (val == JFileChooser.APPROVE_OPTION)
        {
            BufferedReader br = new BufferedReader(new FileReader(jfc.getSelectedFile()));

            int mode = 0;
            int count = 0;
            int block_count = -1;
            Color[][] config = null;

            String line;
            while ((line = br.readLine()) != null)
            {
                if (line.equals("-----BLOCK-----")) mode = 1;
                else if (line.equals("-----FUNCT-----")) mode = 2;
                else if (line.equals("-----COORD-----")) mode = 3;
                else if (line.equals("-----PLAYR-----")) mode = 4;

                else if (mode == 1)
                {
                    if (count == 0) config = new Color[8][8];
                    String[] lineSplit = line.split(" ");
                    int red = Integer.parseInt(lineSplit[0]);
                    int green = Integer.parseInt(lineSplit[1]);
                    int blue = Integer.parseInt(lineSplit[2]);
                    int alpha = Integer.parseInt(lineSplit[3]);
                    Color c = new Color(red, green, blue, alpha);
                    config[count / 8][count % 8] = c;
                    count++;
                }
                else if (mode == 2)
                {
                    Block b = new Block();
                    b.setConfig(config);
                    b.setFunction(Integer.parseInt(line));
                    block_count++;
                    blocks[block_count] = b;
                    count = 0;
                }
                else if (mode == 3)
                {
                    String[] split = line.split(" ");
                    int r = Integer.parseInt(split[0]);
                    int c = Integer.parseInt(split[1]);
                    blockMatrix[r][c] = blocks[block_count];
                }
                else if (mode == 4)
                {
                    String[] lineSplit = line.split(" ");
                    int red = Integer.parseInt(lineSplit[0]);
                    int green = Integer.parseInt(lineSplit[1]);
                    int blue = Integer.parseInt(lineSplit[2]);
                    int alpha = Integer.parseInt(lineSplit[3]);
                    Color c = new Color(red, green, blue, alpha);
                    playerMatrix[count / 8][count % 8] = c;
                    count++;
                }
            }
        }
    }

    private Block[][] getBlankMatrix()
    {
        Block[][] matrix = new Block[25][50];

        for (int r = 0; r < 25; r++)
            for (int c = 0; c < 50; c++)
                matrix[r][c] = new Block();

        return matrix;
    }

    public Block[][] getBlockMatrix()
    {
        return blockMatrix;
    }

    public Block[] getBlocks()
    {
        return blocks;
    }

    public Color[][] getPlayerMatrix()
    {
        return playerMatrix;
    }
}
