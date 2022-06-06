#include "SortJava.h"
#include <iostream>
#include <algorithm>
#include <stdlib.h>
#include<windows.h>

using namespace std;


JNIEXPORT jobjectArray JNICALL Java_SortJava_sort01
  (JNIEnv *env, jobject obj, jobjectArray arr, jobject ord){//środowisko,kopia klasy,tabela,order
	
	jsize len = env->GetArrayLength(arr);

	double *tab = new double[len];

	jclass cDouble = env->FindClass("java/lang/Double");//pobieramy double z javy
	jclass cBool = env->FindClass("java/lang/Boolean");

	for(int i = 0; i < len; i++){
		jobject objDouble = env->GetObjectArrayElement(arr, i);//pobieramy jednego doubla z javy tab[i]
		jmethodID doubleValue = env->GetMethodID(cDouble, "doubleValue", "()D");//id metody z javy doublevalue D-double
		tab[i] = env->CallDoubleMethod(objDouble, doubleValue);
	}

	jmethodID boolValue = env->GetMethodID(cBool, "booleanValue", "()Z");//Z-boolean id booleanvalue

	bool order = env->CallBooleanMethod(ord, boolValue);

	if(order) sort(tab, tab + len);
	else sort(tab, tab + len, greater<>());

	jmethodID cid = env->GetMethodID(cDouble, "<init>", "(D)V"); //konstruktor doubla (Double d = new Double) V-void <init>- konstruktor

	for(int i = 0; i < len; i++){
		jobject result = env->NewObject(cDouble, cid, tab[i]); //zmieniamy double z c na double z javy
		env->SetObjectArrayElement(arr, i, result);  //nadpisujemy tablice wejściową
	}
    delete [] tab;
	return arr; //zwracamy tablice
  }

  JNIEXPORT jobjectArray JNICALL Java_SortJava_sort02
  (JNIEnv *env, jobject obj, jobjectArray arr){

		jclass SortJava = env->GetObjectClass(obj);//pobieramy klase własną
		jfieldID orderField = env->GetFieldID(SortJava, "order", "Ljava/lang/Boolean;");//pobieramy z klasy order
		jobject ord = env->GetObjectField(obj, orderField);//pobieramy order
//reszta praktycznie tak samo
		jclass cBool = env->FindClass("java/lang/Boolean");
		jmethodID boolValue = env->GetMethodID(cBool, "booleanValue", "()Z");
		bool order = env->CallBooleanMethod(ord, boolValue);

		jsize len = env->GetArrayLength(arr);

		double *tab = new double[len];

		jclass cDouble = env->FindClass("java/lang/Double");

		for(int i = 0; i < len; i++){
			jobject objDouble = env->GetObjectArrayElement(arr, i);
			jmethodID doubleValue = env->GetMethodID(cDouble, "doubleValue", "()D");
			tab[i] = env->CallDoubleMethod(objDouble, doubleValue);
		}

		if(order) sort(tab, tab + len);
		else sort(tab, tab + len, greater<>());

		jmethodID cid = env->GetMethodID(cDouble, "<init>", "(D)V");

		for(int i = 0; i < len; i++){
			jobject result = env->NewObject(cDouble, cid, tab[i]);
			env->SetObjectArrayElement(arr, i, result);
		}
        delete [] tab;
		return arr;
  }

  JNIEXPORT void JNICALL Java_SortJava_sort03
  (JNIEnv *env, jobject obj){

		jclass SortJava = env->GetObjectClass(obj); //wszytujemy klase

		jfieldID aField = env->GetFieldID(SortJava, "a", "[Ljava/lang/Double;"); //bierzemy id a z javasort
		jfieldID orderField = env->GetFieldID(SortJava, "order", "Ljava/lang/Boolean;"); //bierzemy id orderu

		jclass cBoolean = env->FindClass("java/lang/Boolean");
		jmethodID cidB = env->GetMethodID(cBoolean, "<init>", "(Z)V");


		jclass guiClass = env->FindClass("LOkno;");//obiekt klasy okno
		jmethodID guiConstructor = env->GetMethodID(guiClass, "<init>", "()V"); //konstruktor okna
		jobject guiObject = env->NewObject(guiClass, guiConstructor); //tworzymy okno

		jfieldID setedFieldId = env->GetFieldID(guiClass, "Work", "Z");// bierzemy id work z okna
		bool Work = env->GetBooleanField(guiObject, setedFieldId);//pobranie work z klasy

		while(!Work){//czekamy na podanie zmiennych
			Work = env->GetBooleanField(guiObject, setedFieldId);
			Sleep(1000);
		}

		jfieldID gaField = env->GetFieldID(guiClass, "a", "[Ljava/lang/Double;"); //pobieramy id wypełnionego a z okna
		jobject ga = env->GetObjectField(guiObject, gaField); //pobieramy a

		jfieldID orrField = env->GetFieldID(guiClass, "order", "Z"); //pobieramy id orderu z okna
		bool ord = env->GetBooleanField(guiObject, orrField); //pobieramy order z okna

		jmethodID disp = env->GetMethodID(guiClass, "dispose", "()V"); //zamknięcie okna
		env->CallVoidMethod(guiObject, disp);
		
		jobject resultBool = env->NewObject(cBoolean, cidB, ord); //ustawimy order
		env->SetObjectField(obj, orderField, resultBool);

		env->SetObjectField(obj, aField, ga);// ustawiamy a z okienka

		jmethodID mid = env->GetMethodID(SortJava, "sort04", "()V"); //wywołanie sort04
		env->CallVoidMethod(obj, mid);
  }