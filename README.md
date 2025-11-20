# File Packer & Unpacker

## Technology
Java Programming

---

## Project Overview
This project is a **Java-based file utility tool** that provides functionality for **packing multiple files into a single archive** and **unpacking them back** with all metadata preserved.

The project also features a **Graphical User Interface (GUI)** for user-friendly interaction.

---

## Key Features

- **File Packing**
  - Combines multiple regular files into a **single archive file**.
  - Stores **metadata** (file name, size, timestamp) along with file content.

- **File Unpacking**
  - Extracts individual files from the packed archive.
  - Restores all **original metadata** and file structure.

- **Graphical User Interface (GUI)**
  - User-friendly GUI built in **Java Swing**.
  - Provides simple options for selecting directories/files, packing, and unpacking.

- **Cross-platform**
  - Runs seamlessly on any system with a **Java Runtime Environment (JRE)**.

---

## Future Scope
- Add **encryption & decryption** features to enhance data security.
- Integrate **compression** to reduce archive size (similar to ZIP).
- Improve GUI with **progress bars & logs** for better user experience.

---

## Example Usage (Console Flow)

```bash
# Compile all files
$ javac -d out src/PackerUnpacker/*.java src/gui/*.java

# Packing files (console version)
$ java -cp out PackerUnpacker.Packer

# Unpacking files (console version)
$ java -cp out PackerUnpacker.Unpacker

# Run Packer GUI
$ java -cp out gui.PackerGui

# Run Unpacker GUI
$ java -cp out gui.UnpackerGui
```

## Packer Image
<img width="1440" height="900" alt="Screenshot 2025-10-13 at 10 52 44 PM" src="https://github.com/user-attachments/assets/40d397bf-becd-45eb-8f59-9fbddcfd3bfa" />

## Unpacker Image
<img width="1440" height="900" alt="Screenshot 2025-10-13 at 10 53 23 PM" src="https://github.com/user-attachments/assets/0ed42c87-e7ca-4da3-9e7a-e2d2d32ca89f" />

