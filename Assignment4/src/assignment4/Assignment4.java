/* ==================================================================
*
*   PROGRAM NAME:
*       Assignment4 - Optical Barcode Readers and Writers
*
*   Description:
*	Industrial application that can read text from a barcode with optical
*       scanning and pattern recognition. This works by converting the sequence
*       of 8 characters in each column of a barcode into the ASCII codes. The
*       class defines a BarcodeIO interface that defines the basic method of
*       any barcode class implementing it.
*       The BarcodeImage class implements the built-in cloneable interface 
*       and overrides the clone() method. It is a 2D representation of the 
*       data of a barcode, which is stored in a 2D array of booleans. The 
*       final class, Datamatrix, implements the BarcodeIO interface, and it 
*       can convert between a barcode to text. It is not a true Datamatrix 
*       because it does not have error correction or encoding. 
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
        String[] sImageIn =
      {
         "                                               ",
         "                                               ",
         "                                               ",
         "     * * * * * * * * * * * * * * * * * * * * * ",
         "     *                                       * ",
         "     ****** **** ****** ******* ** *** *****   ",
         "     *     *    ****************************** ",
         "     * **    * *        **  *    * * *   *     ",
         "     *   *    *  *****    *   * *   *  **  *** ",
         "     *  **     * *** **   **  *    **  ***  *  ",
         "     ***  * **   **  *   ****    *  *  ** * ** ",
         "     *****  ***  *  * *   ** ** **  *   * *    ",
         "     ***************************************** ",  
         "                                               ",
         "                                               ",
         "                                               "

      };      
            
         
      
      String[] sImageIn_2 =
      {
            "                                          ",
            "                                          ",
            "* * * * * * * * * * * * * * * * * * *     ",
            "*                                    *    ",
            "**** *** **   ***** ****   *********      ",
            "* ************ ************ **********    ",
            "** *      *    *  * * *         * *       ",
            "***   *  *           * **    *      **    ",
            "* ** * *  *   * * * **  *   ***   ***     ",
            "* *           **    *****  *   **   **    ",
            "****  *  * *  * **  ** *   ** *  * *      ",
            "**************************************    ",
            "                                          ",
            "                                          ",
            "                                          ",
            "                                          "

      };
     
      BarcodeImage bc = new BarcodeImage(sImageIn);
      DataMatrix dm = new DataMatrix(bc);
     
      // First secret message
      dm.translateImageToText();
      dm.displayTextToConsole();
      dm.displayImageToConsole();
      
      // second secret message
      bc = new BarcodeImage(sImageIn_2);
      dm.scan(bc);
      dm.translateImageToText();
      dm.displayTextToConsole();
      dm.displayImageToConsole();
      
      // create your own message
      dm.readText("What a great resume builder this is!");
      dm.generateImageFromText();
      dm.displayTextToConsole();
      dm.displayImageToConsole();
    }
    
}

    //any class that implements BarcodeIO is expected to store some version
    //of an image and some version of the text associated with the image.
interface BarcodeIO // Faye
{
    public boolean scan(BarcodeImage bc);
    public boolean readText(String text);
    public boolean generateImageFromText();
    public boolean translateImageToText();
    public void displayTextToConsole();
    public void displayImageToConsole();
}

    //class BarcodeImage realizes all essential data and methods associated with a 
    //2D pattern- bar code. It stores and retrieves 2D data
class BarcodeImage implements Cloneable // Faye
{
    public static final int MAX_HEIGHT = 30;
    public static final int MAX_WIDTH = 65;
    private boolean[][] image_data;
    
    //default constructor
    public BarcodeImage()
    {
        image_data = new boolean[MAX_HEIGHT][MAX_WIDTH];
        for(int i = 0; i < MAX_HEIGHT; i++)
            for(int j = 0; j < MAX_WIDTH; j++)
                image_data[i][j] = false;
    }
    
    //constructor BarcodeImage(String[]) accepts a string array
    //and converts the array to a 2D array of booleans. It is stored
    //in the bottom, left corner of the array.
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
    
    //returns a pixel at a given coordinate
    public boolean getPixel(int row, int col)
    {
        if(row < MAX_HEIGHT && col < MAX_WIDTH)
            return image_data[row][col];
        return false;
    }
    
    //sets pixel at a given coordinate
    public boolean setPixel(int row, int col, boolean pixel)
    {
        if(row < MAX_HEIGHT && col < MAX_WIDTH)
        {
            image_data[row][col] = pixel;
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
    
    //method to display the 2D image_data to console in the form of 
    // '*' for true and ' ' for false.
    public void displayToConsole()
    {
        for (int i = 0; i < MAX_WIDTH+2; i++)
            System.out.print("-");
        System.out.println("");
        for(int i = 0; i < MAX_HEIGHT; i++)
        {
            System.out.print("|");
            for (int j = 0; j < MAX_WIDTH; j++)
            {
                if(image_data[i][j] == true)
                    System.out.print("*");
                else
                    System.out.print(" ");
            }
            System.out.println("|");
        }
        for (int i = 0; i < MAX_WIDTH+2; i++)
            System.out.print("-");
        System.out.println("");
    }
    
    //implements the Cloneable's clone() method. Deals with deep data and creates
    //a copy of the this object. Returns the new BarcodeImage.
    public BarcodeImage clone()
    {
        BarcodeImage bcI = new BarcodeImage();
        for(int i = 0; i < MAX_HEIGHT; i++)
            for(int j = 0; j < MAX_WIDTH; j++)
                bcI.image_data[i][j] = image_data[i][j];
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
    
    // Default Constructor
    // creates a blank object when a called.
    public DataMatrix()
    {
        image = new BarcodeImage();
        text = " ";
        actualWidth = 0;
        actualHeight = 0;
    }
    
    // Constructor method with a BarcodeImage parameter
    // Construstor calls scan which clones the BarcodeImage parameter 
    // over to the DataMatrix object's own image
    // set the DataMatrix's test to default blank
    public DataMatrix(BarcodeImage image)
    {
        scan(image);
        text = " ";
    }
    
    // Constructor method with a String parameter
    // Constructor calls readText which tests string length againist
    // BarcodeImage object MAX_WIDTH
    // Initializes image to a blank BarcodeImage
    public DataMatrix(String text)
    {
        this.image = new BarcodeImage();
        if(!readText(text))
            readText(" ");
    }
    
    // readText method is used to test the string for length validity, if the 
    // string is a valid length, it sets the text variable to the test parameter
    // and returns true.
    @Override
    public boolean readText(String text)
    {
        if (text.length() < BarcodeImage.MAX_WIDTH-2)
        {
            this.text = text;
            this.actualHeight = 0;
            this.actualWidth = 0;
            return true;
        }
        return false;
    }
    
    // Scane Method is called by contrustor when an image is passed to the 
    // constructor. it clones the incoming image over to the object's private 
    // BarcodeImage variable. It than calls the the cleanIamge method, sets the 
    // actualHeight and actualWidth variables.
    @Override
    public boolean scan(BarcodeImage image)
    {
        try
        {
            this.image = image.clone();
        }
        catch (Exception e)
        {
            System.err.println("clone failed");
        }
        cleanImage(this.image);
        this.actualHeight = computeSignalHeight();
        this.actualWidth = computeSignalWidth();
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
    
    // Computer the width of only the BarcodeImage that is actually the barcode
    // without the surrounding white space. 
    private int computeSignalWidth()
    {
        int left = -1;
        int right = 0;
        
        for (int y = 0; y < BarcodeImage.MAX_HEIGHT; y++)
        {
            for (int x = 0; x < BarcodeImage.MAX_WIDTH; x++)
            {
                if (left == -1 && image.getPixel(y, x))
                    left = x;
                if (image.getPixel(y, x))
                    right = x+1;
            }
        }
        return right - left;
    }
    
    // Computer the Height of only the BarcodeImage that is actually the barcode
    // without the surrounding white space. 
    private int computeSignalHeight()
    {
        int top = 0;
        int bottom = 0;
        
        for (int y = 0; y < BarcodeImage.MAX_HEIGHT; y++)
        {
            for (int x = 0; x < BarcodeImage.MAX_WIDTH; x++)
            {
                if (top == 0 && image.getPixel(y, x))
                    top = y;
                if (image.getPixel(y, x))
                    bottom = y+1;
            }
        }
        return bottom - top;
    }
    
    // cleanImage is used to clean up the image with the help of
    // moveImageToLowerLeft by placing the image in the lower left corner of the 
    // BarcodeImage box, leaving the white space only above and below the
    // barcode.
    private void cleanImage(BarcodeImage image)
    {
        int leftEdge = 0;
        int bottomEdge = 0;
        boolean cornerLocated = false;
        
        for (int y = (BarcodeImage.MAX_HEIGHT - 1); y >= 0; y--)
        {
            for (int x = 0; x < BarcodeImage.MAX_WIDTH; x++)
            {
                
                if (image.getPixel(y, x))
                {
                    //System.out.println(x + ", " + y);
                    leftEdge = x;
                    bottomEdge = y;
                    cornerLocated = true;
                    break;
                }
            }
            if (cornerLocated) break;
        }
        
        moveImageToLowerLeft(leftEdge, bottomEdge);
    }
    
    // Outputs the objects text to the screen
    @Override
    public void displayTextToConsole()
    {
        System.out.println(this.text);
    }
    
    // Displays the objects barcode image to the screen for viewing. When it
    // displays the barcode, it crops the barcode down to only the relevent
    // portiton croping off excessive white space.
    @Override
    public void displayImageToConsole()
    {
        for (int i = 0; i < computeSignalWidth()+2; i++)
            System.out.print("-");
        System.out.println("");
        for (int y = (BarcodeImage.MAX_HEIGHT - computeSignalHeight());
                    y < BarcodeImage.MAX_HEIGHT; y++ )
        {
            System.out.print("|");
            for (int x = 0; x < computeSignalWidth(); x++)
            {
                if (image.getPixel(y, x))
                    System.out.print('*');
                else
                    System.out.print(' ');
            }
            System.out.println("|");
        }
    }
    
    // converts the text of the object into a barcode
    @Override
    public boolean generateImageFromText()
    {
        for (int topBorder = 0; topBorder <= this.text.length(); topBorder++)
        {
            if (topBorder % 2 == 0)
            {
                this.image.setPixel(0, topBorder, true);
            }
        }
        for (int leftBorder = 1; leftBorder < 9; leftBorder++)
        {
            this.image.setPixel(leftBorder, 0, true);
        }
        for (int c = 0; c < this.text.length(); c++)
        {
            writeCharToCol(c+1, this.text.charAt(c));
        }
        for (int bottomBorder = 0; bottomBorder <= this.text.length();
                bottomBorder++)
        {
            this.image.setPixel(9, bottomBorder, true);
        }
        cleanImage(image);
        this.actualHeight = computeSignalHeight();
        this.actualWidth = computeSignalWidth();
        return true;
    }
    
    // converts the image into text that is saved to the objects text variable
    @Override
    public boolean translateImageToText()
    {
        String newString = "";
        for (int x = 1; x < computeSignalWidth()-1; x++)
        {
            newString += readCharFromCol(x);
        }
        this.text = newString;
        return true;
    }
    
    // helper method for translateImageToText, thaking the parameter of a column
    // reads that columns true/false to determine what ascii code is in each 
    // column
    private char readCharFromCol(int col)
    {
        int newChar = 0;
        int rowCount = 0;
        for (int y = BarcodeImage.MAX_HEIGHT- 2;
                y > BarcodeImage.MAX_HEIGHT- 10; y--)
        {
            if (this.image.getPixel(y, col))
            {
                switch (rowCount)
                {
                    case 0:
                        newChar += 1;
                        break;
                    case 1:
                        newChar += 2;
                        break;
                    case 2:
                        newChar += 4;
                        break;
                    case 3:
                        newChar += 8;
                        break;
                    case 4:
                        newChar += 16;
                        break;
                    case 5:
                        newChar += 32;
                        break;
                    case 6:
                        newChar += 64;
                        break;
                    case 7:
                        newChar += 128;
                        break;
                    default:
                        newChar += 0;
                }
            }
            rowCount++;
        }
        return (char)newChar;
    }
    
    // helper method for generateImageFromText, that takes an ascii code and
    // converts it into the columns that are stored in the image array
    private boolean writeCharToCol(int col, int code)
    {
        if (code % 2 == 1)
        {
            this.image.setPixel(8, col, true);
            code -= 1;
        }
        if (code - 128 >= 0)
        {
            this.image.setPixel(1, col, true);
            code -= 128;
        }
        if (code - 64 >= 0)
        {
            this.image.setPixel(2, col, true);
            code -= 64;
        }
        if (code - 32 >= 0)
        {
            this.image.setPixel(3, col, true);
            code -= 32;
        }
        if (code - 16 >= 0)
        {
            this.image.setPixel(4, col, true);
            code -= 16;
        }
        if (code - 8 >= 0)
        {
            this.image.setPixel(5, col, true);
            code -= 8;
        }
        if (code - 4 >= 0)
        {
            this.image.setPixel(6, col, true);
            code -= 4;
        }
        if (code - 2 >= 0)
        {
            this.image.setPixel(7, col, true);
            code -= 2;
        }            
        return true;
    }
    
    // Dsiplays the barcode in it full size with all white space
    public void displayRawImage()
    {
        for (int i = 0; i < BarcodeImage.MAX_WIDTH+2; i++)
            System.out.print("-");
        System.out.println("");
        for (int y = 0; y < BarcodeImage.MAX_HEIGHT; y++)
        {
            System.out.print("|");
            for (int x = 0; x < BarcodeImage.MAX_WIDTH; x++)
            {
                if (this.image.getPixel(y, x))
                    System.out.print('*');
                else
                    System.out.print(' ');
            }
            System.out.println("|");
        }
        for (int i = 0; i < BarcodeImage.MAX_WIDTH+2; i++)
            System.out.print("-");
        System.out.println("");
    }
    
    // wipes an image to all blank
    private void clearImage()
    {
        for (int i = 0; i < BarcodeImage.MAX_HEIGHT; i++)
            for (int j = 0; j < BarcodeImage.MAX_WIDTH; j++)
                image.setPixel(i, j, false);
    }
    
    // helper method for cleanImage, it moves the barcode from where ever it is
    // located im the barcodeImage box and places it in the bottom left corner.
    private void moveImageToLowerLeft(int leftShift, int downShift)
    {
        int distToBottom = BarcodeImage.MAX_HEIGHT-downShift-1;
        BarcodeImage newImage = new BarcodeImage();
        
        for (int y = downShift; y >= 0; y--)
        {
            for (int x = leftShift; x < BarcodeImage.MAX_WIDTH; x++)
            {
                newImage.setPixel(y + distToBottom, x - leftShift,
                        this.image.getPixel(y, x));
            }
        }
        
        this.image = newImage.clone();
    }
}


/**************RUN**************************************************************
 * 
CSUMB CSIT online program is top notch.
-------------------------------------------
|* * * * * * * * * * * * * * * * * * * * *|
|*                                       *|
|****** **** ****** ******* ** *** *****  |
|*     *    ******************************|
|* **    * *        **  *    * * *   *    |
|*   *    *  *****    *   * *   *  **  ***|
|*  **     * *** **   **  *    **  ***  * |
|***  * **   **  *   ****    *  *  ** * **|
|*****  ***  *  * *   ** ** **  *   * *   |
|*****************************************|
You did it!  Great work.  Celebrate.
----------------------------------------
|* * * * * * * * * * * * * * * * * * * |
|*                                    *|
|**** *** **   ***** ****   *********  |
|* ************ ************ **********|
|** *      *    *  * * *         * *   |
|***   *  *           * **    *      **|
|* ** * *  *   * * * **  *   ***   *** |
|* *           **    *****  *   **   **|
|****  *  * *  * **  ** *   ** *  * *  |
|**************************************|
What a great resume builder this is!
----------------------------------------
|*                                     |
|***** * ***** ****** ******* **** **  |
|* *********************************** |
|**  *    *  * * **    *    * *  *  *  |
|* *               *    **     **  *   |
|**  *   * * *  * ***  * ***  *        |
|**      **    * *    *     *    *  *  |
|** *  * * **   *****  **  *    ** *** |
|************************************* |
|                                      |
|                                      |
|                                      |
|                                      |
|                                      |
|                                      |
|                                      |
|                                      |
|                                      |
|                                      |
|* * * * * * * * * * * * * * * * * * * |
|*                                    *|
|**** *** **   ***** ****   *********  |
|* ************ ************ **********|
|** *      *    *  * * *         * *   |
|***   *  *           * **    *      **|
|* ** * *  *   * * * **  *   ***   *** |
|* *           **    *****  *   **   **|
|****  *  * *  * **  ** *   ** *  * *  |
|**************************************|
 *
 * ****************************************************************************/