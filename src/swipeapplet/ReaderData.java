/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package swipeapplet;


import com.mmm.readers.modules.Swipe.SwipeItem;

/**
 *  Class for buffering data so that it can be processed correctly
 * @author a38jtzz
 */
public class ReaderData {
    
    private SwipeItem prDataItem;
    private int prDataLength;
    private byte prDataPtr[];
    
    
    /**
     * Constructor
     * @param aDataItem - Data item e.g. AAMVA data
     * @param aDataLength - Length of data
     * @param aDataPtr - Array of actual data
     */
    public ReaderData(SwipeItem aDataItem, 
            int aDataLength, 
            byte aDataPtr[]) 
    {
        prDataItem = aDataItem;
        prDataLength = aDataLength;
        prDataPtr = aDataPtr;       
        
    }
    
    /**
     * 
     * @return Data item
     */
    public SwipeItem GetDataItem() {
        return prDataItem;
    }
    
    /**
     * 
     * @return Data length
     */
    public int GetDataLength() {
        return prDataLength;
    }
    
    /**
     * 
     * @return Physical data
     */
    public byte[] GetDataPtr() {
        return prDataPtr;
    }
    
    
    
}
