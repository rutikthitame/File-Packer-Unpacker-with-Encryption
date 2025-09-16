package MarvellousPackerUnpacker;

/////////////////////////////////////////////////////////////////////////
//
//  Class :         BlowfishEncryption
//  Description:    Simplified implementation of the Blowfish encryption 
//                  algorithm. Provides key initialization, block-level 
//                  encryption/decryption, and data-level encryption/
//                  decryption for 8-byte aligned input.
//  Author :        Rutik Shivaji Thitame
//  Date   :        16/09/2025
//
/////////////////////////////////////////////////////////////////////////

public class BlowfishEncryption 
{
    private int[] P;           // P-array (18 entries)
    private int[][] S;         // S-boxes (4 * 256 entries)
    private int key1, key2;    // Two integer keys for simplicity

    /////////////////////////////////////////////////////////////////////////////
    //
    //  Function Name   : BlowfishEncryption (Constructor)
    //  Function Date   : 16/09/2025
    //  Function Author : Rutik Shivaji Thitame
    //  Parameters      : [IN] int a - First encryption key
    //                    [IN] int b - Second encryption key
    //  Description     : Initializes P-array and S-boxes using provided keys.
    //  Returns         : None (constructor)
    //
    /////////////////////////////////////////////////////////////////////////////
    public BlowfishEncryption(int a, int b) 
    {
        this.P = new int[18];
        this.S = new int[4][256];
        this.key1 = a;
        this.key2 = b;

        InitializeKeys(key1, key2);
    }

