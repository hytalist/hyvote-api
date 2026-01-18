package com.hytalist.hyvote.model

/**
 * Represents a vote received from a voting service.
 *
 * Java usage:
 * ```java
 * Vote vote = event.getVote();
 * String player = vote.getUsername();
 * String service = vote.getServiceName();
 * ```
 */
data class Vote(
    /** The username of the player who voted */
    val username: String,
    /** The name of the voting service (e.g., "hytalist.com") */
    val serviceName: String,
    /** The IP address of the voter */
    val address: String,
    /** The timestamp of the vote */
    val timestamp: String
)
