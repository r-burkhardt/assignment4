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
    public DataMatrix()
    {
        
    }
    
    public DataMatrix(BarcodeImage image)
    {
        
    }
    
    public DataMatrix(String text)
    {
        
    }
}
