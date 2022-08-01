# Polecenia do zadań:

## Lab01 (03.03.2022)

Napisz aplikację, która pozwoli na sprawdzania wskazanych katalogów pod kątem wystąpienia zmian w zawartych w nich plikach. 

Aplikacja powinna wyliczać skrót MD5 dla każdego badanego pliku w dwóch krokach: 1. przygotowując "snapshot" bieżącej sytuacji oraz 2. weryfikując na podstawie zapamiętanego "snapshotu", czy w plikach wprowadzono jakieś zmiany.

## Lab02 (10.03.2022)

Napisz aplikację, która umożliwi przeglądanie danych osobowych zapisanych na dysku. Zakładamy, że dane osobowe zapisywane będą w folderach o nazwach odpowiadających identyfikatorom osób, których dotyczą. Dokładniej, w folderach pojawiać się mają dwa pliki: record.txt (o dowolnej strukturze, w pliku tym zapisane mają być: imię, nazwisko, wiek, ....) oraz image.png (ze zdjęciem danej osoby, przy czym do celów testowych można zamiast zdjęcia użyć dowolnego obrazka).

Interfejs graficzny tej aplikacji można zrealizować za pomocą dwóch paneli - jednego, przeznaczonego na listę przeglądanych folderów oraz drugiego, służącego do wyświetlania danych osobowych i zdjęcia odpowiedniego do folderu wybranego z listy.

Aplikację należy zaprojektować z wykorzystaniem słabych referencji (ang. weak references). Zakładamy, że podczas przeglądania folderów zawartość plików tekstowych i  plików zawierających obrazki będzie ładowana do odpowiedniej struktury. Słabe referencje powinny pozwolić na ominięcie konieczności wielokrotnego ładowania tej samej zawartości - co może nastąpić podczas poruszanie się wprzód i wstecz po liście folderów.

Aplikacja powinna wskazywać, czy zawartość pliku została załadowana ponownie, czy też została pobrana z pamięci. Wskazanie to może być zrealizowane za pomocą jakiegoś znacznika prezentowanego na interfejsie.

## Lab03 (17.03.2022)

Napisz aplikację, która pozwoli skonsumować dane pozyskiwane z serwisu oferującego publiczne restowe API. Ciekawą listę serwisów można znaleźć pod adresem:
https://rapidapi.com/collection/list-of-free-apis (wymagają klucza API), czy też https://mixedanalytics.com/blog/list-actually-free-open-no-auth-needed-apis/ (te klucza API nie wymagają). W szczególności w tej drugiej grupie istnieje: https://developers.teleport.org/api/reference/. Właśnie to api ma być użyte w aplikacji.

    Bazując na nim należy zbudować intefejs użytkownika, który pozwoli na przeprowadzanie testów z wiedzy z geograficznej. Renderowanie zapytań i odpowiedzi powinno być tak zaimplementowane, by dało się zmianić ustawienia językowe (lokalizacji) w oparciu o tzw. bundle (definiowane w plikach i klasach - obie te opcje należy przetestować). Wspierane mają być języki: polski i angielski. 

## Lab04 (24.03.2022)

Napisz aplikację, która umożliwi zlecanie wykonywania zadań instancjom klas ładowanym własnym ładowaczem klas. Do realizacji tego ćwiczenia należy użyć Java Reflection API.

Napisz aplikację, która pozwoli skonsumować dane pozyskiwane z serwisu oferującego publiczne restowe API. Ciekawą listę serwisów można znaleźć pod adresem:
https://rapidapi.com/collection/list-of-free-apis (wymagają klucza API), czy też https://mixedanalytics.com/blog/list-actually-free-open-no-auth-needed-apis/ (te klucza API nie wymagają). W szczególności w tej drugiej grupie istnieje: https://developers.teleport.org/api/reference/. Właśnie to api ma być użyte w aplikacji.

    Bazując na nim należy zbudować intefejs użytkownika, który pozwoli na przeprowadzanie testów z wiedzy z geograficznej. Renderowanie zapytań i odpowiedzi powinno być tak zaimplementowane, by dało się zmianić ustawienia językowe (lokalizacji) w oparciu o tzw. bundle (definiowane w plikach i klasach - obie te opcje należy przetestować). Wspierane mają być języki: polski i angielski. 
	
	Tworzona aplikacja powinna udostępniać graficzny interfejs, na którym będzie można:
1. zdefiniować zadanie (zakładamy, że będzie można definiować "dowolne" zadania reprezentowane przez ciąg znaków),
2. załadować klasę wykonującą zadanie (zakładamy, że będzie można załadować więcej niż jedną taką klasę),
3. zlecić wykonanie wskazanego zadania wskazanej załadowanej klasie, monitorować przebieg wykonywania zadania, wyświetlić wynik zadania.
4. wyładować wybraną klasę z wcześniej załadowanych

