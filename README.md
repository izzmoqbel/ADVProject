# ADVProject

#AD-project

package FileStorage: I make a classes 1-DropboxUploader this class help us to upload data to dropbox
by using dropbox api that upload the file and give me download link.
2-FileUploaderFactory class this class help us to use more storage in internet like googleDrive,droopbox,etc
and help us to achieve open-close producible.
3-FileUploader interface have upload file function .
4- ReadToken class help me to read the token was stored in env file to make the token secret.
5-UploaderType enum this have a type of uploader.

package ExportData: 1-DataExporter class this help me to call all data i want to export it
2-ExportActivityData class this have a function export data that help us to collect activity data
for specific user and write it in txt file and check if user new or not because if new user then he hasn't
activity data.
3-ExportIamData class this have a function export data that help us to collect iam data
for specific user and write it in txt file.
4-ExportPaymentData class this have a function export data that help us to collect payment data
for specific user and write it in txt file and check if user Premium or not because if  Premium user then he
has payment data other user they haven't.
5-ExportPostData class this have a function export data that help us to collect post data
for specific user and write it in txt file.
6-ExportStrategy class have a function collect data take id and path to store data.
7- CreateFolder this is a class help us to make a Folder with a path is a userId +"_data"

I make a function UserExists check if the user is existing or not
And make a class UserData is like a class check if the user is existing if yes call the dataExporter to export all data



Document Creation System
Overview:
This Java system generates text and PDF documents, including functionality for creating folders, reading text files, and producing PDFs based on user data.
Files:
CreatDocs.java:
Implements the Docs interface, creating PDF documents by reading user data from a text file.
CreateFolder.java:
Creates folders, ensuring the destination folder exists.
Docs.java:
Interface defining the creatDocs() method for document creation classes.
DocsFactory.java:
Acts as a factory for creating different types of documents using CreatDocs.
Executor.java:
Demonstrates usage by creating various document types iteratively.
TextFileReader.java:
Utility class to read the content of a text file.


Report on file compression: First, an overview of this feature. We created it in order to compress files in a flexible manner and to design the strategy to be in accordance with the required standards of flexibility, scalability, and good performance.
Secondly, how it works:
I created an interface called CompressionStrategy and put inside it the compressFolder function through which I will direct the project. I created the first strategy, which is specialized in creating compressed files of the zip type, and the second strategy of the rar type. These two strategies implement the CompressionStrategy interface, and they implement @Override for the compressFolder function, and there is External classes that support individual responsibility, such as createFolderIfNotExists, which checks whether a folder exists or not.
Input Archive Wizard:
The ArchiveEntryHandler class manages adding individual files to the archive. It uses the Apache Commons Compress library to create archive entries and write the file contents to the archive stream.
Finally, there is Logger
It records information about the success or failure of the operation



UserData.DeleteData Package:
Houses classes responsible for deleting user data.


UserDataDeletionTemplate.java:
Abstract class serving as a template for user data deletion.
Method deleteUserData(String userId) orchestrates the deletion process.


HardDelete.java:
Extends UserDataDeletionTemplate.
Implements hard delete logic for IAM data, user activities, payment data, and post data.
Checks user status (new, premium) before deletion.


SoftDelete.java:
Extends UserDataDeletionTemplate.
Implements soft delete logic for user activities, payment data, and post data.
No specific deletion for IAM data.
