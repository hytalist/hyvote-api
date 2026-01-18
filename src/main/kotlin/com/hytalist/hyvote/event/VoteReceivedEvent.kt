package com.hytalist.hyvote.event

import com.hypixel.hytale.event.ICancellable
import com.hypixel.hytale.event.IEvent
import com.hytalist.hyvote.model.Vote

/**
 * Event fired when a vote is received from a voting service.
 *
 * Listen to this event to handle votes in your plugin:
 *
 * Kotlin:
 * ```kotlin
 * eventRegistry.registerGlobal(VoteReceivedEvent::class.java) { event ->
 *     val vote = event.vote
 *     println("${vote.username} voted on ${vote.serviceName}")
 *
 *     // Cancel to prevent default rewards
 *     event.setCancelled(true)
 * }
 * ```
 *
 * Java:
 * ```java
 * eventRegistry.registerGlobal(VoteReceivedEvent.class, event -> {
 *     Vote vote = event.getVote();
 *     System.out.println(vote.getUsername() + " voted on " + vote.getServiceName());
 *
 *     // Cancel to prevent HyVote's default rewards
 *     event.setCancelled(true);
 * });
 * ```
 */
class VoteReceivedEvent(
    /** The vote data */
    val vote: Vote
) : IEvent<Void>, ICancellable {

    private var cancelled: Boolean = false

    override fun isCancelled(): Boolean = cancelled

    override fun setCancelled(cancelled: Boolean) {
        this.cancelled = cancelled
    }
}
