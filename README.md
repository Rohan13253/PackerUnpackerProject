# 📦 File Packer & Unpacker

> A Java-based file archiving utility that packs multiple files into a single binary archive and restores them on demand — with both a console interface and a Swing GUI.

---

## Table of Contents

- [Technology](#technology)
- [Project Overview](#project-overview)
- [Architecture](#architecture)
- [Package Structure](#package-structure)
- [Class Reference](#class-reference)
- [How It Works](#how-it-works)
  - [Packing](#packing)
  - [Unpacking](#unpacking)
- [Known Limitations & Bugs](#known-limitations--bugs)
- [Key Features](#key-features)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Compile](#compile)
  - [Run](#run)
- [Example Usage](#example-usage)
- [GUI Screenshots](#gui-screenshots)
- [Future Scope](#future-scope)
- [Author](#author)

---

## Technology

| Layer | Technology |
|---|---|
| Language | Java (JDK 8+) |
| GUI Framework | Java Swing (AWT + Swing) |
| I/O | Java `java.io` streams |
| Build | Manual `javac` / any standard Java IDE |

---

## Project Overview

**File Packer & Unpacker** is a lightweight, dependency-free Java utility that combines multiple files from a directory into a single binary archive file, and later extracts them back — preserving file names and sizes. Think of it as a minimalist, educational implementation of concepts behind tools like `tar`.

The project ships with:
- A **console-mode** entry point (`Packer.java`, `Unpacker.java`) for scripted or terminal usage.
- A **Swing GUI** (`PackerGui.java`, `UnpackerGui.java`) for point-and-click interaction.
- A **core engine** (`PackerX.java`, `UnpackerX.java`) that handles all I/O logic, fully decoupled from the interface layer.

---

## Architecture

```
User Interface Layer
┌─────────────────────┐    ┌───────────────────────┐
│   PackerGui.java    │    │   UnpackerGui.java    │
│   (Swing GUI)       │    │   (Swing GUI)         │
└────────┬────────────┘    └───────────┬───────────┘
         │                             │
Console Layer                          │
┌────────┴────────────┐    ┌───────────┴───────────┐
│   Packer.java       │    │   Unpacker.java        │
│   (CLI entry point) │    │   (CLI entry point)    │
└────────┬────────────┘    └───────────┬────────────┘
         │                             │
Core Engine Layer                      │
┌────────┴────────────┐    ┌───────────┴────────────┐
│   PackerX.java      │    │   UnpackerX.java        │
│   (Packing logic)   │    │   (Unpacking logic)     │
└─────────────────────┘    └────────────────────────┘
```

The core engine is completely decoupled from both the GUI and console layers — meaning you can drive it programmatically from any context.

---

## Package Structure

```
project-root/
│
├── src/
│   ├── PackerUnpacker/          # Core engine + console entry points
│   │   ├── PackerX.java         # Packing logic
│   │   ├── Packer.java          # Console entry point for packing
│   │   ├── UnpackerX.java       # Unpacking logic
│   │   └── Unpacker.java        # Console entry point for unpacking
│   │
│   └── gui/                     # Swing GUI layer
│       ├── PackerGui.java        # GUI for packing
│       └── UnpackerGui.java      # GUI for unpacking
│
├── test/                         # Test files / sample data
│   ├── file1.txt
│   ├── file2.txt
│   └── file3.txt
│
└── README.md
```

---

## Class Reference

### `PackerX` — `PackerUnpacker` package

| Member | Type | Description |
|---|---|---|
| `PackName` | `private String` | Path/name of the output packed file |
| `DirName` | `private String` | Path to the source directory |
| `PackerX(String packName, String dirName)` | Constructor | Initializes pack and directory names |
| `PackingActivity()` | `public boolean` | Traverses the directory, writes headers + file contents into the archive. Returns `true` on success. |

---

### `UnpackerX` — `PackerUnpacker` package

| Member | Type | Description |
|---|---|---|
| `PackName` | `private String` | Path/name of the packed archive file |
| `UnpackerX(String packName)` | Constructor | Initializes the archive name |
| `UnpackingActivity()` | `public void` | Reads headers and extracts each file to disk |

---

### `Packer` — `PackerUnpacker` package
Console-mode entry point. Prompts the user for a directory and output file name, then delegates to `PackerX`.

---

### `Unpacker` — `PackerUnpacker` package
Console-mode entry point. Prompts the user for an archive file name, then delegates to `UnpackerX`.

---

### `PackerGui` — `gui` package
Swing GUI. Renders two text fields (directory name, pack file name) and a **Pack** button that invokes `PackerX.PackingActivity()`.

---

### `UnpackerGui` — `gui` package
Swing GUI. Renders one text field (packed file name) and an **Unpack** button that invokes `UnpackerX.UnpackingActivity()`.

---

## How It Works

### Packing

Each file in the source directory is stored in the archive as:

```
┌─────────────────────────────────────────────────────┐
│  HEADER (exactly 100 bytes, space-padded)           │
│  Format:  "<filename> <filesize_in_bytes>           "│
├─────────────────────────────────────────────────────┤
│  FILE CONTENT (raw bytes, exactly <filesize> bytes) │
└─────────────────────────────────────────────────────┘
```

This pattern repeats for every file in the directory. The archive is a flat binary concatenation of `[header][content][header][content]...` blocks.

**Example — packing `file1.txt` (31 bytes) and `file2.txt` (58 bytes):**

```
[file1.txt 31             ][...31 bytes of content...][file2.txt 58             ][...58 bytes of content...]
|<-------- 100 bytes ----->|                           |<-------- 100 bytes ----->|
```

---

### Unpacking

The unpacker reads the archive in a loop:
1. Reads exactly **100 bytes** → parses filename and size from the header.
2. Reads exactly **`<size>`** bytes → writes them to a new file with the parsed filename.
3. Repeats until end-of-file.

---

## Known Limitations & Bugs

> These are documented here in the spirit of transparency and to guide future contributors.

| # | Issue | Severity | Location |
|---|---|---|---|
| 1 | **Subdirectories are not handled.** `fobj.listFiles()` returns subdirectories too; attempting to open one as a `FileInputStream` will throw an exception and silently abort packing via the `catch` block. | High | `PackerX.java` |
| 2 | **Filenames longer than ~90 characters will corrupt the archive.** The header is fixed at 100 bytes. A long filename + space + size digits can exceed 100 bytes, truncating data. | High | `PackerX.java` |
| 3 | **Silent failure on exception in `Packer`/`Unpacker`.** The `catch` blocks in both console entry points are empty — errors are swallowed with no user feedback. | Medium | `Packer.java`, `Unpacker.java` |
| 4 | **Files are extracted to the current working directory**, not to a user-specified output directory. There is no option to control extraction destination. | Medium | `UnpackerX.java` |
| 5 | **Existing packed file is not overwritten — `createNewFile()` returns `false`** if the file already exists, and packing silently fails. | Medium | `PackerX.java` |
| 6 | **No timestamp or permission metadata is preserved.** Only filename and size are stored in the header. | Low | `PackerX.java` |
| 7 | **`FileOutputStream` is never closed** in the unpacking loop if an exception occurs mid-extraction, risking resource leaks. | Low | `UnpackerX.java` |

---

## Key Features

- ✅ **File Packing** — Combines all files from a directory into a single binary archive.
- ✅ **File Unpacking** — Extracts files from the archive, restoring names and content.
- ✅ **Dual Interface** — Both a console CLI and a Swing GUI are available.
- ✅ **Zero Dependencies** — Pure Java standard library. No third-party JARs required.
- ✅ **Decoupled Architecture** — Core engine is independent of the UI layer, making it easy to extend or test.
- ✅ **Cross-Platform** — Runs on any OS with a JRE installed.

---

## Getting Started

### Prerequisites

- **Java Development Kit (JDK) 8 or higher**
- Verify with: `java -version` and `javac -version`

---

### Compile

From the project root:

```bash
# Create output directory
mkdir -p out

# Compile all source files
javac -d out src/PackerUnpacker/*.java src/gui/*.java
```

---

### Run

#### Console — Packer
```bash
java -cp out PackerUnpacker.Packer
```
You will be prompted to enter:
1. The **directory path** containing the files to pack.
2. The **output archive filename** (e.g., `archive.pak`).

#### Console — Unpacker
```bash
java -cp out PackerUnpacker.Unpacker
```
You will be prompted to enter:
1. The **archive filename** to unpack (e.g., `archive.pak`).

#### GUI — Packer
```bash
java -cp out gui.PackerGui
```

#### GUI — Unpacker
```bash
java -cp out gui.UnpackerGui
```

---

## Example Usage

**Scenario:** Pack the three test files into one archive, then unpack them.

```bash
# 1. Compile
javac -d out src/PackerUnpacker/*.java src/gui/*.java

# 2. Pack the test directory
java -cp out PackerUnpacker.Packer
# → Enter directory name: test
# → Enter pack file name: myarchive.pak

# 3. Unpack
java -cp out PackerUnpacker.Unpacker
# → Enter packed file name: myarchive.pak
# → file1.txt, file2.txt, file3.txt are restored in current directory
```

---

## GUI Screenshots

### Packer GUI
<img width="1440" height="900" alt="Packer GUI" src="https://github.com/user-attachments/assets/40d397bf-becd-45eb-8f59-9fbddcfd3bfa" />

### Unpacker GUI
<img width="1440" height="900" alt="Unpacker GUI" src="https://github.com/user-attachments/assets/0ed42c87-e7ca-4da3-9e7a-e2d2d32ca89f" />

---

## Future Scope

| Priority | Enhancement |
|---|---|
| 🔴 High | **Fix subdirectory handling** — skip or recursively pack subdirectories instead of crashing. |
| 🔴 High | **Use a dynamic header format** (e.g., length-prefixed or delimiter-based) to remove the 100-byte filename restriction. |
| 🟠 Medium | **Add compression** (e.g., GZIP via `java.util.zip`) to reduce archive size. |
| 🟠 Medium | **Add encryption** (e.g., AES via `javax.crypto`) for secure archives. |
| 🟠 Medium | **Allow extraction to a user-specified output directory** rather than always using the working directory. |
| 🟡 Low | **Progress bar in GUI** using `JProgressBar` for large directories. |
| 🟡 Low | **Preserve file timestamps and permissions** in the archive header. |
| 🟡 Low | **Unit tests** using JUnit to validate pack/unpack round-trips. |
| 🟡 Low | **Drag-and-drop support** in the GUI for selecting directories and archive files. |

---

## Author

**Rohan Murlidhar Pawar**
- Initial development: August 2025
- GUI layer: August 2025
