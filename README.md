# ChatFilter

**ChatFilter** is a Bukkit/Spigot Minecraft plugin that enhances server chat by filtering spam, excessive capitalization, unwanted links, offensive language, and unrecognized symbols, ensuring a positive and secure environment for all players.

## Features

- **Spam Detection:** Prevents rapid or duplicate messages.
- **Caps Lock Control:** Converts messages with excessive uppercase letters to lowercase.
- **Link Filtering:** Blocks unauthorized URLs and IP addresses, allowing only whitelisted entries.
- **Symbol Filtering:** Removes unwanted or unrecognized symbols from messages.
- **Insult Filtering:** Detects and censors offensive language.
- **Reloadable Configuration:** Update settings on-the-fly without restarting the server.

## Installation

1. **Prerequisites:**
   - Bukkit/Spigot compatible Minecraft server.
   - Java 8 or higher.

2. **Download:**
   - Get the latest `ChatFilter.jar`.

3. **Setup:**
   - Place `ChatFilter.jar` into your server's `plugins` directory.
   - Start the server to generate the `ChatFilter` folder and configuration files.

## Configuration

Customize the plugin by editing the `Chatfilter.yaml` located in `plugins/ChatFilter/`:

- **Whitelist Links/IPs:**
  ```yaml
  links:
    ips: "twitch.tv, google.com, 0.0.0.0"
  ```
- **Adjust Spam Settings:**
  - Modify `spamDelay` and duplication rules in the `SpamCheck` class as needed.
- **Customize Filters:**
  - Update insult lists and symbol patterns in their respective classes.

## Commands

- **Reload Configuration:**
  ```
  /chatfilter reload
  ```
  Reloads the `Chatfilter.yaml` without restarting the server.

## Usage

Once installed, ChatFilter automatically monitors and processes all chat messages:

- **Spam Prevention:** Warns and blocks players sending messages too quickly or duplicately.
- **Caps Control:** Converts overly capitalized messages to lowercase.
- **Link & IP Blocking:** Blocks non-whitelisted links/IPs and notifies the player.
- **Symbol Removal:** Strips unwanted symbols from messages.
- **Insult Censorship:** Censors offensive words to maintain respectful chat.

## Permissions

Integrate with your server’s permission system to manage access:

- Currently, no specific permissions are set. Extend as needed to control command access and filter exemptions.