    /////////////////////////////////////////////////////////////////////////////
    //
    //  Function Name   : InitializeKeys
    //  Function Date   : 16/09/2025
    //  Function Author : Rutik Shivaji Thitame
    //  Parameters      : [IN] int key1 - First encryption key
    //                    [IN] int key2 - Second encryption key
    //  Description     : Populates P-array and S-boxes with values derived
    //                    from the provided keys. (Simplified expansion logic)
    //  Returns         : void
    //
    /////////////////////////////////////////////////////////////////////////////
    private void InitializeKeys(int key1, int key2) 
    {
        for (int i = 0; i < 18; i++) 
        {
            P[i] = key1 + i;
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 256; j++) 
            {
                S[i][j] = key2 + j;
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////
    //
    //  Function Name   : F
    //  Function Date   : 16/09/2025
    //  Function Author : Rutik Shivaji Thitame
    //  Parameters      : [IN] int x - 32-bit input value
    //  Description     : Blowfish F-function. Splits input into 4 bytes,
    //                    performs S-box lookups, additions, and XORs.
    //  Returns         : int - 32-bit result
    //
    /////////////////////////////////////////////////////////////////////////////
    private int F(int x) 
    {
        int a = (x >> 24) & 0xFF;
        int b = (x >> 16) & 0xFF;
        int c = (x >> 8) & 0xFF;
        int d = x & 0xFF;

        return ((S[0][a] + S[1][b]) ^ S[2][c]) + S[3][d];
    }

    /////////////////////////////////////////////////////////////////////////////
    //
    //  Function Name   : EncryptBlock
    //  Function Date   : 16/09/2025
    //  Function Author : Rutik Shivaji Thitame
    //  Parameters      : [IN] int L - Left 32-bit half of the block
    //                    [IN] int R - Right 32-bit half of the block
    //  Description     : Encrypts a 64-bit block using Blowfish round function.
    //  Returns         : int[] - Encrypted {L, R}
    //
    /////////////////////////////////////////////////////////////////////////////
    public int[] EncryptBlock(int L, int R) 
    {
        for (int i = 0; i < 16; i++) 
        {
            L ^= P[i];
            R ^= F(L);

            int temp = L;
            L = R;
            R = temp;
        }

        int temp = L;
        L = R;
        R = temp;

        R ^= P[16];
        L ^= P[17];

        return new int[]{L, R};
    }

    /////////////////////////////////////////////////////////////////////////////
    //
    //  Function Name   : DecryptBlock
    //  Function Date   : 16/09/2025
    //  Function Author : Rutik Shivaji Thitame
    //  Parameters      : [IN] int L - Left 32-bit half of the block
    //                    [IN] int R - Right 32-bit half of the block
    //  Description     : Decrypts a 64-bit block using inverse Blowfish rounds.
    //  Returns         : int[] - Decrypted {L, R}
    //
    /////////////////////////////////////////////////////////////////////////////
    public int[] DecryptBlock(int L, int R) 
    {
        for (int i = 17; i > 1; i--) 
        {
            L ^= P[i];
            R ^= F(L);

            int temp = L;
            L = R;
            R = temp;
        }

        int temp = L;
        L = R;
        R = temp;

        R ^= P[1];
        L ^= P[0];

        return new int[]{L, R};
    }

    /////////////////////////////////////////////////////////////////////////////
    //
    //  Function Name   : EncryptData
    //  Function Date   : 16/09/2025
    //  Function Author : Rutik Shivaji Thitame
    //  Parameters      : [IN] byte[] input - Plaintext byte array
    //  Description     : Encrypts data in 8-byte (64-bit) blocks.
    //  Returns         : byte[] - Encrypted byte array
    //
    /////////////////////////////////////////////////////////////////////////////
    public byte[] EncryptData(byte[] input) 
    {
        byte[] output = new byte[input.length];

        for (int i = 0; i < input.length; i += 8) 
        {
            int L = (input[i] & 0xFF) << 24 | (input[i + 1] & 0xFF) << 16 |
                    (input[i + 2] & 0xFF) << 8 | (input[i + 3] & 0xFF);
            int R = (input[i + 4] & 0xFF) << 24 | (input[i + 5] & 0xFF) << 16 |
                    (input[i + 6] & 0xFF) << 8 | (input[i + 7] & 0xFF);

            int[] result = EncryptBlock(L, R);
            L = result[0];
            R = result[1];

            output[i]     = (byte) (L >> 24);
            output[i + 1] = (byte) (L >> 16);
            output[i + 2] = (byte) (L >> 8);
            output[i + 3] = (byte) (L);
            output[i + 4] = (byte) (R >> 24);
            output[i + 5] = (byte) (R >> 16);
            output[i + 6] = (byte) (R >> 8);
            output[i + 7] = (byte) (R);
        }
        return output;
    }

    /////////////////////////////////////////////////////////////////////////////
    //
    //  Function Name   : DecryptData
    //  Function Date   : 16/09/2025
    //  Function Author : Rutik Shivaji Thitame
    //  Parameters      : [IN] byte[] input - Ciphertext byte array
    //  Description     : Decrypts data in 8-byte (64-bit) blocks.
    //  Returns         : byte[] - Decrypted byte array
    //
    /////////////////////////////////////////////////////////////////////////////
    public byte[] DecryptData(byte[] input) 
    {
        byte[] output = new byte[input.length];

        for (int i = 0; i < input.length; i += 8) 
        {
            int L = (input[i] & 0xFF) << 24 | (input[i + 1] & 0xFF) << 16 |
                    (input[i + 2] & 0xFF) << 8 | (input[i + 3] & 0xFF);
            int R = (input[i + 4] & 0xFF) << 24 | (input[i + 5] & 0xFF) << 16 |
                    (input[i + 6] & 0xFF) << 8 | (input[i + 7] & 0xFF);

            int[] result = DecryptBlock(L, R);
            L = result[0];
            R = result[1];

            output[i]     = (byte) (L >> 24);
            output[i + 1] = (byte) (L >> 16);
            output[i + 2] = (byte) (L >> 8);
            output[i + 3] = (byte) (L);
            output[i + 4] = (byte) (R >> 24);
            output[i + 5] = (byte) (R >> 16);
            output[i + 6] = (byte) (R >> 8);
            output[i + 7] = (byte) (R);
        }
        return output;
    }
}