N

ależy dostarczyć przynajmniej 3 różne klasy implementujące interfejs Processor. Każda taka klasa po załadowaniu powinna oznajmić, poprzez wynik metody getInfo(), jakiego typu zadanie obsługuje. Na przykład pozyskana informacja w postaci "sumowanie" oznaczałaby, że zadanie można zdefiniować ciągiem znaków "1 + 2", gdzie oczekiwanym wynikiem byłoby "3". Informacja "zamiana małych liter na duże" oznaczałaby, że dla zadania "abc" oczekiwanym wynikiem ma być "ABC".

Klasy ładowane powinny być skompilowane w innym projekcie niż sama użytkowa aplikacja (podczas budowania aplikacja nie powinna mieć dostępu do tych klas). Można założyć, że kod bajtowy tych klas będzie umieszczany w katalogu, do którego aplikacja będzie miała dostęp. Ścieżka do tego katalogu powinna być parametrem ustawianym w aplikacji w trakcie jej działania. Wartością domyślną dla ścieżki niech będzie katalog, w którym uruchomiono aplikację. Aplikacja powinna odczytać zawartość tego katalogu i załadować własnym ładowaczem odnalezione klasy. Zakładamy, że podczas działania aplikacji będzie można "dorzucić" nowe klasy do tego katalogu (należy więc pomyśleć o pewnego rodzaju "odświeżaniu").

Wybieranie klas (a tym samym algorytmów przetwarzania) powinno odbywać się poprzez listę wyświetlającą nazwy załadowanych klas. Nazwom tym niech towarzyszą opisy pozyskane metodą getInfo() z utworzonych instancji tych klas.

Zlecanie zadań instancjom klas powinno odbywać się poprzez metodę submitTask(String task, StatusListner sl).  W metodzie tej należy podać słuchacza typu StatusListener, który będzie otrzymywał informacje o zmianie statusu przetwarzania. Do reprezentacji statusu przetwarzania służy klasa Status (klasę tę zadeklarowano tak, aby po utworzeniu statusu jego atrybuty były tylko do odczytu). 

## Lab05 (31.03.2022)

Zaimplementuj aplikację z graficznym interfejsem pozwalającą przeprowadzić analizę statystyczną na danych tabelarycznych.
Analiza ta może polegać na: wyznaczeniu linii trendu, obliczeniu mediany, obliczeniu odchylenia standardowego. 

Aplikacja ta powinna umożliwiać:
- wyświetlanie/edytowanie danych tabelarycznych;
- wybranie algorytmu, jakim będą one przetwarzane (należy zailplementować przynajmniej 2 algorytmy analiz statystycznych);
- wyświetlenie wyników przetwarzania.
W trakcie implementacji należy wykorzystać interfejs dostarczyciela serwisu (ang. Service Provider Interface, SPI).
Dokładniej, stosując podejście SPI należy zapewnić aplikacji możliwość załadowania klas implementujących zadany interfejs
już po zbudowaniu samej aplikacji. 
Klasy te (z zaimplementowanymi wybranymi algorytmami analizy skupień) mają być dostarczane w plikach jar umieszczanych w ścieżce. 
Należy stworzyć dwie wersje projektu: standardową oraz modularną.

## Lab06 (08.04.2022)

Zaimplementuj rozproszony system imitujący działanie sieci tablic reklamowych, na których cyklicznie wyświetlane są zadane teksty (tj. przez określony czas widać jedno hasło reklamowe, po czym następuje zmiana).
Wymiana danych pomiędzy elementami systemu powinna odbywać się poprzez gniazda SSL

W systemie tym wyróżnione mają być trzy typy aplikacji (klas z metodą main):
* Manager (Menadżer) - odpowiedzialna za przyjmowanie od klientów zamówień wyświetlanie haseł reklamowych oraz przesyłanie tych haseł na tablice reklamowe
* Client (Klient) - odpowiedzialna za zgłaszanie menadżerowi zamówień lub ich wycofywanie
* Billboard (Tablica) - odpowiedzialna za wyświetlanie haseł, dowiązująca się do menadżera, który może zatrzymać i uruchomić wyświetlanie haseł

Podczas uruchomienia systemu należy utworzyć: 1 instancję Menadżera, przynajmniej 2 instancje Klienta, przynajmniej 3 instancje Tablicy. 

## Lab07 (13.04.2022)

