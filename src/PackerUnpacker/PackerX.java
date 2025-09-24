package PackerUnpacker;

import java.util.*;
import java.io.*;

////////////////////////////////////////////////////////////////////////
//
//  Class Name :       PackerX
//  Description :      Creates a packed file from a directory by
//                     traversing files, adding headers, and combining
//                     them into one file.
//  Author :           Rohan Murlidhar Pawar
//  Date :             11/08/2025
//
///////////////////////////////////////////////////////////////////////
public class PackerX
{
    private String PackName;
    private String DirName;

////////////////////////////////////////////////////////////////////////
//
//  Constructor :   PackerX(String A, String B)
//  Description :   Initializes the packed file name and directory name.
//  Author :        Rohan Murlidhar Pawar
//  Date :          11/08/2025
//
////////////////////////////////////////////////////////////////////////    
    public PackerX(String A, String B)
    {
        this.PackName = A;
        this.DirName = B;
    }

///////////////////////////////////////////////////////////////////////
//
//  Function Name :     PackingActivity
//  Description :       It is used to creates a packed file from the given directory.
//  Input :             None
//  Output :            It returns true if packing is successful, false otherwise
//  Author :            Rohan Murlidhar Pawar
//  Date :              11/08/2025
//
///////////////////////////////////////////////////////////////////////
    public boolean PackingActivity()
    {
        try
        {

            int i = 0, j = 0, iRet = 0, iCountFile = 0;

            File fobj = new File(DirName);

            // Check the existance of Directory
            if((fobj.exists()) && (fobj.isDirectory()))
            {
                System.out.println(DirName + " is succesfully opened");

                File PackObj = new File(PackName);

                // Create a packed file
                boolean bRet = PackObj.createNewFile();

                if(bRet == false)
                {
                    System.out.println("Unable to create pack file");
                    return false;
                }

                System.out.println("Packed file gets succesfully created with name : "+PackName);
            
                // Retive all files from directory
                File Arr[] = fobj.listFiles();

                // Packed file object
                FileOutputStream foobj = new FileOutputStream(PackObj);
                
                // Buffer for read and write activity
                byte Buffer[] = new byte[1024];

                String Header = null;

                // Directory traversal
                for(i = 0; i < Arr.length; i++)
                {
                    Header = Arr[i].getName() + " " + Arr[i].length();
                
                    // Loop to form 100 bytes header
                    for(j = Header.length(); j < 100; j++)
                    {
                        Header = Header + " ";
                    }
                    
                    // Write header into pacekd file
                    foobj.write(Header.getBytes());

                    // Open file from directoy for reading
                    FileInputStream fiobj = new FileInputStream(Arr[i]);

                    // Write contents of file into packed file
                    while((iRet = fiobj.read(Buffer)) != -1)
                    {
                        foobj.write(Buffer,0,iRet);
                        
                        System.out.println("File name Scanned : "+Arr[i].getName());
                        System.out.println("File size read is : "+iRet);
                    }

                    fiobj.close();
                    iCountFile++;
                }   

                System.out.println("Packing activity done");
            
                System.out.println("Total files Packed : "+iCountFile);
                return true;
            }
            else
            {
                System.out.println("There is no such directory");
                return false;
            }
        } // End of try
        catch(Exception eobj)
        {
            return false;
        }
    } // End of PackingActivity function
} // End of PackerX class
