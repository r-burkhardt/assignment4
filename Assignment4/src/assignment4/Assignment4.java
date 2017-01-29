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
        BarcodeImage bci = new BarcodeImage();
        //bci.displayToConsole();
        String[] strArr = new String[15];
        for(int i = 0; i < strArr.length; i++)
        {
            strArr[i] = "*** ***** ** * * *** * **************S**";
        }
        BarcodeImage bciII = new BarcodeImage(strArr);
        bciII.displayToConsole();
    
}

interface BarcodeIO // Oswaldo
{
    public boolean scan(BarcodeImage bc);
    public boolean readText(String text);
    public boolean generateImageFromText();
    public boolean translateImageToText();
    public void displayTestToConsole();
    public void displayImageToConsole();
}

static class BarcodeImage implements Cloneable // Faye
{
    public static final int MAX_HEIGHT = 30;
    public static final int MAX_WIDTH = 65;
    private boolean[][] image_data;
    
    public BarcodeImage()
    {
        image_data = new boolean[MAX_HEIGHT][MAX_WIDTH];
        for(int i = 0; i < MAX_HEIGHT; i++)
            for(int j = 0; j < MAX_WIDTH; j++)
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
                        image_data[i + height][j] = true;
                    else
                        image_data[i + height][j] = false;
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
        for(int i = 0; i < MAX_HEIGHT; i++)
        {
            for (int j = 0; j < MAX_WIDTH; j++)
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
}
/*
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
        image = scan(image);
        text = " ";
    }
    
    public DataMatrix(String text)
    {
        image = new BarcodeImage();
        text = readText(text);
    }
    
    public boolean readText(String text)
    {
        return true;
    }
    
    public boolean scan(BarcodeImage image)
    {
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
    
    private int computeSignalWidth()
    {
        return 0;
    }
    
    private int computeSignalHeight()
    {
        return 0;
    }
    
    private void cleanImage()
    {
        
    }
    
    public String displayImageToConsole()
    {
        return "";
    }
    
    public String generateImageFromText()
    {
        return "";
    }
    
    public BarcodeImage translateImageToText()
    {
        return new BarcodeImage();
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
}*/