Napisz program, który pozwoli zasymulować działanie narzędzia do obsługi finansów komitetu rodzicielskiego jakiejś szkolnej klasy. Główną funkcją tego narzędzia ma być obsługa przypadku zbierania funduszy na organizowane wydarzenia. Narzędzie może działać jako aplikacja desktopowa w trybie konsolowym, może też być zaimplementowane jako aplikacja z graficznym interfejsem (desktopowa lub internetowa, ale to wymagałoby więcej pracy).

Zadanie należy zrealizować wykorzystując relacyjną bazę danych.

Zakładamy, że w bazie danych będą przechowywane następujące informacje:
* Wydarzenie - identyfikator, nazwa, miejsce, termin
* Osoba - identyfikator, imię, nazwisko
* Raty - identyfikator, identyfikator wydarzenia, numer raty, termin płatności, kwota?
* Wpłaty - identyfikator, termin wpłaty, kwota wpłaty, identyfikator osoby, identyfikator wydarzenia, numer raty
 
Program powinien:
* umożliwiać ręczne wprowadzanie danych (osób, wydarzeń, rat, wpłat) oraz ładowanie danych z plików csv.
* umożliwiać przeglądanie danych (w szczególności przeglądanie należnych i dokonanych wpłat)
* automatycznie sprawdzać terminowość i wysokość wpłat oraz wysyłać monity o kolejnych płatnościach (wystarczy, że będzie pisał do pliku z logami monitów, upływ czasu należy zasymulować).
* automatycznie eskalować monity w przypadku braku terminowej wpłaty (wystarczy, że będzie pisał do pliku z logami ekalowanych monitów, upływ czasu należy zasymulować)

## Lab08 (21.04.2020)

Zaimplementuj serwis komunikujący się protokołem SOAP, który pozwoli wykorzystać logikę biznesową (z warstwą persystencji) zaprojektowaną w ramach lab07. Serwis ten ma udostępniać API pozwalające na zarządzanie wydarzeniami, osobami, ratami, wpłatami. Klientem serwisu może być narzędzie SoapUI lub Postman (nie trzeba samemu implementować żadnego klienta).

Podczas implementacji można wykorzystać dowolną metodę przetwarzania komunikatów SOAP.
Z uwagi na dużą automatyzację zalecane jest wykorzystanie JAX-WS/Apache CXF. Można wybrać podejście bottomUp lub topDown.
Zalecane jest oparcie budowanego rozwiązania na czymś, co pozwoli obsłużyć opis interfejsu w języku WSDL.

## Lab09 (28.04.2022)

Zaimplementuj aplikację pozwalającą na szyfrowanie/deszyfrowanie plików (taka aplikacja mogłaby pełnić rolę narzędzia służącego do szyfrowania/odszyfrowywania załączników do e-maili). 
Na interfejsie graficznym aplikacji użytkownik powinien mieć możliwość wskazania plików wejściowych oraz wyjściowych, jak również algorytmu szyfrowania/deszyfrowania oraz wykorzystywanych kluczy: prywatnego (do szyfrowania) i publicznego (do deszyfrowania).
Cała logika związana z szyfrowaniem/deszyfrowaniem powinna być dostarczona w osobnej bibliotece, spakowanej do podpisanego cyfrowo pliku jar.
Sama zaś aplikacja powinna również być wyeksportowana do wykonywalnego pliku jar podpisanego cyfrowo (i działać w niej ma menadżer bezpieczeństwa korzystający z dostarczonego pliku polityki).
Projekt opierać ma się na technogiach należących do Java Cryptography Architecture (JCA) i/lub Java Cryptography Extension (JCE).
Proszę zwrócić uwagę na ograniczenia związane z rozmiarem szyfrowanych danych narzuczane przez wybrane algorytmu (zależy nam, by zaszyfrować dało się pliki o dowolnym rozmiarze).
W trakcie realizacji laboratorium będzie trzeba skorzystać z repozytoriów kluczy i certyfikatów.


## lab10 (05.05.2022)

Bazując na kodzie stworzonym w trakcie poprzednich laboratoriów przygotuj: a) wielowydaniowy jar, b) instalator aplikacji. Opis czym jest wielowydaniowy jar pojawi się na wykładzie. Towarzyszące mu materiały znaleźć można na stronie poświęconej kursowi. Instalator aplikacji może być wykonany dowolnym narzędziem. Ponadto dla ciekawych polecane jest sprawdzenie, jak działa narzędzie jpackage. 

## Lab11 (12.05.2022)

Napisz program, w którym wykorzystane zostanie JNI. Zadanie do wykonania polegać ma na zadeklarowaniu klasy oferującej trzy metody natywne pozwalające posortować wskazane tablice obiektów typu Double. Schemat implementacji tej klasy powinien odpowiadać poniższemu wzorcowi.

