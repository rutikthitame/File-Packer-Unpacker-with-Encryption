package MarvellousPackerUnpacker;
import MarvellousPackerUnpacker.BlowfishEncryption;

import java.util.*;
import java.io.*;

/////////////////////////////////////////////////////////////////////////
//
//  Class :         MarvellousPacker
//  Description :   Creates a single packed file from all files inside 
//                  a given directory. Each file is stored with a fixed 
//                  size header (100 bytes) containing its name and size,
//                  followed by the file’s encrypted contents (Blowfish).
//  Author :        Rutik Shivaji Thitame
//
/////////////////////////////////////////////////////////////////////////

public class MarvellousPacker
{
    private String PackName;   // Name of the output packed file
    private String DirName;    // Directory containing files to pack
    private int key1;          // First encryption key
    private int key2;          // Second encryption key

    /////////////////////////////////////////////////////////////////////////////
    //
    //  Function Name   : MarvellousPacker (Constructor)
    //  Function Date   : 16/09/2025
    //  Function Author : Rutik Shivaji Thitame
    //  Parameters      : [IN] String A   -> Directory name
    //                    [IN] String B   -> Packed file name
    //                    [IN] int key1   -> First encryption key
    //                    [IN] int key2   -> Second encryption key
    //  Description     : Initializes directory, packed file name, 
    //                    and encryption keys for packing activity.
    //  Returns         : None
    //
    /////////////////////////////////////////////////////////////////////////////
    public MarvellousPacker(String A, String B,int key1,int key2)
    {   
        this.PackName = B;
        this.DirName = A;
        this.key1 = key1;
        this.key2 = key2;
    }

    /////////////////////////////////////////////////////////////////////////////
    //
    //  Function Name   : PackingActivity
    //  Function Date   : 16/09/2025
    //  Function Author : Rutik Shivaji Thitame
    //  Parameters      : None
    //  Description     : Traverses the given directory, reads all files, 
    //                    and stores them inside a single packed file. 
    //                    Each file is stored with a 100-byte header 
    //                    (filename + size) followed by encrypted contents.
    //  Returns         : int
    //                    >0  -> Number of files successfully packed
    //                    -1  -> Packed file already exists
    //                    -2  -> Directory not found
    //                    -3  -> No files in directory
    //                    -99 -> Unknown exception
    //
    /////////////////////////////////////////////////////////////////////////////
    public int PackingActivity()
    {
        try
        {
            int i = 0, j = 0, iRet = 0, iCountFile = 0;

            // Create Blowfish encryption object
            BlowfishEncryption bEncryption = new BlowfishEncryption(key1, key2);

            File fobj = new File(DirName);

            // Check if directory exists
            if (fobj.exists() && fobj.isDirectory())
            {
                File Packobj = new File(PackName);

                // Try to create packed file (fails if already exists)
                boolean bRet = Packobj.createNewFile();

                if (bRet == false)
                {
                    return -1;   // packed file already exists
                }

                // Get list of all files from directory
                File Arr[] = fobj.listFiles();

                if (Arr == null || Arr.length == 0)
                {
                    return -3;  // no files inside directory
                }

                // Open output stream for packed file
                FileOutputStream foobj = new FileOutputStream(Packobj);

                // Buffer for reading 8 bytes at a time (Blowfish block size = 64 bits)
                byte Buffer[] = new byte[8];

                String Header;

                // Process each file inside the directory
                for (i = 0; i < Arr.length; i++)
                {
                    // Create header containing filename and file size
                    Header = Arr[i].getName() + " " + Arr[i].length();

                    // Pad header to make it exactly 100 bytes
                    for (j = Header.length(); j < 100; j++)
                    {
                        Header = Header + " ";
                    }

                    // Write header (plain text) into packed file
                    foobj.write(Header.getBytes("UTF-8"));

                    // Open current file for reading
                    FileInputStream fiobj = new FileInputStream(Arr[i]);

                    // Read file data block by block (8 bytes each)
                    while ((iRet = fiobj.read(Buffer)) != -1)
                    {
                        if (iRet < 8)
                        {
                            // Padding for last block (if not full 8 bytes)
                            for (int k = iRet; k < 8; k++)
                            {
                                Buffer[k] = 0;
                            }
                        }

                        // Encrypt the 8-byte block
                        byte[] encrypted = bEncryption.EncryptData(Buffer);

                        // Write encrypted block into packed file
                        foobj.write(encrypted, 0, encrypted.length);
                    }

                    // Close input file
                    fiobj.close();
                    iCountFile++;
                }

                // Close packed file
                foobj.close();

                return iCountFile;   // success, return number of files packed
            }
            else
            {
                return -2;  // directory not found
            }
        }
        catch (Exception eobj)
        {
            eobj.printStackTrace();
            return -99;  // unknown error occurred
        }
    }

}
