 package SwipeReader;

 import com.mmm.readers.AAMVAData;
 import com.mmm.readers.AAMVAParsedData;
 import com.mmm.readers.CodelineData;
 import com.mmm.readers.DataFormat;
 import com.mmm.readers.Date;
 import com.mmm.readers.ErrorCode;
 import com.mmm.readers.ErrorHandler;
 import com.mmm.readers.FullPage.EventCode;
 import com.mmm.readers.FullPage.EventHandler;
 import com.mmm.readers.interop.Marshal;
 import com.mmm.readers.modules.Swipe.AtbData;
/*     */ import com.mmm.readers.modules.Swipe.AtbTrackData;
/*     */ import com.mmm.readers.modules.Swipe.DataHandler;
/*     */ import com.mmm.readers.modules.Swipe.DataToSend;
/*     */ import com.mmm.readers.modules.Swipe.MsrData;
/*     */ import com.mmm.readers.modules.Swipe.RTEQAData;
/*     */ import com.mmm.readers.modules.Swipe.RTEQALineData;
/*     */ import com.mmm.readers.modules.Swipe.SwipeBarcodeCode128Data;
/*     */ import com.mmm.readers.modules.Swipe.SwipeBarcodeCode39Data;
/*     */ import com.mmm.readers.modules.Swipe.SwipeBarcodePDF417Data;
/*     */ import com.mmm.readers.modules.Swipe.SwipeItem;
          import java.awt.Color;
/*     */ import java.awt.Container;
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.Font;
/*     */ import java.awt.List;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle;
import javax.swing.LayoutStyle.ComponentPlacement;
 
public class MainDialog extends JFrame implements ErrorHandler, DataHandler, EventHandler
{
    private JCheckBox CheckBoxAAMVAParseMSR;
    private JButton btnInitialise;
    private JButton btnShutdown;
    private List puMsgList;
    
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

    public MainDialog()
    {
        initComponents();
    }

