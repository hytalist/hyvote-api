# HyVote API

API for [HyVote](https://github.com/hytalist/hyvote) - Votifier implementation for Hytale servers.

Use this API to listen for vote events in your Hytale plugin.

## Installation

### Gradle (Kotlin DSL)

```kotlin
repositories {
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    compileOnly("com.github.hytalist:hyvote-api:1.0.0")
}
```

### Gradle (Groovy)

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    compileOnly 'com.github.hytalist:hyvote-api:1.0.0'
}
```

### Maven

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.hytalist</groupId>
        <artifactId>hyvote-api</artifactId>
        <version>1.0.0</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

## Usage

### Java

```java
import com.hytalist.hyvote.event.VoteReceivedEvent;
import com.hytalist.hyvote.model.Vote;

@Override
public void setup() {
    eventRegistry.registerGlobal(VoteReceivedEvent.class, event -> {
        Vote vote = event.getVote();
        String player = vote.getUsername();
        String service = vote.getServiceName();

        logger.info(player + " voted on " + service + "!");

        // Cancel to prevent HyVote's default rewards
        // event.setCancelled(true);
    });
}
```

### Kotlin

```kotlin
import com.hytalist.hyvote.event.VoteReceivedEvent

override fun setup() {
    eventRegistry.registerGlobal(VoteReceivedEvent::class.java) { event ->
        val vote = event.vote
        val player = vote.username
        val service = vote.serviceName

        logger.info("$player voted on $service!")

        // Cancel to prevent HyVote's default rewards
        // event.setCancelled(true)
    }
}
```

## API Reference

### VoteReceivedEvent

| Method | Return Type | Description |
|--------|-------------|-------------|
| `getVote()` | `Vote` | Get the vote data |
| `isCancelled()` | `boolean` | Check if event is cancelled |
| `setCancelled(boolean)` | `void` | Cancel to prevent default rewards |

### Vote

| Method | Return Type | Description |
|--------|-------------|-------------|
| `getUsername()` | `String` | Player who voted |
| `getServiceName()` | `String` | Voting service name |
| `getAddress()` | `String` | Voter's IP address |
| `getTimestamp()` | `String` | Vote timestamp |

## License

MIT License
