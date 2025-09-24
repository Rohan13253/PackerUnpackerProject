package PackerUnpacker;

import java.util.*;
import java.io.*;

public class Unpacker
{
    public static void main(String[] args) 
    {
        try 
        {   
            Scanner sobj = new Scanner(System.in);

            System.out.println("Enter the name of file which contains packed data : ");
            String PackName = sobj.nextLine();

            UnpackerX mobj = new UnpackerX(PackName);

            mobj.UnpackingActivity();


        } 
        catch (Exception eobj) 
        {}
    }// End of main
}//End of Unpacker class