class .....{
.....
public Double[] a;
public Double[] b;
public Boolean order;


public native Double[] sort01(Double[] a, Boolean order);
// zakładamy, że po stronie kodu natywnego będzie sortowana przekazana tablica a 
// (order=true oznacza rosnąco, order=false oznacza malejąco)
// metoda powinna zwrócić posortowaną tablicę
public native Double[] sort02(Double[] a); 
// zakładamy, że drugi atrybut będzie pobrany z obiektu przekazanego do metody natywnej (czyli będzie brana wartość pole order) 
public native void sort03();
// zakładamy, że po stronie natywnej utworzone zostanie okienko pozwalające zdefiniować zawartość tablicy do sortowania 
// oraz warunek określający sposób sortowania order.
// wczytana tablica powinna zostać przekazana do obiektu Javy na pole a, zaś warunek sortowania powinien zostać przekazany
// do pola orded
// Wynik sortowania (tablica b w obiekcie Java) powinna wyliczać metoda Javy multi04 
// (korzystająca z parametrów a i order, wstawiająca wynik do b).

public void sort04(){
 ..... // sortuje a według order, a wynik wpisuje do b
 }

}

## Lab12 (19.05.2022)

Celem laboratorium jest przetrenowanie możliwości dynamicznego rozszerzania funkcji programu Java przez ładowanie i wyładowywanie skryptów JavaScript (na podobieństwo ładowania klas własnym ładowaczem). Ponadto chodzi w nim o opanowanie technik przekazywania obiektów pomiędzy wirtualną maszyną Java a silnikiem JavaScript.

Rozwój silnika JavaScript, odbywający się w ramach rozwoju JDK, został w pewnym momencie zatrzymany przez Oracle. Stwierdzono bowiem, że zadanie to realizowane jest w projekcie GrallVM, a szkoda poświęcać zasoby na powielanie prac. Efekt podjętej wtedy decyzji widać w JDK 11, gdzie odpowiedni moduł dostarczający rzeczony silnik otagowano:
    @Deprecated(since="11", forRemoval=true)
    Module jdk.scripting.nashorn

Wraz z tym modułem z JDK z kolejnych wydań JDK zniknąć mają niektóre pomocnicze narzędzia, jak uruchamiane z linii komend interpreter jjs. Póki co można jednak wciąż korzystać z silnika JavaScript w JDK 11. Można też, alternatywnie, pobrać i przećwiczyć środowisko uruchomieniowe GrallVM (patrz https://www.graalvm.org/downloads/). 

GraalVM Community Edition wersja 22.1.0 (obecnie najnowsza) zbudowano na bazie OpenJDK 11.0.15 oraz OpenJDK 17.0.3. Odpowiednią paczkę oprogramowania znaleźć można na stronie: https://github.com/graalvm/graalvm-ce-builds/releases/tag/vm-22.1.0

Zadanie do wykonania polega na napisaniu programu w języku Java pozwalającego na wizualizację działania automatów komórkowych, przy czym logika działania tych automatów powinna być zaimplementowana za pomocą ładowanych dynamicznie skryptów JavaScript zapisanych w plikach o znanej lokalizacji. Nazwy plików ze skryptami powinny odpowiadać nazwom automatów - by było wiadomo, co robi ładowany skrypt. Załadowane skrypty powinno dać się wyładować.

## Lab13 (26.05.2022)

Znając zasady korzystania ze skryptów JS w programach napisanych w języku Java można pójść o krok dalej w zabawie z tymi technologiami. Zdobytą wiedzę można wykorzystać podczas budowy aplikacji z graficznym interfejsem użytkownika opierającym się na klasach JavaFX.

Zadanie polega na zaimplementowaniu aplikacji korzystającej z JavaFX w niestandardowy sposób. Ma to polegać na oskryptowaniu całej logiki w pliku fxml zamiast w kontrolerze napisanym w języku Java. W języku Java ma być zaimplementowane tylko ładowanie fxmla. Proszę spojrzeć na przykłady zamieszczone poniżej (Sample1.fxml, Sample1.java, application.css). 

Sama aplikacja ma pozwalać na generowanie zaproszeń na różnego rodzaju towarzyskie imprezy (parapetówki, andrzejki, sylwestra, rocznice ...) na podstawie spreparowanych wcześniej szablonów uzupełnianych niezbędnymi atrybutami (datą, miejscem, imieniem, ...). Wygenerowane zaproszenia mogą pojawiać się na interfejsie użytkownika jako tekst, który powinno dać się skopiować. Szablony powinny być zapisane w plikach konfiguracyjnych.

Do przemyślenia jest, w jaki sposób użytkownik ma przekazywać do aplikacji niezbędne atrybuty (liczba i typ atrybutów może zależeć od rodzaju szablonu).