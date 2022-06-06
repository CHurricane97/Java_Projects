javac -h . SortJava.java
javac Okno.java
g++ -c -I"%JAVA_HOME%"\include -I"%JAVA_HOME%"\include\win32 SortJava.cpp -o SortJava.o
g++ -shared -o native.dll SortJava.o -Wl,--add-stdcall-alias

