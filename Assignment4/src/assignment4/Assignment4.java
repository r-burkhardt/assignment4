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
        // TODO code application logic here
    }
    
}

interface BarcodeIO // Oswaldo
{
    
}

class BarcodeImage implements Cloneable // Faye
{

    public BarcodeImage()
    {
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
}
