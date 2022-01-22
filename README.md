## Table of contents

* [Random Drops](#random-drops)
  * [config.yml](#configyml)
  * [Commands](#commands)
  * [Permissions](#permissions)

------------
# Random Drops

This plugin changes the drops of blocks and entites to a random item. To complete the challenge you have to kill the enderdragon.

------------
###### config.yml
```yaml
prefix: "&6RandomDrops &8|"
failOnDeath: true

message:
  command:
    no-permission: "%prefix% &cInsufficient permissions."
    challenge:
      usage: "%prefix% &7Usage &e/challenge <start/stop/toggleFail>"
      already:
        running: "%prefix% &7The challenge is &ealready &7started."
        paused: "%prefix% &7The challenge is &ealready &7paused."
        finished: "%prefix% &7The challenge is &ealready &7finished."
      start: "%prefix% &7The challenge is now &estarted&7."
      pause: "%prefix% &7The challenge is now &epaused&7."
      toggleFail:
        enabled: "%prefix% &7The challenge will now &efail &7when a player &edies&7."
        disabled: "%prefix% &7The challenge will no longer &eends &7when a player &edies&7."
  failed:
    reason: "%prefix% &7%deathMessage%"
    message: "%prefix% &cThe challenge has failed!"
    duration: "%prefix% &7Time wasted: &e%duration%"
  completed:
    reason: "%prefix% &7The Ender Dragon was killed"
    message: "%prefix% &aThe challenge was successfully completed!"
    duration: "%prefix% &7Time needed: &e%duration%"
```
------------
###### Commands
    /challenge start - starts the challenge
    /challenge stop - pauses the challenge
    /challenge toggleFail - toggles whether you fail the challenge if you die

------------
###### Permissions
    /challenge - 'randomdrops.command.challenge'
    /challenge start - 'randomdrops.command.challenge.start'
    /challenge stop - 'randomdrops.command.challenge.stop'
    /challenge toggleFail - 'randomdrops.command.challenge.togglefail'
