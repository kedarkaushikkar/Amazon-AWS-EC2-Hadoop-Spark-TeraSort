package org.sort;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.io.*;
public class sharedsort {


//The function will create parts of the large input file.
//The blocksize for each part is the filelength/ numberoffiles.
public static List<File> CreateBatch(File file, Comparator<String> cmp) throws IOException {
		List<File> files = new ArrayList<File>();
		 int partCounter = 1;//I like to name parts from 001, 002, 003, ...



int NumberofFiles = numberoffiles(file);
int blocksize = (int) (file.length() / NumberofFiles);// in bytes

byte[] buffer = new byte[blocksize];

try (BufferedInputStream bis = new BufferedInputStream(
 new FileInputStream(file))) {
String name = file.getName();

int tmp = 0;
while ((tmp = bis.read(buffer)) > 0) {
 //write each chunk of data into separate file with different number in name
 File newFile = new File(file.getParent(), name + "." + partCounter++);
 newFile.deleteOnExit();
 try (FileOutputStream out = new FileOutputStream(newFile)) {
     out.write(buffer, 0, tmp);//tmp is chunk size
     files.add(newFile);
 }
}
}

	return files;
}

		public static List<File> sortFilesThreading(List<File> files, final Comparator<String> cmp) throws IOException, InterruptedException  {
		//Collections.sort(tmplist,cmp);  //
			final List<File> SortedFileList = new ArrayList<File>();
			Thread[] td = new Thread[1];
	        for (int i = 0; i < 1; i++) {
	            td[i] = new Thread(new Runnable() {

	                @SuppressWarnings("null")
					@Override

					public void run() {
	                    try {
	                    	file_semaphore.acquire();
								while(!global_list_of_file.isEmpty())
								{
									File FiletoSort = global_list_of_file.remove(0);
									  List<String> StringListtoSort =  new ArrayList<String>();
										File TempFile = File.createTempFile("sort", "file");
										TempFile.deleteOnExit();
										BufferedWriter fbw = new BufferedWriter(new FileWriter(TempFile));
										String line="";

											BufferedReader fbr = new BufferedReader(new FileReader(FiletoSort));
											try {

											while((line = fbr.readLine()) != null)
											{
												StringListtoSort.add(line);
											}
											Collections.sort(StringListtoSort,cmp);

											try {
												for(String r1 : StringListtoSort) {
													fbw.write(r1);
													fbw.newLine();
												}
											} finally {
												fbw.close();
											}
											}
										finally{
											fbr.close();
										}
											file_semaphore.release();
											list_semaphore.acquire();
						              SortedFileList.add(TempFile);
						              list_semaphore.release();

								}
							} catch (InterruptedException | IOException  ex) {
	                        System.out.println("error");
	                    }
	                }
	            });
	            td[i].start();
	        }
	        for (int i = 0; i < 1; i++) {
	            td[i].join();
	        }

	    	return SortedFileList;
		}



// This function will take up the file that is to be sorted as parameter and break the files by certain block size that will be sent to sort.
// All the temp files created will be sent to the mergeSortedFiles function that will be returned as the output.
// The time taken is the total time taken to sort the files and merge it.
		public static int numberoffiles(File filetobesorted) {
			long FileSize = filetobesorted.length();
			long SizeofTempFile = 10000000 ;
			int Number_of_Files =  (int) (FileSize / SizeofTempFile);
			return Number_of_Files;
		}

		private static List<File> global_list_of_file = new ArrayList<File>();

		  final static Semaphore file_semaphore = new Semaphore(1, true);
	      final static Semaphore list_semaphore = new Semaphore(1, true);

		public static void main(String[] args) throws IOException, InterruptedException {
			final Comparator<String> comparefile = new Comparator<String>() {
				public int compare(String r1, String r2){
					return r1.compareTo(r2);}};


					String InpFile = args[0];
					String OutFile = args[1];

			long start_time = System.currentTimeMillis( );
			List<File> TempFileList = CreateBatch(new File(InpFile), comparefile) ;
			global_list_of_file=TempFileList;
			List<File> TempFileSortedList = new ArrayList<File>();
			TempFileSortedList=sortFilesThreading(TempFileList, comparefile);
			mergeSortedFiles(TempFileSortedList, new File(OutFile), comparefile);
			long end_time = System.currentTimeMillis( );
	        long diff_time = end_time - start_time;
	        System.out.println("Time taken to sort the file : " + diff_time);
	    }
	}
	}
