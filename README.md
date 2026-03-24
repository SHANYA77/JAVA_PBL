# JAVA_PBL
## Overview

The project implements a secure storage system that allows users to store, retrieve, and search encrypted files. A basic authentication mechanism is included, where user credentials are verified using hashed passwords. Files are stored locally, and all data is encrypted before being written to storage.This project is developed in Java. It allows users to register, log in, and manage their files securely. Each user can upload, download, delete, and search their own files. The system ensures data safety using encoding and maintains logs for all operations.

## Features

User Authentication (Register/Login)
File Upload (with encoding)
File Download (with decoding)
File Deletion
List User Files
Keyword-based File Search
User-specific file access (no file sharing between users)

## Project Structure

JAVA_PROJECT/
│
├── MainApp.java          → Main driver (UI + flow control)
├── AuthService.java      → Handles user authentication
├── CryptoService.java   → Handles encoding/decoding
├── StorageService.java  → Handles file storage operations
├── SearchService.java   → Implements search functionality
├── LoggerService.java   → Logs user activities
│
├── users.txt            → Stores user credentials
├── log.txt              → Stores logs
└── storage/             → Stores uploaded files

## Technologies Used

Java (Core Java)
File Handling (BufferedReader, BufferedWriter)
Base64 Encoding (for secure storage)
Data Structures (HashMap, ArrayList)

## Future Improvements

AES Encryption instead of Base64
GUI (JavaFX / Swing)
Cloud storage integration
File sharing between users
Database (MySQL) instead of text files
