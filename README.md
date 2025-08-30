# FactionAddon

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/projects/jdk/21/)
[![Paper](https://img.shields.io/badge/Paper-1.21-blue.svg)](https://papermc.io/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

## 📋 Descrizione

**FactionAddon** è un plugin Minecraft che estende le funzionalità di chat per server con sistema di fazioni. Questo plugin è basato su **LPC (LuckPerms Chat)** e include funzionalità aggiuntive specifiche per server di fazioni.

> ⚠️ **Attenzione**: Questo plugin è attualmente **ancora in sviluppo**. Alcune funzionalità potrebbero non essere completamente implementate o potrebbero contenere bug.

## 🚀 Caratteristiche

### Funzionalità Principali
- **Sistema di Chat Avanzato**: Basato su LPC con supporto per LuckPerms
- **Visualizzazione Item in Chat**: Mostra item e inventari direttamente nella chat
- **Sistema di Permessi**: Integrazione completa con LuckPerms
- **Controllo CAPS**: Sistema automatico di rilevamento e gestione messaggi in maiuscolo
- **Webhook Discord**: Notifiche automatiche per lo staff
- **PlaceholderAPI**: Supporto completo per placeholders personalizzati

### Comandi Disponibili
- `/showitem` - Mostra un item nella chat
- `/showinv` - Mostra l'inventario nella chat  
- `/factionaddonreload` - Ricarica le configurazioni

### Permessi
- `lpc.colorcodes` - Permette l'uso di codici colore
- `lpc.rgbcodes` - Permette l'uso di codici RGB
- `staff.caps` - Esenzione dal controllo CAPS
- `staff.notify` - Riceve notifiche staff

## 📦 Installazione

### Requisiti
- **Java 21** o superiore
- **Paper 1.21** o superiore
- **LuckPerms 5.4** o superiore
- **PlaceholderAPI 2.11.6** o superiore

### Passi di Installazione
1. Scarica l'ultima versione del plugin
2. Posiziona il file `.jar` nella cartella `plugins/`
3. Riavvia il server
4. Configura il plugin secondo le tue esigenze

## ⚙️ Configurazione

Il plugin genera automaticamente un file `config.yml` con le seguenti opzioni:

```yaml
# Configurazione Webhook Discord
webhook:
  url: "YOUR_DISCORD_WEBHOOK_URL"

# Controllo CAPS
warnOnCaps: true
```

## 🔧 Sviluppo

### Tecnologie Utilizzate
- **Java 21**
- **Paper API**
- **LuckPerms API**
- **PlaceholderAPI**
- **MiniMessage** per la formattazione del testo
- **Lombok** per la riduzione del boilerplate

### Struttura del Progetto
```
src/main/java/com/minegolem/factionAddon/
├── commands/          # Comandi del plugin
├── listeners/         # Event listeners
├── utils/            # Utility classes
├── webhook/          # Sistema webhook Discord
├── FactionAddon.java # Classe principale
├── Logger.java       # Sistema di logging
└── ChatInventoryHolder.java
```

## 🤝 Contribuire

Questo progetto è aperto ai contributi! Se vuoi contribuire:

1. Fai un fork del repository
2. Crea un branch per la tua feature (`git checkout -b feature/AmazingFeature`)
3. Committa le tue modifiche (`git commit -m 'Add some AmazingFeature'`)
4. Pusha al branch (`git push origin feature/AmazingFeature`)
5. Apri una Pull Request

## 📝 Licenza

Questo progetto è distribuito sotto licenza MIT. Vedi il file `LICENSE` per maggiori dettagli.

## 🙏 Ringraziamenti

- **LPC (LuckPerms Chat)** - Plugin originale su cui è basato questo fork
- **LuckPerms Team** - Per l'ottimo sistema di permessi
- **PaperMC** - Per l'API di sviluppo
- **PlaceholderAPI** - Per il sistema di placeholders

## 📞 Supporto

Per supporto o domande:
- Apri una issue su GitHub
- Contatta l'autore: **WorkWolf_2**

---

**Nota**: Questo plugin è un fork di LPC e mantiene la compatibilità con le funzionalità originali aggiungendo nuove caratteristiche specifiche per server di fazioni.
