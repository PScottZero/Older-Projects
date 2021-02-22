import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Save
{
    public Save(Block[][] blockMatrix, Block[] blocks, Color[][] player) {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int val = jfc.showSaveDialog(null);

        if (val == JFileChooser.APPROVE_OPTION)
        {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(jfc.getSelectedFile() + ".bw"));

                for (Block b : blocks)
                {
                    bw.append("-----BLOCK-----\n");

                    Color[][] col = b.getConfig();
                    for (Color[] cRow : col)
                        for (Color color : cRow)
                            bw.append(color.getRed() + " "
                                    + color.getGreen() + " "
                                    + color.getBlue() + " "
                                    + color.getAlpha() + "\n");

                    bw.append("-----FUNCT-----\n");

                    bw.append(b.getFunction() + "\n");

                    if (isInBlockMatrix(blockMatrix, b)) bw.append("-----COORD-----\n");

                    for (int r = 0; r < blockMatrix.length; r++)
                        for (int c = 0; c < blockMatrix[r].length; c++)
                            if (blockMatrix[r][c] == b)
                                bw.append(r + " " + c + "\n");
                }

                bw.append("-----PLAYR-----\n");

                for (Color[] row : player)
                    for (Color color : row)
                        bw.append(color.getRed() + " "
                                + color.getGreen() + " "
                                + color.getBlue() + " "
                                + color.getAlpha() + "\n");

                bw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isInBlockMatrix(Block[][] blockMatrix, Block block)
    {
        for (Block[] blockRow : blockMatrix)
            for (Block block1 : blockRow)
                if (block1 == block) return true;

        return false;
    }
}
