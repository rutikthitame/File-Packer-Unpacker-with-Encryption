package MarvellousPackerUnpacker;
import MarvellousPackerUnpacker.BlowfishEncryption;

import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

/////////////////////////////////////////////////////////////////////////
//
//  Class :         MarvellousUnpacker
//  Description :   Extracts individual files from a packed file created 
//                  by MarvellousPacker. Each file is restored by reading 
//                  its 100-byte header (filename + size) and decrypting 
//                  its contents using Blowfish encryption.
//  Author :        Rutik Shivaji Thitame
//
/////////////////////////////////////////////////////////////////////////

public class MarvellousUnpacker
{
    private String PackName;   // Name of packed file to unpack
    private int key1;          // First encryption key
    private int key2;          // Second encryption key

    /////////////////////////////////////////////////////////////////////////////
    //
    //  Function Name   : MarvellousUnpacker (Constructor)
    //  Function Date   : 16/09/2025
    //  Function Author : Rutik Shivaji Thitame
    //  Parameters      : [IN] String A -> Name of packed file
    //                    [IN] int key1 -> First encryption key
    //                    [IN] int key2 -> Second encryption key
    //  Description     : Initializes packed file name and encryption keys 
    //                    for unpacking activity.
    //  Returns         : None
    //
    /////////////////////////////////////////////////////////////////////////////
    public MarvellousUnpacker(String A,int key1,int key2)
    {
        this.PackName = A;
        this.key1 = key1;
        this.key2 = key2;
    }

    /////////////////////////////////////////////////////////////////////////////
    //
    //  Function Name   : UnpakingActivity
    //  Function Date   : 16/09/2025
    //  Function Author : Rutik Shivaji Thitame
    //  Parameters      : None
    //  Description     : Reads the packed file, extracts each file’s 100-byte 
    //                    header (name and size), and restores the original 
    //                    file contents by decrypting 8-byte blocks using 
    //                    BlowfishEncryption.
    //  Returns         : int
    //                    >0  -> Number of files successfully unpacked
    //                    -1  -> Packed file not found
    //                    -99 -> Unknown exception
    //
    /////////////////////////////////////////////////////////////////////////////
    public int UnpakingActivity()
    {
        try
        {
            String Header;
            File fobjnew;
        
            int FileSize, iRet, iCountFile = 0;
        
            // Blowfish encryption object (for decryption)
            BlowfishEncryption bEncryption = new BlowfishEncryption(key1, key2);
        
            File fobj = new File(PackName);
        
            // If packed file is not present
            if (!fobj.exists())
            {
                return -1;
            }
        
            FileInputStream fiobj = new FileInputStream(fobj);
        
            // Buffer to read header (100 bytes fixed)
            byte HeaderBuffer[] = new byte[100];
        
            // Read each file’s header + encrypted contents
            while ((iRet = fiobj.read(HeaderBuffer, 0, 100)) != -1)
            {
                // Extract filename and filesize from header
                Header = new String(HeaderBuffer).trim();
                String Tokens[] = Header.split(" ");
                
                fobjnew = new File(Tokens[0]);
                fobjnew.createNewFile();
            
                FileSize = Integer.parseInt(Tokens[1]);
            
                byte Buffer[] = new byte[8];
                int byteRead;
            
                FileOutputStream foobj = new FileOutputStream(fobjnew);
            
                // Read encrypted data in 8-byte blocks
                while (FileSize > 0 && (byteRead = fiobj.read(Buffer)) != -1)
                {
                    byte[] decrypted = bEncryption.DecryptData(Buffer);
                
                    if (FileSize >= 8)
                    {
                        foobj.write(decrypted, 0, 8);
                        FileSize -= 8;
                    }
                    else
                    {
                        // Write only remaining bytes for last block
                        foobj.write(decrypted, 0, FileSize);
                        FileSize = 0;
                    }
                }
            
                foobj.close();
                iCountFile++;
            }
        
            fiobj.close();
            return iCountFile; // success, number of files unpacked
        }
        catch (Exception eobj)
        {
            eobj.printStackTrace();
            return -99; // unknown error
        }
    }

}
