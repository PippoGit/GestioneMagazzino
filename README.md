# GestioneMagazzino - Documentazione

##Idea di progetto
L’applicazione da sviluppare dovrà permettere la gestione di un magazzino di un’azienda che si occupa di installare materiale ai propri clienti. Per ciascun materiale è presente una categoria di prodotto.

L’applicazione dovrà permettere all’utilizzatore di visualizzare e cercare i materiali presenti nel database dell’azienda, permettendo di selezionare una particolare categoria (ad esempio “telefonia”).

Mostrare per ogni classe materiale (ad esempio un modello di centralino telefonico) informazioni riguardanti la disponibilità in magazzino. 

Infine per ogni istanza di materiale (un’istanza di un materiale non è altro che il prodotto fisico che viene poi installato ad un cliente o è presente in magazzino) monitorare lo stato (funzionante o danneggiato) e la posizione (se in magazzino o in dotazione ad un cliente).

Infine deve essere possibile registrare spostamenti o cambiamenti di stato per ogni singola istanza di materiale.

##Caso d’uso
Esempio di scenario di utilizzo:
*  L’Utente seleziona una categoria di materiale dal SelettoreCategoria
*  L’Utente inserisce un parametro di ricerca nella BarraRicerca e preme invio
*  L’Utente seleziona una classe di materiale dalla ListaMateriali (risultato della ricerca precedente)
*  Il Sistema visualizza la Descrizione, la Categoria e la Disponibilità
*  FOR EACH IstanzaMateriale presente per il materiale selezionato:
   *  Il Sistema visualizza il Cliente, lo Stato e il Codice Materiale nel Monitor stato e spostamento
*  IF l’Utente modifica il Cliente 
   *  Il Sistema registra una modifica
*  IF l’Utente modifica il Cliente 
   *  Il Sistema registra una modifica
   *  Il Sistema aggiorna la Disponibilità
   *  Il Sistema aggiorna il Grafico 
*  IF l’Utente preme Salva
   *  Il Sistema archivia i dati nel DB

##File di configurazione locale in XML
 * All’avvio il Sistema legge dal file di configurazione i seguenti dati:
 * Indirizzo IP del client, indirizzo IP e porta del server di log
 * Pathname Font
 * Pathname del file di stile in formato CSS
 * Numero di categorie di prodotto (per ciascuna categoria legge anche il nome e l’id utilizzato nel file di stile)
 * Numero massimo di Materiali da mostrare nelle query di ricerca.

##Cache locale degli input
Alla chiusura il Sistema salva su file binario tutti i dati relativi alla classe materiale che appare selezionata.
All’avvio il Sistema carica dal file binario i suddetti dati.

##Base di dati
Alla pressione del tasto Salva il Sistema archivia su base di dati:
Lo stato di tutte le istanze modificate per quella particolare Classe di materiale (nuovo stato, nuovo Cliente).

##File di log remoto in XML
* Il sistema invia una riga di log ad ogni evento di seguito:
* Avvio dell’applicazione
* Pressione di un pulsante
* Click sulla barra di ricerca
* Selezione di un elemento della ListView
* Modifica di una cella del Monitor stato e spostamento
* Termine dell’applicazione
