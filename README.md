# FactionAddon

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/projects/jdk/21/)
[![Paper](https://img.shields.io/badge/Paper-1.21-blue.svg)](https://papermc.io/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

## 📋 Description

**FactionAddon** is a Minecraft plugin that extends chat functionality for servers with faction systems. This plugin is based on **LPC (LuckPerms Chat)** and includes additional features specifically designed for faction servers.

> ⚠️ **Warning**: This plugin is currently **still in development**. Some features may not be fully implemented or may contain bugs.

## 🚀 Features

### Main Features
- **Advanced Chat System**: Based on LPC with LuckPerms support
- **Item Display in Chat**: Shows items and inventories directly in chat
- **Permission System**: Complete integration with LuckPerms
- **CAPS Control**: Automatic detection and management of uppercase messages
- **Discord Webhook**: Automatic staff notifications
- **PlaceholderAPI**: Complete support for custom placeholders

### Available Commands
- `/showitem` - Show an item in chat
- `/showinv` - Show inventory in chat  
- `/factionaddonreload` - Reload configurations

### Permissions
- `lpc.colorcodes` - Allows use of color codes
- `lpc.rgbcodes` - Allows use of RGB codes
- `staff.caps` - Exemption from CAPS control
- `staff.notify` - Receives staff notifications

## 📦 Installation

### Requirements
- **Java 21** or higher
- **Paper 1.21** or higher
- **LuckPerms 5.4** or higher
- **PlaceholderAPI 2.11.6** or higher

### Installation Steps
1. Download the latest version of the plugin
2. Place the `.jar` file in the `plugins/` folder
3. Restart the server
4. Configure the plugin according to your needs

## ⚙️ Configuration

The plugin automatically generates a `config.yml` file with the following options:

```yaml
# Discord Webhook Configuration
webhook:
  url: "YOUR_DISCORD_WEBHOOK_URL"

# CAPS Control
warnOnCaps: true
```

## 🔧 Development

### Technologies Used
- **Java 21**
- **Paper API**
- **LuckPerms API**
- **PlaceholderAPI**
- **MiniMessage** for text formatting
- **Lombok** for boilerplate reduction

### Project Structure
```
src/main/java/com/minegolem/factionAddon/
├── commands/          # Plugin commands
├── listeners/         # Event listeners
├── utils/            # Utility classes
├── webhook/          # Discord webhook system
├── FactionAddon.java # Main class
├── Logger.java       # Logging system
└── ChatInventoryHolder.java
```

## 🤝 Contributing

This project is open to contributions! If you want to contribute:

1. Fork the repository
2. Create a branch for your feature (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is distributed under the MIT license. See the `LICENSE` file for more details.

## 🙏 Acknowledgments

- **LPC (LuckPerms Chat)** - Original plugin on which this fork is based
- **LuckPerms Team** - For the excellent permission system
- **PaperMC** - For the development API
- **PlaceholderAPI** - For the placeholder system

## 📞 Support

For support or questions:
- Open an issue on GitHub
- Contact the author: **WorkWolf_2**

---

**Note**: This plugin is a fork of LPC and maintains compatibility with the original features while adding new characteristics specific to faction servers.
