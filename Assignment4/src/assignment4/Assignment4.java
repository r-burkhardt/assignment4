/* ==================================================================
*
*   PROGRAM NAME:
*       Assignment4 - Optical Barcode Readers and Writers
*
*   Description:
*	Barcode......
*
*   Classes:
*       BarcodeIO
*       BarcodeImage implements Cloneable
*       DataMatrix implements BarcodeIO
*
*   Parameters:
*       1. none
*
*   Additional Files:
*
*   Created:
*       2017/01/25
*
*   Author/s:
*       Roderick Burkhardt, Oswaldo Minez, Faiga Revah
*
* ==================================================================*/


package assignment4;


public class Assignment4  // Roderick & Faye
{

    public static void main(String[] args)
    {
        //BarcodeImage testBar = new BarcodeImage();
        String[] barcodeInfo = 
        {
            "                                           ",
            "                                           ",
            " * * * * * * * * * * * * * * * * * * *     ",
            " *                                    *    ",
            " **** *** **   ***** ****   *********      ",
            " * ************ ************ **********    ",
            " ** *      *    *  * * *         * *       ",
            " ***   *  *           * **    *      **    ",
            " * ** * *  *   * * * **  *   ***   ***     ",
            " * *           **    *****  *   **   **    ",
            " ****  *  * *  * **  ** *   ** *  * *      ",
            " **************************************    ",
            "                                           ",
            "                                           ",
            "                                           ",
            "                                           "
        };
        String[] barcodeInfo2 =
        {
            "                                                                " +
            "                                                                " +
            "                                                                " +
            "                                                                " +
            "                                                                " +
            "                                                                " +
            "                                                                " +
            "                                                                " +
            "                                                                " +
            "                                                                " +
            "                                                                " +
            "                                                                " +
            "                                                                " +
            "                                                                " +
            "                                                                " +
            "                                                                " +
            "                                                                " +
            "                                                                " +
            "                                                                " +
            "                                                                " +
            "* * * * * * * * * * * * * * * * * * *                           " +
            "*                                    *                          " +
            "**** *** **   ***** ****   *********                            " +
            "* ************ ************ **********                          " +
            "** *      *    *  * * *         * *                             " +
            "***   *  *           * **    *      **                          " +
            "* ** * *  *   * * * **  *   ***   ***                           " +
            "* *           **    *****  *   **   **                          " +
            "****  *  * *  * **  ** *   ** *  * *                            " +
            "**************************************                          "
        };
        /*int y = 0;
        for (int s = 0; s < barcodeInfo.length; s++)
        {
            for (int c = 0; c < barcodeInfo[s].length(); c++)
            {
                if (barcodeInfo[s].charAt(c) == '*')
                {
                    testBar.setPixel(s, c, true);
                }
            }
                
        }*/
        BarcodeImage testBar = new BarcodeImage(barcodeInfo2);
        
        //testBar.displayToConsole();
        
        DataMatrix test = new DataMatrix(testBar);
        
        test.displayImageToConsole();
        System.out.println(test.computeSignalWidth());
        //System.out.println(test.computeSignalHeight());
        // TODO code application logic here
    }
    
}

interface BarcodeIO // Oswaldo
{
    public boolean scan(BarcodeImage bc);
    public boolean readText(String text);
    public boolean generateImageFromText();
    public boolean translateImageToText();
    public void displayTextToConsole();
    public void displayImageToConsole();
}

class BarcodeImage implements Cloneable // Faye
{
    public static final int MAX_HEIGHT = 30;
    public static final int MAX_WIDTH = 65;
    private boolean[][] image_data;
    
    public BarcodeImage()
    {
        image_data = new boolean[MAX_WIDTH][MAX_HEIGHT];
        for(int i = 0; i < MAX_WIDTH; i++)
            for(int j = 0; j < MAX_HEIGHT; j++)
                image_data[i][j] = false;
    }
    
    public BarcodeImage(String[] str_data)
    {
        image_data = new boolean[MAX_HEIGHT][MAX_WIDTH];
        if(checkSize(str_data))
        {
            int height = str_data.length;
            int firstRow = MAX_HEIGHT - height;
            for(int i = 0; i < height; i++)
                for(int j = 0; j < str_data[i].length(); j++)
                {
                    if(str_data[i].charAt(j) == '*')
                        image_data[i + firstRow][j] = true;
                    //else
                    //    image_data[i + firstRow][j] = false;
                }
        }
        else
            for(int i = 0; i < MAX_HEIGHT; i++)
                for(int j = 0; j < MAX_WIDTH; j++)
                    image_data[i][j] = false;
    }
    
    public boolean getPixel(int row, int col)
    {
        if(row < MAX_WIDTH && col < MAX_HEIGHT)
            return image_data[col][row];
        return false;
    }
  
    public boolean setPixel(int row, int col, boolean pixel)
    {
        if(row < MAX_WIDTH && col < MAX_HEIGHT)
        {
            image_data[col][row] = pixel;
            return true;
        }
        return false;
    }
    