    static 
    {
        System.out.println("DEPENDENCIES INJECTED");
        
        try 
        {
            //String setProperty = System.setProperty( "java.library.path", "D:\\PassportTrying\\PassportTry2" );
            String libpath = System.getProperty("java.library.path");
            libpath = "D:\\PassportTrying\\PassportTry2\\dist;D:\\PassportTrying\\PassportTry2\\dist\\lib;" + libpath;
            System.setProperty("java.library.path",libpath);
    	
            System.out.println("JAVA PATH "+System.getProperty("java.library.path"));
            
            
            System.loadLibrary("ErrorHandlerDll");
            System.loadLibrary("DeviceDll");
            System.loadLibrary("SettingsDll");
            System.loadLibrary("MMMReaderLowLevelAPI");
            System.loadLibrary("MMMReaderLowLevelJava");
            
            
            //System.load("D:\\PassportTrying\\PassportTry2\\libs\\ErrorHandlerDll.dll");
            //System.load("D:\\PassportTrying\\PassportTry2\\libs\\DeviceDll.dll");
            //System.load("D:\\PassportTrying\\PassportTry2\\libs\\SettingsDll.dll");
            //System.load("D:\\PassportTrying\\PassportTry2\\libs\\MMMReaderLowLevelAPI.dll");
            //System.load("D:\\PassportTrying\\PassportTry2\\libs\\MMMReaderLowLevelJava.dll");
        } 
        catch (UnsatisfiedLinkError e) 
        {
          System.out.println("Native code library failed to load.\n" + e);
          System.exit(1);
        }
    }
    
    
    private void initComponents()
    {
        this.btnInitialise = new JButton();
        this.btnShutdown = new JButton();
        this.puMsgList = new List();
        this.CheckBoxAAMVAParseMSR = new JCheckBox();

        setDefaultCloseOperation(3);
        setTitle("3M Swipe Reader Messages");
        this.btnInitialise.setText("Initialise");
        this.btnShutdown.setText("Shutdown");

        this.puMsgList.setFont(new Font("Courier New", 0, 12));
        this.CheckBoxAAMVAParseMSR.setText("AAMVA Parse MSR");
        this.CheckBoxAAMVAParseMSR.addItemListener(new ItemListener() {
  
        public void itemStateChanged(ItemEvent evt) {
            MainDialog.this.CheckBoxAAMVAParseMSRItemStateChanged(evt);
        }
        });
           
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.btnInitialise, GroupLayout.Alignment.LEADING, -1, -1, 32767).addComponent(this.btnShutdown, GroupLayout.Alignment.LEADING, -1, -1, 32767)).addComponent(this.CheckBoxAAMVAParseMSR)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.puMsgList, -1, 493, 32767).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.puMsgList, -1, 375, 32767).addGroup(layout.createSequentialGroup().addComponent(this.btnInitialise).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.btnShutdown).addGap(65, 65, 65).addComponent(this.CheckBoxAAMVAParseMSR))).addContainerGap()));
        pack();
    }

    private void CheckBoxAAMVAParseMSRItemStateChanged(ItemEvent evt) 
    {
        DataToSend lDataToSend = new DataToSend(); 
        com.mmm.readers.modules.Swipe.Reader.GetDataToSend(lDataToSend);
        if (this.CheckBoxAAMVAParseMSR.isSelected())
             lDataToSend.puAAMVA = 1;
        else 
             lDataToSend.puAAMVA = 0;    
        com.mmm.readers.modules.Swipe.Reader.SetDataToSend(lDataToSend);
    }

    public  void start()
    {
        System.out.println("STARTED");
        try
        {
            com.mmm.readers.modules.Reader.SetErrorHandler(this);
            ErrorCode lResult = com.mmm.readers.modules.Reader.InitialiseLogging(true, 5, -1, "SwipeMessagesJava.log");
            if (lResult == ErrorCode.NO_ERROR_OCCURRED)
            {
                lResult = com.mmm.readers.modules.Swipe.Reader.Initialise(this, this);
            }
            if (lResult == ErrorCode.NO_ERROR_OCCURRED)
            {
                AddMsgToMsgList("Swipe Reader initialised");
            }
            else
            {
                AddMsgToMsgList("FAILED TO INITIALISE! - " + lResult.toString());
            }
            if (lResult == ErrorCode.NO_ERROR_OCCURRED)
            {
                DataToSend lDataToSend = new DataToSend();
                lResult = com.mmm.readers.modules.Swipe.Reader.GetDataToSend(lDataToSend);

                if (lResult == ErrorCode.NO_ERROR_OCCURRED)
                {
                    AddMsgToMsgList("Successfully read optional data to send. AAMVA data =" + lDataToSend.puAAMVA);
                    if (lDataToSend.puAAMVA != 0)
                    System.out.println("AAMVA Selected");
                }
                else
                {
                    AddMsgToMsgList("Failed to read DataToSend - " + lResult.toString());
                }
             }
        }
        catch (Throwable ex)
        {
            AddMsgToMsgList("Unable to initialise " + ex.toString());
            BufferedWriter writer = null;
            File logFile = new File("E://Error.txt");
            try 
            {
                writer = new BufferedWriter(new FileWriter(logFile));
                writer.write("Throws Error Detail: "+ ex.toString());
                System.out.println("File saved to : "+logFile.getCanonicalPath());
                writer.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            
            ex.printStackTrace();
            
            
        }
    }                
   
    public static void main(String[] args)
   {
               
     EventQueue.invokeLater(new Runnable() {
        public void run() 
        {  
        new MainDialog().setVisible(true);
        }
     });
    MainDialog main = new MainDialog();
    main.start();
  }

    private void AddMsgToMsgList(String aMsg)
   {
        this.puMsgList.add(aMsg);
        this.puMsgList.makeVisible(this.puMsgList.getItemCount() - 1);
   }
 
   public void OnMMMReaderError(ErrorCode aErrorCode, String aErrorMsg)
   {
        AddMsgToMsgList("Error: " + aErrorCode.toString() + " - " + aErrorMsg);
        
        BufferedWriter writer = null;
        File logFile = new File("E://Error.txt");
        try 
        {
            writer = new BufferedWriter(new FileWriter(logFile));
            writer.write(aErrorCode.toString());
            System.out.println("File saved to : "+logFile.getCanonicalPath());
            writer.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
   }

   public void OnFullPageReaderEvent(EventCode aEventType)
   {
        AddMsgToMsgList("Event: " + aEventType.toString());
   }
 
   public void OnSwipeReaderData(SwipeItem aDataItem, DataFormat aFormat, int aDataLen, byte[] aDataPtr)
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
                DisplayStructData(aDataItem, aDataLen, aDataPtr);
                break;
            }
        }
    }
  
    private void DisplayStructData(com.mmm.readers.modules.Swipe.SwipeItem aDataItem, int aDataLen, byte aDataPtr[])
    {
        switch (aDataItem) 
        {
            case OCR_CODELINE :
            {
                com.mmm.readers.CodelineData lCodeline =
                                com.mmm.readers.modules.Swipe.Reader.ConstructCodelineData(aDataPtr);

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
                File logFile = new File("E://"+prDocumentNumber+".txt");
                try 
                {
                    writer = new BufferedWriter(new FileWriter(logFile));
                    writer.write('"' + prDocumentNumber + '"'+","+'"' + prSurname + '"'+","+'"' + prFirstName+" "+prSecondName + '"'+","+'"' + prNationality + '"'+","+'"' + prDateOfBirth+ '"');
                    System.out.println("File saved to : "+logFile.getCanonicalPath());
                    writer.close();
                    //stop();
                }
                catch (IOException me){}
                
            }
            break;
            case AAMVA_DATA :
            {
                System.out.println("AAMVA DATA");
                
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
                File logFile = new File("E://"+prDocumentNumber+".txt");
                try 
                {
                    writer = new BufferedWriter(new FileWriter(logFile));
                    writer.write('"' + prDocumentNumber + '"'+","+'"' + prSurname + '"'+","+'"' + prFirstName+" "+prSecondName + '"'+","+'"' + prNationality + '"'+","+'"' + prDateOfBirth+ '"');
                    System.out.println("File saved to : "+logFile.getCanonicalPath());
                    AddMsgToMsgList("File saved to : "+logFile.getCanonicalPath());
                    writer.close();
                    //stop(); 
                }
                catch (IOException me){}              
            }
            break;
        }
    }
    
    public void stop()
    {
        System.out.println("Reader Stopped");
        com.mmm.readers.modules.Swipe.Reader.Shutdown();
        com.mmm.readers.modules.Reader.ShutdownLogging();
    }
}