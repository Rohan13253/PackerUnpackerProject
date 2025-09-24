package PackerUnpacker;

import java.util.*;
import java.io.*;

////////////////////////////////////////////////////////////////////////
//
//  Class Name :       UnpackerX
//  Description :      Extracts individual files from a packed file by
//                     reading headers and restoring the original files.
//  Author :           Rohan Murlidhar Pawar
//  Date :             12/08/2025
//
///////////////////////////////////////////////////////////////////////
/// 
public class UnpackerX
{
    private String PackName;

////////////////////////////////////////////////////////////////////////
//
//  Constructor :   UnpackerX(String A)
//  Description :   Initializes the unpacker with the packed file name.
//  Author :        Rohan Murlidhar Pawar
//  Date :          12/08/2025
//
////////////////////////////////////////////////////////////////////////
    public UnpackerX(String A)
    {
        this.PackName = A;
    }

////////////////////////////////////////////////////////////////////////
//
//  Function Name :     UnpackingActivity
//  Description :       Extracts all files from the packed file using 
//                      headers and restores them to disk.
//  Input :             None
//  Output :            None
//  Author :            Rohan Murlidhar Pawar
//  Date :              12/08/2025
//
////////////////////////////////////////////////////////////////////////
/// 
    public void UnpackingActivity()
    {
        try 
        {
            File fobjnew = null;
            String Header = null;
            int FileSize = 0,iRet = 0,iCountFile = 0;

            File fobj = new File(PackName);

            // If packed file not present 
            if(! fobj.exists())
            {
                System.out.println("Unable to access Packed file");
                return;
            }

            System.out.println("packed file get succesfully open");
            FileInputStream fiobj = new FileInputStream(fobj);

            // Buffer to read header
            byte HeaderBuffer[] = new byte[100];

            // Scan the packed file to extarct the file from it 
            while((iRet = fiobj.read(HeaderBuffer,0,100))!= -1)
            {
                // Convert byte array to string
                Header = new String(HeaderBuffer);

                Header = Header.trim();

                // Tokenize the Header into two parts 
                String Tokens[] = Header.split(" ");

                fobjnew = new File(Tokens[0]);

                // Crete nw file to extract
                fobjnew.createNewFile();

                FileSize = Integer.parseInt(Tokens[1]);

                // Crete new bufer to store files data.
                byte Buffer[] = new byte[FileSize];

                FileOutputStream foobj = new FileOutputStream(fobjnew);

                // Read the data from packed file
                fiobj.read(Buffer,0,FileSize);

                // Write the data into extarcted file
                foobj.write(Buffer,0,FileSize);

                System.out.println("Files unpacked with name: "+Tokens[0]+" having size : "+FileSize);

                iCountFile++;

                foobj.close();

            }// End of while


            System.out.println("Total numbder of files unpacked: "+iCountFile);

           
            fiobj.close();

        
        } // End of try
        catch (Exception eobj) 
        {
            System.out.println("Error during unpacking: " + eobj.getMessage());
            eobj.printStackTrace();
        }

    } // End of UnpackingActivity 
}// End of UnpackerX class