    //private method that checks the size of string array
    //checks if it fits into the dimensions specified by MAX_WIDTH and MAX_HEIGHT
    private boolean checkSize(String[] str_data)
    {
        if(str_data.length > MAX_HEIGHT)
            return false;
        for(int i = 0; i < str_data.length; i++)
            if(str_data[i].length() > MAX_WIDTH || str_data[i] == null)
                return false;
        return true;
    }
    
    public void displayToConsole()
    {
        for(int i = 0; i < MAX_WIDTH; i++)
        {
            for (int j = 0; j < MAX_HEIGHT; j++)
            {
                if(image_data[i][j] == true)
                    System.out.print("*");
                else
                    System.out.print(" ");
            }
            System.out.println();
        }
    }
    
    public BarcodeImage clone()
    {
        BarcodeImage bcI = new BarcodeImage();
        for(int i = 0; i < MAX_HEIGHT; i++)
            for(int j = 0; j < MAX_WIDTH; j++)
                bcI.setPixel(j, i, getPixel(j, i));
        return bcI;
    }
}


class DataMatrix implements BarcodeIO // Roderick
{
    public static final char BLACK_CHAR = '*';
    public static final char WHITE_CHAR = ' ';
    private BarcodeImage image;
    private String text;
    private int actualWidth;
    private int actualHeight;
    
    public DataMatrix()
    {
        image = new BarcodeImage();
        text = " ";
        actualWidth = 0;
        actualHeight = 0;
    }
    
    public DataMatrix(BarcodeImage image)
    {
        
        scan(image);
        text = " ";
    }
    
    public DataMatrix(String text)
    {
        image = new BarcodeImage();
        readText(text);
    }
    
    @Override
    public boolean readText(String text)
    {
        this.text = text;
        return true;
    }
    
    @Override
    public boolean scan(BarcodeImage image)
    {
        try
        {
            this.image = image.clone();
        }
        catch (Exception e)
        {
            
        }
        //cleanImage(this.image);
        this.actualWidth = 0;
        this.actualHeight = 0;
        return true;
    }
    
    public int getActualWidth()
    {
        return actualWidth;
    }
    
    public int getActualHeight()
    {
        return actualHeight;
    }
    
    public int computeSignalWidth()
    {
        int left = 0;
        int right = 0;
        
        for (int x = 0; x < BarcodeImage.MAX_WIDTH; x++)
        {
            for (int y = 0; y < BarcodeImage.MAX_HEIGHT; y++)
            {
                if (left == 0 && image.getPixel(x, y))
                    System.out.println(x);//left = x;
                if (image.getPixel(x, y))
                    System.out.println(x+1);//right = x+1;
            }
        }
        return 0;
        //return right - left;
    }
    
    private int computeSignalHeight()
    {
        int top = 0;
        int bottom = 0;
        
        for (int x = 0; x < BarcodeImage.MAX_WIDTH; x++)
        {
            for (int y = 0; y < BarcodeImage.MAX_HEIGHT; y++)
            {
                if (top == 0 && image.getPixel(x, y))
                    top = y;
                if (image.getPixel(x, y))
                    bottom = y+1;
            }
        }
        
        return bottom - top;
    }
    
    private void cleanImage(BarcodeImage image)
    {
        int leftEdge = 0;
        int bottomEdge = 0;
        
        for (int y = (BarcodeImage.MAX_HEIGHT - 1); y >= 0; y--)
        {
            for (int x = 0; x < BarcodeImage.MAX_WIDTH; x++)
            {
                
                if (image.getPixel(x, y))
                {
                    System.out.println(x + ", " + y);
                //    leftEdge = x;
                //    bottomEdge = y;
                    break;
                }
            }
        }
        
        //moveImageToLowerLeft(leftEdge, bottomEdge);
        
    }
    
    @Override
    public void displayTextToConsole()
    {
        
    }
    
    @Override
    public void displayImageToConsole()
    {
        String displayString = "";
        //System.out.println(computeSignalWidth());
        //System.out.println(computeSignalHeight());
//        for (int x = computeSignalWidth() - 1; x >= 0; x-- )
//        {
//            for (int y = BarcodeImage.MAX_HEIGHT - 1;
//                    y >= (BarcodeImage.MAX_HEIGHT-computeSignalHeight()-1); y--)
//            {
//                if (image.getPixel(x, y))
//                    displayString += '*';
//                else
//                    displayString += ' ';
//            }
//            displayString += "\n";
//        }
        System.out.println(displayString);
    }
    
    @Override
    public boolean generateImageFromText()
    {
        return true;
    }
    
    @Override
    public boolean translateImageToText()
    {
        return true;
    }
    
    private char readCharFromCol(int col)
    {
        return ' ';
    }
    
    private boolean writeCharToCol(int col, int code)
    {
        return true;
    }
    
    public void displayRawImage()
    {
        
    }
    
    private void clearImage()
    {
        
    }
    
    private void moveImageToLowerLeft(int leftShift, int downShift)
    {
        int distToBottom = BarcodeImage.MAX_HEIGHT-downShift;
        
        for (int x = leftShift; x < BarcodeImage.MAX_WIDTH; x++)
        {
            for (int y = downShift; y >= 0; y--)
            {
                this.image.setPixel(x - leftShift, y + distToBottom,
                        image.getPixel(x, y));
            }
        }
    }
}
