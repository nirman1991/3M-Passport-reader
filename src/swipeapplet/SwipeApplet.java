package swipeapplet;

import com.mmm.readers.AAMVAData;
import com.mmm.readers.modules.Swipe.SwipeItem;
import java.util.ArrayList;
import javax.swing.JApplet;
import java.util.Calendar;
import java.util.List;
import java.net.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This applet interfaces with a 3M swipe reader, retrieves document information
 * from the API and sends it to a web page javascript function 
 * @author 3M
 */
public class SwipeApplet extends JApplet implements
    com.mmm.readers.ErrorHandler,
    com.mmm.readers.modules.Swipe.DataHandler,
    com.mmm.readers.FullPage.EventHandler
{

    //GUI Declarations
    private javax.swing.JScrollPane jScrollPane1;
    private java.awt.List puMsgList;
    private javax.swing.JTextArea puStatus;
    
    private boolean prIsAAMVA;
    private List<ReaderData> prDataList = new ArrayList<ReaderData>();
    
    //MMM Declarations
    private String prSurname;
    private String prGivenName;
    private String prSecondName;
    private String prFirstName;
    private String prSex;
    private String prShortSex;
    private String prDateOfBirth;
    private String prNationality;

    private String prDocumentNumber;
    private String prDateOfExpiry;
    private String prIssuer;
    private String prDocType;
    private String prDocID;
    private String prLicenceNumber;
    

    private String prMRZ1;
    private String prMRZ2;
    private String prMRZ3;
    
    
    private String prTrack1;
    private String prTrack2;
    private String prTrack3;
    
    private String prTrack11;
    private String prTrack22;
    private String prTrack33;
    
    private String prTrack111;
    private String prTrack222;
    private String prTrack333;
    //This section defines the public interfaces to the data members
    
    /**
     * Get surname
     * @return
     */
    public String GetSurname() {
        return prSurname;
    }
    
    /**
     * Get given name
     * @return 
     */
    public String GetGivenName() {
        return prGivenName;
    }
    
    /**
     * Get second name
     * @return 
     */
    public String GetSecondName() {
        return prSecondName;
    }
    
    /**
     * Get first name
     * @return 
     */
    public String GetFirstName() {
        return prFirstName;
    }
    
    /**
     * Get sex (long version)
     * @return 
     */
    public String GetSex() {
        return prSex;
    }
    
    /**
     * Get sex (short version e.g. m or f)
     * @return 
     */
    public String GetShortSex() {
        return prShortSex;
    }
    
    /**
     * Get date of birth
     * @return 
     */
    public String GetDateOfBirth() {
        return prDateOfBirth;
    }
    
    /**
     * Get nationality
     * @return 
     */
    public String GetNationality() {
        return prNationality;
    }
    
    /**
     * Get document number
     * @return 
     */
    public String GetDocumentNumber() {
        return prDocumentNumber;
    }
    
    /**
     * Get document expiry date
     * @return 
     */
    public String GetDateOfExpiry() {
        return prDateOfExpiry;
    }
    
    /**
     * Get document issuer
     * @return 
     */
    public String GetIssuer() {
        return prIssuer;
    }
    
    /**
     * Get document type
     * @return 
     */
    public String GetDocType() {
        return prDocType;
    }
    
    /**
     * Get document ID
     * @return 
     */
    public String GetDocID() {
        return prDocID;
    }
    
    /**
     * Get licence number
     * @return 
     */
    public String GetLicenceNumber() {
        return prLicenceNumber;
    }
    
    /**
     * Get machine readable zone 1
     * @return 
     */
    public String GetMRZ1() {
        return prMRZ1;
    }
    
    /**
     * Get machine readable zone 2
     * @return 
     */
    public String GetMRZ2() {
        return prMRZ2;
    }
    
    /**
     * Get machine readable zone 3
     * @return 
     */
    public String GetMRZ3() {
        return prMRZ3;
    }
    
    /**
     * Get MSR track 1
     * @return 
     */
    public String GetTrack1() {
        return prTrack1;
    }
    
    /**
     * Get MSR track 2
     * @return 
     */
    public String GetTrack2() {
        return prTrack2;
    }
    
    /**
     * Get MSR track 3
     * @return 
     */
    public String GetTrack3() {
        return prTrack3;
    }
    
    /**
     * Get MSR1 extension track
     * @return 
     */
    public String GetTrack11() {
        return prTrack11;
    }
    
    /**
     * Get MSR2 extension track
     * @return 
     */
    public String GetTrack22() {
        return prTrack22;
    }
    
    /**
     * Get MSR3 extension track
     * @return 
     */
    public String GetTrack33() {
        return prTrack33;
    }
    
    /**
     * Get MSR1 second extension track
     * @return 
     */
    public String GetTrack111() {
        return prTrack111;
    }
    
    /**
     * Get MSR2 second extension track
     * @return 
     */
    public String GetTrack222() {
        return prTrack222;
    }
    
    /**
     * Get MSR3 second extension track
     * @return 
     */
    public String GetTrack333() {
        return prTrack333;
    }
    
    
    /**
     * Initialization method that will be called after the applet is loaded
     * into the browser.
     */
    @Override
    public void init() {
        // TODO start asynchronous download of heavy resources
        
        showStatus("Initialising SwipeApplet");

        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    InitGUI();
                }
            });
        } catch (Exception ex) {
            showStatus("Failed to initialise SwipeApplet: " + ex.getMessage());
        }
        
        initApp();        
        
    }
    
    /**
     * Initialise main application
     */
    private void initApp() {
        prIsAAMVA = false;
        this.setVisible(true);
       
        ClearFields();
        StartReader();
    }
    
    /**
     * Add a message to the message list box
     * @param aMsg - Message 
     */
    private void AddMsgToMsgList(String aMsg){
        puMsgList.add(aMsg);
        puMsgList.makeVisible(puMsgList.getItemCount() - 1);
    }

    /**
     * Initialise all data members to empty strings
     */
    private void ClearFields(){
        prSurname="";
        prGivenName="";
        prSecondName="";
        prFirstName="";
        prDateOfBirth="";
        prSex= "";
        prShortSex="";
        prNationality="";

        prDocType="";
        prDocID="";
        prDocumentNumber="";
        prDateOfExpiry="";
        prIssuer="";

        prMRZ1="";
        prMRZ2="";
        prMRZ3="";
        
        prTrack1 = "";
        prTrack2 = "";
        prTrack3 = "";
        prTrack11="";
        prTrack22="";
        prTrack33="";      
        prTrack111 = "";
        prTrack222 = "";
        prTrack333 = "";     
    }
    
    
    /**
     * Error callback function from MMM API
     * @param aErrorCode
     * @param aErrorMsg 
     */
    @Override
    public void OnMMMReaderError(com.mmm.readers.ErrorCode aErrorCode, String aErrorMsg)
    {
        puStatus.setText(aErrorMsg);
        AddMsgToMsgList("Error: " + aErrorCode.toString() + " - " + aErrorMsg);
    }
    
    /**
     * Event callback function from MMM API
     * @param aEventType - Type of event received from API
     */
    @Override
    public void OnFullPageReaderEvent(com.mmm.readers.FullPage.EventCode aEventType)
     {
         AddMsgToMsgList("Event: " + aEventType.toString());

         switch (aEventType)
         {
             case SWIPE_READER_CONNECTED:
             {
                puStatus.setText("Reader Connected");
                break;
             }

             case READING_DATA:
             {
                 puStatus.setText("Reading Data");
                 break;
             }

             case START_OF_SWIPE_DATA:
             {           
                ClearFields();
                break;
             }

             case END_OF_SWIPE_DATA:
             {
                //Check for AAMVA data
                for(int i = 0; i < prDataList.size(); i++)
                {
                if (prDataList.get(i).GetDataItem() == com.mmm.readers.modules.Swipe.SwipeItem.AAMVA_DATA)
                    prIsAAMVA = true;
                }

                //Now send data for processing
                for(int i = 0; i < prDataList.size(); i++)
                {
                    DisplayStructData(
                         prDataList.get(i).GetDataItem(), 
                         prDataList.get(i).GetDataLength(), 
                         prDataList.get(i).GetDataPtr()
                         );
                }


                // Reset values and clear list
                prIsAAMVA = false;
                prDataList.clear();
                puStatus.setText("Data Received from Reader");
                break;
             }
              
         }
      }
    
    /**
     * Event callback function from MMM API
     * @param aDataItem - Raw data
     * @param aFormat  - Format of data
     * @param aDataLen  - Length of data
     * @param aDataPtr - Pointer to data Struct
     */
    @Override
    public void OnSwipeReaderData(
        com.mmm.readers.modules.Swipe.SwipeItem aDataItem,
        com.mmm.readers.DataFormat aFormat,
        int aDataLen,
        byte aDataPtr[]
    )
    {
        switch (aFormat)
            {
                case INT :
                {
                    int lValue = com.mmm.readers.interop.Marshal.toInt(aDataPtr);
                    AddMsgToMsgList("Data: " + aDataItem.toString() + " = " + lValue);
                    break;
                }
                case BOOLEAN :
                {
                    boolean lValue = com.mmm.readers.interop.Marshal.toBoolean(aDataPtr);
                    AddMsgToMsgList("Data: " + aDataItem.toString() + " = " + lValue);
                    break;
                }
                case FLOAT :
                {
                    float lValue = com.mmm.readers.interop.Marshal.toFloat(aDataPtr);
                    AddMsgToMsgList("Data: " + aDataItem.toString() + " = " + lValue);
                    break;
                }
                case STRING_ASCII :
                {
                    String lString = com.mmm.readers.interop.Marshal.toNewString(aDataPtr, aDataLen);
                    AddMsgToMsgList("Data: " + aDataItem.toString() + " = " + lString);
                    break;
                }
                case BYTE :
                {
                    byte lValue = com.mmm.readers.interop.Marshal.toByte(aDataPtr);
                    AddMsgToMsgList("Data: " + aDataItem.toString() + " = " + lValue);
                    break;
                }
                case BYTE_ARRAY :
                {
                    StringBuffer lString = new StringBuffer();
                    for (int loop = 0; loop < aDataLen; ++loop)
                    {
                            lString.append(Integer.toHexString((int)aDataPtr[loop]));
                            lString.append(" ");
                    }
                    AddMsgToMsgList("Data: " + aDataItem.toString() + " = " + lString);
                    break;
                }
                case STRUCT :
                {
                      
                    ReaderData lRd = new ReaderData(aDataItem, aDataLen, aDataPtr);
                    prDataList.add(lRd);
                    break;
                }
                
                 
            }
    }
    
    public static void main(String ar[])
    {
        SwipeApplet mSwipe = new SwipeApplet();
        mSwipe.init();
    }
    
    /**
     * Initialises the GUI components for the Applet
     */
    private void InitGUI() {
        
        puMsgList = new java.awt.List();
        jScrollPane1 = new javax.swing.JScrollPane();
        puStatus = new javax.swing.JTextArea();

        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setName("panel1"); // NOI18N
        setSize(330, 137);

        puMsgList.setFont(new java.awt.Font("Courier New", 0, 12));

        puStatus.setColumns(20);
        puStatus.setEditable(false);
        puStatus.setLineWrap(true);
        puStatus.setRows(3);
        puStatus.setWrapStyleWord(true);
        puStatus.setFocusable(false);
        jScrollPane1.setViewportView(puStatus);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(puMsgList, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(puMsgList, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }
        
    
    /**
     * This method adds data from document into GUI message boxes
     * and calls reader function on calling web page
     * @param aDataItem - Types of data accessible in document
     * @param aDataLen - Length of data - not used
     * @param aDataPtr - Data from document
     */
    private void DisplayStructData(
	com.mmm.readers.modules.Swipe.SwipeItem aDataItem,
        int aDataLen,
        byte aDataPtr[]
	)
    {   
        switch (aDataItem) {     
            
            //Machine readable data using OCR
            case OCR_CODELINE :
            {
                com.mmm.readers.CodelineData lCodeline =
                                com.mmm.readers.modules.Swipe.Reader.ConstructCodelineData(aDataPtr);

                AddMsgToMsgList("Data: " + aDataItem.toString()
                        + " = " + lCodeline.Data);
                AddMsgToMsgList("OCR Line 1 = " + lCodeline.Line1);
                AddMsgToMsgList("OCR Line 2 = " + lCodeline.Line2);
                AddMsgToMsgList("OCR Line 3 = " + lCodeline.Line3);
                //AddMsgToMsgList("OCR Entries = " + lCodeline.Entries.toString());

                // Raw OCR information
                if (lCodeline.LineCount == 2){
                    prMRZ1       =   lCodeline.Line2;
                    prMRZ2       =   lCodeline.Line3;
                    prMRZ3       =   "";
                    }
                if (lCodeline.LineCount == 3){
                    prMRZ1       =   lCodeline.Line1;
                    prMRZ2       =   lCodeline.Line2;
                    prMRZ3       =   lCodeline.Line3;
                    }

                // Holder information
                prSurname        =   lCodeline.Surname;
                prGivenName      =   lCodeline.Forenames;
                prSecondName     =   lCodeline.SecondName;
                prFirstName      =   lCodeline.Forename;

                
                //Because document refers to short year, must check if
                //in pre or plus 2000
                int lFullYear = 0;
                if (lCodeline.DateOfBirth.Year +2000 > Calendar.YEAR) {
                    lFullYear    =   lCodeline.DateOfBirth.Year +1900;
                } else {
                    lFullYear    =   lCodeline.DateOfBirth.Year +2000;
                }

                prDateOfBirth    =   lCodeline.DateOfBirth.Day + "-" 
                        + lCodeline.DateOfBirth.Month + "-" 
                        + lFullYear;
                prSex            =   lCodeline.Sex;
                prShortSex       =   Character.toString(lCodeline.ShortSex);
                prNationality    =   lCodeline.Nationality;

                // Document information
                prDocType        =   lCodeline.DocType;
                prDocID          =   lCodeline.DocId;
                prIssuer         =   lCodeline.IssuingState;
                prDocumentNumber =   lCodeline.DocNumber;

                //Expiry date must be year plus year 2000
                lFullYear        =   lCodeline.ExpiryDate.Year + 2000;
                prDateOfExpiry   =   lCodeline.ExpiryDate.Day + "-" 
                        + lCodeline.ExpiryDate.Month 
                        + "-" + lFullYear;


                String lDataTypeString = "ocr";
                BufferedWriter writer = null;
                File logFile = new File(prDocumentNumber+".txt");    
                try 
                {
                    writer = new BufferedWriter(new FileWriter(logFile));
                    writer.write(prDocType+" "+prDocumentNumber+" "+prSurname+" "+prFirstName+" "+prSecondName+" "+prSurname+" "+prNationality+" "+prDateOfBirth+" "+prDateOfExpiry+" "+prIssuer);
                    System.out.println("Writing Done");
                    writer.close();
                    getAppletContext().showDocument
                    (new URL("javascript:readerdata(\"" + lDataTypeString +"\")"));
                }
                catch (IOException me){}
                break;
            }
            case AAMVA_DATA :
            {
                com.mmm.readers.AAMVAData lData = 
                        com.mmm.readers.interop.Marshal.ConstructAAMVAData(aDataPtr);
                
                prDocType       =   "AAMVA";
                prLicenceNumber =   lData.Parsed.LicenceNumber;
                prSurname       =   lData.Parsed.Surname;
                prGivenName     =   lData.Parsed.GivenNames;
                
                int lFullYear = 0;
                if (lData.Parsed.DateOfBirth.Year +2000 > Calendar.YEAR) {
                    lFullYear    =   lData.Parsed.DateOfBirth.Year +1900;
                } else {
                    lFullYear    =   lData.Parsed.DateOfBirth.Year +2000;
                }

                prDateOfBirth    =   lData.Parsed.DateOfBirth.Day + "-" 
                        + lData.Parsed.DateOfBirth.Month + "-" 
                        + lFullYear;
                
                prSex           =    lData.Parsed.Sex;
                
                prDateOfExpiry   =   lData.Parsed.ExpiryDate.Day + "-" 
                        + lData.Parsed.ExpiryDate.Month 
                        + "-" + lFullYear;
                
                
                prIssuer        =   lData.Parsed.AddressState;
                
                
                String lDataTypeString = "aamva";
                
               BufferedWriter writer = null;
                File logFile = new File(prDocumentNumber+".txt");    
                try 
                {
                    writer = new BufferedWriter(new FileWriter(logFile));
                    writer.write(prDocType+" "+prDocumentNumber+" "+prSurname+" "+prFirstName+" "+prSecondName+" "+prSurname+" "+prNationality+" "+prDateOfBirth+" "+prDateOfExpiry+" "+" "+prIssuer);
                    System.out.println("Writing Done");
                    writer.close();
                    getAppletContext().showDocument
                    (new URL("javascript:readerdata(\"" + lDataTypeString +"\")"));
                }
                catch (IOException me){}
                break; 
                
            }
                // Magnetic Swipe Reader data
            case MSR_DATA :
            {
                if (!prIsAAMVA) {
                
                    com.mmm.readers.modules.Swipe.MsrData lData =
                            com.mmm.readers.modules.Swipe.Reader.ConstructMsrData(aDataPtr);

                    AddMsgToMsgList("MSR Track 1 = " + lData.Track1);
                    AddMsgToMsgList("MSR Track 2 = " + lData.Track2);
                    AddMsgToMsgList("MSR Track 3 = " + lData.Track3);


                    SplitMSRData(lData.Track1, lData.Track2, lData.Track3);


                    String lDataTypeString = "msr";
                    
                    BufferedWriter writer = null;
                File logFile = new File(prDocumentNumber+".txt");    
                try 
                {
                    writer = new BufferedWriter(new FileWriter(logFile));
                    writer.write(prDocType+" "+prDocumentNumber+" "+prSurname+" "+prFirstName+" "+prSecondName+" "+prSurname+" "+prNationality+" "+prDateOfBirth+" "+prDateOfExpiry+" "+prIssuer);
                    System.out.println("Writing Done");
                    writer.close();
                    getAppletContext().showDocument
                    (new URL("javascript:readerdata(\"" + lDataTypeString +"\")"));
                }
                catch (IOException me){}
                
                }
                break;
            }
            case ATB_DATA :
            {
                com.mmm.readers.modules.Swipe.AtbData lData =
                        com.mmm.readers.modules.Swipe.Reader.ConstructAtbData(aDataPtr);

                AddMsgToMsgList("ATB Track 1 Block 1 = " + lData.Track1.Block1);
                AddMsgToMsgList("ATB Track 1 Block 2 = " + lData.Track1.Block2);
                AddMsgToMsgList("ATB Track 1 Block 3 = " + lData.Track1.Block3);

                AddMsgToMsgList("ATB Track 2 Block 1 = " + lData.Track2.Block1);
                AddMsgToMsgList("ATB Track 2 Block 2 = " + lData.Track2.Block2);
                AddMsgToMsgList("ATB Track 2 Block 3 = " + lData.Track2.Block3);

                AddMsgToMsgList("ATB Track 3 Block 1 = " + lData.Track3.Block1);
                AddMsgToMsgList("ATB Track 3 Block 2 = " + lData.Track3.Block2);
                AddMsgToMsgList("ATB Track 3 Block 3 = " + lData.Track3.Block3);

                AddMsgToMsgList("ATB Track 4 Block 1 = " + lData.Track4.Block1);
                AddMsgToMsgList("ATB Track 4 Block 2 = " + lData.Track4.Block2);
                AddMsgToMsgList("ATB Track 4 Block 3 = " + lData.Track4.Block3);
                break;
            }
            case RTE_QA_DATA :
            {
                com.mmm.readers.modules.Swipe.RTEQAData lData =
                        com.mmm.readers.modules.Swipe.Reader.ConstructRTEQAData(aDataPtr);

                AddMsgToMsgList("QA Codeline Count = " + lData.CodelineCount);
                AddMsgToMsgList("QA Column Count = " + lData.ColumnCount);
                AddMsgToMsgList("QA Clear Area Present = " + lData.ClearAreaPresent);
                AddMsgToMsgList("QA Spot Count = " + lData.SpotCount);

                DisplayQALineData(1, lData.Line1);
                DisplayQALineData(2, lData.Line2);
                DisplayQALineData(3, lData.Line3);
                break;
            }
            
             
        }
    }
    
    /**
     * Split the mag stripe data into three pieces per track
     * dependant on length. Currently 30 per line
     * @param aTrack1 - First track of mag stripe data
     * @param aTrack2 - Second track of mag stripe data
     * @param aTrack3 - Second track of mag stripe data
     */
    private void SplitMSRData(String aTrack1, String aTrack2, String aTrack3) {
        
        if (aTrack1.length() > 30) {
            prTrack1 = aTrack1.substring(0, 29);
            prTrack11 = aTrack1.substring(29, aTrack1.length() > 60 ? 59 : aTrack1.length()-1);
            if (aTrack1.length() > 60) {
                prTrack111 = aTrack1.substring(59, aTrack1.length()-1);
            }
        } else {
            prTrack1 = aTrack1;
        }
        
        if (aTrack2.length() > 30) {
            prTrack2 = aTrack2.substring(0, 29);
            prTrack22 = aTrack2.substring(29, aTrack2.length() > 60 ? 59 : aTrack2.length()-1);
            if (aTrack2.length() > 60) {
                prTrack222 = aTrack2.substring(59, aTrack2.length()-1);
            }
        } else {
            prTrack2 = aTrack2;
        }
        
        if (aTrack3.length() > 30) {
            prTrack3 = aTrack3.substring(0, 29);
            prTrack33 = aTrack3.substring(29, aTrack3.length() > 60 ? 59 : aTrack3.length()-1);
            if (aTrack3.length() > 60) {
                prTrack333 = aTrack3.substring(59, aTrack3.length()-1);
            }
        } else {
            prTrack3 = aTrack3;
        }
        
    }
    
    
    /**
     * Adds QA data to message list
     * @param aLine - Line number
     * @param aData - QA data
     */
    private void DisplayQALineData
            (
		int aLine,
		com.mmm.readers.modules.Swipe.RTEQALineData aData
            )
    {
        //Check data exists
        if (aData.HasData)
        {
                AddMsgToMsgList("Line " + aLine + " - QA Char Count = " + aData.CharCount);
                AddMsgToMsgList("Line " + aLine + " - QA Lower Line Boundary = " + aData.LowerLineBoundary);
                AddMsgToMsgList("Line " + aLine + " - QA Upper Line Boundary = " + aData.UpperLineBoundary);
                AddMsgToMsgList("Line " + aLine + " - QA Recognised Count = " + aData.RecognisedCount);
                AddMsgToMsgList("Line " + aLine + " - QA Average Stroke Width = " + aData.AverageStrokeWidth);
                AddMsgToMsgList("Line " + aLine + " - QA Thinnest Stroke Width = " + aData.ThinnestStrokeWidth);
                AddMsgToMsgList("Line " + aLine + " - QA Thickest Stroke Width = " + aData.ThickestStrokeWidth);
                AddMsgToMsgList("Line " + aLine + " - QA Non Continuous Count = " + aData.NonContinuousCount);
        }
    }

    /**
     * Initialises and starts the swipe reader
     */
    public void StartReader() {

        try
		{
			// Initialise the error handler and logging to record errors that
			// occur
			com.mmm.readers.modules.Reader.SetErrorHandler(
				this	// aErrorHandler
			);
			com.mmm.readers.ErrorCode lResult =
				com.mmm.readers.modules.Reader.InitialiseLogging(
					true,
					1,
					-1,
					"SwipeApplet.log"
				);

			if (lResult == com.mmm.readers.ErrorCode.NO_ERROR_OCCURRED)
			{
				// Now initialise the Swipe Reader
				lResult = com.mmm.readers.modules.Swipe.Reader.Initialise(
					this,	// aDataHandler
					this	// aEventHandler
				);
			}

			if (lResult == com.mmm.readers.ErrorCode.NO_ERROR_OCCURRED)
			{
			    puStatus.setText("Swipe Reader initialised");
                            AddMsgToMsgList("Swipe Reader initialised");
			}
			else
			{
                            puStatus.setText("Swipe Reader FAILED to initialise");
                            AddMsgToMsgList("FAILED TO INITIALISE! - " + lResult.toString());

                        }
		}
		catch (java.security.AccessControlException ex)
		{
                    javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "This applet was built against a different \n" +
                        "mmmreader.jar to the one that it is running against"
                    );
                    ex.printStackTrace(); 
		}     
        }

    /**
     * Stops reader on application exit
     */
    @Override
    public void stop() {

        puStatus.setText("Closing Down Reader");
        com.mmm.readers.modules.Reader.ShutdownLogging();
        com.mmm.readers.modules.Swipe.Reader.Shutdown();
            
    }
    
    
    
}
