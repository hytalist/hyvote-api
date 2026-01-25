# HyVote API

API for [HyVote](https://www.curseforge.com/hytale/mods/hyvote-votifier) - Votifier implementation for Hytale servers.

Use this API to listen for vote events and query vote data in your Hytale plugin.

## Installation

### Gradle (Kotlin DSL)

```kotlin
repositories {
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    compileOnly("com.github.hytalist:hyvote-api:1.6.0")
}
```

### Gradle (Groovy)

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    compileOnly 'com.github.hytalist:hyvote-api:1.6.0'
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
        <version>1.6.0</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

## Usage

### Listening to Vote Events

#### Java

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

#### Kotlin

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

### Querying Vote Data (since 1.6.0)

#### Java

```java
import com.hytalist.hyvote.api.HyVoteAPI;
import com.hytalist.hyvote.api.HyVoteAPI.VoterInfo;
import kotlin.Pair;
import java.util.List;
import java.util.UUID;

// Get the API instance
HyVoteAPI api = HyVoteAPI.get();

// Get vote counts
int totalVotes = api.getPlayerVotes(playerUuid);           // All-time total
int thisMonth = api.getPlayerVotesThisMonth(playerUuid);   // Current month
int lastMonth = api.getPlayerVotesPreviousMonth(playerUuid); // Previous month
int jan2025 = api.getPlayerVotesForMonth(playerUuid, 2025, 1); // Specific month

// Get player rankings (returns null if player hasn't voted)
Pair<Integer, Integer> rank = api.getPlayerRankAllTime(playerUuid);
if (rank != null) {
    int position = rank.getFirst();  // Rank position (1 = first place)
    int votes = rank.getSecond();    // Vote count
}

// Get top voters
List<VoterInfo> topVoters = api.getTopVotersThisMonth(10);
for (VoterInfo voter : topVoters) {
    UUID uuid = voter.getUuid();
    String username = voter.getUsername();
    int votes = voter.getVotes();
}

// Get last vote time (epoch milliseconds, null if never voted)
Long lastVoteTime = api.getLastVoteTime(playerUuid);
```

#### Kotlin

```kotlin
import com.hytalist.hyvote.api.HyVoteAPI

// Get the API instance
val api = HyVoteAPI.get()

// Get vote counts
val totalVotes = api.getPlayerVotes(playerUuid)           // All-time total
val thisMonth = api.getPlayerVotesThisMonth(playerUuid)   // Current month
val lastMonth = api.getPlayerVotesPreviousMonth(playerUuid) // Previous month
val jan2025 = api.getPlayerVotesForMonth(playerUuid, 2025, 1) // Specific month

// Get player rankings (returns null if player hasn't voted)
val rank = api.getPlayerRankAllTime(playerUuid)
if (rank != null) {
    val (position, votes) = rank  // Destructuring
}

// Get top voters
val topVoters = api.getTopVotersThisMonth(10)
for (voter in topVoters) {
    val uuid = voter.uuid
    val username = voter.username
    val votes = voter.votes
}

// Get last vote time (epoch milliseconds, null if never voted)
val lastVoteTime = api.getLastVoteTime(playerUuid)
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

### HyVoteAPI (since 1.6.0)

Get the API instance with `HyVoteAPI.get()`. Throws `IllegalStateException` if HyVote is not loaded.

#### Vote Counts

| Method | Return Type | Description |
|--------|-------------|-------------|
| `getPlayerVotes(UUID)` | `int` | Total votes all-time (0 if never voted) |
| `getPlayerVotesThisMonth(UUID)` | `int` | Votes this month (0 if none) |
| `getPlayerVotesPreviousMonth(UUID)` | `int` | Votes previous month (0 if none) |
| `getPlayerVotesForMonth(UUID, int, int)` | `int` | Votes for specific year/month (0 if none) |

#### Rankings

| Method | Return Type | Description |
|--------|-------------|-------------|
| `getPlayerRankAllTime(UUID)` | `Pair<Int, Int>?` | (rank, votes) or null |
| `getPlayerRankThisMonth(UUID)` | `Pair<Int, Int>?` | (rank, votes) or null |
| `getPlayerRankPreviousMonth(UUID)` | `Pair<Int, Int>?` | (rank, votes) or null |
| `getPlayerRankForMonth(UUID, int, int)` | `Pair<Int, Int>?` | (rank, votes) for year/month or null |

#### Top Voters

| Method | Return Type | Description |
|--------|-------------|-------------|
| `getTopVotersAllTime(int)` | `List<VoterInfo>` | Top voters all-time (limit param) |
| `getTopVotersThisMonth(int)` | `List<VoterInfo>` | Top voters this month |
| `getTopVotersPreviousMonth(int)` | `List<VoterInfo>` | Top voters previous month |
| `getTopVotersForMonth(int, int, int)` | `List<VoterInfo>` | Top voters for year/month/limit |

#### Utility

| Method | Return Type | Description |
|--------|-------------|-------------|
| `getLastVoteTime(UUID)` | `Long?` | Last vote epoch millis, null if never voted |

### VoterInfo (since 1.6.0)

| Property | Type | Description |
|----------|------|-------------|
| `uuid` | `UUID` | Player's UUID |
| `username` | `String` | Player's username (as of last vote) |
| `votes` | `int` | Number of votes |

## License

MIT License
