# GestioneMagazzino

Documentazione Fittizia e Fasulla

USE CASES

0) Carica dal file binario di cache la situazione precedente se esiste.
Se non esiste un file binario di cache mostra in lista tutti i prodotti
e nel pannello principali le informazioni del primo prodotto presente in lista.
(il prodotto nella lista viene visualizzato come quello attivo)

1) Visualizzazione prodotti
Lutente seleziona la categoria di prodotti che intende ricercare,
inserisce una parola chiave ed effettua la ricerca premendo invio.
Lapplicazione effettua una query con il db mysql e mostra il risultato
su una ListView

2) Visualizzazione informazioni prodotto
Lutente seleziona un prodotto dalla lista e vengono caricate da un db mysql
le informazioni che verranno mostrate nel pannello principale dellapp. 

3) Inserimento nuovo prodotto
Quando l-utente clicca sul pulsante per aggiungere una nuova Classe di prodotti
il pannello principale nasconde i pannelli di monitor e mostra solo quello
delle informazioni principali, che adesso risulta composto cosi
Label | TextView
Label | TextView
Label | Select (per selezionare una categoria)

4) Inserimento ordini
Aggiunge nuove istanze di un particolare prodotto. Dopo aver cliccato sul 
pulsante viene mostrata una Text Input Dialog e dopo aver confermato l-ordine 
viene aggiornata la view principale mostrando le nuove istanze con location 
Magazzino. 


CLASSI 

1) VIEW CONTROLLER:
GestioneMagazzino
SearchPanelController
ContentPanelController
ToolBarController

2) FUNZIONALI
Prodotto {Id, Nome, Categoria, Quantita, Ordini[]}
Ordine {IdProdotto, Cliente, Stato} 
Cliente {Nominativo}

3) ADAPTER (circa)
MySQLManager
XMLManager
BinManager
RemoteLoggerManager
