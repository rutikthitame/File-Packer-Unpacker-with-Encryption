#  File Packer & Unpacker with Encryption  

##  Technology  
- **Language:** Java  
- **Libraries/Frameworks:** Java I/O Streams, Swing  

---

##  Project Overview  
This project is a **Java-based file utility tool** that provides functionality for:  
- Packing multiple files into a **single archive**.  
- Unpacking them back with all **metadata preserved**.  
- Adding **encryption** for enhanced data security.  

The project also features a **Graphical User Interface (GUI)** for easy interaction.  

---

##  Key Features  

###  File Packing  
- Combines multiple regular files into a **single archive file**.  
- Stores **metadata** (file name, size, timestamp) along with file content.  

###  File Unpacking  
- Extracts individual files from the packed archive.  
- Restores all **original metadata** and file structure.  

###  Data Security  
- Built-in **encryption and decryption** to protect packed files.  

###  Graphical User Interface (GUI)  
- User-friendly interface built in **Java Swing**.  
- Provides simple options for selecting files, encrypting, packing, and unpacking.  

###  Cross-Platform  
- Runs on any system with a **Java Runtime Environment (JRE)**.  

---

##  Learning Outcomes  
- Practical experience with **Java I/O Streams** and **File Handling APIs**.  
- Implementation of **metadata management** during file operations.  
- Strong understanding of **encryption/decryption techniques** in Java.  
- Hands-on experience with **GUI development** (Swing).  
- Insight into **archiving and compression utilities** (similar to ZIP/TAR).  

---

##  Example Usage (Console Flow)  

### Packing files  
```bash
java MarvellousPackerUnpackerMain Demo MarvellousPack.txt
