import MarvellousPackerUnpacker.MarvellousPacker;
import MarvellousPackerUnpacker.MarvellousUnpacker;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;


/////////////////////////////////////////////////////////////////////////
//
//  Class :         MarvellousPackerUnpackerGUI
//  Description:    GUI-based implementation for Packing and Unpacking
//                  files using MarvellousPacker and MarvellousUnpacker.
//                  Provides two tabs: Packer and Unpacker.
//  Author :        Rutik Shivaji Thitame
//  Date   :        16/09/2025
//
/////////////////////////////////////////////////////////////////////////


class MarvellousPackerUnpackerMain
{

    ///////////////////////////////////////////////////////////////////////////////
    //
    //  Function Name   : main
    //  Function Date   : 16/09/2025
    //  Function Author : Rutik Shivaji Thitame
    //  Parameters      : [IN] String[] A
    //                    Command-line arguments (not used in this program)
    //  Description     : Entry point for the GUI application. Creates a JFrame,
    //                    initializes tabs (Packer & Unpacker), and sets up
    //                    event handlers for file packing and unpacking.
    //  Returns         : void
    //
    ///////////////////////////////////////////////////////////////////////////////
    /// 
    public static void main(String A[])
    {
        JFrame fobj = new JFrame("Marvellous Packer-Unpacker");

        // Buttons
        JButton bobj1 = new JButton("Pack");
        bobj1.setBounds(200, 230, 100, 40);

        JButton bobj2 = new JButton("Unpack");
        bobj2.setBounds(200, 230, 100, 40);

        // TabbedPane for switching between Packer and Unpacker
        JTabbedPane tbobj = new JTabbedPane();
        tbobj.setBounds(50, 20, 500, 400);

        JPanel panel1 = new JPanel();
        panel1.setLayout(null);

        JPanel panel2 = new JPanel();
        panel2.setLayout(null);

        // -------------------------- Panel 1 (Packer) --------------------------

        JLabel dirLable = new JLabel("Enter Directory Name : ");
        dirLable.setBounds(30, 10, 150, 50);

        JLabel packLable1 = new JLabel("Enter Pack file Name : ");
        packLable1.setBounds(30, 40, 150, 50);

        JLabel key1Lable1 = new JLabel("Enter first key : ");
        key1Lable1.setBounds(30, 85, 150, 50);

        JLabel key2Lable1 = new JLabel("Enter second key : ");
        key2Lable1.setBounds(30, 115, 150, 50);

        JTextField dirField = new JTextField("",30);
        dirField.setBounds(180, 20, 150, 30);

        JTextField packField1 = new JTextField("",30);
        packField1.setBounds(180, 55, 150, 30);

        JTextField key1Field1 = new JTextField("",30);
        key1Field1.setBounds(180, 95, 150, 30);

        JTextField key2Field1 = new JTextField("",30);
        key2Field1.setBounds(180, 130, 150, 30);


        // -------------------------- Panel 2 (Unpacker) --------------------------

        JLabel packLable2 = new JLabel("Enter Pack file Name : ");
        packLable2.setBounds(30, 40, 150, 50);

        JLabel key1Lable2 = new JLabel("Enter first key : ");
        key1Lable2.setBounds(30, 85, 150, 50);

        JLabel key2Lable2 = new JLabel("Enter second key : ");
        key2Lable2.setBounds(30, 115, 150, 50);

        JTextField packField2 = new JTextField("",30);
        packField2.setBounds(180, 55, 150, 30);

        JTextField key1Field2 = new JTextField("",30);
        key1Field2.setBounds(180, 95, 150, 30);

        JTextField key2Field2 = new JTextField("",30);
        key2Field2.setBounds(180, 130, 150, 30);

        ///////////////////////////////////////////////////////////////////////////////
        //
        //  Function Name   : actionPerformed (Pack button)
        //  Function Date   : 16/09/2025
        //  Function Author : Rutik Shivaji Thitame
        //  Parameters      : [IN] ActionEvent aobj
        //                    Action event triggered when "Pack" button is clicked
        //  Description     : Reads input directory name, pack file name, and keys.
        //                    Calls MarvellousPacker to pack files into a single
        //                    file with encryption. Displays result in message box.
        //  Returns         : void
        //
        ///////////////////////////////////////////////////////////////////////////////
        
        bobj1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent aobj)
            {
                String DirName = dirField.getText(); 
                String PackName = packField1.getText();
                String strkey1 = key1Field1.getText();
                String strkey2 = key2Field1.getText();
                int key1 = Integer.parseInt(strkey1);
                int key2 = Integer.parseInt(strkey2);


                MarvellousPacker mpobj = new MarvellousPacker(DirName, PackName,key1,key2);
                int result = mpobj.PackingActivity();

                switch (result) {
                    case -1:
                        JOptionPane.showMessageDialog(fobj,"Error: Pack file already exists!");
                        break;
                    case -2:
                        JOptionPane.showMessageDialog(fobj,"Error: Directory not found!");
                        break;
                    case -3:
                        JOptionPane.showMessageDialog(fobj,"Error: No files in directory!");
                        break;
                    case -99:
                        JOptionPane.showMessageDialog(fobj,"Error: Unknown exception occurred!");
                        break;
                    default:
                        JOptionPane.showMessageDialog(fobj,"Packing successful! Files packed: " + result);
                        dirField.setText("");
                        packField1.setText("");
                        key1Field1.setText("");
                        key2Field2.setText("");
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////
        //
        //  Function Name   : actionPerformed (Unpack button)
        //  Function Date   : 16/09/2025
        //  Function Author : Rutik Shivaji Thitame
        //  Parameters      : [IN] ActionEvent aeobj
        //                    Action event triggered when "Unpack" button is clicked
        //  Description     : Reads pack file name and keys. Calls MarvellousUnpacker
        //                    to extract files from packed file. Displays result in
        //                    message box.
        //  Returns         : void
        //
        ///////////////////////////////////////////////////////////////////////////////

        bobj2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent aeobj)
            {
                String PackName = packField2.getText();
                String strkey1 = key1Field2.getText();
                String strkey2 = key2Field2.getText();
                int key1 = Integer.parseInt(strkey1);
                int key2 = Integer.parseInt(strkey2);

                MarvellousUnpacker muobj = new MarvellousUnpacker(PackName,key1,key2);
                int result = muobj.UnpakingActivity();

                switch (result) {
                    case -1:
                        JOptionPane.showMessageDialog(fobj,"Error: Pack file not found!");
                        break;
                    case -99:
                        JOptionPane.showMessageDialog(fobj,"Error: Unknown exception occurred!");
                        break;
                    default:
                        JOptionPane.showMessageDialog(fobj,"Unpacking successful! Files unpacked: " + result);
                        dirField.setText("");
                        packField2.setText("");
                        key1Field2.setText("");
                        key2Field2.setText("");
                }
            }
        });


        // Adding components to Panel 1
        panel1.add(dirLable);
        panel1.add(packLable1);
        panel1.add(key1Lable1);
        panel1.add(key2Lable1);
        panel1.add(bobj1);
        panel1.add(dirField);
        panel1.add(packField1);
        panel1.add(key1Field1);
        panel1.add(key2Field1);

        // Adding components to Panel 2
        panel2.add(packField2);
        panel2.add(key2Field2);
        panel2.add(key1Field2);
        panel2.add(bobj2);
        panel2.add(packLable2);
        panel2.add(key1Lable2);
        panel2.add(key2Lable2);
                
        // Add panels to tabbed pane
        tbobj.addTab("Packer",panel1);
        tbobj.addTab("Unpacker",panel2);

         // Add tabbed pane to frame
        fobj.add(tbobj);
        fobj.setSize(600,500);
        fobj.setLayout(null);
        fobj.setVisible(true);
        fobj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
    }
}

